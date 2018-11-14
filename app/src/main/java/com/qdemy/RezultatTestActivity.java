package com.qdemy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RezultatTestActivity extends AppCompatActivity {

    private ImageView inapoi;
    private TextView promovati;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezultat_test);

        initializare();
    }

    private void initializare() {

        inapoi = findViewById(R.id.back_image_rezultatTest);
        promovati = findViewById(R.id.promovati_text_rezultatTest);
        //cati promovati sunt

        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
