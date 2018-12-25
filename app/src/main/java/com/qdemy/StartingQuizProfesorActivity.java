package com.qdemy;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.qdemy.clase.Test;
import com.qdemy.clase.TestDao;
import com.qdemy.clase.VariantaRaspunsDao;
import com.qdemy.db.App;

import org.greenrobot.greendao.query.Query;

import java.io.OutputStreamWriter;
import java.util.Random;

public class StartingQuizProfesorActivity extends AppCompatActivity {

    private Button start;
    private TextView renunta;
    private TextView cod;
    private long testId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_quiz_profesor);

        initializare();
    }

    private void initializare() {

        start = findViewById(R.id.start_button_startingQuiz);
        renunta = findViewById(R.id.renunta_text_startingQuiz);
        cod = findViewById(R.id.cod_text_startingQuiz);


        Query<Test> queryTest = ((App) getApplication()).getDaoSession().getTestDao().queryBuilder().where(
                TestDao.Properties.Nume.eq(getIntent().getStringExtra(Constante.CHEIE_TRANSFER))).build();
        testId = queryTest.list().get(0).getId();
        cod.setText(random(String.valueOf(testId)));


        renunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                salvareTestFisier(cod.getText().toString(), v.getContext());
                setResult(RESULT_OK, getIntent());
                finish();
                //start test
            }
        });
    }


    public static String random( String testId ) {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = 4;
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        randomStringBuilder.append("$" + testId);
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
}
