package com.qdemy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.qdemy.clase.IntrebareGrila;
import com.qdemy.clase.TestSustinut;
import com.qdemy.clase_adapter.IntrebareAdapter;
import com.qdemy.clase_adapter.IstoricProfesorAdapter;

import java.util.ArrayList;
import java.util.List;

public class IstoricProfesorActivity extends AppCompatActivity {

    private ImageView inapoi;
    private ListView testeList;
    private List<TestSustinut> teste = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_istoric_profesor);

        initializare();
    }

    private void initializare() {

        inapoi = findViewById(R.id.back_image_istoricProfesor);
        testeList = findViewById(R.id.istoric_list_istoricProfesor);
        //initializare teste
        IstoricProfesorAdapter adapter = new IstoricProfesorAdapter(getApplicationContext(),
                R.layout.item_text_text_text, teste, getLayoutInflater());
        testeList.setAdapter(adapter);

        //INITIALIZARE CAMPURI CU ISTORICUL PROFESORULUI

        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        testeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), RezumatTestActivity.class);
                //intent.putExtra(Constante.CHEIE_AUTENTIFICARE, teste.get(position));
                startActivity(intent);
            }
        });
    }
}
