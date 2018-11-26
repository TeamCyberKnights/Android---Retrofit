package com.qdemy;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class EditeazaIntrebareActivity extends AppCompatActivity {

    private ImageView inapoi;
    private TextInputEditText nume;
    private TextInputEditText intrebare;
    private Spinner dificultatiSpinner;
    // raspunsurile + checkBox-uri vor fi adaugate ca adapter personalizat
    private Button actualizeaza;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editeaza_intrebare);

        initializare();
    }

    private void initializare() {
        inapoi = findViewById(R.id.back_image_editeazaIntrebare);
        nume = findViewById(R.id.nume_textInput_editeazaIntrebare);
        intrebare = findViewById(R.id.descriere_textInput_editeazaIntrebare);
        actualizeaza = findViewById(R.id.editeaza_button_editeazaIntrebare);
        dificultatiSpinner = findViewById(R.id.dificultati_spinner_editeazaIntrebare);

        String[] dificultati = new String[] {
                getString(R.string.usor),
                getString(R.string.mediu),
                getString(R.string.greu)
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, dificultati);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dificultatiSpinner.setAdapter(adapter);

        //INITIALIZARE CAMPURI CU INTREBAREA DE EDITAT

        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        actualizeaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //actualizeaza intrebare
            }
        });
    }
}
