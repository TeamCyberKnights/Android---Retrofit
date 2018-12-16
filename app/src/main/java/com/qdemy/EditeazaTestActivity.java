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
import android.widget.TextView;

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

public class EditeazaTestActivity extends AppCompatActivity {

    private ImageView inapoi;
    private TextView nume;
    private TextInputEditText descriere;
    private Spinner durateSpinner;
    private Spinner materii;
    private Spinner intrebariSpinner;
    private Button adaugaIntrebare;
    private Button actualizeazaTest;
    private ListView intrebariList;
    private RadioGroup tipuri;
    private RadioButton estePublic;
    private RadioButton estePrivat;
    private List<IntrebareGrila> intrebari = new ArrayList<>();

    private Profesor profesor;
    private Test test;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editeaza_test);

        initializare();
    }

    private void initializare() {

        //region Initializare componenete vizuale
        inapoi = findViewById(R.id.back_image_editeazaTest);
        nume = findViewById(R.id.nume_text_editeazaTest);
        descriere = findViewById(R.id.descriere_textInput_editeazaTest);
        durateSpinner = findViewById(R.id.durate_spinner_editeazaTest);
        materii = findViewById(R.id.materii_spinner_editeazaTest);
        intrebariSpinner = findViewById(R.id.intrebari_spinner_editeazaTest);
        adaugaIntrebare = findViewById(R.id.adaugaIntrebare_button_editeazaTest);
        actualizeazaTest = findViewById(R.id.actualizeaza_button_editeazaTest);
        intrebariList = findViewById(R.id.intrebari_list_editeazaTest);
        tipuri = findViewById(R.id.tipuri_radioGroup_editeazaTest);
        estePrivat = findViewById(R.id.privat_radioButton_editeazaTest);
        estePublic = findViewById(R.id.public_radioButton_editeazaTest);

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
        //endregion

        //region Initializare test
        sharedPreferences = getSharedPreferences(Constante.FISIER_PREFERINTA_UTILIZATOR, MODE_PRIVATE);
        incarcareUtilizatorSalvat();
        test = getIntent().getParcelableExtra(Constante.CHEIE_TRANSFER);
        nume.setText(test.getNume());
        descriere.setText(test.getDescriere());
        for (String s : durate.keySet()) {
            if (durate.get(s).equals(test.getMinute())) {
                durateSpinner.setSelection(adapterDurate.getPosition(s));
                break;
            }
        }
        tipuri.check(test.getEstePublic()?estePublic.getId():estePrivat.getId());

        final List<String> intrebariSpinnerList = new ArrayList<>();
        final ArrayAdapter<String> adapterIntrebari = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, intrebariSpinnerList );
        adapterIntrebari.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        intrebariSpinner.setAdapter(adapterIntrebari);

        ArrayAdapter<String> adapterMaterii = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, profesor.getNumeMaterii());
        adapterMaterii.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        materii.setAdapter(adapterMaterii);

        intrebari = test.getIntrebari();
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

        actualizeazaTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test.setDescriere(descriere.getText().toString());
                test.setIntrebari(intrebari); //!!!!!stergerea
                test.setMinute(durate.get(durateSpinner.getSelectedItem()));
                test.setEstePublic(tipuri.getCheckedRadioButtonId()==estePublic.getId()?true:false);
                test.setIntrebari(intrebari);
                profesor.setTeste(test, test.getNume());
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
                intent.putExtra(Constante.CHEIE_AUTENTIFICARE, intrebari.get(position));
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
