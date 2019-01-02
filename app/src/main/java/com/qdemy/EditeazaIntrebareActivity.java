package com.qdemy;

import android.content.Context;
import android.content.Intent;
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
import com.qdemy.clase.IntrebareGrilaDao;
import com.qdemy.clase.Profesor;
import com.qdemy.clase.VariantaRaspuns;
import com.qdemy.clase.VariantaRaspunsDao;
import com.qdemy.db.App;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EditeazaIntrebareActivity extends AppCompatActivity {

    private ImageView inapoi;
    private TextView materia;
    private TextInputEditText text;
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
    private List<VariantaRaspuns> varianteRaspuns = new ArrayList<>();
    private List<Boolean> raspunsuri = new ArrayList<>();
    private SharedPreferences sharedPreferences;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

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
        text = findViewById(R.id.descriere_textInput_editeazaIntrebare);
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
        adapter.setDropDownViewResource(R.layout.item_spinner);
        dificultatiSpinner.setAdapter(adapter);

        profesor = ((App) getApplication()).getProfesor();

        //endregion

        //region Initilizare intrebare
        intrebare =  ((App) getApplication()).getIntrebareGrila(getIntent().getLongExtra(Constante.CHEIE_TRANSFER, -1));
        materia.setText(intrebare.getMaterie());
        text.setText(intrebare.getText());
        dificultatiSpinner.setSelection((int)intrebare.getDificultate()-1);

        int nr_variante = intrebare.getVariante().size();
        getVariantaRaspuns(variantaA, raspunsA, 0);
        getVariantaRaspuns(variantaB, raspunsB, 1);
        if(nr_variante>=3) getVariantaRaspuns(variantaC, raspunsC, 2);
        if(nr_variante>=4) getVariantaRaspuns(variantaD, raspunsD, 3);
        if(nr_variante>=5) getVariantaRaspuns(variantaE, raspunsE, 4);
        if(nr_variante==6) getVariantaRaspuns(variantaF, raspunsF, 5);
        //endregion

        //region Variante raspuns

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

        //endregion


        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        actualizeaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //region Validare variante raspuns

                boolean raspunsuriCorecte = false;
                if(raspunsA.isChecked() || raspunsB.isChecked() ||raspunsC.isChecked() ||
                        raspunsD.isChecked() ||raspunsE.isChecked() ||raspunsF.isChecked())
                    raspunsuriCorecte=true;
                if (raspunsuriCorecte==false)
                {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_message_min_1_corect), Toast.LENGTH_LONG).show();
                    return;
                }
                if (text.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_message_intrebare_inexistenta), Toast.LENGTH_LONG).show();
                    return;
                }

                if (!(text.getText().toString().contains("?")))
                {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_message_intrebare_invalida), Toast.LENGTH_LONG).show();
                    return;
                }

                if(!(variantaA.getText().toString().trim().isEmpty()))
                    varianteRaspuns.add(new VariantaRaspuns(variantaA.getText().toString(), raspunsA.isChecked()));
                else {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_message_min_2_variante), Toast.LENGTH_LONG).show();
                    return;
                }
                if(!(variantaB.getText().toString().trim().isEmpty()))
                    varianteRaspuns.add(new VariantaRaspuns(variantaB.getText().toString(), raspunsB.isChecked()));
                else {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_message_min_2_variante), Toast.LENGTH_LONG).show();
                    return;
                }
                if(!(variantaC.getText().toString().trim().isEmpty()))
                    varianteRaspuns.add(new VariantaRaspuns(variantaC.getText().toString(), raspunsC.isChecked()));
                if(!(variantaD.getText().toString().trim().isEmpty()))
                    varianteRaspuns.add(new VariantaRaspuns(variantaD.getText().toString(), raspunsD.isChecked()));
                if(!(variantaE.getText().toString().trim().isEmpty()))
                    varianteRaspuns.add(new VariantaRaspuns(variantaE.getText().toString(), raspunsE.isChecked()));
                if(!(variantaF.getText().toString().trim().isEmpty()))
                    varianteRaspuns.add(new VariantaRaspuns(variantaF.getText().toString(), raspunsF.isChecked()));


                //endregion

                //////////////////////////////////////////////////////////////////////////////////////////////////////////

                actualizeazaIntrebare();
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

    private void actualizeazaIntrebare() {

        //COSMIN - TO DO SELECT VARIANTELE RASPUNS ALE INTREBARII CURENTE
        Query<VariantaRaspuns> queryVarianteRaspuns = ((App) getApplication()).getDaoSession().getVariantaRaspunsDao().queryBuilder().where(
                VariantaRaspunsDao.Properties.IntrebareId.eq(intrebare.getId())).build();

        List<VariantaRaspuns> varianteRaspunsActualizate = new ArrayList<>();
        //preluare variante care NU se modifica + stergere variante care nu exista
        for ( VariantaRaspuns varianta : queryVarianteRaspuns.list()) {
            boolean exista = false;
            for (int i = 0; i < varianteRaspuns.size(); i++)
                if (varianta.getText().equals(varianteRaspuns.get(i).getText()) &&
                        varianta.getCorect() == varianteRaspuns.get(i).getCorect())
                { varianteRaspunsActualizate.add(varianteRaspuns.get(i));
                  exista = true; break;}
            if(exista==false)
                ((App) getApplication()).getDaoSession().getVariantaRaspunsDao().deleteByKey(varianta.getId());
        }
        //preluare variante care se modifica + adaugare in bd
        for ( VariantaRaspuns varianta : varianteRaspuns) {
            boolean exista = false;
            for (int i = 0; i < varianteRaspunsActualizate.size(); i++)
                if (varianta.getText().equals(varianteRaspunsActualizate.get(i).getText()) &&
                    varianta.getCorect() == varianteRaspunsActualizate.get(i).getCorect())
                    exista=true;
            if(exista==false) {
                varianteRaspunsActualizate.add(varianta);
                ((App) getApplication()).getDaoSession().getVariantaRaspunsDao().insert(
                        new VariantaRaspuns(varianta.getText(), varianta.getCorect(), intrebare.getId()));
            }
        }

        /////////////////// stergere variante

        //COSMIN - TO DO SELECT INTREBARE CURENTA
        //actualizare intrebare
        Query<IntrebareGrila> queryIntrebare = ((App) getApplication()).getDaoSession().getIntrebareGrilaDao().queryBuilder().where(
                IntrebareGrilaDao.Properties.Id.eq(intrebare.getId())).build();

        //COSMIN - TO DO UPDATE INTREBARE CU NOUA LISTA DE VARIANTE RASPUNS
        IntrebareGrila intrebareDeActualizat = queryIntrebare.list().get(0);
        intrebareDeActualizat.setText(text.getText().toString());
        intrebareDeActualizat.setDificultate(dificultatiSpinner.getSelectedItemPosition()+1);
        intrebareDeActualizat.setVariante(varianteRaspunsActualizate);
        ((App) getApplication()).getDaoSession().getIntrebareGrilaDao().update(intrebare);

        //COSMIN - TO DO UPDATE PROFESOR CU INTREBAREA ACTUALIZATA
        //actualizare profesor
        profesor.setIntrebare(intrebareDeActualizat);
        ((App) getApplication()).getDaoSession().getProfesorDao().update(profesor);

        finish();
    }
}
