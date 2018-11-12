package com.qdemy;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class TesteActivity extends AppCompatActivity {

    private ImageView inapoi;
    private FloatingActionButton adauga;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);

        initializare();
    }

    private void initializare() {

        inapoi = findViewById(R.id.back_image_teste);
        adauga = findViewById(R.id.adauga_button_teste);

        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adauga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdaugaTestActivity.class);
                startActivity(intent);
            }
        });
    }
}
