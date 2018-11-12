package com.qdemy;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ContActivity extends AppCompatActivity {

    private TextInputEditText mail;
    private TextInputEditText nume;
    private TextInputEditText parola;
    private Button salveaza;
    private TextView renunta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cont);

        initializare();
    }


    private void initializare()
    {
        nume = findViewById(R.id.nume_textInput_cont);
        parola = findViewById(R.id.parola_textInput_cont);
        mail = findViewById(R.id.mail_textInput_cont);
        salveaza = findViewById(R.id.salveaza_button_cont);
        renunta = findViewById(R.id.renunta_textView_cont);

        salveaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nume.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(),"Completeaza campul - Nume!", Toast.LENGTH_SHORT).show();

                else if(parola.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(),"Completeaza campul - Parola!", Toast.LENGTH_SHORT).show();

                else if(mail.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(),"Completeaza campul - Mail institutional!", Toast.LENGTH_SHORT).show();

                else
                {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        renunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
