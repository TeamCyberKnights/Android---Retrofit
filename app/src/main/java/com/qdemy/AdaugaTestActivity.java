package com.qdemy;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.qdemy.clase.IntrebareGrila;
import com.qdemy.clase_adapter.IntrebareAdapter;
import com.qdemy.clase_adapter.IntrebareTestAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdaugaTestActivity extends AppCompatActivity {

    private ImageView inapoi;
    private TextInputEditText nume;
    private TextInputEditText descriere;
    private Spinner durate;
    private Spinner materii;
    private Spinner intrebariSpinner;
    private Button adaugaIntrebare;
    private Button adaugaTest;
    private ListView intrebariList;
    private List<IntrebareGrila> intrebari = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adauga_test);

        initializare();
    }

    private void initializare() {

        inapoi = findViewById(R.id.back_image_adaugaTest);
        nume = findViewById(R.id.nume_text_adaugaTest);
        descriere = findViewById(R.id.descriere_text_adaugaTest);
        durate = findViewById(R.id.durate_spinner_adaugaTest);
        materii = findViewById(R.id.materii_spinner_adaugaTest);
        intrebariSpinner = findViewById(R.id.intrebare_spinner_adaugaTest);
        adaugaIntrebare = findViewById(R.id.adaugaIntrebare_button_adaugaTest);
        adaugaTest = findViewById(R.id.adauga_button_adaugaTest);
        intrebariList = findViewById(R.id.intrebari_list_adaugaTest);

        //initializare intrebari
        IntrebareTestAdapter adapter = new IntrebareTestAdapter(getApplicationContext(),
                R.layout.item_text_button, intrebari, getLayoutInflater());
        intrebariList.setAdapter(adapter);
//
//
//        String[] intrebari = new String[] {
//                getString(R.string.poo),
//                getString(R.string.sdd),
//                getString(R.string.java),
//                getString(R.string.paw),
//                getString(R.string.dam),
//                getString(R.string.tw)
//        };
//
//        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, intrebari);
//        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//
        intrebariSpinner.setAdapter(adapter);



        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adaugaIntrebare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adauga intrebarea in layout
            }
        });

        adaugaTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adauga testul
                finish();
            }
        });

        intrebariList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), IntrebareActivity.class);
                intent.putExtra(Constante.CHEIE_AUTENTIFICARE, intrebari.get(position));
                startActivity(intent);
            }
        });
    }
}