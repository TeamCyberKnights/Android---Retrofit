package com.qdemy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.qdemy.clase.IntrebareGrila;
import com.qdemy.clase.Profesor;
import com.qdemy.clase.Test;
import com.qdemy.clase_adapter.IntrebareAdapter;
import com.qdemy.clase_adapter.IntrebareTestAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdaugaTestActivity extends AppCompatActivity {

    private ImageView inapoi;
    private TextInputEditText nume;
    private TextInputEditText descriere;
    private Spinner durateSpinner;
    private Spinner materii;
    private Spinner intrebariSpinner;
    private Button adaugaIntrebare;
    private Button adaugaTest;
    private ListView intrebariList;
    private RadioGroup tipuri;
    private RadioButton estePublic;
    private RadioButton estePrivat;
    private List<IntrebareGrila> intrebari = new ArrayList<>();

    private SharedPreferences sharedPreferences;
    private Profesor profesor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adauga_test);

        initializare();
    }

    private void initializare() {

        //region Initializare componente vizuale
        inapoi = findViewById(R.id.back_image_adaugaTest);
        nume = findViewById(R.id.nume_textInput_adaugaTest);
        descriere = findViewById(R.id.descriere_textInput_adaugaTest);
        durateSpinner = findViewById(R.id.durate_spinner_adaugaTest);
        materii = findViewById(R.id.materii_spinner_adaugaTest);
        intrebariSpinner = findViewById(R.id.intrebare_spinner_adaugaTest);
        adaugaIntrebare = findViewById(R.id.adaugaIntrebare_button_adaugaTest);
        adaugaTest = findViewById(R.id.adauga_button_adaugaTest);
        intrebariList = findViewById(R.id.intrebari_list_adaugaTest);
        tipuri = findViewById(R.id.tipuri_radioGroup_adaugaTest);
        estePrivat = findViewById(R.id.privat_radioButton_adaugaTest);
        estePublic = findViewById(R.id.public_radioButton_adaugaTest);

        final Map<String, Integer> durate = new HashMap<String, Integer>();
        durate.put("10 minute",10);
        durate.put("15 minute",15);
        durate.put("30 minute",30);
        durate.put("45 minute",45);
        durate.put("o oră",60);
        durate.put("întregul seminar",80);

        List<String> list = new ArrayList<String>(durate.keySet());
        ArrayAdapter<String> adapterDurate = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        adapterDurate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        durateSpinner.setAdapter(adapterDurate);

        sharedPreferences = getSharedPreferences(Constante.FISIER_PREFERINTA_UTILIZATOR, MODE_PRIVATE);
        incarcareUtilizatorSalvat();

        final List<String> intrebariSpinnerList = new ArrayList<>();
        final ArrayAdapter<String> adapterIntrebari = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, intrebariSpinnerList );
        adapterIntrebari.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        intrebariSpinner.setAdapter(adapterIntrebari);

        ArrayAdapter<String> adapterMaterii = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, profesor.getNumeMaterii());
        adapterMaterii.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        materii.setAdapter(adapterMaterii);

        final IntrebareTestAdapter adapterIntrebariSelectate = new IntrebareTestAdapter(getApplicationContext(),
                R.layout.item_text_button, intrebari, getLayoutInflater(), true);
        intrebariList.setAdapter(adapterIntrebariSelectate);

        //endregion

        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adaugaIntrebare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intrebari.add(profesor.getIntrebari(intrebariSpinner.getSelectedItem().toString()));
                adapterIntrebariSelectate.notifyDataSetChanged();
                adapterIntrebari.remove(intrebariSpinner.getSelectedItem().toString());
            }
        });

        adaugaTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Test test = new Test(nume.getText().toString(), descriere.getText().toString(), profesor.getNume(),
                                     intrebari, durate.get(durateSpinner.getSelectedItem()),
                                     tipuri.getCheckedRadioButtonId()==estePublic.getId()?true:false);
                profesor.getTeste().add(test);
                salvareUtilizator();
                finish();
            }
        });

        materii.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                intrebariSpinnerList.clear();
                for(int i=0;i<profesor.getIntrebari().size();i++)
                    if(profesor.getIntrebari(i).getMaterie().equals(materii.getSelectedItem())&&
                            !(intrebari.contains(profesor.getIntrebari(i))))
                        intrebariSpinnerList.add(profesor.getIntrebari(i).getNume());
                adapterIntrebari.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        intrebariList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), IntrebareActivity.class);
                intent.putExtra(Constante.CHEIE_TRANSFER, intrebari.get(position));
                startActivity(intent);
            }
        });
    }

    private void incarcareUtilizatorSalvat() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(getString(R.string.utilizator), "");
        profesor = gson.fromJson(json, Profesor.class);
    }

    private void salvareUtilizator() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(profesor);
        editor.putString(getString(R.string.utilizator), json);
        editor.commit();
    }
}