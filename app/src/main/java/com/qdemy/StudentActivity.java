package com.qdemy;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.qdemy.clase.Student;
import com.qdemy.db.App;

public class StudentActivity extends AppCompatActivity {

    private ImageView inapoi;
    private ImageView setari;
    private ImageView start;
    private Button istoric;

    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        initializare();
    }

    private void initializare() {

        inapoi = findViewById(R.id.back_image_student);
        setari = findViewById(R.id.setari_image_student);
        start = findViewById(R.id.start_image_student);
        istoric = findViewById(R.id.istoric_button_student);

        student = ((App) getApplication()).getStudent();
        Toast.makeText(getApplicationContext(), getString(R.string.salutare) + " " +student.getNume() + " " +student.getPrenume(), Toast.LENGTH_LONG).show();

        inapoi.setOnClickListener(new View.OnClickListener() {
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


            }
        });

        istoric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IstoricStudentActivity.class);
                startActivity(intent);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StartQuizActivity.class);
                startActivity(intent);
            }
        });
    }
}
