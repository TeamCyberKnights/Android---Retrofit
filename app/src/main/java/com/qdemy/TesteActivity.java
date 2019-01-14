package com.qdemy;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.qdemy.clase.EvidentaIntrebariTeste;
import com.qdemy.clase.EvidentaIntrebariTesteDao;
import com.qdemy.clase.EvidentaMateriiProfesori;
import com.qdemy.clase.EvidentaMateriiProfesoriDao;
import com.qdemy.clase.IntrebareGrila;
import com.qdemy.clase.IntrebareGrilaDao;
import com.qdemy.clase.Profesor;
import com.qdemy.clase.Test;
import com.qdemy.clase.TestDao;
import com.qdemy.clase.TestPartajat;
import com.qdemy.clase.TestPartajatDao;
import com.qdemy.clase.VariantaRaspuns;
import com.qdemy.clase.VariantaRaspunsDao;
import com.qdemy.clase_adapter.IntrebareAdapter;
import com.qdemy.clase_adapter.TestAdapter;
import com.qdemy.db.App;
import com.qdemy.servicii.EvidentaIntrebariTesteService;
import com.qdemy.servicii.NetworkConnectionService;
import com.qdemy.servicii.ProfesorService;
import com.qdemy.servicii.ServiceBuilder;
import com.qdemy.servicii.TestPartajatService;
import com.qdemy.servicii.TestService;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TesteActivity extends AppCompatActivity {

    private ImageView inapoi;
    private ImageView acasa;
    private ImageView istoric;
    private ImageView intrebari;
    private ImageView testeleMele;
    private FloatingActionButton adauga;
    private ListView testeList;
    private String materie;
    private TextView materieTitlu;
    private SearchView cautaTest;
    private TestAdapter adapter;
    private List<Test> teste = new ArrayList<>();
    private List<TestPartajat> testePartajate = new ArrayList<>();
    private Test test;
    private Profesor profesor;
    private List<EvidentaIntrebariTeste> evidentaIntrebariTeste;
    private TestPartajat testPartajat;
    private List<Test> testeAux;
    private List<Test> testeAux2;
    private boolean internetIsAvailable;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);
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

        //region Intializare componenete vizuale
        inapoi = findViewById(R.id.back_image_teste);
        testeleMele = findViewById(R.id.teste_image_teste);
        acasa = findViewById(R.id.acasa_image_teste);
        istoric = findViewById(R.id.istoric_image_teste);
        intrebari = findViewById(R.id.intrebari_image_teste);
        adauga = findViewById(R.id.adauga_button_teste);
        testeList = findViewById(R.id.teste_list_teste);
        cautaTest = findViewById(R.id.search_teste);
        int id = cautaTest.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchEditText = (EditText) cautaTest.findViewById(id);
        searchEditText.setTextColor(Color.WHITE);
        searchEditText.setHintTextColor(getResources().getColor(R.color.gri));
        materieTitlu = findViewById(R.id.titlu_text_teste);
        //endregion

        //region Initializare intrebari

            profesor = ((App) getApplication()).getProfesor();


        materie = getIntent().getStringExtra(Constante.CHEIE_TRANSFER);
        materieTitlu.setText(materie);

        //incarcare teste proprii si partajate
        if (internetIsAvailable) {
            TestService testService = ServiceBuilder.buildService(TestService.class);
            Call<List<Test>> testeRequest = testService.getTesteByProfesorId((int)(long)profesor.getId());
            testeRequest.enqueue(new Callback<List<Test>>() {
                @Override
                public void onResponse(Call<List<Test>> call, Response<List<Test>> response) {
                    testeAux = response.body();
                }

                @Override
                public void onFailure(Call<List<Test>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Nu s-au putut gasi teste", Toast.LENGTH_SHORT).show();
                }
            });
            for(int i=0;i<testeAux.size();i++)
                if(testeAux.get(i).getMaterie().equals(materie))
                    teste.add(testeAux.get(i));
        } else {
            for (int i = 0; i < profesor.getTeste().size(); i++)
                if (profesor.getTeste(i).getMaterie().equals(materie))
                    teste.add(profesor.getTeste(i));
        }

        if (internetIsAvailable) {
            TestPartajatService testPartajatService = ServiceBuilder.buildService(TestPartajatService.class);
            Call<List<TestPartajat>> testePartajateRequest = testPartajatService.getTestePartajateByProfesorId((int) (long)profesor.getId());
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
            TestService testService = ServiceBuilder.buildService(TestService.class);

            for(int i=0;i<testePartajate.size();i++) {
                Call<Test> testRequest = testService.getTestById((int)testePartajate.get(i).getTestId());
                testRequest.enqueue(new Callback<Test>() {
                    @Override
                    public void onResponse(Call<Test> call, Response<Test> response) {
                        test = response.body();
                    }

                    @Override
                    public void onFailure(Call<Test> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Nu s-a putut gasi testul", Toast.LENGTH_SHORT).show();
                    }
                });

                if (test.getMaterie().equals(materie))
                    teste.add(test);
            }


            adapter = new TestAdapter(getApplicationContext(), R.layout.item_text_button_button,
                    teste, getLayoutInflater(), true, TesteActivity.this, profesor.getId());
            testeList.setAdapter(adapter);

        } else {
            Query<TestPartajat> queryTestePartajate = ((App) getApplication()).getDaoSession().getTestPartajatDao().queryBuilder().where(
                    TestPartajatDao.Properties.ProfesorId.eq(profesor.getId())).build();
            for(int i=0;i<queryTestePartajate.list().size();i++) {
                Query<Test> queryTest = ((App) getApplication()).getDaoSession().getTestDao().queryBuilder().where(
                        TestDao.Properties.Id.eq(queryTestePartajate.list().get(i).getTestId())).build();
                if (queryTest.list().get(0).getMaterie().equals(materie))
                    teste.add(queryTest.list().get(0));
            }


            adapter = new TestAdapter(getApplicationContext(), R.layout.item_text_button_button,
                    teste, getLayoutInflater(), true, TesteActivity.this, profesor.getId());
            testeList.setAdapter(adapter);

        }
        //endregion

        adauga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdaugaTestActivity.class);
                intent.putExtra(Constante.CHEIE_TRANSFER, materie);
                startActivity(intent);
                finish();
            }
        });

        cautaTest.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)){
                    adapter.filter("");
                    testeList.clearTextFilter();
                }
                else {
                    adapter.filter(newText);
                }
                return true;
            }
        });

        //region Meniu

        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(v.getContext());
                dlgAlert.setMessage(R.string.deconectare_message);
                dlgAlert.setTitle(R.string.deconectare_title);
                dlgAlert.setPositiveButton(R.string.da, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                dlgAlert.setNegativeButton(R.string.nu, null);
                dlgAlert.setCancelable(true);
                AlertDialog dialog = dlgAlert.create();
                dialog.show();
                dialog.getWindow().setBackgroundDrawableResource(R.color.bej);
            }
        });

        acasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfesorActivity.class);
                startActivity(intent);
                finish();
            }
        });

        istoric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IstoricProfesorActivity.class);
                startActivity(intent);
                finish();
            }
        });


        intrebari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MateriiActivity.class);
                intent.putExtra(Constante.CHEIE_TRANSFER, getString(R.string.ntreb_rile_mele));
                startActivity(intent);
                finish();
            }
        });

        testeleMele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MateriiActivity.class);
                intent.putExtra(Constante.CHEIE_TRANSFER, getString(R.string.testele_mele));
                startActivity(intent);
                finish();
            }
        });

        //endregion

    }


    public void stergeTest(String numeTest)
    {

        // SQLLITE
        Query<Test> queryTest = ((App) getApplication()).getDaoSession().getTestDao().queryBuilder().where(
                TestDao.Properties.Nume.eq(numeTest),
                TestDao.Properties.Materie.eq(materie),
                TestDao.Properties.ProfesorId.eq(profesor.getId())).build();

        Query<EvidentaIntrebariTeste> queryEvidenta = ((App) getApplication()).getDaoSession().getEvidentaIntrebariTesteDao()
                .queryBuilder().where(EvidentaIntrebariTesteDao.Properties.TestId.eq(queryTest.list().get(0).getId())).build();

        for ( EvidentaIntrebariTeste evidenta : queryEvidenta.list()) {
            ((App) getApplication()).getDaoSession().getEvidentaIntrebariTesteDao().deleteByKey(evidenta.getId()); }


        Query<TestPartajat> queryTestePartajate = ((App) getApplication()).getDaoSession().getTestPartajatDao()
                .queryBuilder().where(TestPartajatDao.Properties.TestId.eq(queryTest.list().get(0).getId())).build();

        for ( TestPartajat testPartajat : queryTestePartajate.list()) {
            ((App) getApplication()).getDaoSession().getTestPartajatDao().deleteByKey(testPartajat.getId()); }


        List<Test> testeActualizate = profesor.getTeste();
        testeActualizate.remove(queryTest.list().get(0));
        profesor.setTeste(testeActualizate);
        ((App) getApplication()).getDaoSession().getProfesorDao().update(profesor);

        queryTest.list().get(0).setProfesorId(-1);
        ((App) getApplication()).getDaoSession().getTestDao().update(queryTest.list().get(0));


        // RETROFIT
        if (internetIsAvailable) {
            TestService testService = ServiceBuilder.buildService(TestService.class);
            Call<Test> testRequest = testService.getTestByNumeMaterieAndProfesorId(numeTest, materie, (int) (long)profesor.getId());
            testRequest.enqueue(new Callback<Test>() {
                @Override
                public void onResponse(Call<Test> call, Response<Test> response) {
                    test = response.body();
                }

                @Override
                public void onFailure(Call<Test> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Nu s-a putut gasi testul", Toast.LENGTH_SHORT).show();
                }
            });
            EvidentaIntrebariTesteService intrebariTesteService = ServiceBuilder.buildService(
                    EvidentaIntrebariTesteService.class);
            Call<List<EvidentaIntrebariTeste>> evidentaIntrebariTesteRequest = intrebariTesteService
                    .getEvidentaIntrebariTesteByTestId((int) (long)test.getId());
            evidentaIntrebariTesteRequest.enqueue(new Callback<List<EvidentaIntrebariTeste>>() {
                @Override
                public void onResponse(Call<List<EvidentaIntrebariTeste>> call, Response<List<EvidentaIntrebariTeste>> response) {
                    evidentaIntrebariTeste = response.body();
                }

                @Override
                public void onFailure(Call<List<EvidentaIntrebariTeste>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Nu s-a putut gasi evidenta testelor", Toast.LENGTH_SHORT).show();
                }
            });
            for ( EvidentaIntrebariTeste evidenta : evidentaIntrebariTeste) {
                Call<EvidentaIntrebariTeste> deleteRequest = intrebariTesteService
                        .deleteEvidentaIntrebariTesteById((int) (long)evidenta.getId());
                deleteRequest.enqueue(new Callback<EvidentaIntrebariTeste>() {
                    @Override
                    public void onResponse(Call<EvidentaIntrebariTeste> call, Response<EvidentaIntrebariTeste> response) {
                        Toast.makeText(getApplicationContext(), "Evidenta a fost stearsa cu succes", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<EvidentaIntrebariTeste> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Evidenta nu a putut fi stearsa", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            TestPartajatService testPartajatService = ServiceBuilder.buildService(TestPartajatService.class);
            Call<List<TestPartajat>> testePartajateRequest = testPartajatService.getTestePartajatByTestId((int) (long)test.getId());
            testePartajateRequest.enqueue(new Callback<List<TestPartajat>>() {
                @Override
                public void onResponse(Call<List<TestPartajat>> call, Response<List<TestPartajat>> response) {
                    testePartajate = response.body();
                }

                @Override
                public void onFailure(Call<List<TestPartajat>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Nu s-a putut gasi teste partajate", Toast.LENGTH_SHORT).show();
                }
            });


            for ( TestPartajat testPartajat : testePartajate) {
                Call<TestPartajat> deleteTestPartajatRequest = testPartajatService
                        .deleteTestPartajatById((int) (long)testPartajat.getId());
                deleteTestPartajatRequest.enqueue(new Callback<TestPartajat>() {
                    @Override
                    public void onResponse(Call<TestPartajat> call, Response<TestPartajat> response) {
                        Toast.makeText(getApplicationContext(), "Testul partajat a fost sters cu succes", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<TestPartajat> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Testul partajat nu a putut fi fost sters", Toast.LENGTH_SHORT).show();
                    }
                });
            }


            Call<List<Test>> testeRequest =  testService.getTesteByProfesorId((int)(long)profesor.getId());
            testeRequest.enqueue(new Callback<List<Test>>() {
                @Override
                public void onResponse(Call<List<Test>> call, Response<List<Test>> response) {
                    testeAux2 = response.body();
                }

                @Override
                public void onFailure(Call<List<Test>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Nu s-au putut gasi teste", Toast.LENGTH_SHORT).show();
                }
            });

            testeActualizate =  testeAux2;
            testeActualizate.remove(test);
            profesor.setTeste(testeActualizate);


            ProfesorService profesorService = ServiceBuilder.buildService(ProfesorService.class);
            Call<Profesor> updateProfesor = profesorService.updateProfesor((int) (long)profesor.getId(), profesor);
            updateProfesor.enqueue(new Callback<Profesor>() {
                @Override
                public void onResponse(Call<Profesor> call, Response<Profesor> response) {
                    Toast.makeText(getApplicationContext(), "Datele au fost modificate cu succes", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Profesor> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Datele nu au putut fi modificate", Toast.LENGTH_SHORT).show();
                }
            });

            test.setProfesorId(-1);
            Call<Test> updateTest = testService.updateTest((int) (long)test.getId(), test);
            updateTest.enqueue(new Callback<Test>() {
                @Override
                public void onResponse(Call<Test> call, Response<Test> response) {
                    Toast.makeText(getApplicationContext(), "Datele au fost modificate cu succes", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Test> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Datele nu au putut fi modificate", Toast.LENGTH_SHORT).show();
                }
            });

        }


    }

    public void stergeTestPartajat(long id)
    {

        // SQLITE
        Query<TestPartajat> queryTestePartajate = ((App) getApplication()).getDaoSession().getTestPartajatDao().queryBuilder().where(
                TestPartajatDao.Properties.TestId.eq(id),
                TestPartajatDao.Properties.ProfesorId.eq(profesor.getId())).build();

        ((App) getApplication()).getDaoSession().getTestPartajatDao().deleteByKey(queryTestePartajate.list().get(0).getId());


         // RETROFIT
        TestPartajatService testPartajatService = ServiceBuilder.buildService(TestPartajatService.class);
        Call<TestPartajat> testePartajateRequest = testPartajatService.getTestPartajatByTestIdAndProfesorId((int)id, (int) (long)profesor.getId());
        testePartajateRequest.enqueue(new Callback<TestPartajat>() {
            @Override
            public void onResponse(Call<TestPartajat> call, Response<TestPartajat> response) {
                testPartajat = response.body();
            }

            @Override
            public void onFailure(Call<TestPartajat> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Testul partajat nu a putut fi gasit", Toast.LENGTH_SHORT).show();
            }
        });
        Call<TestPartajat> deleteTestPartajat = testPartajatService.deleteTestPartajatById((int) (long)testPartajat.getId());
        deleteTestPartajat.enqueue(new Callback<TestPartajat>() {
            @Override
            public void onResponse(Call<TestPartajat> call, Response<TestPartajat> response) {
                Toast.makeText(getApplicationContext(), "Testul a fost sters cu succes", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<TestPartajat> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Testul nu a putut fi sters", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
