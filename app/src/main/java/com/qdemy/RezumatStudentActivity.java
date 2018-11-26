package com.qdemy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.qdemy.clase.RaspunsIntrebareGrila;
import com.qdemy.clase_adapter.RezultatStudentAdapter;
import com.qdemy.clase_adapter.RezumatStudentAdapter;

import java.util.List;

public class RezumatStudentActivity extends AppCompatActivity {

    private ImageView inapoi;
    private TextView numeTest;
    private TextView numeStudent;
    private TextView promovat;
    private List<RaspunsIntrebareGrila> intrebari;
    private ListView intrebariList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezumat_student);

        initializare();
    }

    private void initializare() {

        inapoi = findViewById(R.id.back_image_rezumatStudent);
        numeStudent = findViewById(R.id.numeStudent_text_rezumatStudent);
        numeTest = findViewById(R.id.test_text_rezumatStudent);
        promovat = findViewById(R.id.promovat_text_rezumatStudent);
        intrebariList = findViewById(R.id.intrebari_list_rezumatStudent);
        //initializare intrebari

        RezumatStudentAdapter adapter = new RezumatStudentAdapter(getApplicationContext(),
                R.layout.item_text_text_text, intrebari, getLayoutInflater());
        intrebariList.setAdapter(adapter);

        //INITIALIZARE CAMPURI TEST SELECTAT

        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
