package com.qdemy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartingQuizProfesorActivity extends AppCompatActivity {

    private Button start;
    private TextView renunta;
    private TextView cod;


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
        //initializare cod random

        renunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfesorActivity.class);
                startActivity(intent);
                finish();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfesorActivity.class);
                startActivity(intent);
                finish();
                //start test
            }
        });
    }
}
