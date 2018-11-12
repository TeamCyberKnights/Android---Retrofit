package com.qdemy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class PartajeazaTestActivity extends AppCompatActivity {

    private ImageView inapoi;
    private Button partajeaza;
    private Spinner persoane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partajeaza_test);

        initializare();
    }

    private void initializare() {

        inapoi = findViewById(R.id.back_image_partajeazaTest);
        partajeaza = findViewById(R.id.partajeaza_button_partajeazaTest);
        persoane = findViewById(R.id.persoane_spinner_partajeazaTest);

        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        partajeaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adauga persoana in layout
            }
        });
    }
}
