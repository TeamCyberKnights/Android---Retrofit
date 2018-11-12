package com.qdemy;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class IstoricStudentActivity extends AppCompatActivity {

    private ImageView inapoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_istoric_student);

        initializare();
    }

    private void initializare() {

        inapoi = findViewById(R.id.back_image_istoricStudent);
        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
