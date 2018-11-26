package com.qdemy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.qdemy.clase.RezultatTestProfesor;
import com.qdemy.clase.Student;
import com.qdemy.clase_adapter.RezultatTestAdapter;

import java.util.List;

public class ScorLiveActivity extends AppCompatActivity {

    private ImageView inapoi;
    private TextView timer;
    private List<Student> studenti;
    private ListView studentiList;
    private RezultatTestProfesor test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scor_live);

        initializare();
    }

    private void initializare() {

        inapoi = findViewById(R.id.back_image_scorLive);
        timer = findViewById(R.id.timer_text_scorLive);
        studentiList = findViewById(R.id.studenti_list_scorLive);

        //initializare studenti
        //initializare test
        RezultatTestAdapter adapter = new RezultatTestAdapter(getApplicationContext(),
                R.layout.item_text_text, studenti, getLayoutInflater(), test);
        studentiList.setAdapter(adapter);


        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
