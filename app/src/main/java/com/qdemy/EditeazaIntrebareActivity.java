package com.qdemy;

import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qdemy.clase.IntrebareGrila;
import com.qdemy.clase.Profesor;
import com.qdemy.clase.VariantaRaspuns;

import java.util.ArrayList;
import java.util.List;

public class EditeazaIntrebareActivity extends AppCompatActivity {

    private ImageView inapoi;
    private TextView materia;
    private TextView nume;
    private TextInputEditText continut;
    private TextInputEditText variantaA;
    private TextInputEditText variantaB;
    private TextInputEditText variantaC;
    private TextInputEditText variantaD;
    private TextInputEditText variantaE;
    private TextInputEditText variantaF;
    private CheckBox raspunsA;
    private CheckBox raspunsB;
    private CheckBox raspunsC;
    private CheckBox raspunsD;
    private CheckBox raspunsE;
    private CheckBox raspunsF;
    private Spinner dificultatiSpinner;
    // raspunsurile + checkBox-uri vor fi adaugate ca adapter personalizat
    private Button actualizeaza;

    private Profesor profesor;
    private IntrebareGrila intrebare;
    private List<VariantaRaspuns> variante = new ArrayList<>();
    private List<Boolean> raspunsuri = new ArrayList<>();
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editeaza_intrebare);

        initializare();
    }

    private void initializare() {

        //region Initializare componente vizuale
        inapoi = findViewById(R.id.back_image_editeazaIntrebare);
        materia = findViewById(R.id.materia_text_editeazaIntrebare);
        nume = findViewById(R.id.nume_text_editeazaIntrebare);
        continut = findViewById(R.id.descriere_textInput_editeazaIntrebare);
        actualizeaza = findViewById(R.id.editeaza_button_editeazaIntrebare);
        variantaA = findViewById(R.id.A_textInput_editeazaIntrebare);
        variantaB = findViewById(R.id.B_textInput_editeazaIntrebare);
        variantaC = findViewById(R.id.C_textInput_editeazaIntrebare);
        variantaD = findViewById(R.id.D_textInput_editeazaIntrebare);
        variantaE = findViewById(R.id.E_textInput_editeazaIntrebare);
        variantaF = findViewById(R.id.F_textInput_editeazaIntrebare);
        raspunsA = findViewById(R.id.A_checkBox_editeazaIntrebare);
        raspunsB = findViewById(R.id.B_checkBox_editeazaIntrebare);
        raspunsC = findViewById(R.id.C_checkBox_editeazaIntrebare);
        raspunsD = findViewById(R.id.D_checkBox_editeazaIntrebare);
        raspunsE = findViewById(R.id.E_checkBox_editeazaIntrebare);
        raspunsF = findViewById(R.id.F_checkBox_editeazaIntrebare);

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

        sharedPreferences = getSharedPreferences(Constante.FISIER_PREFERINTA_UTILIZATOR, MODE_PRIVATE);
        incarcareUtilizatorSalvat();
        //endregion

        //region Initilizare intrebare
        intrebare = getIntent().getParcelableExtra(Constante.CHEIE_TRANSFER);
        materia.setText(intrebare.getMaterie());
        nume.setText(intrebare.getNume());
        continut.setText(intrebare.getContinut());
        dificultatiSpinner.setSelection((int)intrebare.getPunctaj()-1);

        int nr_variante = intrebare.getVariante().size();
        getVariantaRaspuns(variantaA, raspunsA, 0);
        getVariantaRaspuns(variantaB, raspunsB, 1);
        if(nr_variante>=3) getVariantaRaspuns(variantaC, raspunsC, 2);
        if(nr_variante>=4) getVariantaRaspuns(variantaD, raspunsD, 3);
        if(nr_variante>=5) getVariantaRaspuns(variantaE, raspunsE, 4);
        if(nr_variante==6) getVariantaRaspuns(variantaF, raspunsF, 5);
        //endregion


        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        variantaB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0)
                {
                    variantaC.setVisibility(View.VISIBLE);
                    raspunsC.setVisibility(View.VISIBLE);
                }
                else
                {
                    variantaC.setVisibility(View.INVISIBLE);
                    raspunsC.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        variantaC.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0)
                {
                    variantaD.setVisibility(View.VISIBLE);
                    raspunsD.setVisibility(View.VISIBLE);
                }
                else
                {
                    variantaD.setVisibility(View.INVISIBLE);
                    raspunsD.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        variantaD.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0)
                {
                    variantaE.setVisibility(View.VISIBLE);
                    raspunsE.setVisibility(View.VISIBLE);
                }
                else
                {
                    variantaE.setVisibility(View.INVISIBLE);
                    raspunsE.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        variantaE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0)
                {
                    variantaF.setVisibility(View.VISIBLE);
                    raspunsF.setVisibility(View.VISIBLE);
                }
                else
                {
                    variantaF.setVisibility(View.INVISIBLE);
                    raspunsF.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        actualizeaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(variantaA.getText().toString().trim().isEmpty()))
                    variante.add(new VariantaRaspuns(variantaA.getText().toString(), raspunsA.isChecked()));
                else {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_message_min_2_variante), Toast.LENGTH_LONG).show();
                    return;
                }
                if(!(variantaB.getText().toString().trim().isEmpty()))
                    variante.add(new VariantaRaspuns(variantaB.getText().toString(), raspunsB.isChecked()));
                else {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_message_min_2_variante), Toast.LENGTH_LONG).show();
                    return;
                }
                if(!(variantaC.getText().toString().trim().isEmpty()))
                    variante.add(new VariantaRaspuns(variantaC.getText().toString(), raspunsC.isChecked()));
                if(!(variantaD.getText().toString().trim().isEmpty()))
                    variante.add(new VariantaRaspuns(variantaD.getText().toString(), raspunsD.isChecked()));
                if(!(variantaE.getText().toString().trim().isEmpty()))
                    variante.add(new VariantaRaspuns(variantaE.getText().toString(), raspunsE.isChecked()));
                if(!(variantaF.getText().toString().trim().isEmpty()))
                    variante.add(new VariantaRaspuns(variantaF.getText().toString(), raspunsF.isChecked()));

                //////////////////////////////////////////////////////////////////////////////////////////////////////////

                intrebare.setContinut(continut.getText().toString());
                intrebare.setPunctaj(dificultatiSpinner.getSelectedItemPosition()+1);
                intrebare.setVariante(variante);
                profesor.setIntrebari(intrebare, intrebare.getNume());
                salvareUtilizator();
                finish();
            }
        });

    }


    private void getVariantaRaspuns(TextInputEditText varianta, CheckBox raspuns, int index) {
        varianta.setText(intrebare.getVariante(index).getText());
        varianta.setVisibility(View.VISIBLE);
        raspuns.setChecked(intrebare.getVariante(index).getCorect());
        raspuns.setVisibility(View.VISIBLE);
    }

    private void incarcareUtilizatorSalvat() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(getString(R.string.utilizator), "");
        profesor = gson.fromJson(json, Profesor.class);
    }

    private void salvareUtilizator() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(profesor);
        editor.putString(getString(R.string.utilizator), json);
        editor.commit();
    }
}
