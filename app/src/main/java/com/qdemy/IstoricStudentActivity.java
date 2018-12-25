package com.qdemy;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.qdemy.clase.RezultatTestStudent;
import com.qdemy.clase_adapter.IstoricProfesorAdapter;
import com.qdemy.clase_adapter.IstoricStudentAdapter;

import java.util.ArrayList;
import java.util.List;

public class IstoricStudentActivity extends AppCompatActivity {

    private ImageView inapoi;
    private ListView testeList;
    private List<RezultatTestStudent> teste = new ArrayList<>();
    private final String origine = getString(R.string.student);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_istoric_student);

        initializare();
    }

    private void initializare() {

        inapoi = findViewById(R.id.back_image_istoricStudent);
        testeList = findViewById(R.id.istoric_list_istoricStudent);
        //initializare teste
        IstoricStudentAdapter adapter = new IstoricStudentAdapter(getApplicationContext(),
                R.layout.item_text_text_button, teste, getLayoutInflater());
        testeList.setAdapter(adapter);

        //INITIALIZARE CAMPURI CU ISTORICUL STUDENTULUI

        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        testeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), RezumatStudentActivity.class);
                //intent.putExtra(Constante.CHEIE_AUTENTIFICARE, teste.get(position));
                intent.putExtra(Constante.CHEIE_AUTENTIFICARE_EXTRA, origine);
                startActivity(intent);
            }
        });
    }
}
