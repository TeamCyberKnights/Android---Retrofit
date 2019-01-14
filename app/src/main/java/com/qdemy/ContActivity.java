package com.qdemy;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.qdemy.Constante;
import com.qdemy.R;
import com.qdemy.clase.DaoSession;
import com.qdemy.clase.Student;
import com.qdemy.clase.StudentDao;
import com.qdemy.db.App;
import com.qdemy.servicii.NetworkConnectionService;
import com.qdemy.servicii.ServiceBuilder;
import com.qdemy.servicii.StudentService;

import org.greenrobot.greendao.query.Query;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class ContActivity extends AppCompatActivity {

    private TextInputEditText mail;
    private TextInputEditText nume;
    private TextInputEditText prenume;
    private TextInputEditText utilizator;
    private TextInputEditText parola;
    private Button salveaza;
    private TextView renunta;
    Intent intent;
    private Student student;
    private boolean internetIsAvailable = false;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cont);

        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
       if ( activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting()) {
           internetIsAvailable = true;
       }

        initializare();
    }


    private void initializare()
    {
        nume = findViewById(R.id.nume_textInput_cont);
        prenume = findViewById(R.id.prenume_textInput_cont);
        utilizator = findViewById(R.id.utilizator_textInput_cont);
        parola = findViewById(R.id.parola_textInput_cont);
        mail = findViewById(R.id.mail_textInput_cont);
        salveaza = findViewById(R.id.salveaza_button_cont);
        renunta = findViewById(R.id.renunta_textView_cont);
        intent=getIntent();

        salveaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nume.getText().toString().trim().isEmpty())
                    Toast.makeText(getApplicationContext(),getString(R.string.completeaza_nume), Toast.LENGTH_SHORT).show();

                if(prenume.getText().toString().trim().isEmpty())
                    Toast.makeText(getApplicationContext(),getString(R.string.completeaza_prenume), Toast.LENGTH_SHORT).show();

                else if(utilizator.getText().toString().trim().isEmpty())
                    Toast.makeText(getApplicationContext(),getString(R.string.completeaza_utilizator), Toast.LENGTH_SHORT).show();

                else if(parola.getText().toString().trim().isEmpty())
                    Toast.makeText(getApplicationContext(),getString(R.string.completeaza_parola), Toast.LENGTH_SHORT).show();

                else if(mail.getText().toString().trim().isEmpty())
                    Toast.makeText(getApplicationContext(),getString(R.string.completeaza_mail), Toast.LENGTH_SHORT).show();

                else if (!mail.getText().toString().trim().matches(Constante.MAIL_FORMAT))
                    Toast.makeText(getApplicationContext(),getString(R.string.mail_invalid), Toast.LENGTH_SHORT).show();

                else
                {
                    if (internetIsAvailable) {
                        Toast.makeText(getApplicationContext(), "There is internet", Toast.LENGTH_SHORT).show();
                        StudentService studentService = ServiceBuilder.buildService(StudentService.class);
                        Call<Student> studentRequest = studentService.getStudentByUtilizator(utilizator.getText().toString());
                        studentRequest.enqueue(new Callback<Student>() {
                            @Override
                            public void onResponse(Call<Student> call, Response<Student> response) {
                                student = response.body();
                            }

                            @Override
                            public void onFailure(Call<Student> call, Throwable t) {

                            }
                        });

                        if (student!=null)
                            Toast.makeText(getApplicationContext(), getString(R.string.eroare_utilizator_deja_inregistrat), Toast.LENGTH_SHORT).show();
                        else {
                            Toast.makeText(getApplicationContext(), "se face request", Toast.LENGTH_SHORT).show();
                            Student student = new Student(utilizator.getText().toString(), nume.getText().toString(), prenume.getText().toString(),
                                    parola.getText().toString(), mail.getText().toString());

                            Call<Student> insertStudentRequest = studentService.saveStudent(student);
                            insertStudentRequest.enqueue(new Callback<Student>() {
                                @Override
                                public void onResponse(Call<Student> call, Response<Student> response) {
                                    Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onFailure(Call<Student> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "Studentul nu a putut fi salvat", Toast.LENGTH_SHORT).show();
                                }
                            });

                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "There is no internet", Toast.LENGTH_SHORT).show();
                        StudentDao studentDao = ((App) getApplication()).getDaoSession().getStudentDao();
                        Query<Student> query = studentDao.queryBuilder().where(
                                StudentDao.Properties.Utilizator.eq(utilizator.getText())).build();
                        if (query.list().size() > 0)
                            Toast.makeText(getApplicationContext(), getString(R.string.eroare_utilizator_deja_inregistrat), Toast.LENGTH_SHORT).show();
                        else {

                            Student student = new Student(utilizator.getText().toString(), nume.getText().toString(), prenume.getText().toString(),
                                    parola.getText().toString(), mail.getText().toString());
                            DaoSession DaoSession = ((App) getApplication()).getDaoSession();
                            DaoSession.getStudentDao().insert(student);

                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
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
}

