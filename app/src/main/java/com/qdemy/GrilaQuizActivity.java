package com.qdemy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.qdemy.clase.EvidentaIntrebariTeste;
import com.qdemy.clase.EvidentaIntrebariTesteDao;
import com.qdemy.clase.IntrebareGrila;
import com.qdemy.clase.IntrebareGrilaDao;
import com.qdemy.clase.RaspunsIntrebareGrila;
import com.qdemy.clase.RezultatTestStudent;
import com.qdemy.clase.RezultatTestStudentDao;
import com.qdemy.clase.Student;
import com.qdemy.clase.Test;
import com.qdemy.clase.TestDao;
import com.qdemy.clase.VariantaRaspuns;
import com.qdemy.clase.VariantaRaspunsDao;
import com.qdemy.db.App;

import org.greenrobot.greendao.query.Query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.media.MediaExtractor.MetricsConstants.FORMAT;

public class GrilaQuizActivity extends AppCompatActivity {

    private TextView textIntrebare;
    private TextView timer;
    private TextView sariPeste;
    private ProgressBar progres;
    private FloatingActionButton raspunde;
    private LinearLayout varianteLayout;
    private List<Button> butoaneVariante = new ArrayList<>();

    private IntrebareGrila intrebare;
    private int nrRaspunsuriCorecte;
    private int nrRaspunsuriSelectate;
    private int minuteTrecute=0;
    long[] intrebariId;
    private long idTest;
    private int unitateProgres;
    private int indexIntrebare=0;
    private List<Integer> listaIntrebariSarite = new ArrayList<>();
    private List<RaspunsIntrebareGrila> raspunsuri = new ArrayList<>();
    private boolean intrebariSarite=false;

    private boolean estePublic=false;
    private int puncteMaxime=0;
    private int puncteObtinute=0;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grila_quiz);

        initializare();
        initializareIntrebare(0);
    }

    private void initializareIntrebare(int index) {

        //verificare exista index
        if(index>=intrebariId.length) {
            if(estePublic){
                int nota = (100/puncteMaxime)*puncteObtinute;
                Toast.makeText(getApplicationContext(), getString(R.string.nota) + " " + nota/10, Toast.LENGTH_LONG).show();
                finish();
            }
            else rezultatTest();

            return;
        }


        //verificare ultima intrebare
        if(raspunsuri.size()==intrebariId.length-1)
            sariPeste.setVisibility(View.INVISIBLE);


        //selectare intrebare
        Query<IntrebareGrila> queryIntrebare = ((App) getApplication()).getDaoSession().getIntrebareGrilaDao().queryBuilder().where(
                IntrebareGrilaDao.Properties.Id.eq(intrebariId[index])).build();
        intrebare = queryIntrebare.list().get(0);
        textIntrebare.setText(intrebare.getText());


        //selectare variante
        Query<VariantaRaspuns> queryVariante = ((App) getApplication()).getDaoSession().getVariantaRaspunsDao().queryBuilder().where(
                VariantaRaspunsDao.Properties.IntrebareId.eq(intrebare.getId())).build();
        nrRaspunsuriSelectate=0;
        nrRaspunsuriCorecte=0;
        long[] varianteId = new long[queryVariante.list().size()];
        Random generator = new Random();
        for(int i=0;i<queryVariante.list().size();i++) {
            varianteId[i] = queryVariante.list().get(i).getId();
            if(queryVariante.list().get(i).getCorect()) nrRaspunsuriCorecte++;
        }


        //permutare variante
        for(int i=0;i<varianteId.length;i++) {
            int position = i+ generator.nextInt(varianteId.length-i);
            long aux = varianteId[i];
            varianteId[i] = varianteId[position];
            varianteId[position] = aux;
        }


        //initializare variante
        float weight = varianteLayout.getWeightSum()/varianteId.length;
        butoaneVariante.clear();
        varianteLayout.removeAllViews();

        for(int i=0;i<varianteId.length;i++) {
            Query<VariantaRaspuns> queryVarianta = ((App) getApplication()).getDaoSession().getVariantaRaspunsDao().queryBuilder().where(
                    VariantaRaspunsDao.Properties.Id.eq(varianteId[i])).build();

            final Button btn = new Button(this);
            btn.setId(queryVarianta.list().get(0).getId().intValue());
            btn.setText(queryVarianta.list().get(0).getText());
            btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, weight));
            btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.bej));
            btn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.bleu_marin));

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(((ColorDrawable)btn.getBackground()).getColor()==ContextCompat.getColor(getApplicationContext(), R.color.bej)) {
                        if(nrRaspunsuriSelectate+1>nrRaspunsuriCorecte)
                            Toast.makeText(getApplicationContext(), getString(R.string.selectare_maxima_variante_raspuns, nrRaspunsuriCorecte), Toast.LENGTH_LONG).show();
                        else{
                        nrRaspunsuriSelectate++;
                        btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.portocaliu));}
                    }
                    else {
                        btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.bej));
                        nrRaspunsuriSelectate--;
                    }
                }
            });

            varianteLayout.addView(btn);
            butoaneVariante.add(btn);
        }


        //marcare progres
        progres.setProgress(unitateProgres*raspunsuri.size());
    }



    private void initializare() {

        //region Initializare test

        //selectare intrebari test
        idTest = getIntent().getLongExtra(Constante.CHEIE_TRANSFER, -1);
        if(getIntent().getStringExtra(Constante.CHEIE_TRANSFER2).equals(getString(R.string.public1)))
            estePublic=true;

        Query<EvidentaIntrebariTeste> queryIntrebari = ((App) getApplication()).getDaoSession().getEvidentaIntrebariTesteDao().queryBuilder().where(
                EvidentaIntrebariTesteDao.Properties.TestId.eq(idTest)).build();
        unitateProgres = 100 / queryIntrebari.list().size();
        intrebariId = new long[queryIntrebari.list().size()];
        Random generator = new Random();
        for(int i=0;i<queryIntrebari.list().size();i++) {
            intrebariId[i] = queryIntrebari.list().get(i).getIntrebareId();
        }

        //permutare intrebari
        for(int i=0;i<intrebariId.length;i++) {
            int position = i+ generator.nextInt(intrebariId.length-i);
            long aux = intrebariId[i];
            intrebariId[i] = intrebariId[position];
            intrebariId[position] = aux;
        }

        //endregion

        //region Initializare componente vizuale

        textIntrebare = findViewById(R.id.intrebare_text_grila);
        timer = findViewById(R.id.timer_text_grila);
        sariPeste = findViewById(R.id.sari_text_grila);
        progres = findViewById(R.id.progressBar_grila);
        raspunde = findViewById(R.id.raspunde_button_grila);
        varianteLayout = findViewById(R.id.variante_layout_grila);
        //endregion

        //region Timer
        final Query<Test> queryTest = ((App) getApplication()).getDaoSession().getTestDao().queryBuilder().where(
                TestDao.Properties.Id.eq(idTest)).build();
        new CountDownTimer((queryTest.list().get(0).getTimpDisponibil())*59*1000, 1000){
            int minute = queryTest.list().get(0).getTimpDisponibil()-1;
            int secunde = 59;
            @Override
            public void onTick(long millisUntilFinished) {
                if(secunde<10) timer.setText(String.valueOf(minute)+":0"+String.valueOf(secunde));
                else timer.setText(String.valueOf(minute)+":"+String.valueOf(secunde));
                secunde--;
                if(secunde==0)
                {
                    minute--;
                    secunde=59;
                    minuteTrecute++;
                }
            }

            @Override
            public void onFinish() {
                int nota = (100/puncteMaxime)*puncteObtinute;
                Toast.makeText(getApplicationContext(), getString(R.string.nota) + " " + nota/10, Toast.LENGTH_LONG).show();
                finish();
            }
        }.start();
        //endregion


        sariPeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaIntrebariSarite.add(indexIntrebare);

                if(intrebariSarite==false) {
                    indexIntrebare++;
                    if (indexIntrebare == intrebariId.length) {
                        if (listaIntrebariSarite.size() > 0) {
                            intrebariSarite = true;
                            initializareIntrebare(listaIntrebariSarite.get(0));
                            indexIntrebare=listaIntrebariSarite.get(0);
                            listaIntrebariSarite.remove(0);
                        }
                    }
                    else initializareIntrebare(indexIntrebare);
                }
                else {
                    initializareIntrebare(listaIntrebariSarite.get(0));
                    indexIntrebare=listaIntrebariSarite.get(0);
                    listaIntrebariSarite.remove(0);
                }

            }
        });

        raspunde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificareRaspuns();
            }
        });

    }

    private void verificareRaspuns(){

        //calculare punctaj
        double punctajRaspunsCorect = intrebare.getDificultate()/nrRaspunsuriCorecte;
        double punctajObtinut=0;
        for ( Button buton : butoaneVariante ) {
            if(((ColorDrawable)buton.getBackground()).getColor()==ContextCompat.getColor(getApplicationContext(), R.color.portocaliu)) {
                Query<VariantaRaspuns> queryVarianta = ((App) getApplication()).getDaoSession().getVariantaRaspunsDao().queryBuilder().where(
                        VariantaRaspunsDao.Properties.Id.eq(buton.getId())).build();
                if(queryVarianta.list().get(0).getCorect()) punctajObtinut+=punctajRaspunsCorect;
            }
        }
        if(estePublic){
            puncteMaxime+=intrebare.getDificultate();
            puncteObtinute+=punctajObtinut;
        }
        else
        {
            RaspunsIntrebareGrila raspuns = new RaspunsIntrebareGrila(intrebare.getId(), (float)punctajObtinut);
            ((App) getApplication()).getDaoSession().getRaspunsIntrebareGrilaDao().insert(raspuns);
            raspunsuri.add(raspuns);
        }


        //feedback raspuns
        for ( Button buton : butoaneVariante ) {
            if(((ColorDrawable)buton.getBackground()).getColor()==ContextCompat.getColor(getApplicationContext(), R.color.portocaliu)) {
                Query<VariantaRaspuns> queryVariantaSelectata = ((App) getApplication()).getDaoSession().getVariantaRaspunsDao().queryBuilder().where(
                        VariantaRaspunsDao.Properties.Id.eq(buton.getId())).build();
                if (!(queryVariantaSelectata.list().get(0).getCorect()))
                    buton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.rosu));
            }
            Query<VariantaRaspuns> queryVarianta = ((App) getApplication()).getDaoSession().getVariantaRaspunsDao().queryBuilder().where(
                        VariantaRaspunsDao.Properties.Id.eq(buton.getId())).build();
                if(queryVarianta.list().get(0).getCorect())  buton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.verde));
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                indexIntrebare++;
                initializareIntrebare(indexIntrebare);
            }
        }, 1000);
    }


    private void rezultatTest() {

        Student student = ((App) getApplication()).getStudent();
        String data = new SimpleDateFormat(Constante.DATE_FORMAT).format(Calendar.getInstance().getTime());
        ((App) getApplication()).getDaoSession().getRezultatTestStudentDao().insert(
                new RezultatTestStudent(idTest, data, minuteTrecute, raspunsuri,
                        ((App) getApplication()).getPunctajTest(raspunsuri)>50?true:false, student.getId()));

        Query<RezultatTestStudent> queryRezultat = ((App) getApplication()).getDaoSession().getRezultatTestStudentDao().queryBuilder().where(
                RezultatTestStudentDao.Properties.TestId.eq(idTest),
                RezultatTestStudentDao.Properties.Data.eq(data),
                RezultatTestStudentDao.Properties.StudentId.eq(student.getId())).build();

        for (RaspunsIntrebareGrila raspuns: raspunsuri ) {
            raspuns.setRezultatTestStudentId(queryRezultat.list().get(0).getId());
            ((App) getApplication()).getDaoSession().getRaspunsIntrebareGrilaDao().update(raspuns);
        }

        Intent intent = new Intent(getApplicationContext(), RezultatStudentActivity.class);
        intent.putExtra(Constante.CHEIE_TRANSFER, queryRezultat.list().get(0).getId());
        startActivity(intent);
        finish();
    }


}
