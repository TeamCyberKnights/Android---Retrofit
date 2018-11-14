package com.qdemy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class RezumatTestActivity extends AppCompatActivity {

    private TextView numeTest;
    private TextView promovati;
    private TextView nume;
    private TextView scor;
    private ImageView inapoi;
    private RadioButton studenti;
    private RadioButton intrebari;
    private RadioGroup optiuni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezumat_test);

        initializare();
    }

    private void initializare() {

        numeTest = findViewById(R.id.test_text_rezumatTest);
        promovati = findViewById(R.id.promovati_text_rezumatTest);
        nume = findViewById(R.id.nume_text_rezumatTest);
        scor = findViewById(R.id.scor_text_rezumatTest);
        inapoi = findViewById(R.id.back_image_rezumatTest);
        studenti = findViewById(R.id.studenti_radioButton_rezumatTest);
        intrebari = findViewById(R.id.intrebari_radioButton_rezumatTest);
        optiuni = findViewById(R.id.optiuni_radioGroup_rezumatTest);

        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        optiuni.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(studenti.isChecked())
                {
                    scor.setText("Corecte");
                    nume.setText("Întrebare");
                }
                else
                {   scor.setText("Punctaj");
                    nume.setText("Nume student");

                }
            }
        });

    }
}
