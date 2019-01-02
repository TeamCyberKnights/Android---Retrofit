package com.qdemy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.qdemy.clase.EvidentaIntrebariTeste;
import com.qdemy.clase.EvidentaIntrebariTesteDao;
import com.qdemy.clase.EvidentaMateriiProfesori;
import com.qdemy.clase.EvidentaMateriiProfesoriDao;
import com.qdemy.clase.IntrebareGrila;
import com.qdemy.clase.IntrebareGrilaDao;
import com.qdemy.clase.Profesor;
import com.qdemy.clase.Test;
import com.qdemy.clase.TestDao;
import com.qdemy.clase.TestPartajat;
import com.qdemy.clase.TestPartajatDao;
import com.qdemy.clase.VariantaRaspuns;
import com.qdemy.clase.VariantaRaspunsDao;
import com.qdemy.clase_adapter.IntrebareAdapter;
import com.qdemy.clase_adapter.TestAdapter;
import com.qdemy.db.App;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TestPartajatDetaliiActivity extends AppCompatActivity {

    private ImageView deconectare;
    private ImageView inapoi;
    private ImageView acasa;
    private ImageView istoric;
    private ImageView intrebarileMele;
    private ImageView testeleMele;
    private ListView intrebariList;
    private TextView nume;
    private TextView descriere;
    private TextView estePublic;
    private TextView timp;
    private ArrayAdapter<String> adapter;
    private List<String> intrebari = new ArrayList<>();
    private Test test;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_partajat_detalii);

        initializare();
    }

    private void initializare() {

        //region Intializare componenete vizuale
        deconectare = findViewById(R.id.deconectare_image_testPartajatDetalii);
        inapoi = findViewById(R.id.back_image_testPartajatDetalii);
        testeleMele = findViewById(R.id.teste_image_testPartajatDetalii);
        acasa = findViewById(R.id.acasa_image_testPartajatDetalii);
        istoric = findViewById(R.id.istoric_image_testPartajatDetalii);
        intrebarileMele = findViewById(R.id.intrebari_image_testPartajatDetalii);
        intrebariList = findViewById(R.id.intrebari_list_testPartajatDetalii);
        nume = findViewById(R.id.nume_text_testPartajatDetalii);
        descriere = findViewById(R.id.descriere_text_testPartajatDetalii);
        estePublic = findViewById(R.id.public_text_testPartajatDetalii);
        timp = findViewById(R.id.timp_text_testPartajatDetalii);
        //endregion

        //region Initializare test partajat
        test = ((App) getApplication()).getTest(getIntent().getLongExtra(Constante.CHEIE_TRANSFER,-1));

        //COSMIN - TO DO SELECT INTREBARI TEST CURENT
        for (IntrebareGrila intrebare : test.getIntrebari()) {
            intrebari.add(intrebare.getText());
        }
        nume.setText(test.getNume());
        descriere.setText(test.getDescriere());
        estePublic.setText(test.getEstePublic() ? getString(R.string.public1) : getString(R.string.privat));
        timp.setText(getString(R.string.minute2, test.getTimpDisponibil()));

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, intrebari);
        intrebariList.setAdapter(adapter);
        //endregion

        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //region Meniu

        deconectare.setOnClickListener(new View.OnClickListener() {
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
                dialog.getWindow().setBackgroundDrawableResource(R.color.bej);
            }
        });

        acasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfesorActivity.class);
                startActivity(intent);
                finish();
            }
        });

        istoric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IstoricProfesorActivity.class);
                startActivity(intent);
                finish();
            }
        });


        intrebarileMele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MateriiActivity.class);
                intent.putExtra(Constante.CHEIE_TRANSFER, getString(R.string.ntreb_rile_mele));
                startActivity(intent);
                finish();
            }
        });

        testeleMele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MateriiActivity.class);
                intent.putExtra(Constante.CHEIE_TRANSFER, getString(R.string.testele_mele));
                startActivity(intent);
                finish();
            }
        });

        //endregion

    }
}
