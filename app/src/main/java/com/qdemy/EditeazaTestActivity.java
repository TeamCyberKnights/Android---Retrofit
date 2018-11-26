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

public class EditeazaTestActivity extends AppCompatActivity {

    private ImageView inapoi;
    private TextInputEditText nume;
    private TextInputEditText descriere;
    private Spinner durate;
    private Spinner materii;
    private Spinner intrebariSpinner;
    private Button adaugaIntrebare;
    private Button actualizeazaTest;
    private ListView intrebariList;
    private List<IntrebareGrila> intrebari = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editeaza_test);

        initializare();
    }

    private void initializare() {

        inapoi = findViewById(R.id.back_image_editeazaTest);
        nume = findViewById(R.id.nume_text_editeazaTest);
        descriere = findViewById(R.id.descriere_text_editeazaTest);
        durate = findViewById(R.id.durate_spinner_editeazaTest);
        materii = findViewById(R.id.materii_spinner_editeazaTest);
        intrebariSpinner = findViewById(R.id.intrebari_spinner_editeazaTest);
        adaugaIntrebare = findViewById(R.id.adaugaIntrebare_button_editeazaTest);
        actualizeazaTest = findViewById(R.id.actualizeaza_button_editeazaTest);
        intrebariList = findViewById(R.id.intrebari_list_adaugaTest);

        //initializare intrebari
        IntrebareTestAdapter adapter = new IntrebareTestAdapter(getApplicationContext(),
                R.layout.item_text_button, intrebari, getLayoutInflater());
        intrebariList.setAdapter(adapter);
        intrebariSpinner.setAdapter(adapter);

        //INITIALIZARE CAMPURI CU TESTUL DE EDITAT


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

        actualizeazaTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adauga testul
                Intent intent = new Intent(getApplicationContext(), TesteActivity.class);
                startActivity(intent);
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
