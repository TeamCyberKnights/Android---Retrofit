package com.qdemy;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.qdemy.servicii.NetworkConnectionService;
import com.qdemy.servicii.RezultatTestStudentService;
import com.qdemy.servicii.ServiceBuilder;
import com.qdemy.servicii.TestService;

import org.greenrobot.greendao.query.Query;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class StartQuizActivity extends AppCompatActivity {

    private TextInputEditText cod;
    private TextView asteapta;
    private Button acceseaza;
    private TextView renunta;
    private Test test;
    private RezultatTestStudent rezultatTestStudent;
    private Student student1;
    private boolean internetIsAvailable;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz);
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

        cod = findViewById(R.id.cod_textInput_startQuiz);
        asteapta = findViewById(R.id.asteapta_text_startQuiz);
        acceseaza = findViewById(R.id.acceseaza_button_startQuiz);
        renunta = findViewById(R.id.renunta_textView_startQuiz);

        cod.setText(citesteCodTestFisier());

        student1 = ((App) getApplication()).getStudent(); // DE CE?
        acceseaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //validare cod test
                try {
                    final long id = Long.parseLong(cod.getText().toString().trim().split("%")[1]);

                    if (internetIsAvailable) {
                        TestService testService = ServiceBuilder.buildService(TestService.class);
                        Call<Test> testRequest = testService.getTestById((int)(long)id);
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
                        if (test == null)
                            Toast.makeText(getApplicationContext(), getString(R.string.error_message_cod_invalid), Toast.LENGTH_LONG).show();
                        else {
                            Student student = ((App) getApplication()).getStudent();
                            String data = new SimpleDateFormat(Constante.DATE_FORMAT).format(Calendar.getInstance().getTime());


                            RezultatTestStudentService rezultatTestStudentService = ServiceBuilder.buildService(RezultatTestStudentService.class);
                            Call<RezultatTestStudent> rezultatTestStudentRequest = rezultatTestStudentService.getRezultatTestStudentByTestIdDataAndStudentId((int)(long)id, data, (int)(long)student.getId());
                            rezultatTestStudentRequest.enqueue(new Callback<RezultatTestStudent>() {
                                @Override
                                public void onResponse(Call<RezultatTestStudent> call, Response<RezultatTestStudent> response) {
                                    rezultatTestStudent = response.body();
                                }

                                @Override
                                public void onFailure(Call<RezultatTestStudent> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "Nu s-a putut gasi rezultatul studentului", Toast.LENGTH_SHORT).show();
                                }

                            });

                            if (rezultatTestStudent != null) {
                                Toast.makeText(getApplicationContext(), getString(R.string.error_message_test_deja_sustinut), Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                asteapta.setVisibility(View.VISIBLE);
                                cod.setVisibility(View.INVISIBLE);
                                cod.setVisibility(View.GONE);
                                acceseaza.setVisibility(View.GONE);

                               // trebuie trimis request ca s-a conectat studentul
                                // ii apare profesorului in lista profesorului ca studentul s-a conectat

                                //delay
                                // asta e o alta metoda apelata atunci cand profesorul da start test in StartingQuizProfesorActivity
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
                    }  else {

                    Query<Test> queryTest = ((App) getApplication()).getDaoSession().getTestDao().queryBuilder().where(
                            TestDao.Properties.Id.eq(id)).build();
                    if (queryTest.list().size() == 0)
                        Toast.makeText(getApplicationContext(), getString(R.string.error_message_cod_invalid), Toast.LENGTH_LONG).show();
                    else {
                        Student student = ((App) getApplication()).getStudent();
                        String data = new SimpleDateFormat(Constante.DATE_FORMAT).format(Calendar.getInstance().getTime());

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
