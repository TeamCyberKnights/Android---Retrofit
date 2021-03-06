package com.qdemy;

import android.content.Context;
import android.content.Intent;
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

import com.qdemy.clase.IntrebareGrila;
import com.qdemy.clase.IntrebareGrilaDao;
import com.qdemy.clase.Profesor;
import com.qdemy.clase.VariantaRaspuns;
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

    private IntrebareGrila intrebareGrila;
    private IntrebareGrila intrebareGrila2;
    List<IntrebareGrila> intrebari = new ArrayList<>();
    private boolean internetIsAvailable = false;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adauga_intrebare);

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
        adapter.setDropDownViewResource(R.layout.item_spinner);
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

                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                adaugaIntrebare();

            }
        });
    }


    private void adaugaIntrebare() {

        //adaugare intrebare


        if (internetIsAvailable) {
            IntrebareGrilaService intrebareGrilaService = ServiceBuilder.buildService(IntrebareGrilaService.class);
            final Call<IntrebareGrila> intrebareGrilaRequest = intrebareGrilaService
                    .getIntrebareGrilaByTextMaterieAndProfesorId(text.getText().toString(), materie.getText().toString(), (int)(long)profesor.getId());
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
            if(intrebareGrila!=null)
            {
                Toast.makeText(getApplicationContext(), getString(R.string.error_message_intrebare_existenta), Toast.LENGTH_LONG).show();
                return;
            }



            Call<IntrebareGrila> insertIntrebareGrilaRequest = intrebareGrilaService.saveIntrebareGrila(new IntrebareGrila(text.getText().toString(),
                    materie.getText().toString(), (dificultatiSpinner.getSelectedItemPosition()+1), profesor.getId()));
            insertIntrebareGrilaRequest.enqueue(new Callback<IntrebareGrila>() {
                @Override
                public void onResponse(Call<IntrebareGrila> call, Response<IntrebareGrila> response) {
                    Toast.makeText(getApplicationContext(), "Intrebarea a fost salvata", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<IntrebareGrila> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Intrebarea nu a putut fi salvata", Toast.LENGTH_SHORT).show();
                }
            });

            Call<IntrebareGrila> intrebareGrilaRequest2 = intrebareGrilaService
                    .getIntrebareGrilaByTextMaterieAndProfesorId(text.getText().toString(), materie.getText().toString(), (int)(long)profesor.getId());
            intrebareGrilaRequest2.enqueue(new Callback<IntrebareGrila>() {
                @Override
                public void onResponse(Call<IntrebareGrila> call, Response<IntrebareGrila> response) {
                    intrebareGrila2 = response.body();
                }

                @Override
                public void onFailure(Call<IntrebareGrila> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Intrebarea nu a putut fi gasita", Toast.LENGTH_SHORT).show();
                }
            });


            IntrebareGrila intrebare =   intrebareGrila2;
            intrebare.setVariante(varianteRaspuns);
            Call<IntrebareGrila> updateIntrebareGrilaRequest = intrebareGrilaService.updateIntrebareGrila((int)(long)intrebare.getId(), intrebare);
            updateIntrebareGrilaRequest.enqueue(new Callback<IntrebareGrila>() {
                @Override
                public void onResponse(Call<IntrebareGrila> call, Response<IntrebareGrila> response) {
                    Toast.makeText(getApplicationContext(), "Intrebarea a fost actualizata", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<IntrebareGrila> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Intrebarea nu a putut fi actualizata", Toast.LENGTH_SHORT).show();
                }
            });


            //adaugare variante raspuns

            VariantaRaspunsService variantaRaspunsService = ServiceBuilder.buildService(VariantaRaspunsService.class);
            for ( VariantaRaspuns varianta : varianteRaspuns) {
                Call<VariantaRaspuns> insertVariantaRaspunsRequest = variantaRaspunsService.saveVariantaRaspuns(varianta);
                insertIntrebareGrilaRequest.enqueue(new Callback<IntrebareGrila>() {
                    @Override
                    public void onResponse(Call<IntrebareGrila> call, Response<IntrebareGrila> response) {
                        Toast.makeText(getApplicationContext(), "Varianta de raspuns a fost salvata", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<IntrebareGrila> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Varianta de raspuns nu a putut fi salvata", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            Call<List<IntrebareGrila>> intrebGrilaRequest = intrebareGrilaService.getIntrebariGrilaByProfesorId((int)(long)profesor.getId());
            intrebGrilaRequest.enqueue(new Callback<List<IntrebareGrila>>() {
                @Override
                public void onResponse(Call<List<IntrebareGrila>> call, Response<List<IntrebareGrila>> response) {
                    intrebari = response.body();
                }

                @Override
                public void onFailure(Call<List<IntrebareGrila>> call, Throwable t) {

                }
            });


            intrebari.add(intrebare);
            profesor.setIntrebari(intrebari);

            ProfesorService profesorService = ServiceBuilder.buildService(ProfesorService.class);
            Call<Profesor> updateProfesorRequest = profesorService.updateProfesor((int)(long)profesor.getId(), profesor);
            updateProfesorRequest.enqueue(new Callback<Profesor>() {
                @Override
                public void onResponse(Call<Profesor> call, Response<Profesor> response) {
                    Toast.makeText(getApplicationContext(), "Profesorul a fost actualizat", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Profesor> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Profesorul nu a putut fi actualizat", Toast.LENGTH_SHORT).show();
                }
            });


//            Intent intent = new Intent(this, IntrebariMaterieActivity.class);
//            intent.putExtra(Constante.CHEIE_TRANSFER, intrebare.getMaterie());
//            startActivity(intent);
        }




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
