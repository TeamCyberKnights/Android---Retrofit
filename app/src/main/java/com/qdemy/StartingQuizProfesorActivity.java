package com.qdemy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import org.greenrobot.greendao.query.Query;

import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_quiz_profesor);

        initializare();
    }

    private void initializare() {

        //region Initializare componente vizuale
        start = findViewById(R.id.start_button_startingQuiz);
        renunta = findViewById(R.id.renunta_text_startingQuiz);
        studentiList = findViewById(R.id.studenti_list_startingQuiz);
        cod = findViewById(R.id.cod_text_startingQuiz);
        //endregion

        Query<Test> queryTest = ((App) getApplication()).getDaoSession().getTestDao().queryBuilder().where(
                TestDao.Properties.Nume.eq(getIntent().getStringExtra(Constante.CHEIE_TRANSFER))).build();
        testId = queryTest.list().get(0).getId();
        cod.setText(random(String.valueOf(testId)));

        adapter = new StartingQuizAdapter(getApplicationContext(),
                R.layout.item_text_text, studentiConectati, getLayoutInflater());
        studentiList.setAdapter(adapter);


        renunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //STEREGERE COD DE PE SERVER
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //salvare id test live
                sharedPreferences = getSharedPreferences(Constante.FISIER_PREFERINTA_UTILIZATOR, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong(getString(R.string.testLive), testId);
                editor.commit();


                //adaugare test sustinut
                profesor = ((App) getApplication()).getProfesor();
                ((App) getApplication()).getDaoSession().getTestSustinutDao().insert(new TestSustinut(profesor.getId()));
                Query<TestSustinut> queryTestSustinut = ((App) getApplication()).getDaoSession().getTestSustinutDao().queryBuilder().where(
                        TestSustinutDao.Properties.ProfesorId.eq(profesor.getId())).build();

                List<TestSustinut> testeSustinute = profesor.getTesteSustinute();
                testeSustinute.add(new TestSustinut(queryTestSustinut.list().get(queryTestSustinut.list().size()-1).getId(),
                        profesor.getId()));
                profesor.setTesteSustinute(testeSustinute);
                ((App) getApplication()).getDaoSession().getProfesorDao().update(profesor);


                //salvare cod test
                salvareTestFisier(cod.getText().toString(), v.getContext());
                setResult(RESULT_OK, getIntent());
                finish();

                //START TIMER PE SERVER CU DURATA TESTULUI

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

        //ADAUGARE STUDENT CAND SE CONECTEAZA CU CODUL TESTULUI CURENT
        adapter.notifyDataSetChanged();
    }
}
