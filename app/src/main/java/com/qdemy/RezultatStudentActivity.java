package com.qdemy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RezultatStudentActivity extends AppCompatActivity {

    private TextView scor;
    private Button salveaza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezultat_student);

        initializare();
    }

    private void initializare() {

        scor = findViewById(R.id.scor_text_rezultatStudent);
        salveaza = findViewById(R.id.salveaza_button_rezultatStudent);

        salveaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //salveaza rezultatul
                Intent intent = new Intent(getApplicationContext(), StudentActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
