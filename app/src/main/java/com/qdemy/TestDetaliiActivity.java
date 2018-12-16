package com.qdemy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.qdemy.clase.IntrebareGrila;
import com.qdemy.clase.Profesor;
import com.qdemy.clase.Test;
import com.qdemy.clase_adapter.IntrebareTestAdapter;

import java.util.ArrayList;
import java.util.List;

public class TestDetaliiActivity extends AppCompatActivity {

    private ImageView inapoi;
    private TextView nume;
    private TextView descriere;
    private TextView estePublic;
    private TextView minute;
    private FloatingActionButton editeaza;
    private FloatingActionButton partajeaza;
    private ListView intrebariList;
    private List<IntrebareGrila> intrebari = new ArrayList<>();

    private Test test;
    private Profesor profesor;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_detalii);

        initializare();
    }

    private void initializare() {

        //region Initializare componente vizuale
        inapoi = findViewById(R.id.back_image_testDetalii);
        nume = findViewById(R.id.titlu_text_testDetalii);
        descriere = findViewById(R.id.descriere_text_testDetalii);
        estePublic = findViewById(R.id.public_text_testDetalii);
        minute = findViewById(R.id.minute_text_testDetalii);
        editeaza = findViewById(R.id.editeaza_button_testDetalii);
        partajeaza = findViewById(R.id.partajeaza_button_testDetalii);
        intrebariList = findViewById(R.id.intrebari_list_testDetalii);
        //endregion

        //region Initializare test
        sharedPreferences = getSharedPreferences(Constante.FISIER_PREFERINTA_UTILIZATOR, MODE_PRIVATE);
        incarcareUtilizatorSalvat();
        test = getIntent().getParcelableExtra(Constante.CHEIE_TRANSFER);

        nume.setText(test.getNume());
        descriere.setText(test.getDescriere());
        minute.setText(getString(R.string.minute2, test.getMinute()));
        estePublic.setText(test.getEstePublic()?getString(R.string.test_public):getString(R.string.test_privat));
        intrebari = test.getIntrebari();
        IntrebareTestAdapter adapter = new IntrebareTestAdapter(getApplicationContext(),
                R.layout.item_text_button, intrebari, getLayoutInflater(), false);
        intrebariList.setAdapter(adapter);
        //endregion

        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editeaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditeazaTestActivity.class);
                intent.putExtra(Constante.CHEIE_TRANSFER, test);
                startActivity(intent);
                finish();
            }
        });

        partajeaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PartajeazaTestActivity.class);
                intent.putExtra(Constante.CHEIE_TRANSFER, test);
                startActivity(intent);
            }
        });

        intrebariList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), IntrebareActivity.class);
                intent.putExtra(Constante.CHEIE_TRANSFER, intrebari.get(position));
                startActivity(intent);
            }
        });
    }

    private void incarcareUtilizatorSalvat() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(getString(R.string.utilizator), "");
        profesor = gson.fromJson(json, Profesor.class);
    }
}
