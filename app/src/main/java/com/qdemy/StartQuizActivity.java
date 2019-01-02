package com.qdemy;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.qdemy.clase.EvidentaIntrebariTeste;
import com.qdemy.clase.EvidentaIntrebariTesteDao;
import com.qdemy.clase.RezultatTestStudent;
import com.qdemy.clase.RezultatTestStudentDao;
import com.qdemy.clase.Student;
import com.qdemy.clase.Test;
import com.qdemy.clase.TestDao;
import com.qdemy.db.App;

import org.greenrobot.greendao.query.Query;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class StartQuizActivity extends AppCompatActivity {

    private TextInputEditText cod;
    private TextView asteapta;
    private Button acceseaza;
    private TextView renunta;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz);

        initializare();
    }

    private void initializare() {

        cod = findViewById(R.id.cod_textInput_startQuiz);
        asteapta = findViewById(R.id.asteapta_text_startQuiz);
        acceseaza = findViewById(R.id.acceseaza_button_startQuiz);
        renunta = findViewById(R.id.renunta_textView_startQuiz);

        cod.setText(citesteCodTestFisier());


        acceseaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //validare cod test
                try {
                    final long id = Long.parseLong(cod.getText().toString().trim().split("%")[1]);
                    //COSMIN - TO DO SELECT TEST CU ID-UL PRELUAT DIN COD
                    Query<Test> queryTest = ((App) getApplication()).getDaoSession().getTestDao().queryBuilder().where(
                            TestDao.Properties.Id.eq(id)).build();
                    if (queryTest.list().size() == 0)
                        Toast.makeText(getApplicationContext(), getString(R.string.error_message_cod_invalid), Toast.LENGTH_LONG).show();
                    else {
                        Student student = ((App) getApplication()).getStudent();
                        String data = new SimpleDateFormat(Constante.DATE_FORMAT).format(Calendar.getInstance().getTime());
                        //COSMIN - TO DO SELECT REZULTAT TEST STUDENT => STUDENTUL NU POATE SUSTINE DE 2 ORI IN ACEASI ZI ACELASI TEST INDIFERENT DE CODUL TESTULUI
                        Query<RezultatTestStudent> queryRezultat = ((App) getApplication()).getDaoSession().getRezultatTestStudentDao().queryBuilder().where(
                                RezultatTestStudentDao.Properties.TestId.eq(id),
                                RezultatTestStudentDao.Properties.Data.eq(data),
                                RezultatTestStudentDao.Properties.StudentId.eq(student.getId())).build();
                        if (queryRezultat.list().size() > 1) {
                            Toast.makeText(getApplicationContext(), getString(R.string.error_message_test_deja_sustinut), Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            asteapta.setVisibility(View.VISIBLE);
                            cod.setVisibility(View.INVISIBLE);
                            cod.setVisibility(View.GONE);
                            acceseaza.setVisibility(View.GONE);

                            //delay
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(getApplicationContext(), GrilaQuizActivity.class);
                                    intent.putExtra(Constante.CHEIE_TRANSFER, id);
                                    intent.putExtra(Constante.CHEIE_TRANSFER2, "");
                                    startActivity(intent);
                                    finish();
                                }
                            }, 1000);
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_message_cod_invalid), Toast.LENGTH_LONG).show();
                }
            }
        });

        renunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String citesteCodTestFisier() {

        String cod = "";

        try {
            InputStream inputStream = this.openFileInput(getString(R.string.test_txt));

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                cod = stringBuilder.toString();
            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), getString(R.string.error_message_citire_cod_test), Toast.LENGTH_LONG).show();
        }
        return cod;
    }
}
