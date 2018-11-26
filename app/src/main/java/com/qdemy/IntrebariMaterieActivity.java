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
import com.qdemy.clase_adapter.IntrebareAdapter;

import java.util.ArrayList;
import java.util.List;

public class IntrebariMaterieActivity extends AppCompatActivity {

    private ImageView inapoi;
    private FloatingActionButton adauga;
    private ListView intrebariList;
    private List<IntrebareGrila> intrebari = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intrebari_materie);

        initializare();
    }

    private void initializare() {

        inapoi = findViewById(R.id.back_image_intrebariMaterie);
        adauga = findViewById(R.id.adauga_button_intrebariMaterii);
        intrebariList = findViewById(R.id.intrebari_list_intrebariMaterie);

        //initializare intrebari
        IntrebareAdapter adapter = new IntrebareAdapter(getApplicationContext(),
                R.layout.item_text_button, intrebari, getLayoutInflater());
        intrebariList.setAdapter(adapter);

        //INITIALIZARE CAMPURI CU IMATERIA SELECTATA

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
                intent.putExtra(Constante.CHEIE_AUTENTIFICARE, intrebari.get(position));
                startActivity(intent);
            }
        });
    }
}
