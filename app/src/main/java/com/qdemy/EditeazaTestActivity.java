package com.qdemy;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.TextView;
import android.widget.Toast;

import com.qdemy.clase.EvidentaIntrebariTeste;
import com.qdemy.clase.EvidentaIntrebariTesteDao;
import com.qdemy.clase.IntrebareGrila;
import com.qdemy.clase.IntrebareGrilaDao;
import com.qdemy.clase.Profesor;
import com.qdemy.clase.Test;
import com.qdemy.clase.TestDao;
import com.qdemy.clase_adapter.AdaugaTestAdapter;
import com.qdemy.clase_adapter.EditeazaTestAdapter;
import com.qdemy.db.App;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class EditeazaTestActivity extends AppCompatActivity {

    private ImageView inapoi;
    private TextView nume;
    private TextInputEditText descriere;
    private Spinner durateSpinner;
    private Button actualizeazaTest;
    private ListView intrebariList;
    private RadioGroup tipuri;
    private RadioButton estePublic;
    private RadioButton estePrivat;
    private FloatingActionButton partajeaza;
    public List<IntrebareGrila> intrebari = new ArrayList<>();
    public List<Boolean> selectari = new ArrayList<>();
    public List<IntrebareGrila> intrebariSelectate = new ArrayList<>();
    private Map<String, Integer> durate = new TreeMap<String, Integer>();

    private Profesor profesor;
    private Test test;
    private String materie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editeaza_test);

        initializare();
    }

    private void initializare() {

        //region Initializare componenete vizuale
        inapoi = findViewById(R.id.back_image_editeazaTest);
        nume = findViewById(R.id.nume_text_editeazaTest);
        descriere = findViewById(R.id.descriere_textInput_editeazaTest);
        durateSpinner = findViewById(R.id.durate_spinner_editeazaTest);
        actualizeazaTest = findViewById(R.id.actualizeaza_button_editeazaTest);
        intrebariList = findViewById(R.id.intrebari_list_editeazaTest);
        tipuri = findViewById(R.id.tipuri_radioGroup_editeazaTest);
        estePrivat = findViewById(R.id.privat_radioButton_editeazaTest);
        estePublic = findViewById(R.id.public_radioButton_editeazaTest);
        partajeaza = findViewById(R.id.partajeaza_button_editeazaTest);

        durate.put("10 minute",10);
        durate.put("15 minute",15);
        durate.put("30 minute",30);
        durate.put("45 minute",45);
        durate.put("o oră",60);
        durate.put("întregul seminar",80);

        List<String> list = new ArrayList<String>(durate.keySet());
        ArrayAdapter<String> adapterDurate = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        adapterDurate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        durateSpinner.setAdapter(adapterDurate);
        //endregion

        //region Initializare test
        profesor = ((App) getApplication()).getProfesor();
        test =  ((App) getApplication()).getTest(getIntent().getLongExtra(Constante.CHEIE_TRANSFER, -1));

        materie = test.getMaterie();
        nume.setText(test.getNume());
        descriere.setText(test.getDescriere());
        for (String s : durate.keySet()) {
            if (durate.get(s).equals(test.getTimpDisponibil())) {
                durateSpinner.setSelection(adapterDurate.getPosition(s));
                break;
            }
        }
        tipuri.check(test.getEstePublic()?estePublic.getId():estePrivat.getId());



        Query<IntrebareGrila> queryIntrebari = ((App) getApplication()).getDaoSession().getIntrebareGrilaDao().queryBuilder().where(
                IntrebareGrilaDao.Properties.Materie.eq(materie),
                IntrebareGrilaDao.Properties.ProfesorId.eq(profesor.getId())).build();
        intrebari = queryIntrebari.list();
        intrebariSelectate = test.getIntrebari();

        for(int i=0;i<intrebari.size();i++) {
            boolean selectat = false;
            for (int j = 0; j < intrebariSelectate.size(); j++)
                if (intrebari.get(i).equals(intrebariSelectate.get(j))) {selectat = true; break;}

            selectari.add(selectat);
        }

        final EditeazaTestAdapter adapter = new EditeazaTestAdapter(getApplicationContext(),
                R.layout.item_text_button, intrebari, getLayoutInflater(), EditeazaTestActivity.this);
        intrebariList.setAdapter(adapter);


        //endregion


        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        actualizeazaTest.setOnClickListener(new View.OnClickListener() {
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

                actualizeazaTest();
            }
        });


        partajeaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PartajeazaTestActivity.class);
                intent.putExtra(Constante.CHEIE_TRANSFER, test.getId());
                startActivity(intent);
            }
        });

    }

    private void actualizeazaTest() {

        //selectare test
        Query<Test> queryTest = ((App) getApplication()).getDaoSession().getTestDao().queryBuilder().where(
                TestDao.Properties.Nume.eq(nume.getText().toString()),
                TestDao.Properties.Materie.eq(materie),
                TestDao.Properties.ProfesorId.eq(profesor.getId())).build();

        //actualizare test
        Test test = queryTest.list().get(0);
        test.setIntrebari(intrebariSelectate);
        test.setDescriere(descriere.getText().toString());
        test.setTimpDisponibil(durate.get(durateSpinner.getSelectedItem()));
        test.setEstePublic(tipuri.getCheckedRadioButtonId()==estePublic.getId()?true:false);
        ((App) getApplication()).getDaoSession().getTestDao().update(test);


        //actualizare evidenta intrebari test
        //actualizare intrebari
        for ( IntrebareGrila intrebare : intrebariSelectate) {
            Query<EvidentaIntrebariTeste> queryEvidentaIntrebare = ((App) getApplication()).getDaoSession().getEvidentaIntrebariTesteDao().queryBuilder().where(
                    EvidentaIntrebariTesteDao.Properties.IntrebareId.eq(intrebare.getId()),
                    EvidentaIntrebariTesteDao.Properties.TestId.eq(test.getId())).build();

            if(queryEvidentaIntrebare.list().size()<1)
                ((App) getApplication()).getDaoSession().getEvidentaIntrebariTesteDao().insert(
                        new EvidentaIntrebariTeste(intrebare.getId(), test.getId()));
        }

        //stergere intrebari neselectate (selectate anterior)
        Query<EvidentaIntrebariTeste> queryEvidentaIntrebari = ((App) getApplication()).getDaoSession().getEvidentaIntrebariTesteDao().queryBuilder().where(
                EvidentaIntrebariTesteDao.Properties.TestId.eq(test.getId())).build();
        for ( EvidentaIntrebariTeste evidentaIntrebare : queryEvidentaIntrebari.list()) {
            boolean gasit = false;
            for (int i = 0; i < intrebariSelectate.size(); i++)
                if(intrebariSelectate.get(i).getId().equals(evidentaIntrebare.getIntrebareId()))
                {gasit=true; break;}

            if(gasit==false)
                ((App) getApplication()).getDaoSession().getEvidentaIntrebariTesteDao().deleteByKey(evidentaIntrebare.getId());
        }


        //actualizare profesor
        profesor.setTest(test);
        ((App) getApplication()).getDaoSession().getProfesorDao().update(profesor);

        finish();
    }


}
