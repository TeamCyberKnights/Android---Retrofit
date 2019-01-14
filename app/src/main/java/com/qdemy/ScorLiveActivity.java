package com.qdemy;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.qdemy.clase.Profesor;
import com.qdemy.clase.RezultatTestStudent;
import com.qdemy.clase.Student;
import com.qdemy.clase.Test;
import com.qdemy.clase.TestDao;
import com.qdemy.clase.TestSustinut;
import com.qdemy.clase.TestSustinutDao;
import com.qdemy.clase.VariantaRaspunsDao;
import com.qdemy.clase_adapter.RezultatTestAdapter;
import com.qdemy.db.App;
import com.qdemy.servicii.NetworkConnectionService;
import com.qdemy.servicii.ServiceBuilder;
import com.qdemy.servicii.TestSustinutService;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ScorLiveActivity extends AppCompatActivity {

    private ImageView inapoi;
    private TextView timer;
    private ListView studentiList;
    private Test test;
    private int minuteTrecute=0;
    private ProgressBar progres;
    private RezultatTestAdapter adapter;

    private List<RezultatTestStudent> rezultateStudenti = new ArrayList<>();
    private Profesor profesor;
    private List<TestSustinut> testeSustinute;
    private boolean internetIsAvailable;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scor_live);
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

        //region Initializare componente vizuale
        inapoi = findViewById(R.id.back_image_scorLive);
        timer = findViewById(R.id.timer_text_scorLive);
        studentiList = findViewById(R.id.studenti_list_scorLive);
        progres = findViewById(R.id.progressBar_scorLive);
        //endregion

        adapter = new RezultatTestAdapter(getApplicationContext(),
                R.layout.item_text_text, rezultateStudenti, getLayoutInflater(), ScorLiveActivity.this);
        studentiList.setAdapter(adapter);


        //region Timer
        test = ((App) getApplication()).getTestLive();
        new CountDownTimer((test.getTimpDisponibil())*59*1000, 1000){
            int minute = test.getTimpDisponibil()-1;
            int secunde = 59;
            final int unitateProgres = 100/((test.getTimpDisponibil())*59);
            int secundeRamase = (test.getTimpDisponibil())*59;

            @Override
            public void onTick(long millisUntilFinished) {
                if(secunde<10) timer.setText(String.valueOf(minute)+":0"+String.valueOf(secunde));
                else timer.setText(String.valueOf(minute)+":"+String.valueOf(secunde));
                secunde--;
                secundeRamase--;
                progres.setProgress(unitateProgres*secundeRamase);
                if(secunde==0)
                {
                    minute--;
                    secunde=59;
                    minuteTrecute++;
                }
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(), RezultatStudentActivity.class);
                //intent.putExtra(Constante.CHEIE_TRANSFER, id);
                startActivity(intent);
                finish();
            }
        }.start();
        //endregion

        //endregion

        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        studentiList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(), RezumatStudentActivity.class);
                    intent.putExtra(Constante.CHEIE_TRANSFER, rezultateStudenti.get(position).getId());
                    startActivity(intent);
                    finish();
            }
        });
    }
    // Verifica daca s-au adaugat rezultate ale testelor in bd
    public void actualizareRezultateStudenti()
    {
        //VEZI AICI
        //COSMIN - TO DO
        //ACTUALIZARE LIVE CAND TERMINA STUDENTII TESTUL
        //VA APAREA REZULTATUL FIECARUI STUDENT CARE TERMINA TESTUL
        //INSERT REZULTAT TEST STUDENT IN LISTA DE REZULTATE DIN TEST SUSTINUT

        //1. select rezultatteststudent in funtie de idTest si data
        // select test sustinut in functie de idTest

        //iau atributul rezultate din test sustinut si adaug 1.
        // modific rezultate pentru test sustinut cu noile rezultate
        // update test sustinut
        //Trebuie si in sql lite


        if (internetIsAvailable) {
            TestSustinutService testSustinutService = ServiceBuilder.buildService(TestSustinutService.class);
            Call<List<TestSustinut>> testeSustinuteRequest = testSustinutService.getTesteSustinutByProfesorId((int)(long)profesor.getId());
            testeSustinuteRequest.enqueue(new Callback<List<TestSustinut>>() {
                @Override
                public void onResponse(Call<List<TestSustinut>> call, Response<List<TestSustinut>> response) {
                    testeSustinute = response.body();
                }

                @Override
                public void onFailure(Call<List<TestSustinut>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Nu s-a putut gasi teste", Toast.LENGTH_SHORT).show();
                }
            });

            TestSustinut testSustinut = testeSustinute.get(testeSustinute.size()-1);
            rezultateStudenti = testSustinut.getRezultate();
           // adapter.notifyDataSetChanged();
        }


            Query<TestSustinut> queryTestSustinut = ((App) getApplication()).getDaoSession().getTestSustinutDao().queryBuilder().where(
                    TestSustinutDao.Properties.ProfesorId.eq(profesor.getId())).build();
            TestSustinut testSustinut = queryTestSustinut.list().get(queryTestSustinut.list().size() - 1); //ultimul test sustinut adaugat
            rezultateStudenti = testSustinut.getRezultate();
            // VEZI AICI
            //UPDATE TEST SUSTINUT CU NOUA LISTA DE REZULTATE
            // Trebuie si in sql lite
            adapter.notifyDataSetChanged();


    }
}
