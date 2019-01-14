package com.qdemy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.qdemy.clase.Profesor;
import com.qdemy.clase.Test;
import com.qdemy.clase.TestDao;
import com.qdemy.clase.TestPartajat;
import com.qdemy.clase.TestPartajatDao;
import com.qdemy.db.App;
import com.qdemy.servicii.NetworkConnectionService;
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

public class ProfesorActivity extends AppCompatActivity {

    private ImageView intrebari;
    private ImageView istoric;
    private ImageView teste;
    private TextView desfasurare;
    private ImageView inapoi;
    private ImageView start;
    private ProgressBar progresBar;

    private Profesor profesor;
    private List<String> testeProfesor = new ArrayList<>();
    private List<TestPartajat> testePartajate;
    private Test test;
    private boolean internetIsAvailable;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor);
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

        //region Initializare componente vizuale
        intrebari = findViewById(R.id.intrebari_button_profesor);
        istoric = findViewById(R.id.istoric_button_profesor);
        teste = findViewById(R.id.teste_button_profesor);
        desfasurare = findViewById(R.id.desfasurare_button_profesor);
        inapoi = findViewById(R.id.back_image_profesor);
        start = findViewById(R.id.start_image_profesor);
        progresBar = findViewById(R.id.progressBar_profesor);
        //endregion


        profesor = ((App) getApplication()).getProfesor();
        for(int i=0;i<profesor.getTeste().size();i++)
           testeProfesor.add(profesor.getTeste(i).getNume());


        if (internetIsAvailable) {
            TestPartajatService testPartajatService = ServiceBuilder.buildService(TestPartajatService.class);
            Call<List<TestPartajat>> testePartajateRequest = testPartajatService.getTestePartajateByProfesorId((int)(long)profesor.getId());
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
                Call<Test> testRequest = testService.getTestById((int)(long)testePartajate.get(i).getTestId());
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

                testeProfesor.add(test.getNume());
            }
        } else {

            Query<TestPartajat> queryTestePartajate = ((App) getApplication()).getDaoSession().getTestPartajatDao().queryBuilder().where(
                    TestPartajatDao.Properties.ProfesorId.eq(profesor.getId())).build();
            for (int i = 0; i < queryTestePartajate.list().size(); i++) {
                Query<Test> queryTest = ((App) getApplication()).getDaoSession().getTestDao().queryBuilder().where(
                        TestDao.Properties.Id.eq(queryTestePartajate.list().get(i).getTestId())).build();
                testeProfesor.add(queryTest.list().get(0).getNume());
            }
        }
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

        istoric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IstoricProfesorActivity.class);
                startActivity(intent);
                finish();
            }
        });

        teste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MateriiActivity.class);
                intent.putExtra(Constante.CHEIE_TRANSFER, getString(R.string.testele_mele));
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

        //endregion


        desfasurare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScorLiveActivity.class);
                startActivity(intent);
            }
        });
        progresBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScorLiveActivity.class);
                startActivity(intent);
            }
        });


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle(R.string.selecteaza_testul);
                View view = getLayoutInflater().inflate(R.layout.spinner_dialog, null);
                final Spinner spinner = view.findViewById(R.id.spinner_dialog);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(),
                        android.R.layout.simple_spinner_item, testeProfesor);
                adapter.setDropDownViewResource(R.layout.item_spinner);
                spinner.setAdapter(adapter);

                builder.setPositiveButton(getString(R.string.start), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Intent intent = new Intent(getApplicationContext(), StartingQuizProfesorActivity.class);
                            intent.putExtra(Constante.CHEIE_TRANSFER, spinner.getSelectedItem().toString());
                            startActivityForResult(intent, Constante.REQUEST_CODE_START_TEST);
                        }
                        catch(Exception e) {}
                    }
                });


                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getWindow().setBackgroundDrawableResource(R.color.portocaliu);
            }
        });

        // daca exista un timer pe server afisez comp vizuale, le fac visible toate 3
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            desfasurare.setVisibility(View.VISIBLE);
            start.setVisibility(View.INVISIBLE);
            progresBar.setVisibility(View.VISIBLE);
        }

        else {
            desfasurare.setVisibility(View.INVISIBLE);
            start.setVisibility(View.VISIBLE);
            progresBar.setVisibility(View.INVISIBLE);
        }
    }
}
