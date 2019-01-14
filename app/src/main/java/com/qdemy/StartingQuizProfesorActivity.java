package com.qdemy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.qdemy.clase.Profesor;
import com.qdemy.clase.Student;
import com.qdemy.clase.Test;
import com.qdemy.clase.TestDao;
import com.qdemy.clase.TestSustinut;
import com.qdemy.clase.TestSustinutDao;
import com.qdemy.clase.VariantaRaspunsDao;
import com.qdemy.clase_adapter.IstoricProfesorAdapter;
import com.qdemy.clase_adapter.StartingQuizAdapter;
import com.qdemy.db.App;
import com.qdemy.servicii.NetworkConnectionService;
import com.qdemy.servicii.ProfesorService;
import com.qdemy.servicii.ServiceBuilder;
import com.qdemy.servicii.TestService;
import com.qdemy.servicii.TestSustinutService;

import org.greenrobot.greendao.query.Query;

import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class StartingQuizProfesorActivity extends AppCompatActivity {

    private Button start;
    private TextView renunta;
    private TextView cod;
    private long testId;
    private SharedPreferences sharedPreferences;
    private Profesor profesor;
    private ListView studentiList;
    private List<Student> studentiConectati = new ArrayList<>();
    StartingQuizAdapter adapter;
    private Test test;
    private TestSustinut testSustinut;
    private boolean internetIsAvailable;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_quiz_profesor);
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
        start = findViewById(R.id.start_button_startingQuiz);
        renunta = findViewById(R.id.renunta_text_startingQuiz);
        studentiList = findViewById(R.id.studenti_list_startingQuiz);
        cod = findViewById(R.id.cod_text_startingQuiz);
        //endregion

        if (internetIsAvailable) {
            TestService testService = ServiceBuilder.buildService(TestService.class);
            Call<Test> testRequest = testService.getTestByNume(getIntent().getStringExtra(Constante.CHEIE_TRANSFER));
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

            cod.setText(random(String.valueOf(test.getId())));
        } else {

            Query<Test> queryTest = ((App) getApplication()).getDaoSession().getTestDao().queryBuilder().where(
                    TestDao.Properties.Nume.eq(getIntent().getStringExtra(Constante.CHEIE_TRANSFER))).build();
            testId = queryTest.list().get(0).getId();
            cod.setText(random(String.valueOf(testId)));
        }

        adapter = new StartingQuizAdapter(getApplicationContext(),
                R.layout.item_text_text, studentiConectati, getLayoutInflater());
        studentiList.setAdapter(adapter);


        renunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // VEZI AICI
                //STEREGERE COD DE PE SERVER
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //salvare id test live pe server
                // VEZI AICI
                //COSMIN - ASTA O SA DISPARA
                sharedPreferences = getSharedPreferences(Constante.FISIER_PREFERINTA_UTILIZATOR, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong(getString(R.string.testLive), testId);
                editor.commit();

                if (internetIsAvailable) {

                    profesor = ((App) getApplication()).getProfesor();

                    TestSustinutService testSustinutService = ServiceBuilder.buildService(TestSustinutService.class);
                    Call<TestSustinut> insertTestSustinutRequest = testSustinutService.saveTestSustinut(new TestSustinut(profesor.getId()));
                    insertTestSustinutRequest.enqueue(new Callback<TestSustinut>() {
                        @Override
                        public void onResponse(Call<TestSustinut> call, Response<TestSustinut> response) {
                            Toast.makeText(getApplicationContext(), "Testul a fost salvat cu succes", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<TestSustinut> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Testul nu a putut fi salvat", Toast.LENGTH_SHORT).show();
                        }
                    });

                    //VEZI AICI
                    // DE LA AL DOILEA QUERY
                   // ACOLO VIN MAI MULTE TESTE SUSTINUTE DACA SE FACE QUERY DUPA PROFESOR ID

                } else {

                    //adaugare test sustinut
                    //COSMIN - TO DO INSERT TEST SUSTINUT
                    profesor = ((App) getApplication()).getProfesor();
                    ((App) getApplication()).getDaoSession().getTestSustinutDao().insert(new TestSustinut(profesor.getId()));

                    //COSMIN - TO DO SELECT TEST SUSTINUT NOU ADAUGAT
                    Query<TestSustinut> queryTestSustinut = ((App) getApplication()).getDaoSession().getTestSustinutDao().queryBuilder().where(
                            TestSustinutDao.Properties.ProfesorId.eq(profesor.getId())).build();

                    //COSMIN - TO DO UPDATE PROFESOR CU LISTA TESTE SUSTINUTE CU CEL NOU ADAUGAT
                    List<TestSustinut> testeSustinute = profesor.getTesteSustinute();
                    testeSustinute.add(new TestSustinut(queryTestSustinut.list().get(queryTestSustinut.list().size() - 1).getId(), //ULTIMUL ADAUGAT
                            profesor.getId()));
                    profesor.setTesteSustinute(testeSustinute);
                    ((App) getApplication()).getDaoSession().getProfesorDao().update(profesor);


                    //salvare cod test
                    salvareTestFisier(cod.getText().toString(), v.getContext());
                    setResult(RESULT_OK, getIntent());

                    //COSMIN - TO DO trimitere cod generat pe server + START TIMER PE SERVER CU DURATA TESTULUI
                    // split % si ultimul eelement e id ul testului
                    //TIMER-UL TREBUIE SINCRONIZAT SI PE PROFESOR SI PE STUDENT

                    finish();
                }

                }

        });
    }


    public static String random( String testId ) {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = 4;
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(74) + 48);
            randomStringBuilder.append(tempChar);
        }
        randomStringBuilder.append("%" + testId);
        return randomStringBuilder.toString();
    }

    private void salvareTestFisier(String cod, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(getString(R.string.test_txt), Context.MODE_PRIVATE));
            outputStreamWriter.write(cod);
            outputStreamWriter.close();
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), getString(R.string.erroe_message_start_test), Toast.LENGTH_LONG).show();

        }
    }


    private void addStudent(){
        // VEZI AICI
        //COSMIN - TO DO
        //ADAUGARE STUDENT CAND SE CONECTEAZA CU CODUL TESTULUI CURENT studentul care a dat acceseaza la lista de studenti conectati
        // se acceseaza aceasta metoda atunci cand un student apasa acceseaza

        adapter.notifyDataSetChanged();
    }
}
