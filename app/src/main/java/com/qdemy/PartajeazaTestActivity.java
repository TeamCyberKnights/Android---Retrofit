package com.qdemy;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.qdemy.clase.Profesor;
import com.qdemy.clase.ProfesorDao;
import com.qdemy.clase.Test;
import com.qdemy.clase.TestPartajat;
import com.qdemy.clase.TestPartajatDao;
import com.qdemy.clase_adapter.MaterieAdapter;
import com.qdemy.clase_adapter.PartajareTestAdapter;
import com.qdemy.db.App;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PartajeazaTestActivity extends AppCompatActivity {

    private ImageView inapoi;
    private TextView nume;
    private Button partajeaza;
    private Spinner profesoriSpinner;
    private ListView profesoriList;
    private List<String> numeProfesori = new ArrayList<>();
    private List<Profesor> profesoriPartajati = new ArrayList<>();
    private ArrayAdapter<String> adapterProfesori;
    private PartajareTestAdapter adapterProfesoriPartajati;

    private Test test;
    private Profesor profesorAutor;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partajeaza_test);

        initializare();
    }

    private void initializare() {

        inapoi = findViewById(R.id.back_image_partajeazaTest);
        nume = findViewById(R.id.titlul_text_partajeazaTest);
        partajeaza = findViewById(R.id.partajeaza_button_partajeazaTest);
        profesoriSpinner = findViewById(R.id.persoane_spinner_partajeazaTest);
        profesoriList = findViewById(R.id.persoane_list_partajeazaTest);

        profesorAutor = ((App) getApplication()).getProfesor();
        test =  ((App) getApplication()).getTest(getIntent().getLongExtra(Constante.CHEIE_TRANSFER, -1));
        nume.setText(test.getNume());

        //COSMIN - TO DO SELECT TOTI PROFESORI INAFARA DE CEL CURENT
        Query<Profesor> queryProfesori = ((App) getApplication()).getDaoSession().getProfesorDao().queryBuilder().build();

        for ( Profesor profesor : queryProfesori.list()) {
            if(!(profesor.equals(profesorAutor))) numeProfesori.add(profesor.getUtilizator());
        }
        adapterProfesori = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, numeProfesori);
        adapterProfesori.setDropDownViewResource(R.layout.item_spinner);
        profesoriSpinner.setAdapter(adapterProfesori);

        //COSMIN - TO DO SELECT PROFESORII PARTAJATI CU TESTUL CURENT
        Query<TestPartajat> queryTestPartajat = ((App) getApplication()).getDaoSession().getTestPartajatDao().queryBuilder().where(
                TestPartajatDao.Properties.TestId.eq(test.getId())).build();
        for ( TestPartajat testPartajat : queryTestPartajat.list()) {
            Query<Profesor> queryProfesor = ((App) getApplication()).getDaoSession().getProfesorDao().queryBuilder().where(
                    ProfesorDao.Properties.Id.eq(testPartajat.getProfesorId())).build();
            profesoriPartajati.add(queryProfesor.list().get(0));
            numeProfesori.remove(queryProfesor.list().get(0).getUtilizator());
        }
        adapterProfesoriPartajati = new PartajareTestAdapter(getApplicationContext(),
                R.layout.item_text_button, profesoriPartajati, getLayoutInflater(), PartajeazaTestActivity.this);
        profesoriList.setAdapter(adapterProfesoriPartajati);
        adapterProfesori.notifyDataSetChanged();

        
        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        partajeaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //actualizare componente vizuale
                //COSMIN - TO DO SELECT PROFESORI PARTAJATI
                Query<Profesor> queryProfesor = ((App) getApplication()).getDaoSession().getProfesorDao().queryBuilder().where(
                        ProfesorDao.Properties.Utilizator.eq(profesoriSpinner.getSelectedItem().toString())).build();
                profesoriPartajati.add(queryProfesor.list().get(0));
                numeProfesori.remove(queryProfesor.list().get(0).getUtilizator());
                adapterProfesori.notifyDataSetChanged();
                adapterProfesoriPartajati.notifyDataSetChanged();

                //actualizare teste pertajate
                //COSMIN - TO DO INSERT TEST PARTAJAT
                ((App) getApplication()).getDaoSession().getTestPartajatDao().insert(
                        new TestPartajat(test.getId(), queryProfesor.list().get(0).getId()));
            }
        });
    }

    public void stergeTest(Profesor profesor)
    {
        //selectare test partajat
        //COSMIN - TO DO SELECT TEST PARTAJAT
        Query<TestPartajat> queryTest = ((App) getApplication()).getDaoSession().getTestPartajatDao().queryBuilder().where(
                TestPartajatDao.Properties.ProfesorId.eq(profesor.getId()),
                TestPartajatDao.Properties.TestId.eq(test.getId())).build();

        //selectare profesor
        //COSMIN - TO DO SELECT PROFESOR PARTAJAT
        Query<Profesor> queryProfesor = ((App) getApplication()).getDaoSession().getProfesorDao().queryBuilder().where(
                ProfesorDao.Properties.Id.eq(queryTest.list().get(0).getProfesorId())).build();
//
        //stergere test partajat
        //COSMIN - TO DO DELETE TEST PARTAJAT
        ((App) getApplication()).getDaoSession().getTestPartajatDao().deleteByKey(queryTest.list().get(0).getId());

        profesoriPartajati.remove(queryProfesor.list().get(0));
        numeProfesori.add(queryProfesor.list().get(0).getUtilizator());
        adapterProfesori.notifyDataSetChanged();
        adapterProfesoriPartajati.notifyDataSetChanged();
    }
}
