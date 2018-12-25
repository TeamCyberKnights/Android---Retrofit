package com.qdemy;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.qdemy.clase.EvidentaIntrebariTeste;
import com.qdemy.clase.EvidentaIntrebariTesteDao;
import com.qdemy.clase.EvidentaMateriiProfesori;
import com.qdemy.clase.EvidentaMateriiProfesoriDao;
import com.qdemy.clase.IntrebareGrila;
import com.qdemy.clase.IntrebareGrilaDao;
import com.qdemy.clase.Profesor;
import com.qdemy.clase.Test;
import com.qdemy.clase.TestDao;
import com.qdemy.clase.TestPartajat;
import com.qdemy.clase.TestPartajatDao;
import com.qdemy.clase.VariantaRaspuns;
import com.qdemy.clase.VariantaRaspunsDao;
import com.qdemy.clase_adapter.IntrebareAdapter;
import com.qdemy.clase_adapter.TestAdapter;
import com.qdemy.db.App;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

public class TesteActivity extends AppCompatActivity {

    private ImageView inapoi;
    private FloatingActionButton adauga;
    private ListView testeList;
    private String materie;
    private TextView materieTitlu;
    private List<Test> teste = new ArrayList<>();
    private Profesor profesor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);

        initializare();
    }

    private void initializare() {

        inapoi = findViewById(R.id.back_image_teste);
        adauga = findViewById(R.id.adauga_button_teste);
        testeList = findViewById(R.id.teste_list_teste);
        materieTitlu = findViewById(R.id.titlu_text_teste);


        //region Initializare intrebari
        profesor = ((App) getApplication()).getProfesor();

        materie = getIntent().getStringExtra(Constante.CHEIE_TRANSFER);
        materieTitlu.setText(materie);

        //incarcare teste proprii si partajate
        for(int i=0;i<profesor.getTeste().size();i++)
            if(profesor.getTeste(i).getMaterie().equals(materie))
                teste.add(profesor.getTeste(i));
        Query<TestPartajat> queryTestePartajate = ((App) getApplication()).getDaoSession().getTestPartajatDao().queryBuilder().where(
                TestPartajatDao.Properties.ProfesorId.eq(profesor.getId())).build();
        for(int i=0;i<queryTestePartajate.list().size();i++) {
            Query<Test> queryTest = ((App) getApplication()).getDaoSession().getTestDao().queryBuilder().where(
                    TestDao.Properties.Id.eq(queryTestePartajate.list().get(i).getId())).build();
            if (queryTest.list().get(0).getMaterie().equals(materie))
                teste.add(queryTest.list().get(0));
        }


        TestAdapter adapter = new TestAdapter(getApplicationContext(), R.layout.item_text_button_button,
                teste, getLayoutInflater(), true, TesteActivity.this, profesor.getId());
        testeList.setAdapter(adapter);
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
                Intent intent = new Intent(getApplicationContext(), AdaugaTestActivity.class);
                intent.putExtra(Constante.CHEIE_TRANSFER, materie);
                startActivity(intent);
                finish();
            }
        });

    }


    public void stergeTest(String numeTest)
    {
        //selectare test
        Query<Test> queryTest = ((App) getApplication()).getDaoSession().getTestDao().queryBuilder().where(
                TestDao.Properties.Nume.eq(numeTest),
                TestDao.Properties.Materie.eq(materie),
                TestDao.Properties.ProfesorId.eq(profesor.getId())).build();


        //stergere evidente intrebari test
        Query<EvidentaIntrebariTeste> queryEvidenta = ((App) getApplication()).getDaoSession().getEvidentaIntrebariTesteDao()
                .queryBuilder().where(EvidentaIntrebariTesteDao.Properties.TestId.eq(queryTest.list().get(0).getId())).build();

        for ( EvidentaIntrebariTeste evidenta : queryEvidenta.list()) {
            ((App) getApplication()).getDaoSession().getEvidentaIntrebariTesteDao().deleteByKey(evidenta.getId()); }


        //stergere test partajat
        Query<TestPartajat> queryTestePartajate = ((App) getApplication()).getDaoSession().getTestPartajatDao()
                .queryBuilder().where(TestPartajatDao.Properties.TestId.eq(queryTest.list().get(0).getId())).build();

        for ( TestPartajat testPartajat : queryTestePartajate.list()) {
            ((App) getApplication()).getDaoSession().getTestPartajatDao().deleteByKey(testPartajat.getId()); }


        //actualizare profesor
        List<Test> testeActualizate = profesor.getTeste();
        testeActualizate.remove(queryTest.list().get(0));
        profesor.setTeste(testeActualizate);
        ((App) getApplication()).getDaoSession().getProfesorDao().update(profesor);


        //NU se sterge testul ci doar se elimina id-ul profesorului
        queryTest.list().get(0).setProfesorId(-1);
        ((App) getApplication()).getDaoSession().getTestDao().update(queryTest.list().get(0));
    }
}
