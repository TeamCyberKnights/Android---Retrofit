package com.qdemy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ProfesorActivity extends AppCompatActivity {

    private Button intrebari;
    private Button istoric;
    private Button teste;
    private Button desfasurare;
    private ImageView inapoi;
    private ImageView start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor);

        initializare();
    }

    private void initializare() {

        intrebari = findViewById(R.id.intrebari_button_profesor);
        istoric = findViewById(R.id.istoric_button_profesor);
        teste = findViewById(R.id.teste_button_profesor);
        desfasurare = findViewById(R.id.desfasurare_button_profesor);
        inapoi = findViewById(R.id.back_image_profesor);
        start = findViewById(R.id.start_image_profesor);

        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        istoric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IstoricProfesorActivity.class);
                startActivity(intent);
            }
        });

        teste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TesteActivity.class);
                startActivity(intent);
            }
        });

        intrebari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MateriiActivity.class);
                startActivity(intent);
            }
        });

        desfasurare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScorLiveActivity.class);
                startActivity(intent);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StartQuizActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
