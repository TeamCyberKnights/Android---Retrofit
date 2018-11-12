package com.qdemy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class MateriiActivity extends AppCompatActivity {

    private ImageView inapoi;
    private Spinner materii;
    private Button adaugaMaterie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materii);

        initializare();
    }

    private void initializare() {

        inapoi = findViewById(R.id.back_image_materii);
        materii = findViewById(R.id.nomenclator_spinner_materii);
        adaugaMaterie = findViewById(R.id.adauga_button_materii);
        //initializare nomenclator cu materii

        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adaugaMaterie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adauga materie in layout
            }
        });
    }
}
