package com.qdemy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.qdemy.clase.EvidentaIntrebariTeste;
import com.qdemy.clase.EvidentaIntrebariTesteDao;
import com.qdemy.clase.EvidentaMateriiProfesori;
import com.qdemy.clase.EvidentaMateriiProfesoriDao;
import com.qdemy.clase.IntrebareGrila;
import com.qdemy.clase.IntrebareGrilaDao;
import com.qdemy.clase.Profesor;
import com.qdemy.clase.Student;
import com.qdemy.clase.Test;
import com.qdemy.clase.TestDao;
import com.qdemy.clase.TestPartajat;
import com.qdemy.clase.TestPartajatDao;
import com.qdemy.clase.VariantaRaspuns;
import com.qdemy.clase.VariantaRaspunsDao;
import com.qdemy.clase_adapter.IntrebareAdapter;
import com.qdemy.clase_adapter.TestAdapter;
import com.qdemy.clase_adapter.TestePubliceAdapter;
import com.qdemy.db.App;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TestePubliceActivity extends AppCompatActivity {

    private ImageView inapoi;
    private ImageView mail;
    private ImageView istoric;
    private ImageView acasa;
    private ListView testeList;
    private SearchView cautaTest;
    private TestePubliceAdapter adapter;
    private List<Test> testePublice = new ArrayList<>();
    private Student student;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_publice);

        initializare();
    }

    private void initializare() {

        //region Intializare componenete vizuale
        inapoi = findViewById(R.id.back_image_testePublice);
        istoric = findViewById(R.id.istoric_button_testePublice);
        mail = findViewById(R.id.mail_button_testePublice);
        testeList = findViewById(R.id.teste_list_testePublice);
        cautaTest = findViewById(R.id.search_testePublice);
        int id = cautaTest.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchEditText = (EditText) cautaTest.findViewById(id);
        searchEditText.setTextColor(Color.WHITE);
        searchEditText.setHintTextColor(getResources().getColor(R.color.gri));
        acasa = findViewById(R.id.acasa_image_testePublice);
        //endregion

        //incarcare teste publice
        Query<Test> queryTeste = ((App) getApplication()).getDaoSession().getTestDao().queryBuilder().build();
        for ( Test test : queryTeste.list()) {
            if(test.getEstePublic()) testePublice.add(test);
        }

        adapter = new TestePubliceAdapter(getApplicationContext(), R.layout.item_test_public,
                testePublice, getLayoutInflater(), TestePubliceActivity.this);
        testeList.setAdapter(adapter);
        //endregion


        cautaTest.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)){
                    adapter.filter("");
                    testeList.clearTextFilter();
                }
                else {
                    adapter.filter(newText);
                }
                return true;
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

        istoric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IstoricStudentActivity.class);
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

}
