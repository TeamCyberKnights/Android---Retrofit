package com.qdemy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RezumatStudentActivity extends AppCompatActivity {

    private ImageView inapoi;
    private TextView numeTest;
    private TextView numeStudent;
    private TextView promovat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezumat_student);

        initializare();
    }

    private void initializare() {

        inapoi = findViewById(R.id.back_image_rezumatStudent);
        numeStudent = findViewById(R.id.numeStudent_text_rezumatStudent);
        numeTest = findViewById(R.id.test_text_rezumatStudent);
        promovat = findViewById(R.id.promovat_text_rezumatStudent);
        //incarcare date despre testul studentului

        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
