package com.qdemy;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText nume;
    private TextInputEditText parola;
    private Button intra_cont;
    private TextView creeaza_cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializare();
    }

    private void initializare()
    {
        nume = findViewById(R.id.nume_textInput_main);
        parola = findViewById(R.id.parola_textInput_main);
        intra_cont = findViewById(R.id.intra_button_main);
        creeaza_cont = findViewById(R.id.creeaza_textView_main);

        intra_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nume.getText().toString().equals("student"))
                {
                    Intent intent = new Intent(getApplicationContext(), StudentActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(nume.getText().toString().equals("profesor"))
                {
                    Intent intent = new Intent(getApplicationContext(), ProfesorActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                    Toast.makeText(getApplicationContext(),"Nume gresit!", Toast.LENGTH_SHORT).show();
            }
        });

        creeaza_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ContActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
