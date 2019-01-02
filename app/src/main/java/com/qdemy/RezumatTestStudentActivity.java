package com.qdemy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.qdemy.clase.IntrebareGrila;
import com.qdemy.clase.IntrebareGrilaDao;
import com.qdemy.clase.Profesor;
import com.qdemy.clase.RaspunsIntrebareGrila;
import com.qdemy.clase.RezultatTestStudent;
import com.qdemy.clase.RezultatTestStudentDao;
import com.qdemy.clase.Student;
import com.qdemy.clase.StudentDao;
import com.qdemy.clase_adapter.RezultatStudentAdapter;
import com.qdemy.clase_adapter.RezumatStudentAdapter;
import com.qdemy.clase_adapter.RezumatTestStudentAdapter;
import com.qdemy.db.App;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RezumatTestStudentActivity extends AppCompatActivity {

    private ImageView inapoi;
    private ImageView intrebarileMele;
    private ImageView acasa;
    private ImageView testeleMele;
    private ImageView istoric;
    private TextView numeStudent;
    private List<RaspunsIntrebareGrila> raspunsuri;
    private ListView intrebariList;
    private PieChart pieChart;

    private float corecte=0;
    private float gresite=0;
    private float partiale=0;
    private long rezultatId;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezumat_student);

        initializare();
    }

    private void initializare() {

        //region Initializare componente vizuale
        inapoi = findViewById(R.id.back_image_rezumatTestStudent);
        testeleMele = findViewById(R.id.teste_button_rezumatTestStudent);
        intrebarileMele = findViewById(R.id.intrebari_button_rezumatTestStudent);
        acasa = findViewById(R.id.acasa_image_rezumatTestStudent);
        intrebariList = findViewById(R.id.intrebari_list_rezumatTestStudent);
        istoric = findViewById(R.id.istoric_button_rezumatTestStudent);
        pieChart = findViewById(R.id.piechart_rezumatTestStudent);
        numeStudent = findViewById(R.id.student_text_rezumatTestStudent);
        //endregion

        //region Initializare intrebari
        rezultatId = getIntent().getLongExtra(Constante.CHEIE_TRANSFER, -1);
        Query<RezultatTestStudent> queryRezultat = ((App) getApplication()).getDaoSession().getRezultatTestStudentDao().queryBuilder().where(
                RezultatTestStudentDao.Properties.Id.eq(rezultatId)).build();

        raspunsuri = queryRezultat.list().get(0).getRaspunsuri();
        RezumatTestStudentAdapter adapter = new RezumatTestStudentAdapter(getApplicationContext(),
                R.layout.item_text_text, raspunsuri, getLayoutInflater(), RezumatTestStudentActivity.this);
        intrebariList.setAdapter(adapter);

        for (RaspunsIntrebareGrila raspuns : raspunsuri ) {
            Query<IntrebareGrila> queryIntrebare = ((App) getApplication()).getDaoSession().getIntrebareGrilaDao().queryBuilder().where(
                    IntrebareGrilaDao.Properties.Id.eq(raspuns.getIntrebareId())).build();
            if(raspuns.getPunctajObtinut()==0) gresite++;
            else if (raspuns.getPunctajObtinut()==queryIntrebare.list().get(0).getDificultate()) corecte++;
            else partiale++;
        }

        Query<Student> queryStudent = ((App) getApplication()).getDaoSession().getStudentDao().queryBuilder().where(
                StudentDao.Properties.Id.eq(queryRezultat.list().get(0).getStudentId())).build();
        numeStudent.setText(queryStudent.list().get(0).getNume() + " " + queryStudent.list().get(0).getPrenume());

        setPieChart( raspunsuri.size());

        //endregion

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

        acasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StudentActivity.class);
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

    private void setPieChart(int nrIntrebari) {

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setExtraOffsets(3,10,3,10);
        pieChart.setTransparentCircleRadius(0f);
        pieChart.setHoleRadius(0f);
        pieChart.animateY(2000, Easing.EasingOption.EaseInOutCubic);
        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry((corecte/nrIntrebari)*100.0f ,getString(R.string.corecte2)));
        yValues.add(new PieEntry((partiale/nrIntrebari)*100.0f,getString(R.string.partiale)));
        yValues.add(new PieEntry((gresite/nrIntrebari)*100.0f,getString(R.string.gresite)));

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(0f);
        dataSet.setSelectionShift(10f);
        dataSet.setColors(new int[] {ContextCompat.getColor(getApplicationContext(), R.color.verde),
                ContextCompat.getColor(getApplicationContext(), R.color.galben),
                ContextCompat.getColor(getApplicationContext(), R.color.rosu)});
        PieData pieData = new PieData((dataSet));
        pieData.setValueTextSize(20f);
        pieData.setValueTypeface(Typeface.DEFAULT_BOLD);
        pieData.setValueTextColor(Color.WHITE);
        pieChart.setData(pieData);
        //PieChart Ends Here
    }
}
