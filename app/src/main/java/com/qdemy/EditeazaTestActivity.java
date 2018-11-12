package com.qdemy;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class EditeazaTestActivity extends AppCompatActivity {

    private ImageView inapoi;
    private TextInputEditText nume;
    private TextInputEditText descriere;
    private Spinner durate;
    private Spinner materii;
    private Spinner intrebari;
    private Button adaugaIntrebare;
    private Button actualizeazaTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editeaza_test);

        initializare();
    }

    private void initializare() {

        inapoi = findViewById(R.id.back_image_editeazaTest);
        nume = findViewById(R.id.nume_text_editeazaTest);
        descriere = findViewById(R.id.descriere_text_editeazaTest);
        durate = findViewById(R.id.durate_spinner_editeazaTest);
        materii = findViewById(R.id.materii_spinner_editeazaTest);
        intrebari = findViewById(R.id.intrebari_spinner_editeazaTest);
        adaugaIntrebare = findViewById(R.id.adaugaIntrebare_button_editeazaTest);
        actualizeazaTest = findViewById(R.id.actualizeaza_button_editeazaTest);

        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adaugaIntrebare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adauga intrebarea in layout
            }
        });

        actualizeazaTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adauga testul
                Intent intent = new Intent(getApplicationContext(), TesteActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
