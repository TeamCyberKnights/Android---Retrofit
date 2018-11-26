package com.qdemy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.qdemy.clase.RaspunsIntrebareGrila;
import com.qdemy.clase.RezultatTestStudent;
import com.qdemy.clase_adapter.MaterieAdapter;
import com.qdemy.clase_adapter.RezultatStudentAdapter;

import java.util.List;

public class RezultatStudentActivity extends AppCompatActivity {

    private TextView scor;
    private Button salveaza;
    private List<RaspunsIntrebareGrila> intrebari;
    private ListView intrebariList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezultat_student);

        initializare();
    }

    private void initializare() {

        scor = findViewById(R.id.scor_text_rezultatStudent);
        salveaza = findViewById(R.id.salveaza_button_rezultatStudent);
        intrebariList = findViewById(R.id.intrebari_list_rezultatStudent);

        //initializare intrebari
        RezultatStudentAdapter adapter = new RezultatStudentAdapter(getApplicationContext(),
                R.layout.item_text_button, intrebari, getLayoutInflater());
        intrebariList.setAdapter(adapter);

        salveaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //salveaza rezultatul
                Intent intent = new Intent(getApplicationContext(), StudentActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
