package com.qdemy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.qdemy.clase.Profesor;
import com.qdemy.clase_adapter.MaterieAdapter;
import com.qdemy.clase_adapter.PartajareTestAdapter;

import java.util.ArrayList;
import java.util.List;

public class PartajeazaTestActivity extends AppCompatActivity {

    private ImageView inapoi;
    private Button partajeaza;
    private Spinner profesoriSpinner;
    private ListView profesoriList;
    private List<Profesor> profesori = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partajeaza_test);

        initializare();
    }

    private void initializare() {

        inapoi = findViewById(R.id.back_image_partajeazaTest);
        partajeaza = findViewById(R.id.partajeaza_button_partajeazaTest);
        profesoriSpinner = findViewById(R.id.persoane_spinner_partajeazaTest);
        profesoriList = findViewById(R.id.persoane_list_partajeazaTest);

        //INITIALIZARE PROFESORI

        PartajareTestAdapter adapter = new PartajareTestAdapter(getApplicationContext(),
                R.layout.item_text_button, profesori, getLayoutInflater());
        profesoriList.setAdapter(adapter);

        
        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        partajeaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adauga persoana in layout
            }
        });
    }
}
