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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materii);

        initializare();
    }

    private void initializare() {

        inapoi = findViewById(R.id.back_image_materii);
        materiiList = findViewById(R.id.materii_list_materii);

        //INITIALIZARE MATERII PROFESOR in adaptor

        materiiSpinner = findViewById(R.id.nomenclator_spinner_materii);
        adaugaMaterie = findViewById(R.id.adauga_button_materii);

        String[] nomenclator_materii = new String[] {
                getString(R.string.poo),
                getString(R.string.sdd),
                getString(R.string.java),
                getString(R.string.paw),
                getString(R.string.dam),
                getString(R.string.tw)
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, nomenclator_materii);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        materiiSpinner.setAdapter(adapter);


        MaterieAdapter adapter1 = new MaterieAdapter(getApplicationContext(),
                R.layout.item_text_button, materii, getLayoutInflater());
        materiiList.setAdapter(adapter1);



        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adaugaMaterie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adauga materie in layout
            }
        });


        materiiList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), IntrebariMaterieActivity.class);
                intent.putExtra(Constante.CHEIE_AUTENTIFICARE, materii.get(position));
                startActivity(intent);
            }
        });
    }
}
