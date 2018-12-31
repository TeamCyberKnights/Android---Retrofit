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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.qdemy.clase.IntrebareGrila;
import com.qdemy.clase.Profesor;
import com.qdemy.clase.RezultatTestStudent;
import com.qdemy.clase.TestSustinut;
import com.qdemy.clase.TestSustinutDao;
import com.qdemy.clase_adapter.IntrebareAdapter;
import com.qdemy.clase_adapter.IstoricProfesorAdapter;
import com.qdemy.db.App;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class IstoricProfesorActivity extends AppCompatActivity {

    private ImageView inapoi;
    private ImageView intrebari;
    private ImageView acasa;
    private ImageView testeleMele;
    private ListView testeList;
    private List<TestSustinut> teste = new ArrayList<>();
    private PieChart pieChart;

    private Profesor profesor;
    private int nrRezultate=0;
    private int promovate=0;
    private int picate=0;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_istoric_profesor);

        initializare();
        //setPieChart();

    }

    private void initializare() {

        //region Intializare componente vizuale
        inapoi = findViewById(R.id.back_image_istoricProfesor);
        intrebari = findViewById(R.id.intrebari_image_istoricProfesor);
        acasa = findViewById(R.id.acasa_image_istoricProfesor);
        testeleMele = findViewById(R.id.teste_image_istoricProfesor);
        testeList = findViewById(R.id.istoric_list_istoricProfesor);
        pieChart = findViewById(R.id.piechart_istoricProfesor);
        //endregion

        //region Initializare rezultate
        profesor = ((App) getApplication()).getProfesor();
        final Query<TestSustinut> queryTesteSustinute = ((App) getApplication()).getDaoSession().getTestSustinutDao().queryBuilder().where(
                TestSustinutDao.Properties.ProfesorId.eq(profesor.getId())).build();

        for (TestSustinut testSustinut : queryTesteSustinute.list()) {
            for ( RezultatTestStudent rezultat : testSustinut.getRezultate()) {
                nrRezultate++;
                if(rezultat.isPromovat()) promovate++;
                else picate++;
            }
        }

        IstoricProfesorAdapter adapter = new IstoricProfesorAdapter(getApplicationContext(),
                R.layout.item_text_text_text, queryTesteSustinute.list(), getLayoutInflater(), IstoricProfesorActivity.this);
        testeList.setAdapter(adapter);

        //endregion


        testeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), RezumatTestActivity.class);
                intent.putExtra(Constante.CHEIE_TRANSFER, queryTesteSustinute.list().get(position).getId());
                startActivity(intent);
                finish();
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

    private void setPieChart() {

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setExtraOffsets(3,10,3,10);
        pieChart.setTransparentCircleRadius(0f);
        pieChart.setHoleRadius(0f);
        pieChart.animateY(2000, Easing.EasingOption.EaseInOutCubic);
        ArrayList<PieEntry> yValues = new ArrayList<>();

        if(nrRezultate>0) {
            yValues.add(new PieEntry((promovate/nrRezultate)*100.0f ,"promovate"));
            yValues.add(new PieEntry((picate/nrRezultate)*100.0f,"picate"));
        }
        else {
            Toast.makeText(getApplicationContext(), getString(R.string.error_message_niciun_test_sustinut), Toast.LENGTH_SHORT).show();
        }

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(0f);
        dataSet.setSelectionShift(10f);
        dataSet.setColors(new int[] {R.color.verde, R.color.rosu});
        PieData pieData = new PieData((dataSet));
        pieData.setValueTextSize(20f);
        pieData.setValueTypeface(Typeface.DEFAULT_BOLD);
        pieData.setValueTextColor(Color.WHITE);
        pieChart.setData(pieData);
        //PieChart Ends Here

    }
}
