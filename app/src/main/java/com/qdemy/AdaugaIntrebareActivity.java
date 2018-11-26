package com.qdemy;

import android.media.Image;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class AdaugaIntrebareActivity extends AppCompatActivity {

    private ImageView inapoi;
    private TextInputEditText nume;
    private TextInputEditText intrebare;
    private Spinner dificultatiSpinner;
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
        dificultatiSpinner = findViewById(R.id.dificultati_spinner_adaugaIntrebare);

        String[] dificultati = new String[] {
                getString(R.string.usor),
                getString(R.string.mediu),
                getString(R.string.greu)
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, dificultati);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dificultatiSpinner.setAdapter(adapter);


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
