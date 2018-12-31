package com.qdemy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.qdemy.clase.Profesor;
import com.qdemy.clase.RezultatTestStudent;
import com.qdemy.clase.RezultatTestStudentDao;
import com.qdemy.clase.Student;
import com.qdemy.clase_adapter.IstoricProfesorAdapter;
import com.qdemy.clase_adapter.IstoricStudentAdapter;
import com.qdemy.db.App;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class IstoricStudentActivity extends AppCompatActivity {

    private ImageView inapoi;
    private ImageView mail;
    private ImageView testePublice;
    private ImageView acasa;
    private ListView testeList;
    private List<RezultatTestStudent> teste = new ArrayList<>();
    private PieChart pieChart;
    public float promovate=0;
    public float picate=0;

    private Student student;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_istoric_student);

        initializare();

    }

    private void initializare() {

        //region Initializare componente vizuale
        inapoi = findViewById(R.id.back_image_istoricStudent);
        testePublice = findViewById(R.id.testePublice_button_istoricStudent);
        mail = findViewById(R.id.mail_button_istoricStudent);
        acasa = findViewById(R.id.acasa_image_istoricStudent);
        testeList = findViewById(R.id.istoric_list_istoricStudent);
        pieChart = findViewById(R.id.piechart_istoricStudent);
        //endregion

        //region Initializare teste
        Student student = ((App) getApplication()).getStudent();
        final Query<RezultatTestStudent> queryRezultateTeste = ((App) getApplication()).getDaoSession().getRezultatTestStudentDao().queryBuilder().where(
                RezultatTestStudentDao.Properties.StudentId.eq(student.getId())).build();

        IstoricStudentAdapter adapter = new IstoricStudentAdapter(getApplicationContext(),
                R.layout.item_text_text_text, queryRezultateTeste.list(), getLayoutInflater(), IstoricStudentActivity.this);
        testeList.setAdapter(adapter);

        for (RezultatTestStudent rezultat : queryRezultateTeste.list()) {
            if(rezultat.isPromovat()) promovate++;
            else picate++;
        }

        setPieChart(queryRezultateTeste.list().size());

        //endregion


        testeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), RezumatStudentActivity.class);
                intent.putExtra(Constante.CHEIE_TRANSFER, queryRezultateTeste.list().get(position).getId());
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

        testePublice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TestePubliceActivity.class);
                startActivity(intent);
                finish();
            }
        });


        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Query<Profesor> query = ((App) getApplication()).getDaoSession().getProfesorDao().queryBuilder().build();
                List<String> mailuriProfesori = new ArrayList<>();
                for (Profesor profesor : query.list()) {
                    mailuriProfesori.add(profesor.getMail());}

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle(R.string.alege_profesorul);
                View view = getLayoutInflater().inflate(R.layout.spinner_dialog, null);
                final Spinner spinner = view.findViewById(R.id.spinner_dialog);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(),
                        android.R.layout.simple_spinner_item, mailuriProfesori);
                adapter.setDropDownViewResource(R.layout.item_spinner);
                spinner.setAdapter(adapter);

                builder.setPositiveButton(R.string.trimite_mail, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setData(Uri.parse("mailto:"));
                        emailIntent.setType("text/plain");
                        String[] TO = {spinner.getSelectedItem().toString()};
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                        //emailIntent.putExtra(Intent.EXTRA_SUBJECT, "TEST");
                        //emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");
                        try {
                            startActivity(Intent.createChooser(emailIntent, getString(R.string.trimite_mail2)));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(getApplicationContext(), getString(R.string.error_message_app_mail), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getWindow().setBackgroundDrawableResource(R.color.portocaliu);
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

        //endregion

    }

    private void setPieChart(int nrTeste) {

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setExtraOffsets(3,10,3,10);
        pieChart.setTransparentCircleRadius(0f);
        pieChart.setHoleRadius(0f);
        pieChart.animateY(2000, Easing.EasingOption.EaseInOutCubic);
        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry((promovate/nrTeste)*100.0f ,"promovate"));
        yValues.add(new PieEntry((picate/nrTeste)*100.0f,"picate"));

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(0f);
        dataSet.setSelectionShift(10f);
        dataSet.setColors(new int[] { ContextCompat.getColor(getApplicationContext(), R.color.verde), ContextCompat.getColor(getApplicationContext(), R.color.rosu)});
        PieData pieData = new PieData((dataSet));
        pieData.setValueTextSize(20f);
        pieData.setValueTypeface(Typeface.DEFAULT_BOLD);
        pieData.setValueTextColor(Color.WHITE);
        pieChart.setData(pieData);
        //PieChart Ends Here
    }

}
