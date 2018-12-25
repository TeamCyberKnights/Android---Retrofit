package com.qdemy;

import android.content.Intent;
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

import com.qdemy.clase.IntrebareGrila;
import com.qdemy.clase.IntrebareGrilaDao;
import com.qdemy.clase.Profesor;
import com.qdemy.clase.VariantaRaspuns;
import com.qdemy.db.App;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

public class AdaugaIntrebareActivity extends AppCompatActivity {

    private ImageView inapoi;
    private TextView materie;
    private TextInputEditText nume;
    private TextInputEditText text;
    private Spinner dificultatiSpinner;
    private Button adauga;
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

    private Profesor profesor;
    public List<VariantaRaspuns> varianteRaspuns = new ArrayList<>();

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
        text = findViewById(R.id.descriere_textInput_adaugaIntrebare);
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


        profesor = ((App) getApplication()).getProfesor();
        materie.setText(getIntent().getStringExtra(Constante.CHEIE_TRANSFER));

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

        //endregion


        //region Variante raspuns

        variantaA.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0)
                    variantaA.setHint("");
            }

            @Override
            public void afterTextChanged(Editable s) {

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
                    variantaB.setHint("");
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
                    variantaC.setHint("");
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
                    variantaD.setHint("");
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
                if (s.length() != 0) {
                    variantaF.setVisibility(View.VISIBLE);
                    raspunsF.setVisibility(View.VISIBLE);
                    variantaE.setHint("");
                } else {
                    variantaF.setVisibility(View.INVISIBLE);
                    raspunsF.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        variantaF.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0)
                    variantaF.setHint("");
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


        adauga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //region Validare variante raspuns

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
                //endregion

                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                adaugaIntrebare();

            }
        });
    }


    private void adaugaIntrebare() {

        //adaugare intrebare
        Query<IntrebareGrila> queryIntrebare = ((App) getApplication()).getDaoSession().getIntrebareGrilaDao().queryBuilder().where(
                IntrebareGrilaDao.Properties.Text.eq(text.getText().toString()),
                IntrebareGrilaDao.Properties.Materie.eq(materie.getText().toString()),
                IntrebareGrilaDao.Properties.ProfesorId.eq(profesor.getId())).build();

        if(queryIntrebare.list().size()>0)
        {
            Toast.makeText(getApplicationContext(), getString(R.string.error_message_intrebare_existenta), Toast.LENGTH_LONG).show();
            return;
        }
        ((App) getApplication()).getDaoSession().getIntrebareGrilaDao().insert(new IntrebareGrila(text.getText().toString(),
                    materie.getText().toString(), (dificultatiSpinner.getSelectedItemPosition()+1), profesor.getId()));

        queryIntrebare = ((App) getApplication()).getDaoSession().getIntrebareGrilaDao().queryBuilder().where(
                IntrebareGrilaDao.Properties.Text.eq(text.getText().toString()),
                IntrebareGrilaDao.Properties.Materie.eq(materie.getText().toString()),
                IntrebareGrilaDao.Properties.ProfesorId.eq(profesor.getId())).build();
        IntrebareGrila intrebare = queryIntrebare.list().get(0);
        intrebare.setVariante(varianteRaspuns);
        ((App) getApplication()).getDaoSession().getIntrebareGrilaDao().update(intrebare);


        //adaugare variante raspuns
        for ( VariantaRaspuns varianta : varianteRaspuns) {
                ((App) getApplication()).getDaoSession().getVariantaRaspunsDao().insert(
                 new VariantaRaspuns(varianta.getText(), varianta.getCorect(), intrebare.getId()));
        }


        //actualizare profesor
        List<IntrebareGrila> intrebari = profesor.getIntrebari();
        intrebari.add(intrebare);
        profesor.setIntrebari(intrebari);
        ((App) getApplication()).getDaoSession().getProfesorDao().update(profesor);

        Intent intent = new Intent(this, IntrebariMaterieActivity.class);
        intent.putExtra(Constante.CHEIE_TRANSFER, intrebare.getMaterie());
        startActivity(intent);
        finish();
    }


}
