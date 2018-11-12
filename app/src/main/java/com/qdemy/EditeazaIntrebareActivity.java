package com.qdemy;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class EditeazaIntrebareActivity extends AppCompatActivity {

    private ImageView inapoi;
    private TextInputEditText nume;
    private TextInputEditText intrebare;
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
