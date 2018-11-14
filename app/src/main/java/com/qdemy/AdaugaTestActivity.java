package com.qdemy;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class AdaugaTestActivity extends AppCompatActivity {

    private ImageView inapoi;
    private TextInputEditText nume;
    private TextInputEditText descriere;
    private Spinner durate;
    private Spinner materii;
    private Spinner intrebari;
    private Button adaugaIntrebare;
    private Button adaugaTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adauga_test);

        initializare();
    }

    private void initializare() {

        inapoi = findViewById(R.id.back_image_adaugaTest);
        nume = findViewById(R.id.nume_text_adaugaTest);
        descriere = findViewById(R.id.descriere_text_adaugaTest);
        durate = findViewById(R.id.durate_spinner_adaugaTest);
        materii = findViewById(R.id.materii_spinner_adaugaTest);
        intrebari = findViewById(R.id.intrebare_spinner_adaugaTest);
        adaugaIntrebare = findViewById(R.id.adaugaIntrebare_button_adaugaTest);
        adaugaTest = findViewById(R.id.adauga_button_adaugaTest);

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

        adaugaTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adauga testul
                finish();
            }
        });
    }
}