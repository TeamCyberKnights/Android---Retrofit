package com.qdemy;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qdemy.clase.IntrebareGrila;

public class IntrebareActivity extends AppCompatActivity {

    private ImageView inapoi;
    private FloatingActionButton editeaza;
    private TextView materia;
    private TextView numeIntrebare;
    private TextView continutIntrebare;
    private TextView variantaA;
    private TextView variantaB;
    private TextView variantaC;
    private TextView variantaD;
    private TextView variantaE;
    private TextView variantaF;

    private IntrebareGrila intrebare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intrebare);

        initialzare();
    }

    private void initialzare() {

        inapoi = findViewById(R.id.back_image_intrebare);
        editeaza = findViewById(R.id.editeaza_button_intrebare);
        materia = findViewById(R.id.materia_text_intrebare);
        numeIntrebare = findViewById(R.id.intrebare_text_intrebare);
        continutIntrebare = findViewById(R.id.descriere_text_intrebare);
        variantaA = findViewById(R.id.A_text_raspuns);
        variantaB = findViewById(R.id.B_text_raspuns);
        variantaC = findViewById(R.id.C_text_raspuns);
        variantaD = findViewById(R.id.D_text_raspuns);
        variantaE = findViewById(R.id.E_text_raspuns);
        variantaF = findViewById(R.id.F_text_raspuns);

        intrebare = getIntent().getParcelableExtra(Constante.CHEIE_AUTENTIFICARE);
        materia.setText(intrebare.getMaterie());
        numeIntrebare.setText(intrebare.getNume());
        continutIntrebare.setText(intrebare.getContinut());
        variantaA.setText(intrebare.getVariante(0));
        variantaB.setText(intrebare.getVariante(1));
        if(intrebare.getVariante(2)!=null)
        { variantaC.setText(intrebare.getVariante(2)); variantaC.setVisibility(View.VISIBLE);}
        if(intrebare.getVariante(3)!=null)
        { variantaD.setText(intrebare.getVariante(3)); variantaD.setVisibility(View.VISIBLE);}
        if(intrebare.getVariante(4)!=null)
        { variantaE.setText(intrebare.getVariante(4)); variantaE.setVisibility(View.VISIBLE);}
        if(intrebare.getVariante(5)!=null)
        { variantaF.setText(intrebare.getVariante(5)); variantaF.setVisibility(View.VISIBLE);}


        //INITIALIZARE CAMPURI CU INTREBAREA SELECTATA

        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editeaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditeazaIntrebareActivity.class);
                startActivity(intent);
            }
        });
    }
}
