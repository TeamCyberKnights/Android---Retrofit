package com.qdemy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.qdemy.clase.EvidentaMateriiProfesori;
import com.qdemy.clase.EvidentaMateriiProfesoriDao;
import com.qdemy.clase.IntrebareGrila;
import com.qdemy.clase.Materie;
import com.qdemy.clase.MaterieDao;
import com.qdemy.clase.Profesor;
import com.qdemy.clase_adapter.IntrebareAdapter;
import com.qdemy.clase_adapter.MaterieAdapter;
import com.qdemy.db.App;
import com.qdemy.db.DbOpenHelper;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MateriiActivity extends AppCompatActivity {

    private ImageView inapoi;
    private ImageView acasa;
    private ImageView istoric;
    private ImageView teste;
    private ImageView intrebari;
    private ListView materiiList;
    private Spinner materiiSpinner;
    private Button adaugaMaterie;
    private List<String> materii = new ArrayList<>();
    private Profesor profesor;
    private List<String> nomenclator_materii = new ArrayList<>();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materii);

        initializare();
    }

    private void initializare() {

        //region Initializare componente vizuale
        inapoi = findViewById(R.id.back_image_materii);
        acasa = findViewById(R.id.acasa_image_materii);
        istoric = findViewById(R.id.istoric_image_materii);
        teste = findViewById(R.id.teste_image_materii);
        intrebari = findViewById(R.id.intrebari_image_materii);
        materiiList = findViewById(R.id.materii_list_materii);
        materiiSpinner = findViewById(R.id.nomenclator_spinner_materii);
        adaugaMaterie = findViewById(R.id.adauga_button_materii);
        //endregion

        if(getIntent().getStringExtra(Constante.CHEIE_TRANSFER).equals(getString(R.string.testele_mele))) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) materiiList.getLayoutParams();
            params.weight = 8.5f;
            materiiList.setLayoutParams(params);
            materiiSpinner.setVisibility(View.GONE);
            adaugaMaterie.setVisibility(View.GONE);
            teste.setImageResource(R.drawable.teste);
            intrebari.setImageResource(R.drawable.intrebarix);
            LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) intrebari.getLayoutParams();
            params1.weight=1;
            params1.setMargins(15,15,15,15);
            LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) teste.getLayoutParams();
            params2.weight=1.5f;
        }

        //region Initializare profesor

        profesor = ((App) getApplication()).getProfesor();

        if(profesor.getMaterii()!=null) materii = profesor.getNumeMaterii();
        final MaterieAdapter adapter = new MaterieAdapter(getApplicationContext(), R.layout.item_text_button,
                materii, getLayoutInflater(), MateriiActivity.this, getIntent().getStringExtra(Constante.CHEIE_TRANSFER));
        materiiList.setAdapter(adapter);

        Query<Materie> query = ((App) getApplication()).getDaoSession().getMaterieDao().queryBuilder().build();
        nomenclator_materii.add(query.list().get(0).getNume());
        nomenclator_materii.add(query.list().get(1).getNume());
        nomenclator_materii.add(query.list().get(2).getNume());
        nomenclator_materii.add(query.list().get(3).getNume());
        nomenclator_materii.add(query.list().get(4).getNume());
        nomenclator_materii.add(query.list().get(5).getNume());

        actualizeazaNomenclator();
        final ArrayAdapter<String> adapterNomenclator = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, nomenclator_materii);
        adapterNomenclator.setDropDownViewResource(R.layout.item_spinner);
        materiiSpinner.setAdapter(adapterNomenclator);
        //endregion


        adaugaMaterie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materii.add(materiiSpinner.getSelectedItem().toString());
                adapter.notifyDataSetChanged();

                actualizeazaMateriiProfesor();

                actualizeazaNomenclator();
                adapterNomenclator.notifyDataSetChanged();

            }
        });

        //region Meniu

        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(v.getContext());
                dlgAlert.setMessage(R.string.deconectare_message);
                dlgAlert.setTitle(R.string.deconectare_title);
                dlgAlert.setPositiveButton(R.string.da, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                dlgAlert.setNegativeButton(R.string.nu, null);
                dlgAlert.setCancelable(true);
                AlertDialog dialog = dlgAlert.create();
                dialog.show();
                dialog.getWindow().setBackgroundDrawableResource(R.color.bej);
            }
        });

        acasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfesorActivity.class);
                startActivity(intent);
                finish();
            }
        });

        istoric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IstoricProfesorActivity.class);
                startActivity(intent);
                finish();
            }
        });

        teste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MateriiActivity.class);
                intent.putExtra(Constante.CHEIE_TRANSFER, getString(R.string.testele_mele));
                startActivity(intent);
                finish();
            }
        });

        intrebari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MateriiActivity.class);
                intent.putExtra(Constante.CHEIE_TRANSFER, getString(R.string.ntreb_rile_mele));
                startActivity(intent);
                finish();
            }
        });

        //endregion


    }



    public void actualizeazaNomenclator()
    {
        for(int i=0;i<nomenclator_materii.size();i++)
            for(int j=0;j<materii.size();j++)
                if(nomenclator_materii.get(i).equals(materii.get(j)))
                    nomenclator_materii.remove(i);
    }

    public void actualizeazaMateriiProfesor()
    {
        Query<Materie> queryMaterii = ((App) getApplication()).getDaoSession().getMaterieDao().queryBuilder().where(
                MaterieDao.Properties.Nume.eq(materiiSpinner.getSelectedItem().toString())).build();

        ((App) getApplication()).getDaoSession().getEvidentaMateriiProfesoriDao().insert(
                new EvidentaMateriiProfesori(queryMaterii.list().get(0).getId(), profesor.getId()));

        List<Materie> materiile = profesor.getMaterii();
        materiile.add(queryMaterii.list().get(0));
        profesor.setMaterii(materiile);
        ((App) getApplication()).getDaoSession().getProfesorDao().update(profesor);
    }

    public void stergeMaterieProfesor(String  numeMaterie)
    {
        Query<Materie> queryMaterii = ((App) getApplication()).getDaoSession().getMaterieDao().queryBuilder().where(
                MaterieDao.Properties.Nume.eq(numeMaterie)).build();
        Query<EvidentaMateriiProfesori> queryEvidenta = ((App) getApplication()).getDaoSession().getEvidentaMateriiProfesoriDao()
                .queryBuilder().where(EvidentaMateriiProfesoriDao.Properties.MaterieId.eq(queryMaterii.list().get(0).getId()),
                                      EvidentaMateriiProfesoriDao.Properties.ProfesorId.eq(profesor.getId())).build();

        ((App) getApplication()).getDaoSession().getEvidentaMateriiProfesoriDao().deleteByKey(queryEvidenta.list().get(0).getId());

        List<Materie> materiile = profesor.getMaterii();
        materiile.remove(queryMaterii.list().get(0));
        profesor.setMaterii(materiile);
        ((App) getApplication()).getDaoSession().getProfesorDao().update(profesor);
    }
}
