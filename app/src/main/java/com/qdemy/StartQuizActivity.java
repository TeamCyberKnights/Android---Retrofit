package com.qdemy;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
}
