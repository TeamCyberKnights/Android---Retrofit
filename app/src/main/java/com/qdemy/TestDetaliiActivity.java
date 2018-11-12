package com.qdemy;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class TestDetaliiActivity extends AppCompatActivity {

    private ImageView inapoi;
    private FloatingActionButton editeaza;
    private FloatingActionButton partajeaza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_detalii);

        initializare();
    }

    private void initializare() {

        inapoi = findViewById(R.id.back_image_testDetalii);
        editeaza = findViewById(R.id.editeaza_button_testDetalii);
        partajeaza = findViewById(R.id.partajeaza_button_testDetalii);

        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editeaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditeazaTestActivity.class);
                startActivity(intent);
            }
        });

        partajeaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PartajeazaTestActivity.class);
                startActivity(intent);
            }
        });
    }
}
