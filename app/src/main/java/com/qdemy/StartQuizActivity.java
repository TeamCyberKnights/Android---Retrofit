package com.qdemy;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StartQuizActivity extends AppCompatActivity {

    private TextInputEditText cod;
    private TextView asteapta;
    private Button acceseaza;
    private TextView renunta;

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
                //verificare codul testului
                asteapta.setVisibility(View.VISIBLE);
                cod.setVisibility(View.GONE);
                acceseaza.setVisibility(View.GONE);

                // incarcare test + activitati intrebari

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
