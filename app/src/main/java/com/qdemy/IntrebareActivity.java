package com.qdemy;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class IntrebareActivity extends AppCompatActivity {

    private ImageView inapoi;
    private FloatingActionButton editeaza;
    private TextView materia;
    private TextView numeIntrebare;
    private TextView intrebare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intrebare);

        initialzare();
    }

    private void initialzare() {

        inapoi = findViewById(R.id.back_image_intrebare);
        editeaza = findViewById(R.id.editeaza_button_intrebare);
        materia = findViewById(R.id.materia_text_intrebare);
        numeIntrebare = findViewById(R.id.intrebare_text_intrebare);
        intrebare = findViewById(R.id.descriere_text_intrebare);

        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editeaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditeazaIntrebareActivity.class);
                startActivity(intent);
            }
        });
    }
}
