package com.qdemy;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.qdemy.clase.EvidentaIntrebariTeste;
import com.qdemy.clase.IntrebareGrila;
import com.qdemy.clase.IntrebareGrilaDao;
import com.qdemy.clase.Profesor;
import com.qdemy.clase.Test;
import com.qdemy.clase.TestDao;
import com.qdemy.clase_adapter.AdaugaTestAdapter;
import com.qdemy.db.App;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AdaugaTestActivity extends AppCompatActivity {

    private ImageView inapoi;
    private TextInputEditText nume;
    private TextInputEditText descriere;
    private Spinner durateSpinner;
    private Button adaugaTest;
    private ListView intrebariList;
    private RadioGroup tipuri;
    private RadioButton estePublic;
    private RadioButton estePrivat;
    public List<IntrebareGrila> intrebari = new ArrayList<>();
    public List<Boolean> selectari = new ArrayList<>();
    public List<IntrebareGrila> intrebariSelectate = new ArrayList<>();
    private Map<String, Integer> durate = new TreeMap<String, Integer>();

    private Profesor profesor;
    private String materie;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adauga_test);

        initializare();
    }

    private void initializare() {

        //region Initializare componente vizuale
        inapoi = findViewById(R.id.back_image_adaugaTest);
        nume = findViewById(R.id.nume_textInput_adaugaTest);
        descriere = findViewById(R.id.descriere_textInput_adaugaTest);
        durateSpinner = findViewById(R.id.durate_spinner_adaugaTest);
        adaugaTest = findViewById(R.id.adauga_button_adaugaTest);
        intrebariList = findViewById(R.id.intrebari_list_adaugaTest);
        tipuri = findViewById(R.id.tipuri_radioGroup_adaugaTest);
        estePrivat = findViewById(R.id.privat_radioButton_adaugaTest);
        estePublic = findViewById(R.id.public_radioButton_adaugaTest);

        durate.put("10 minute",10);
        durate.put("15 minute",15);
        durate.put("30 minute",30);
        durate.put("45 minute",45);
        durate.put("o oră",60);
        durate.put("întregul seminar",80);
        List<String> list = new ArrayList<String>(durate.keySet());
        ArrayAdapter<String> adapterDurate = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        adapterDurate.setDropDownViewResource(R.layout.item_spinner);
        durateSpinner.setAdapter(adapterDurate);


        profesor = ((App) getApplication()).getProfesor();
        materie = getIntent().getStringExtra(Constante.CHEIE_TRANSFER);

        //COSMIN - TO DO SELECT toate intrebarile materiei testului
        Query<IntrebareGrila> queryIntrebari = ((App) getApplication()).getDaoSession().getIntrebareGrilaDao().queryBuilder().where(
                IntrebareGrilaDao.Properties.Materie.eq(materie),
                IntrebareGrilaDao.Properties.ProfesorId.eq(profesor.getId())).build();
        intrebari = queryIntrebari.list();
        for(int i=0;i<intrebari.size();i++)
            selectari.add(false);

        final AdaugaTestAdapter adapter = new AdaugaTestAdapter(getApplicationContext(),
                R.layout.item_text_button, intrebari, getLayoutInflater(), AdaugaTestActivity.this);
        intrebariList.setAdapter(adapter);

        //endregion

        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        adaugaTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (intrebariSelectate==null)
                {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_message_min_1_intrebare), Toast.LENGTH_LONG).show();
                    return;
                }

                if (nume.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_message_nume_inexistent), Toast.LENGTH_LONG).show();
                    return;
                }

                if (descriere.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_message_descriere_inexistenta), Toast.LENGTH_LONG).show();
                    return;
                }

                adaugaTest();
            }
        });

    }


    private void adaugaTest() {

        //adaugare test
        //COSMIN - TO DO SELECT TEST DACA EXISTA
        Query<Test> queryTest = ((App) getApplication()).getDaoSession().getTestDao().queryBuilder().where(
                TestDao.Properties.Nume.eq(nume.getText().toString())).build();

        if(queryTest.list().size()>0)
        {
            Toast.makeText(getApplicationContext(), getString(R.string.error_message_test_existent), Toast.LENGTH_LONG).show();
            return;
        }
        //COSMIN - TO DO INSERARE TEST
        ((App) getApplication()).getDaoSession().getTestDao().insert(
                new Test(nume.getText().toString(), descriere.getText().toString(), durate.get(durateSpinner.getSelectedItem()),
                        tipuri.getCheckedRadioButtonId()==estePublic.getId()?true:false, materie, profesor.getId()));

        //COSMIN - TO DO SELECT TEST NOU ADAUGAT
        queryTest = ((App) getApplication()).getDaoSession().getTestDao().queryBuilder().where(
                TestDao.Properties.Nume.eq(nume.getText().toString()),
                TestDao.Properties.Materie.eq(materie),
                TestDao.Properties.ProfesorId.eq(profesor.getId())).build();

        //COSMIN - TO DO UPDATE TEST NOU CU LISTA INTREBARI SELECTATE
        Test test = queryTest.list().get(0);
        test.setIntrebari(intrebariSelectate);
        ((App) getApplication()).getDaoSession().getTestDao().update(test);


        //adaugare evidenta intrebari test
        //COSMIN - TO DO INSERT EVIDENTA INTREBARI TESTE
        for ( IntrebareGrila intrebare : intrebariSelectate) {
            ((App) getApplication()).getDaoSession().getEvidentaIntrebariTesteDao().insert(
                    new EvidentaIntrebariTeste(intrebare.getId(), test.getId()));
        }


        //actualizare profesor
        //COSMIN - TO DO UPDATE PROFESOR CU LISTA CU TESTUL NOU ADAUGAT
        List<Test> teste = profesor.getTeste();
        teste.add(test);
        profesor.setTeste(teste);
        ((App) getApplication()).getDaoSession().getProfesorDao().update(profesor);

        Intent intent = new Intent(this, TesteActivity.class);
        intent.putExtra(Constante.CHEIE_TRANSFER, materie);
        startActivity(intent);
        finish();
    }
}