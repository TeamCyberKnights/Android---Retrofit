package com.qdemy;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.qdemy.clase.IntrebareGrila;
//DE SCOOOOOS!!!!!!!!!!
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

        //region Initializare componente vizuale
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
        //endregion

        //region Initializare intrebare
        intrebare = getIntent().getParcelableExtra(Constante.CHEIE_TRANSFER);
        materia.setText(intrebare.getMaterie());
        //numeIntrebare.setText(intrebare.getNume());
        //continutIntrebare.setText(intrebare.getContinut());

        //int nr_variante = intrebare.getVariante().size();
        getVariantaRaspuns(variantaA,0);
        getVariantaRaspuns(variantaB,1);
        //if(nr_variante>=3) getVariantaRaspuns(variantaC, 2);
        //if(nr_variante>=4) getVariantaRaspuns(variantaD, 3);
        //if(nr_variante>=5) getVariantaRaspuns(variantaE, 4);
        //if(nr_variante==6) getVariantaRaspuns(variantaF, 5);
        //endregion


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
                //intent.putExtra(Constante.CHEIE_TRANSFER, intrebare);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getVariantaRaspuns(TextView varianta, int index) {
        varianta.setText(intrebare.getVariante(index).getText());
        varianta.setVisibility(View.VISIBLE);
        varianta.setTextColor(intrebare.getVariante(index).getCorect()? Color.GREEN : Color.BLACK);
    }
}
