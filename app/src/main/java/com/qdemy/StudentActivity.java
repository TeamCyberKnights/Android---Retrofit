package com.qdemy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.qdemy.clase.Profesor;
import com.qdemy.clase.ProfesorDao;
import com.qdemy.clase.Student;
import com.qdemy.db.App;
import com.qdemy.servicii.NetworkConnectionService;
import com.qdemy.servicii.ProfesorService;
import com.qdemy.servicii.ServiceBuilder;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class StudentActivity extends AppCompatActivity {

    private ImageView inapoi;
    private ImageView start;
    private ImageView mail;
    private ImageView istoric;
    private ImageView testePublice;
    private List<Profesor> profesori;
    private boolean internetIsAvailable;

    private Student student;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if ( activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting()) {
            internetIsAvailable = true;
        }
        initializare();
    }

    private void initializare() {

        inapoi = findViewById(R.id.back_image_student);
        start = findViewById(R.id.start_image_student);
        istoric = findViewById(R.id.istoric_button_student);
        mail = findViewById(R.id.mail_button_student);
        testePublice = findViewById(R.id.testePublice_button_student);

        student = ((App) getApplication()).getStudent();


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StartQuizActivity.class);
                startActivity(intent);
            }
        });


        //region Meniu

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
                dialog.getWindow().setBackgroundDrawableResource(R.color.bej);

            }
        });

        istoric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IstoricStudentActivity.class);
                startActivity(intent);
                finish();
            }
        });


        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> mailuriProfesori = new ArrayList<>();

                if (internetIsAvailable) {
                    ProfesorService profesorService = ServiceBuilder.buildService(ProfesorService.class);
                    Call<List<Profesor>> profesorsRequest = profesorService.getProfesori();
                    profesorsRequest.enqueue(new Callback<List<Profesor>>() {
                        @Override
                        public void onResponse(Call<List<Profesor>> call, Response<List<Profesor>> response) {
                            profesori = response.body();
                        }

                        @Override
                        public void onFailure(Call<List<Profesor>> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Nu s-au putut gasi profesori", Toast.LENGTH_SHORT).show();
                        }
                    });

                    for (Profesor profesor : profesori) {
                        mailuriProfesori.add(profesor.getMail());
                    }

                }
                else {
                    Query<Profesor> query = ((App) getApplication()).getDaoSession().getProfesorDao().queryBuilder().build();
                    for (Profesor profesor : query.list()) {
                        mailuriProfesori.add(profesor.getMail());
                    }
                }



                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle(R.string.alege_profesorul);
                View view = getLayoutInflater().inflate(R.layout.spinner_dialog, null);
                final Spinner spinner = view.findViewById(R.id.spinner_dialog);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(),
                        android.R.layout.simple_spinner_item, mailuriProfesori);
                adapter.setDropDownViewResource(R.layout.item_spinner);
                spinner.setAdapter(adapter);

                builder.setPositiveButton(R.string.trimite_mail, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setData(Uri.parse("mailto:"));
                        emailIntent.setType("text/plain");
                        String[] TO = {spinner.getSelectedItem().toString()};
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                        //emailIntent.putExtra(Intent.EXTRA_SUBJECT, "TEST");
                        //emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");
                        try {
                            startActivity(Intent.createChooser(emailIntent, getString(R.string.trimite_mail2)));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(getApplicationContext(), getString(R.string.error_message_app_mail), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getWindow().setBackgroundDrawableResource(R.color.portocaliu);

            }
        });

        testePublice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TestePubliceActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //endregion
    }
}
