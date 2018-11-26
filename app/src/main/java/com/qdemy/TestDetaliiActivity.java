package com.qdemy;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.qdemy.clase.IntrebareGrila;
import com.qdemy.clase_adapter.IntrebareTestAdapter;

import java.util.ArrayList;
import java.util.List;

public class TestDetaliiActivity extends AppCompatActivity {

    private ImageView inapoi;
    private FloatingActionButton editeaza;
    private FloatingActionButton partajeaza;
    private ListView intrebariList;
    private List<IntrebareGrila> intrebari = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_detalii);

        initializare();
    }

    private void initializare() {

        inapoi = findViewById(R.id.back_image_testDetalii);
        editeaza = findViewById(R.id.editeaza_button_testDetalii);
        partajeaza = findViewById(R.id.partajeaza_button_testDetalii);
        intrebariList = findViewById(R.id.intrebari_list_testDetalii);

        //initializare intrebari
        IntrebareTestAdapter adapter = new IntrebareTestAdapter(getApplicationContext(),
                R.layout.item_text_button, intrebari, getLayoutInflater());
        intrebariList.setAdapter(adapter);

        //INITIALIZARE CAMPURI TEST SELECTAT

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
                startActivity(intent);
            }
        });

        partajeaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PartajeazaTestActivity.class);
                startActivity(intent);
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
