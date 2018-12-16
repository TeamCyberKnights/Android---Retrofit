package com.qdemy;

import android.content.SharedPreferences;
import android.media.Image;
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

public class AdaugaIntrebareActivity extends AppCompatActivity {

    private ImageView inapoi;
    private TextView materie;
    private TextInputEditText nume;
    private TextInputEditText continut;
    private Spinner dificultatiSpinner;
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
    private Button adauga;

    private Profesor profesor;
    private IntrebareGrila intrebare;
    private List<VariantaRaspuns> variante = new ArrayList<>();
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adauga_intrebare);

        initializare();
    }

    private void initializare() {

        //region Initializare componente vizuale
        inapoi = findViewById(R.id.back_image_adaugaIntrebare);
        materie = findViewById(R.id.materia_text_adaugaIntrebare);
        nume = findViewById(R.id.nume_textInput_adaugaIntrebare);
        continut = findViewById(R.id.descriere_textInput_adaugaIntrebare);
        adauga = findViewById(R.id.adauga_button_adaugaIntrebare);
        variantaA = findViewById(R.id.A_textInput_adaugaIntrebare);
        variantaB = findViewById(R.id.B_textInput_adaugaIntrebare);
        variantaC = findViewById(R.id.C_textInput_adaugaIntrebare);
        variantaD = findViewById(R.id.D_textInput_adaugaIntrebare);
        variantaE = findViewById(R.id.E_textInput_adaugaIntrebare);
        variantaF = findViewById(R.id.F_textInput_adaugaIntrebare);
        raspunsA = findViewById(R.id.A_checkBox_adaugaIntrebare);
        raspunsB = findViewById(R.id.B_checkBox_adaugaIntrebare);
        raspunsC = findViewById(R.id.C_checkBox_adaugaIntrebare);
        raspunsD = findViewById(R.id.D_checkBox_adaugaIntrebare);
        raspunsE = findViewById(R.id.E_checkBox_adaugaIntrebare);
        raspunsF = findViewById(R.id.F_checkBox_adaugaIntrebare);

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

        sharedPreferences = getSharedPreferences(Constante.FISIER_PREFERINTA_UTILIZATOR, MODE_PRIVATE);
        incarcareUtilizatorSalvat();
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

        adauga.setOnClickListener(new View.OnClickListener() {
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

                ////////////////////////////////////////////////////////////////////////////////////////////////////////

                intrebare = new IntrebareGrila(nume.getText().toString(), continut.getText().toString(), materie.getText().toString(),
                        variante, dificultatiSpinner.getSelectedItemPosition()+1);
                profesor.getIntrebari().add(intrebare);
                salvareUtilizator();
                finish();

            }
        });
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
