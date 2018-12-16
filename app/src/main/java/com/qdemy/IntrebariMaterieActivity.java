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
import com.qdemy.clase_adapter.IntrebareAdapter;

import java.util.ArrayList;
import java.util.List;

public class IntrebariMaterieActivity extends AppCompatActivity {

    private ImageView inapoi;
    private FloatingActionButton adauga;
    private ListView intrebariList;
    private TextView materieTitlu;
    private String materie;
    private List<IntrebareGrila> intrebari = new ArrayList<>();

    private SharedPreferences sharedPreferences;
    private Profesor profesor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intrebari_materie);

        initializare();
    }

    private void initializare() {

        //region Intializare componente vizuale
        inapoi = findViewById(R.id.back_image_intrebariMaterie);
        adauga = findViewById(R.id.adauga_button_intrebariMaterii);
        intrebariList = findViewById(R.id.intrebari_list_intrebariMaterie);
        materieTitlu = findViewById(R.id.titlu_text_intrebariMaterie);
        //endregion

        //region Initializare intrebari
        sharedPreferences = getSharedPreferences(Constante.FISIER_PREFERINTA_UTILIZATOR, MODE_PRIVATE);
        incarcareUtilizatorSalvat();
        materie = getIntent().getStringExtra(Constante.CHEIE_TRANSFER);
        materieTitlu.setText(materie);

        for(int i=0;i<profesor.getIntrebari().size();i++)
            if(profesor.getIntrebari(i).getMaterie().equals(materie))
                intrebari.add(profesor.getIntrebari(i));
        IntrebareAdapter adapter = new IntrebareAdapter(getApplicationContext(),
                R.layout.item_text_button, intrebari, getLayoutInflater());
        intrebariList.setAdapter(adapter);
        //endregion



        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adauga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdaugaIntrebareActivity.class);
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
