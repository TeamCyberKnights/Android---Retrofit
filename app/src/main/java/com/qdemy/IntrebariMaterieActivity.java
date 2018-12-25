package com.qdemy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qdemy.clase.EvidentaIntrebariTeste;
import com.qdemy.clase.EvidentaIntrebariTesteDao;
import com.qdemy.clase.EvidentaMateriiProfesori;
import com.qdemy.clase.EvidentaMateriiProfesoriDao;
import com.qdemy.clase.IntrebareGrila;
import com.qdemy.clase.IntrebareGrilaDao;
import com.qdemy.clase.Materie;
import com.qdemy.clase.MaterieDao;
import com.qdemy.clase.Profesor;
import com.qdemy.clase.VariantaRaspuns;
import com.qdemy.clase.VariantaRaspunsDao;
import com.qdemy.clase_adapter.IntrebareAdapter;
import com.qdemy.db.App;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

public class IntrebariMaterieActivity extends AppCompatActivity {

    private ImageView inapoi;
    private FloatingActionButton adauga;
    private ListView intrebariList;
    private TextView materieTitlu;
    private String materie;
    private List<IntrebareGrila> intrebari = new ArrayList<>();
    private IntrebareAdapter adapter;

    private Profesor profesor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intrebari_materie);

        initializare();
    }

    private void initializare() {

        //region Intializare componente vizuale
        inapoi = findViewById(R.id.back_image_intrebariMaterie);
        adauga = findViewById(R.id.adauga_button_intrebariMaterie);
        intrebariList = findViewById(R.id.intrebari_list_intrebariMaterie);
        materieTitlu = findViewById(R.id.titlu_text_intrebariMaterie);
        //endregion

        //region Initializare intrebari
        profesor = ((App) getApplication()).getProfesor();

        materie = getIntent().getStringExtra(Constante.CHEIE_TRANSFER);
        materieTitlu.setText(materie);

        for(int i=0;i<profesor.getIntrebari().size();i++)
            if(profesor.getIntrebari(i).getMaterie().equals(materie))
                intrebari.add(profesor.getIntrebari(i));
        adapter = new IntrebareAdapter(getApplicationContext(),
                R.layout.item_text_button, intrebari, getLayoutInflater(), IntrebariMaterieActivity.this);
        intrebariList.setAdapter(adapter);
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
                Intent intent = new Intent(getApplicationContext(), AdaugaIntrebareActivity.class);
                intent.putExtra(Constante.CHEIE_TRANSFER, materie);
                startActivity(intent);
                finish();
            }
        });
    }


    public void stergeIntrebare(String textIntrebare)
    {
        //selectareintrebare
        Query<IntrebareGrila> queryIntrebare = ((App) getApplication()).getDaoSession().getIntrebareGrilaDao().queryBuilder().where(
                IntrebareGrilaDao.Properties.Text.eq(textIntrebare),
                IntrebareGrilaDao.Properties.Materie.eq(materie),
                IntrebareGrilaDao.Properties.ProfesorId.eq(profesor.getId())).build();


        //stergere variante raspuns
        Query<VariantaRaspuns> queryVarianteRaspuns = ((App) getApplication()).getDaoSession().getVariantaRaspunsDao()
                .queryBuilder().where(VariantaRaspunsDao.Properties.IntrebareId.eq(queryIntrebare.list().get(0).getId())).build();
        for ( VariantaRaspuns varianta : queryVarianteRaspuns.list()) {
            ((App) getApplication()).getDaoSession().getVariantaRaspunsDao().deleteByKey(varianta.getId());
        }


        //stergere evidente intrebari test
        Query<EvidentaIntrebariTeste> queryEvidenta = ((App) getApplication()).getDaoSession().getEvidentaIntrebariTesteDao()
                .queryBuilder().where(EvidentaIntrebariTesteDao.Properties.IntrebareId.eq(queryIntrebare.list().get(0).getId())).build();

        for ( EvidentaIntrebariTeste evidenta : queryEvidenta.list()) {
            ((App) getApplication()).getDaoSession().getEvidentaIntrebariTesteDao().deleteByKey(evidenta.getId()); }


        //actualizare profesor
        List<IntrebareGrila> intrebariActualizate = profesor.getIntrebari();
        intrebariActualizate.remove(queryIntrebare.list().get(0));
        profesor.setIntrebari(intrebariActualizate);
        ((App) getApplication()).getDaoSession().getProfesorDao().update(profesor);
        ((App) getApplication()).getDaoSession().getIntrebareGrilaDao().deleteByKey(queryIntrebare.list().get(0).getId());
    }
}
