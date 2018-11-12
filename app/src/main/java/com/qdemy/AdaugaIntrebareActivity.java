package com.qdemy;

import android.media.Image;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AdaugaIntrebareActivity extends AppCompatActivity {

    private ImageView inapoi;
    private TextInputEditText nume;
    private TextInputEditText intrebare;
    // raspunsurile + checkBox-uri vor fi adaugate ca adapter personalizat
    private Button adauga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adauga_intrebare);

        initializare();
    }

    private void initializare() {

        inapoi = findViewById(R.id.back_image_adaugaIntrebare);
        nume = findViewById(R.id.nume_textInput_adaugaIntrebare);
        intrebare = findViewById(R.id.descriere_textInput_adaugaIntrebare);
        adauga = findViewById(R.id.adauga_button_adaugaIntrebare);

        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adauga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adaugare intrebare
            }
        });
    }
}
