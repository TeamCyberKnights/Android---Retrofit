package com.qdemy;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.qdemy.clase.Profesor;
import com.qdemy.clase.ProfesorDao;
import com.qdemy.clase.Test;
import com.qdemy.clase.TestPartajat;
import com.qdemy.clase.TestPartajatDao;
import com.qdemy.clase_adapter.PartajareTestAdapter;
import com.qdemy.db.App;
import com.qdemy.servicii.NetworkConnectionService;
import com.qdemy.servicii.ProfesorService;
import com.qdemy.servicii.ServiceBuilder;
import com.qdemy.servicii.TestPartajatService;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PartajeazaTestActivity extends AppCompatActivity {

    private ImageView inapoi;
    private TextView nume;
    private Button partajeaza;
    private Spinner profesoriSpinner;
    private ListView profesoriList;
    private List<String> numeProfesori = new ArrayList<>();
    private List<Profesor> profesoriPartajati = new ArrayList<>();
    private ArrayAdapter<String> adapterProfesori;
    private PartajareTestAdapter adapterProfesoriPartajati;

    private Test test;
    private Profesor profesorAutor;

    private List<Profesor> profesori;
    private List<TestPartajat> testePartajate;
    private Profesor prof;
    private TestPartajat testPartajat;
    private Profesor prof2;
    private boolean internetIsAvailable;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partajeaza_test);
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if ( activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting()) {
            internetIsAvailable = true;
        }
        initializare();
    }

    private void initializare() {

        inapoi = findViewById(R.id.back_image_partajeazaTest);
        nume = findViewById(R.id.titlul_text_partajeazaTest);
        partajeaza = findViewById(R.id.partajeaza_button_partajeazaTest);
        profesoriSpinner = findViewById(R.id.persoane_spinner_partajeazaTest);
        profesoriList = findViewById(R.id.persoane_list_partajeazaTest);

        profesorAutor = ((App) getApplication()).getProfesor();
        test =  ((App) getApplication()).getTest(getIntent().getLongExtra(Constante.CHEIE_TRANSFER, -1));
        nume.setText(test.getNume());


        if (internetIsAvailable) {
            final ProfesorService profesorService = ServiceBuilder.buildService(ProfesorService.class);
            Call<List<Profesor>> profesoriRequest = profesorService.getProfesori();

            profesoriRequest.enqueue(new Callback<List<Profesor>>() {
                @Override
                public void onResponse(Call<List<Profesor>> call, Response<List<Profesor>> response) {
                    profesori = response.body();
                }

                @Override
                public void onFailure(Call<List<Profesor>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Nu s-au putut gasi profesori", Toast.LENGTH_SHORT).show();
                }
            });

            for ( Profesor profesor : profesori) {
                if(!(profesor.equals(profesorAutor))) numeProfesori.add(profesor.getUtilizator());
            }
            adapterProfesori = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, numeProfesori);
            adapterProfesori.setDropDownViewResource(R.layout.item_spinner);
            profesoriSpinner.setAdapter(adapterProfesori);

            final TestPartajatService testPartajatService = ServiceBuilder.buildService(TestPartajatService.class);
            Call<List<TestPartajat>> testePartajateRequest = testPartajatService.getTestePartajatByTestId((int) (long)test.getId());

            testePartajateRequest.enqueue(new Callback<List<TestPartajat>>() {
                @Override
                public void onResponse(Call<List<TestPartajat>> call, Response<List<TestPartajat>> response) {
                    testePartajate = response.body();
                }

                @Override
                public void onFailure(Call<List<TestPartajat>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Nu s-au putut gasi teste partajate", Toast.LENGTH_SHORT).show();
                }
            });


            for ( TestPartajat testPartajat : testePartajate) {
                Call<Profesor> profesorRequest = profesorService.getProfesorById((int)testPartajat.getProfesorId());
                profesorRequest.enqueue(new Callback<Profesor>() {
                    @Override
                    public void onResponse(Call<Profesor> call, Response<Profesor> response) {
                        prof = response.body();
                    }

                    @Override
                    public void onFailure(Call<Profesor> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Nu s-a putut gasi profesorul", Toast.LENGTH_SHORT).show();
                    }
                });

                profesoriPartajati.add(prof);
                numeProfesori.remove(prof.getUtilizator());
            }
            adapterProfesoriPartajati = new PartajareTestAdapter(getApplicationContext(),
                    R.layout.item_text_button, profesoriPartajati, getLayoutInflater(), PartajeazaTestActivity.this);
            profesoriList.setAdapter(adapterProfesoriPartajati);
            adapterProfesori.notifyDataSetChanged();

            inapoi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            partajeaza.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Call<Profesor> profesorRequest = profesorService.getProfesorByUtilizator(profesoriSpinner.getSelectedItem().toString());

                    profesorRequest.enqueue(new Callback<Profesor>() {
                        @Override
                        public void onResponse(Call<Profesor> call, Response<Profesor> response) {
                            prof = response.body();
                        }

                        @Override
                        public void onFailure(Call<Profesor> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Nu s-au putut gasi profesorul", Toast.LENGTH_SHORT).show();
                        }
                    });

                    profesoriPartajati.add(prof);
                    numeProfesori.remove(prof.getUtilizator());
                    adapterProfesori.notifyDataSetChanged();
                    adapterProfesoriPartajati.notifyDataSetChanged();


                    Call<TestPartajat> insertTestPartajatRequest = testPartajatService.saveTestPartajat( new TestPartajat(test.getId(), prof.getId()));
                    insertTestPartajatRequest.enqueue(new Callback<TestPartajat>() {
                        @Override
                        public void onResponse(Call<TestPartajat> call, Response<TestPartajat> response) {
                            Toast.makeText(getApplicationContext(), "Testul partajat a fost salvat cu succes", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<TestPartajat> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Testul partajat nu a putut fi salvat", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

        else {
            Query<Profesor> queryProfesori = ((App) getApplication()).getDaoSession().getProfesorDao().queryBuilder().build();

            for (Profesor profesor : queryProfesori.list()) {
                if (!(profesor.equals(profesorAutor))) numeProfesori.add(profesor.getUtilizator());
            }
            adapterProfesori = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, numeProfesori);
            adapterProfesori.setDropDownViewResource(R.layout.item_spinner);
            profesoriSpinner.setAdapter(adapterProfesori);

            Query<TestPartajat> queryTestPartajat = ((App) getApplication()).getDaoSession().getTestPartajatDao().queryBuilder().where(
                    TestPartajatDao.Properties.TestId.eq(test.getId())).build();
            for (TestPartajat testPartajat : queryTestPartajat.list()) {
                Query<Profesor> queryProfesor = ((App) getApplication()).getDaoSession().getProfesorDao().queryBuilder().where(
                        ProfesorDao.Properties.Id.eq(testPartajat.getProfesorId())).build();
                profesoriPartajati.add(queryProfesor.list().get(0));
                numeProfesori.remove(queryProfesor.list().get(0).getUtilizator());
            }
            adapterProfesoriPartajati = new PartajareTestAdapter(getApplicationContext(),
                    R.layout.item_text_button, profesoriPartajati, getLayoutInflater(), PartajeazaTestActivity.this);
            profesoriList.setAdapter(adapterProfesoriPartajati);
            adapterProfesori.notifyDataSetChanged();


            inapoi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            partajeaza.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Query<Profesor> queryProfesor = ((App) getApplication()).getDaoSession().getProfesorDao().queryBuilder().where(
                            ProfesorDao.Properties.Utilizator.eq(profesoriSpinner.getSelectedItem().toString())).build();
                    profesoriPartajati.add(queryProfesor.list().get(0));
                    numeProfesori.remove(queryProfesor.list().get(0).getUtilizator());
                    adapterProfesori.notifyDataSetChanged();
                    adapterProfesoriPartajati.notifyDataSetChanged();


                    ((App) getApplication()).getDaoSession().getTestPartajatDao().insert(
                            new TestPartajat(test.getId(), queryProfesor.list().get(0).getId()));
                }
            });
        }
    }

    public void stergeTest(Profesor profesor)
    {


        if (internetIsAvailable) {
            TestPartajatService testPartajatService = ServiceBuilder.buildService(TestPartajatService.class);
            Call<TestPartajat> testPartajatRequest = testPartajatService.getTestPartajatByTestIdAndProfesorId((int) (long)test.getId(), (int) (long)profesor.getId());
            testPartajatRequest.enqueue(new Callback<TestPartajat>() {
                @Override
                public void onResponse(Call<TestPartajat> call, Response<TestPartajat> response) {
                    testPartajat = response.body();
                }

                @Override
                public void onFailure(Call<TestPartajat> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Nu s-a putut gasi testul partajat", Toast.LENGTH_SHORT).show();
                }
            });

            ProfesorService profesorService = ServiceBuilder.buildService(ProfesorService.class);
            Call<Profesor> profesorRequest = profesorService.getProfesorById((int)testPartajat.getProfesorId());
            profesorRequest.enqueue(new Callback<Profesor>() {
                @Override
                public void onResponse(Call<Profesor> call, Response<Profesor> response) {
                    prof2 = response.body();
                }

                @Override
                public void onFailure(Call<Profesor> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Nu s-a putut gasi profesorul", Toast.LENGTH_SHORT).show();
                }
            });
            Call<TestPartajat> deleteTestPartajatRequest = testPartajatService.deleteTestPartajatById((int) (long)testPartajat.getId());
            deleteTestPartajatRequest.enqueue(new Callback<TestPartajat>() {
                @Override
                public void onResponse(Call<TestPartajat> call, Response<TestPartajat> response) {
                    Toast.makeText(getApplicationContext(), "Testul partajat a fost sters cu succes", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<TestPartajat> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Testul partajat nu a putut fi sters", Toast.LENGTH_SHORT).show();
                }
            });
        }

        Query<TestPartajat> queryTest = ((App) getApplication()).getDaoSession().getTestPartajatDao().queryBuilder().where(
                TestPartajatDao.Properties.ProfesorId.eq(profesor.getId()),
                TestPartajatDao.Properties.TestId.eq(test.getId())).build();


        Query<Profesor> queryProfesor = ((App) getApplication()).getDaoSession().getProfesorDao().queryBuilder().where(
                ProfesorDao.Properties.Id.eq(queryTest.list().get(0).getProfesorId())).build();

        ((App) getApplication()).getDaoSession().getTestPartajatDao().deleteByKey(queryTest.list().get(0).getId());

        profesoriPartajati.remove(queryProfesor.list().get(0));
        numeProfesori.add(queryProfesor.list().get(0).getUtilizator());
        adapterProfesori.notifyDataSetChanged();
        adapterProfesoriPartajati.notifyDataSetChanged();
    }
}
