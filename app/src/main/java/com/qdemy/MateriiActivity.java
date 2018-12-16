package com.qdemy;

import android.content.Intent;
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
import com.qdemy.clase.Profesor;
import com.qdemy.clase_adapter.IntrebareAdapter;
import com.qdemy.clase_adapter.MaterieAdapter;

import java.util.ArrayList;
import java.util.List;

public class MateriiActivity extends AppCompatActivity {

    private ImageView inapoi;
    private ListView materiiList;
    private Spinner materiiSpinner;
    private Button adaugaMaterie;
    private List<String> materii = new ArrayList<>();
    private Profesor profesor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materii);

        initializare();
    }

    private void initializare() {

        //region Initializare componente vizuale
        inapoi = findViewById(R.id.back_image_materii);
        materiiList = findViewById(R.id.materii_list_materii);
        materiiSpinner = findViewById(R.id.nomenclator_spinner_materii);
        adaugaMaterie = findViewById(R.id.adauga_button_materii);

        profesor = getIntent().getParcelableExtra(Constante.CHEIE_TRANSFER);
        materii = profesor.getNumeMaterii();
        final MaterieAdapter adapter = new MaterieAdapter(getApplicationContext(),
                R.layout.item_text_button, materii, getLayoutInflater());
        materiiList.setAdapter(adapter);

        List<String> nomenclator_materii = new ArrayList<>();
        nomenclator_materii.add(getString(R.string.poo));
        nomenclator_materii.add(getString(R.string.sdd));
        nomenclator_materii.add(getString(R.string.java));
        nomenclator_materii.add(getString(R.string.paw));
        nomenclator_materii.add(getString(R.string.dam));
        nomenclator_materii.add(getString(R.string.tw));

        for(int i=0;i<nomenclator_materii.size();i++)
            for(int j=0;j<materii.size();j++)
                if(nomenclator_materii.get(i).equals(materii.get(j)))
                    nomenclator_materii.remove(i);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, nomenclator_materii);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        materiiSpinner.setAdapter(adapter1);
        //endregion


        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adaugaMaterie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materii.add(materiiSpinner.getSelectedItem().toString());
                adapter.notifyDataSetChanged();
            }
        });

    }
}
