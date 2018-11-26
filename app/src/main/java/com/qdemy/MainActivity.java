package com.qdemy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.qdemy.clase.Profesor;
import com.qdemy.clase.Student;
import com.qdemy.network.HttpManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText nume;
    private TextInputEditText parola;
    private Button intraCont;
    private TextView creeazaCont;
    private String dataCurenta;
    private static final String urlJSONStudenti = "......"; //URL cu JSON pentru clasa Student
    private static final String urlJSONProfesori = "......"; // (la fel la restul)
    private static final String urlJSONTeste = "......";
    private static final String urlJSONIntrebariGrila = "......";
    private static final String urlJSONRaspunsuriIntrebariGrila = "......";
    private static final String urlJSONRezultateTesteProfesor = "......";
    private static final String urlJSONRezultateTesteStudenti = "......";

    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region preluare date din URL-uri

        @SuppressLint("StaticFieldLeak") HttpManager manager = new HttpManager() {
            @Override
            protected void onPostExecute(String string) {

                //student = StudentParser.fromJson(string);

                //DE FACUT CLASE PARSER PENTRU FIECARE CLASA

            }};

        manager.execute(urlJSONStudenti);
        manager.execute(urlJSONProfesori);
        // ......

        //endregion

        initializare();
    }

    private void initializare()
    {
        nume = findViewById(R.id.nume_textInput_main);
        parola = findViewById(R.id.parola_textInput_main);
        intraCont = findViewById(R.id.intra_button_main);
        creeazaCont = findViewById(R.id.creeaza_textView_main);


        //region creare cont exemplu profesor
        //conturile profesorilor vor fi predefinite in aplicatia finala
        final Profesor profesor = new Profesor(getString(R.string.ex_prof_nume), getString(R.string.ex_prof_utilizator), getString(R.string.ex_prof_parola), getString(R.string.ex_prof_mail));
        //nume: DitaAlexandru
        //parola: 1234

        //endregion


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatData = new SimpleDateFormat(Constante.DATE_FORMAT);
        dataCurenta = formatData.format(calendar.getTime());

        intraCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(nume.getText().toString().equals(profesor.getNume()) &&
                        parola.getText().toString().equals(profesor.getParola()) )
                    {
                        Intent intent = new Intent(getApplicationContext(), ProfesorActivity.class);
                        intent.putExtra(Constante.CHEIE_AUTENTIFICARE, profesor);
                        intent.putExtra(Constante.CHEIE_AUTENTIFICARE_EXTRA, dataCurenta);
                        startActivity(intent);
                        finish();
                    }
                    else if(nume.getText().toString().equals(student.getNume()) &&
                            parola.getText().toString().equals(student.getParola()) )
                    {

                        Intent intent = new Intent(getApplicationContext(), StudentActivity.class);
                        intent.putExtra(Constante.CHEIE_AUTENTIFICARE, student);
                        intent.putExtra(Constante.CHEIE_AUTENTIFICARE_EXTRA, dataCurenta);
                        startActivity(intent);
                        finish();
                    }
                    else
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constante.REQUEST_CODE_CONT_NOU && resultCode == RESULT_OK && data != null)
        {
            student = data.getParcelableExtra(Constante.CHEIE_CONT_NOU);
            Toast.makeText(getApplicationContext(),getString(R.string.inregistrare_succes), Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getApplicationContext(),getString(R.string.inregistrare_eroare), Toast.LENGTH_SHORT).show();

    }
}
