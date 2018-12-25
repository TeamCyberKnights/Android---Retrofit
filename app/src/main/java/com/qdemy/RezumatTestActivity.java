package com.qdemy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.qdemy.clase.IntrebareGrila;
import com.qdemy.clase.RaspunsIntrebareGrila;
import com.qdemy.clase.Student;
import com.qdemy.clase.TestSustinut;
import com.qdemy.clase_adapter.RezumatStudentAdapter;
import com.qdemy.clase_adapter.RezumatTestIntrebariAdapter;
import com.qdemy.clase_adapter.RezumatTestStudentiAdapter;

import java.util.List;

public class RezumatTestActivity extends AppCompatActivity {

    private TextView numeTest;
    private TextView promovati;
    private TextView nume;
    private TextView scor;
    private ImageView inapoi;
    private RadioButton studentiRadioButton;
    private RadioButton intrebariRadioButton;
    private RadioGroup optiuni;
    private List<Student> studenti;
    private List<IntrebareGrila> intrebari;
    private ListView informatiiList;
    private TestSustinut test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezumat_test);

        initializare();
    }

    private void initializare() {

        numeTest = findViewById(R.id.test_text_rezumatTest);
        promovati = findViewById(R.id.promovati_text_rezumatTest);
        nume = findViewById(R.id.nume_text_rezumatTest);
        scor = findViewById(R.id.scor_text_rezumatTest);
        inapoi = findViewById(R.id.back_image_rezumatTest);
        studentiRadioButton = findViewById(R.id.studenti_radioButton_rezumatTest);
        intrebariRadioButton = findViewById(R.id.intrebari_radioButton_rezumatTest);
        optiuni = findViewById(R.id.optiuni_radioGroup_rezumatTest);
        informatiiList = findViewById(R.id.informatii_list_rezumatTest);
        //initializare studenti initial
        //initializare intrebari
        //initializare test
        final RezumatTestStudentiAdapter adapterStudenti = new RezumatTestStudentiAdapter(getApplicationContext(),
                R.layout.item_text_text, studenti, getLayoutInflater(), test);
        final RezumatTestIntrebariAdapter adapterIntrebari = new RezumatTestIntrebariAdapter(getApplicationContext(),
                R.layout.item_text_text, intrebari, getLayoutInflater(), test);
        informatiiList.setAdapter(adapterStudenti);

        //INITIALIZARE CAMPURI TEST SELECTAT

        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        optiuni.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(studentiRadioButton.isChecked())
                {
                    scor.setText(getString(R.string.corecte));
                    nume.setText(getString(R.string.ntrebare));
                    informatiiList.setAdapter(adapterStudenti);
                }
                else
                {
                    scor.setText(getString(R.string.punctaj));
                    nume.setText(getString(R.string.Nume_student1));
                    informatiiList.setAdapter(adapterIntrebari);
                }
            }
        });

    }
}
