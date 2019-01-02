package com.qdemy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.qdemy.clase.DaoMaster;
import com.qdemy.clase.EvidentaMateriiProfesori;
import com.qdemy.clase.Profesor;
import com.qdemy.clase.ProfesorDao;
import com.qdemy.clase.Student;
import com.qdemy.clase.StudentDao;
import com.qdemy.db.App;
import com.qdemy.db.DbOpenHelper;

import org.greenrobot.greendao.query.Query;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText nume;
    private TextInputEditText parola;
    private Button intraCont;
    private TextView creeazaCont;
    private String dataCurenta;
    private static final String urlJSONProfesor = "https://api.myjson.com/bins/17t1sq";

    private Student student;
    private Profesor profesor;
    private SharedPreferences sharedPreferences;

    private Query<Profesor> profesoriQuery;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        //region preluare date din URL-uri
//
//        @SuppressLint("StaticFieldLeak") HttpManager manager = new HttpManager() {
//            @Override
//            protected void onPostExecute(String string) {
//
//                try {
//                    profesor = ProfesorParser.getProfesorJSON(getApplicationContext(), string);
//                    //Toast.makeText(getApplicationContext(), profesor.toString(), Toast.LENGTH_LONG).show();
//                } catch (Exception e) {
//                    Toast.makeText(getApplicationContext(),getString(R.string.eroare_parsare),Toast.LENGTH_LONG).show();
//                }
//
//            }};
//
//        manager.execute(urlJSONProfesor);
//
//        //endregion

        //Calligrapher calligrapher = new Calligrapher(this);
        //calligrapher.setFont(this, "font/ubuntu_regular.ttf", true);

        initializare();



        if(profesor != null){
            nume.setText(profesor.getUtilizator());
            parola.setText(profesor.getParola());
        }

    }

    private void initializare()
    {
        nume = findViewById(R.id.nume_textInput_main);
        parola = findViewById(R.id.parola_textInput_main);
        intraCont = findViewById(R.id.intra_button_main);
        creeazaCont = findViewById(R.id.creeaza_textView_main);

        //profesor = ((App)getApplication()).getDaoSession().getProfesorDao().load(1L);
        sharedPreferences = getSharedPreferences(Constante.FISIER_PREFERINTA_UTILIZATOR, MODE_PRIVATE);
        try {
            if (sharedPreferences.getString(getString(R.string.tip), "").equals(getString(R.string.profesor)))
                incarcareProfesorSalvat();
            else if (sharedPreferences.getString(getString(R.string.tip), "").equals(getString(R.string.student)))
                incarcareStudentSalvat();
        }
        catch(Exception e) {}
        //endregion



        intraCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nume.getText().toString().isEmpty() || parola.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.completeaza_ambele_campuri), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (profesor!=null)
                    if (nume.getText().toString().equals(profesor.getUtilizator()) &&
                        parola.getText().toString().equals(profesor.getParola())) {
                        salvareUtilizatorProfesor();
                        return;
                    }
                    else try {
                        identificareProfesor();
                        salvareUtilizatorProfesor();
                        return;
                    } catch (Exception e) {}


                else try {
                    identificareProfesor();
                    salvareUtilizatorProfesor();
                    return;
                } catch (Exception e) {}



                if(student!=null)
                    if (nume.getText().toString().equals(student.getUtilizator()) &&
                            parola.getText().toString().equals(student.getParola())) {
                        salvareUtilizatorStudent();
                        return;
                    }
                    else try {
                        identificareStudent();
                        salvareUtilizatorStudent();
                        return;
                    } catch (Exception e) {}

                else try {
                    identificareStudent();
                    salvareUtilizatorStudent();
                    return;
                } catch (Exception e) {}


                Toast.makeText(getApplicationContext(), getString(R.string.autentificare_eroare), Toast.LENGTH_SHORT).show();

            }

        });

        creeazaCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ContActivity.class);
                startActivityForResult(intent, Constante.REQUEST_CODE_CONT_NOU);
                nume.setText("");
                parola.setText("");
            }
        });


    }

    private void identificareProfesor()    {
        ProfesorDao profesorDao = ((App) getApplication()).getDaoSession().getProfesorDao();
        Query<Profesor> query = profesorDao.queryBuilder().where(
                ProfesorDao.Properties.Utilizator.eq(nume.getText()),
                ProfesorDao.Properties.Parola.eq(parola.getText())).build();
        profesor = query.list().get(0);
    }

    private void identificareStudent() {
        StudentDao studentDao = ((App) getApplication()).getDaoSession().getStudentDao();
        Query<Student> query = studentDao.queryBuilder().where(
                StudentDao.Properties.Utilizator.eq(nume.getText()),
                StudentDao.Properties.Parola.eq(parola.getText())).build();
        student = query.list().get(0);
    }

    private void salvareUtilizatorProfesor() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.tip), getString(R.string.profesor));
        editor.putString(getString(R.string.utilizator), profesor.getUtilizator());
        editor.putString(getString(R.string.parola), profesor.getParola());
        editor.commit();

        Intent intent = new Intent(getApplicationContext(), ProfesorActivity.class);
        intent.putExtra(Constante.CHEIE_AUTENTIFICARE, profesor.getUtilizator());
        startActivity(intent);
        finish();
    }

    private void salvareUtilizatorStudent() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.tip), getString(R.string.student));
        editor.putString(getString(R.string.utilizator), student.getUtilizator());
        editor.putString(getString(R.string.parola), student.getParola());
        editor.commit();

        Intent intent = new Intent(getApplicationContext(), StudentActivity.class);
        intent.putExtra(Constante.CHEIE_AUTENTIFICARE, student.getUtilizator());
        startActivity(intent);
        finish();
    }

    private void incarcareProfesorSalvat() {

        //COSMIN - TO DO SELECT PROFESOR

        String utilizatorText = sharedPreferences.getString(getString(R.string.utilizator), "");
        String parolaText = sharedPreferences.getString(getString(R.string.parola), "");
        ProfesorDao profesorDao = ((App) getApplication()).getDaoSession().getProfesorDao();
        Query<Profesor> query = profesorDao.queryBuilder().where(
                ProfesorDao.Properties.Utilizator.eq(utilizatorText),
                ProfesorDao.Properties.Parola.eq(parolaText)).build();
        profesor = query.list().get(0);

        nume.setText(utilizatorText);
        parola.setText(parolaText);
    }

    private void incarcareStudentSalvat() {

        //COSMIN - TO DO SELECT STUDENT

        String utilizatorText = sharedPreferences.getString(getString(R.string.utilizator), "");
        String parolaText = sharedPreferences.getString(getString(R.string.parola), "");
        StudentDao studentDao = ((App) getApplication()).getDaoSession().getStudentDao();
        Query<Student> query = studentDao.queryBuilder().where(
                StudentDao.Properties.Utilizator.eq(utilizatorText),
                StudentDao.Properties.Parola.eq(parolaText)).build();
        student = query.list().get(0);

        nume.setText(utilizatorText);
        parola.setText(parolaText);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
            Toast.makeText(getApplicationContext(),getString(R.string.inregistrare_succes), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(),getString(R.string.inregistrare_eroare), Toast.LENGTH_SHORT).show();

    }
}
