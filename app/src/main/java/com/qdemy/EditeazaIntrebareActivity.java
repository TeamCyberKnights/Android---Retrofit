package com.qdemy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.qdemy.servicii.IntrebareGrilaService;
import com.qdemy.servicii.NetworkConnectionService;
import com.qdemy.servicii.ProfesorService;
import com.qdemy.servicii.ServiceBuilder;
import com.qdemy.servicii.VariantaRaspunsService;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
    private List<VariantaRaspuns> varianteRaspuns2;
    private IntrebareGrila intrebareGrila;
    private boolean internetIsAvailable;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editeaza_intrebare);
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if ( activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting()) {
            internetIsAvailable = true;
        }
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


        if (internetIsAvailable) {
            VariantaRaspunsService variantaRaspunsService = ServiceBuilder.buildService(VariantaRaspunsService.class);
            Call<List<VariantaRaspuns>> varianteRaspunsRequest = variantaRaspunsService.getVarianteRaspunsByIntrebareId((int)(long)intrebare.getId());
            varianteRaspunsRequest.enqueue(new Callback<List<VariantaRaspuns>>() {
                @Override
                public void onResponse(Call<List<VariantaRaspuns>> call, Response<List<VariantaRaspuns>> response) {
                    varianteRaspuns2 = response.body();
                }

                @Override
                public void onFailure(Call<List<VariantaRaspuns>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Variantele de raspuns nu au putut fi gasite", Toast.LENGTH_SHORT).show();
                }
            });

            List<VariantaRaspuns> varianteRaspunsActualizate = new ArrayList<>();
            //preluare variante care NU se modifica + stergere variante care nu exista
            for ( VariantaRaspuns varianta : varianteRaspuns2) {
                boolean exista = false;
                for (int i = 0; i < varianteRaspuns.size(); i++)
                    if (varianta.getText().equals(varianteRaspuns.get(i).getText()) &&
                            varianta.getCorect() == varianteRaspuns.get(i).getCorect())
                    { varianteRaspunsActualizate.add(varianteRaspuns.get(i));
                        exista = true; break;}
                if(exista==false)
                {
                    Call<VariantaRaspuns> deleteVariantaRaspuns = variantaRaspunsService.deleteVariantaRaspunsById((int)(long)varianta.getId());
                    deleteVariantaRaspuns.enqueue(new Callback<VariantaRaspuns>() {
                        @Override
                        public void onResponse(Call<VariantaRaspuns> call, Response<VariantaRaspuns> response) {
                            Toast.makeText(getApplicationContext(), "Varianta de raspuns a fost stearsa cu succes", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<VariantaRaspuns> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Varianta de raspuns nu a putut fi stearsa", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

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

                    Call<VariantaRaspuns> insertVariantaRaspuns = variantaRaspunsService
                            .saveVariantaRaspuns(new VariantaRaspuns(varianta.getText(), varianta.getCorect(), intrebare.getId()));
                    insertVariantaRaspuns.enqueue(new Callback<VariantaRaspuns>() {
                        @Override
                        public void onResponse(Call<VariantaRaspuns> call, Response<VariantaRaspuns> response) {
                            Toast.makeText(getApplicationContext(), "Varianta de raspuns a fost salvata cu succes", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<VariantaRaspuns> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Varianta de raspuns nu a putut fi salvata", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }

            //actualizare intrebare

            IntrebareGrilaService intrebareGrilaService = ServiceBuilder.buildService(IntrebareGrilaService.class);
            final Call<IntrebareGrila> intrebareGrilaRequest = intrebareGrilaService.getIntrebareGrilaById((int)(long)intrebare.getId());
            intrebareGrilaRequest.enqueue(new Callback<IntrebareGrila>() {
                @Override
                public void onResponse(Call<IntrebareGrila> call, Response<IntrebareGrila> response) {
                    intrebareGrila = response.body();
                }

                @Override
                public void onFailure(Call<IntrebareGrila> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Intrebarea nu a putut fi gasita", Toast.LENGTH_SHORT).show();
                }
            });



            IntrebareGrila intrebareDeActualizat = intrebareGrila ;
            intrebareDeActualizat.setText(text.getText().toString());
            intrebareDeActualizat.setDificultate(dificultatiSpinner.getSelectedItemPosition()+1);
            intrebareDeActualizat.setVariante(varianteRaspunsActualizate);

            Call<IntrebareGrila> updateIntrebareGrila = intrebareGrilaService.updateIntrebareGrila((int)(long)intrebareDeActualizat.getId(), intrebare);
            updateIntrebareGrila.enqueue(new Callback<IntrebareGrila>() {
                @Override
                public void onResponse(Call<IntrebareGrila> call, Response<IntrebareGrila> response) {
                    Toast.makeText(getApplicationContext(), "Intrebarea a fost actualizata", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<IntrebareGrila> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Intrebarea nu a putut fi actualizata", Toast.LENGTH_SHORT).show();
                }
            });


            //actualizare profesor
            ProfesorService profesorService = ServiceBuilder.buildService(ProfesorService.class);
            Call<Profesor> updateProfesorRequest = profesorService.updateProfesor((int)(long)profesor.getId(), profesor);
            updateProfesorRequest.enqueue(new Callback<Profesor>() {
                @Override
                public void onResponse(Call<Profesor> call, Response<Profesor> response) {
                    Toast.makeText(getApplicationContext(), "Datele au fost actualizate", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Profesor> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Datele nu au putut fi actualizate", Toast.LENGTH_SHORT).show();
                }
            });

        }



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


        //actualizare intrebare
        Query<IntrebareGrila> queryIntrebare = ((App) getApplication()).getDaoSession().getIntrebareGrilaDao().queryBuilder().where(
                IntrebareGrilaDao.Properties.Id.eq(intrebare.getId())).build();


        IntrebareGrila intrebareDeActualizat = queryIntrebare.list().get(0);
        intrebareDeActualizat.setText(text.getText().toString());
        intrebareDeActualizat.setDificultate(dificultatiSpinner.getSelectedItemPosition()+1);
        intrebareDeActualizat.setVariante(varianteRaspunsActualizate);
        ((App) getApplication()).getDaoSession().getIntrebareGrilaDao().update(intrebare);


        //actualizare profesor
        profesor.setIntrebare(intrebareDeActualizat);
        ((App) getApplication()).getDaoSession().getProfesorDao().update(profesor);

        finish();
    }
}
