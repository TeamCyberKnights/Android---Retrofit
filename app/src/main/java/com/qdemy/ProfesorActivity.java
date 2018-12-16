package com.qdemy;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.qdemy.clase.Profesor;

public class ProfesorActivity extends AppCompatActivity {

    private Button intrebari;
    private Button istoric;
    private Button teste;
    private Button desfasurare;
    private ImageView inapoi;
    private ImageView start;

    private Profesor profesor;
    private String dataCurenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor);

        initializare();
    }

    private void initializare() {

        intrebari = findViewById(R.id.intrebari_button_profesor);
        istoric = findViewById(R.id.istoric_button_profesor);
        teste = findViewById(R.id.teste_button_profesor);
        desfasurare = findViewById(R.id.desfasurare_button_profesor);
        inapoi = findViewById(R.id.back_image_profesor);
        start = findViewById(R.id.start_image_profesor);

        profesor = getIntent().getParcelableExtra(Constante.CHEIE_AUTENTIFICARE);
        Toast.makeText(getApplicationContext(), "Salutare " +profesor.getNume(), Toast.LENGTH_LONG).show();

        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(v.getContext());
                dlgAlert.setMessage(R.string.deconectare_message);
                dlgAlert.setTitle(R.string.deconectare_title);
                dlgAlert.setPositiveButton(R.string.da, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                dlgAlert.setNegativeButton(R.string.nu, null);
                dlgAlert.setCancelable(true);
                AlertDialog dialog = dlgAlert.create();
                dialog.show();
            }
        });

        istoric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IstoricProfesorActivity.class);
                intent.putExtra(Constante.CHEIE_TRANSFER, profesor);
                startActivity(intent);
            }
        });

        teste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TesteActivity.class);
                intent.putExtra(Constante.CHEIE_TRANSFER, profesor);
                startActivity(intent);
            }
        });

        intrebari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MateriiActivity.class);
                intent.putExtra(Constante.CHEIE_TRANSFER, profesor);
                startActivity(intent);
            }
        });

        desfasurare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScorLiveActivity.class);
                startActivity(intent);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StartingQuizProfesorActivity.class);
                startActivity(intent);

            }
        });
    }
}
