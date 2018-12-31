package com.qdemy;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

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

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scor_live);

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
    }

    public void actualizareRezultateStudenti()
    {
        //ACTUALIZARE LIVE CAND TERMINA STUDENTII TESTUL
        //VA APAREA REZULTATUL FIECARUI STUDENT CARE TERMINA TESTUL
        Query<TestSustinut> queryTestSustinut = ((App) getApplication()).getDaoSession().getTestSustinutDao().queryBuilder().where(
                TestSustinutDao.Properties.ProfesorId.eq(profesor.getId())).build();
        TestSustinut testSustinut = queryTestSustinut.list().get(queryTestSustinut.list().size()-1); //ultimul test sustinut adaugat
        rezultateStudenti = testSustinut.getRezultate();
        adapter.notifyDataSetChanged();

    }
}
