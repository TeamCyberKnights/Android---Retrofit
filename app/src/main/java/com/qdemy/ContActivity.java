package com.qdemy;

import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.qdemy.clase.Student;

public class ContActivity extends AppCompatActivity {

    private TextInputEditText mail;
    private TextInputEditText nume;
    private TextInputEditText utilizator;
    private TextInputEditText parola;
    private Button salveaza;
    private TextView renunta;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cont);

        initializare();
    }


    private void initializare()
    {
        nume = findViewById(R.id.nume_textInput_cont);
        utilizator = findViewById(R.id.utilizator_textInput_cont);
        parola = findViewById(R.id.parola_textInput_cont);
        mail = findViewById(R.id.mail_textInput_cont);
        salveaza = findViewById(R.id.salveaza_button_cont);
        renunta = findViewById(R.id.renunta_textView_cont);
        intent=getIntent();

        salveaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nume.getText().toString().trim().isEmpty())
                    Toast.makeText(getApplicationContext(),getString(R.string.completeaza_nume), Toast.LENGTH_SHORT).show();

                else if(parola.getText().toString().trim().isEmpty())
                    Toast.makeText(getApplicationContext(),getString(R.string.completeaza_parola), Toast.LENGTH_SHORT).show();

                else if(mail.getText().toString().trim().isEmpty())
                    Toast.makeText(getApplicationContext(),getString(R.string.completeaza_mail), Toast.LENGTH_SHORT).show();

                else
                {
                    Student student = new Student(nume.getText().toString(), utilizator.getText().toString(), parola.getText().toString(), mail.getText().toString());

                    intent.putExtra(Constante.CHEIE_CONT_NOU, student);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        renunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
