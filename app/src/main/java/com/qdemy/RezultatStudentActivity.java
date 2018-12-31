package com.qdemy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.qdemy.clase.RaspunsIntrebareGrila;
import com.qdemy.clase.RezultatTestStudent;
import com.qdemy.clase.TestSustinut;
import com.qdemy.clase.TestSustinutDao;
import com.qdemy.clase_adapter.MaterieAdapter;
import com.qdemy.clase_adapter.RezultatStudentAdapter;
import com.qdemy.db.App;

import org.greenrobot.greendao.query.Query;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RezultatStudentActivity extends AppCompatActivity {

    private TextView scor;
    private Button salveaza;
    private RezultatTestStudent rezultat;
    private List<RaspunsIntrebareGrila> raspunsuri;
    private ListView intrebariList;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezultat_student);

        initializare();
    }

    private void initializare() {

        scor = findViewById(R.id.scor_text_rezultatStudent);
        salveaza = findViewById(R.id.salveaza_button_rezultatStudent);
        intrebariList = findViewById(R.id.intrebari_list_rezultatStudent);

        rezultat = ((App) getApplication()).getRezultatTest(getIntent().getLongExtra(Constante.CHEIE_TRANSFER, -1));
        raspunsuri = rezultat.getRaspunsuri();
        scor.setText(String.valueOf((int)((App) getApplication()).getPunctajTest(rezultat.getRaspunsuri())));
        scor.setBackgroundColor(rezultat.getPromovat()?ContextCompat.getColor(getApplicationContext(), R.color.verde):
                ContextCompat.getColor(getApplicationContext(), R.color.rosu));

        RezultatStudentAdapter adapter = new RezultatStudentAdapter(getApplicationContext(),
                R.layout.item_text_button, raspunsuri, getLayoutInflater(), RezultatStudentActivity.this);
        intrebariList.setAdapter(adapter);


        /*//ACTUALIZARE TEST SUSTINUT CU REZULTATUL ACESTUI STUDENT
        long id=-1; //= preluat de pe server
        Query<TestSustinut> queryTestSustinut = ((App) getApplication()).getDaoSession().getTestSustinutDao().queryBuilder().where(
                TestSustinutDao.Properties.Id.eq(id)).build();
        TestSustinut testSustinut = queryTestSustinut.list().get(0);
        List<RezultatTestStudent> rezultate = testSustinut.getRezultate();
        rezultate.add(rezultat);
        testSustinut.setRezultate(rezultate);
        ((App) getApplication()).getDaoSession().getTestSustinutDao().update(testSustinut);*/

        salveaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
