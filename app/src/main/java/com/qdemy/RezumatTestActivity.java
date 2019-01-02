package com.qdemy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.qdemy.clase.IntrebareGrila;
import com.qdemy.clase.RaspunsIntrebareGrila;
import com.qdemy.clase.RezultatTestStudent;
import com.qdemy.clase.Student;
import com.qdemy.clase.Test;
import com.qdemy.clase.TestDao;
import com.qdemy.clase.TestSustinut;
import com.qdemy.clase.TestSustinutDao;
import com.qdemy.clase_adapter.RezumatStudentAdapter;
import com.qdemy.clase_adapter.RezumatTestIntrebariAdapter;
import com.qdemy.clase_adapter.RezumatTestStudentiAdapter;
import com.qdemy.db.App;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RezumatTestActivity extends AppCompatActivity {

    private TextView scor;
    private TextView nume;
    private ImageView inapoi;
    private ImageView intrebarileMele;
    private ImageView acasa;
    private ImageView testeleMele;
    private ImageView istoric;
    private RadioButton studentiRadioButton;
    private RadioButton intrebariRadioButton;
    private RadioGroup optiuni;
    private List<Student> studenti;
    private List<IntrebareGrila> intrebari;
    private ListView informatiiList;
    private TestSustinut test;
    private BarChart barchart;
    private RezumatTestStudentiAdapter adapterStudenti;
    private RezumatTestIntrebariAdapter adapterIntrebari;

    private long testSustinutId;
    private float[] note = new float[12];

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezumat_test);

        initializare();
    }

    private void initializare() {

        //region Initializare componente vizuale
        scor = findViewById(R.id.scor_text_rezumatTest);
        nume = findViewById(R.id.nume_text_rezumatTest);
        inapoi = findViewById(R.id.back_image_rezumatTest);
        istoric = findViewById(R.id.istoric_image_rezumatTest);
        intrebarileMele = findViewById(R.id.intrebari_image_rezumatTest);
        acasa = findViewById(R.id.acasa_image_rezumatTest);
        testeleMele = findViewById(R.id.teste_image_rezumatTest);
        studentiRadioButton = findViewById(R.id.studenti_radioButton_rezumatTest);
        intrebariRadioButton = findViewById(R.id.intrebari_radioButton_rezumatTest);
        optiuni = findViewById(R.id.optiuni_radioGroup_rezumatTest);
        informatiiList = findViewById(R.id.informatii_list_rezumatTest);
        //endregion

        testSustinutId = getIntent().getLongExtra(Constante.CHEIE_TRANSFER, -1);
        //COSMIN - TO DO SELECT TESTUL SUSTINUT SELECTAT
        final Query<TestSustinut> queryTestSustinut = ((App) getApplication()).getDaoSession().getTestSustinutDao().queryBuilder().where(
                TestSustinutDao.Properties.Id.eq(testSustinutId)).build();

        //COSMIN - TO DO SELECT TEST AFERENT TESTULUI SUSTINUT
        Query<Test> queryTest = ((App) getApplication()).getDaoSession().getTestDao().queryBuilder().where(
                TestDao.Properties.Id.eq(queryTestSustinut.list().get(0).getTestId())).build();

        adapterStudenti = new RezumatTestStudentiAdapter(getApplicationContext(), R.layout.item_text_text,
                queryTestSustinut.list().get(0).getRezultate(), getLayoutInflater(), RezumatTestActivity.this);
        adapterIntrebari = new RezumatTestIntrebariAdapter(getApplicationContext(), R.layout.item_text_text,
                queryTest.list().get(0).getIntrebari(), getLayoutInflater(), RezumatTestActivity.this, testSustinutId);
        informatiiList.setAdapter(adapterStudenti);


        //generare grafic
        for ( RezultatTestStudent rezultat:queryTestSustinut.list().get(0).getRezultate()) {
            note[(int)((App) getApplication()).getPunctajTest(rezultat.getRaspunsuri())]++;
        }
        setBarChart(queryTestSustinut.list().get(0).getRezultate().size());


        optiuni.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(intrebariRadioButton.isChecked())
                {
                    scor.setText(getString(R.string.corecte));
                    nume.setText(getString(R.string.ntrebare));
                    informatiiList.setAdapter(adapterIntrebari);
                }
                else
                {
                    scor.setText(getString(R.string.punctaj));
                    nume.setText(getString(R.string.Nume_student1));
                    informatiiList.setAdapter(adapterStudenti);
                }
            }
        });

        informatiiList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(studentiRadioButton.isChecked()) {
                    Intent intent = new Intent(getApplicationContext(), RezumatStudentActivity.class);
                    intent.putExtra(Constante.CHEIE_TRANSFER, queryTestSustinut.list().get(0).getRezultat(position).getId());
                    startActivity(intent);
                    finish();
                }
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

        testeleMele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MateriiActivity.class);
                intent.putExtra(Constante.CHEIE_TRANSFER, getString(R.string.testele_mele));
                startActivity(intent);
                finish();
            }
        });

        intrebarileMele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MateriiActivity.class);
                intent.putExtra(Constante.CHEIE_TRANSFER, getString(R.string.ntreb_rile_mele));
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

        //endregion

    }

    private void setBarChart(int nrStudenti) {

        barchart.getDescription().setEnabled(false);
        barchart.getLegend().setEnabled(false);
        barchart.setExtraOffsets(3,10,3,10);
        barchart.animateY(2000, Easing.EasingOption.EaseInOutCubic);
        ArrayList<BarEntry> yValues = new ArrayList<>();
        yValues.add(new BarEntry(note[1]/nrStudenti, 1));
        yValues.add(new BarEntry(note[2]/nrStudenti, 2));
        yValues.add(new BarEntry(note[3]/nrStudenti, 3));
        yValues.add(new BarEntry(note[4]/nrStudenti, 4));
        yValues.add(new BarEntry(note[5]/nrStudenti, 5));
        yValues.add(new BarEntry(note[6]/nrStudenti, 6));
        yValues.add(new BarEntry(note[7]/nrStudenti, 7));
        yValues.add(new BarEntry(note[8]/nrStudenti, 8));
        yValues.add(new BarEntry(note[9]/nrStudenti, 9));
        yValues.add(new BarEntry(note[10]/nrStudenti, 10));

        BarDataSet dataSet = new BarDataSet(yValues, "");
        dataSet.setColors(ContextCompat.getColor(getApplicationContext(), R.color.portocaliu));
        BarData barData = new BarData((dataSet));
        barData.setValueTextSize(10f);
        barData.setValueTypeface(Typeface.DEFAULT_BOLD);
        barData.setValueTextColor(Color.WHITE);
        barchart.setData(barData);
        //PieChart Ends Here
    }

}
