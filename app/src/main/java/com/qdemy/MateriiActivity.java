package com.qdemy;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class MateriiActivity extends AppCompatActivity {

    private ImageView inapoi;
    private TextView titlu;
    private ListView materiiList;
    private Spinner materiiSpinner;
    private Button adaugaMaterie;
    private List<String> materii = new ArrayList<>();
    private Profesor profesor;
    private List<String> nomenclator_materii = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materii);

        initializare();
    }

    private void initializare() {

        //region Initializare componente vizuale
        inapoi = findViewById(R.id.back_image_materii);
        titlu = findViewById(R.id.titlu_text_Materii);
        materiiList = findViewById(R.id.materii_list_materii);
        materiiSpinner = findViewById(R.id.nomenclator_spinner_materii);
        adaugaMaterie = findViewById(R.id.adauga_button_materii);
        //endregion

        titlu.setText(getIntent().getStringExtra(Constante.CHEIE_TRANSFER));
        if(titlu.getText().equals(getString(R.string.testele_mele))) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) materiiList.getLayoutParams();
            params.weight = 8.5f;
            materiiList.setLayoutParams(params);
            materiiSpinner.setVisibility(View.GONE);
            adaugaMaterie.setVisibility(View.GONE);
        }

        //region Initializare profesor

        profesor = ((App) getApplication()).getProfesor();

        if(profesor.getMaterii()!=null) materii = profesor.getNumeMaterii();
        final MaterieAdapter adapter = new MaterieAdapter(getApplicationContext(), R.layout.item_text_button,
                materii, getLayoutInflater(), MateriiActivity.this, titlu.getText().toString());
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
        adapterNomenclator.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        materiiSpinner.setAdapter(adapterNomenclator);
        //endregion


        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
