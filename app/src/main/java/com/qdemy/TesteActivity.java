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
import com.qdemy.clase.Test;
import com.qdemy.clase_adapter.IntrebareAdapter;
import com.qdemy.clase_adapter.TestAdapter;

import java.util.ArrayList;
import java.util.List;

public class TesteActivity extends AppCompatActivity {

    private ImageView inapoi;
    private FloatingActionButton adauga;
    private ListView testeList;
    private List<Test> teste = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);

        initializare();
    }

    private void initializare() {

        inapoi = findViewById(R.id.back_image_teste);
        adauga = findViewById(R.id.adauga_button_teste);
        testeList = findViewById(R.id.teste_list_teste);
        //initializare teste
        TestAdapter adapter = new TestAdapter(getApplicationContext(),
                R.layout.item_text_button, teste, getLayoutInflater());
        testeList.setAdapter(adapter);

        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adauga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdaugaTestActivity.class);
                startActivity(intent);
            }
        });

        testeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), TestDetaliiActivity.class);
                intent.putExtra(Constante.CHEIE_AUTENTIFICARE, teste.get(position));
                startActivity(intent);
            }
        });
    }
}