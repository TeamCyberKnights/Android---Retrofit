package com.qdemy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.qdemy.clase.RaspunsIntrebareGrila;
import com.qdemy.clase.Student;
import com.qdemy.clase.TestSustinut;
import com.qdemy.clase_adapter.RezultatStudentAdapter;
import com.qdemy.clase_adapter.RezultatTestAdapter;

import java.util.List;

public class RezultatTestActivity extends AppCompatActivity {

    private ImageView inapoi;
    private TextView promovati;
    private List<Student> studenti;
    private ListView studentiList;
    private final String origine = getString(R.string.profesor);
    private TestSustinut test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezultat_test);

        initializare();
    }

    private void initializare() {

        inapoi = findViewById(R.id.back_image_rezultatTest);
        promovati = findViewById(R.id.promovati_text_rezultatTest);
        studentiList = findViewById(R.id.studenti_list_rezultatTest);

        //initializare studenti
        //initializare test

        RezultatTestAdapter adapter = new RezultatTestAdapter(getApplicationContext(),
                R.layout.item_text_text, studenti, getLayoutInflater(), test);
        studentiList.setAdapter(adapter);

        //INITIALIZARE CAMPURI TEST SELECTAT

        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        studentiList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), RezumatStudentActivity.class);
                //intent.putExtra(Constante.CHEIE_AUTENTIFICARE, studenti.get(position));
                intent.putExtra(Constante.CHEIE_AUTENTIFICARE_EXTRA, origine);
                startActivity(intent);
            }
        });
    }
}
