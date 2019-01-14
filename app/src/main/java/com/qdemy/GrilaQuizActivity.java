package com.qdemy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.qdemy.servicii.EvidentaIntrebariTesteService;
import com.qdemy.servicii.IntrebareGrilaService;
import com.qdemy.servicii.NetworkConnectionService;
import com.qdemy.servicii.RaspunsIntrebareGrilaService;
import com.qdemy.servicii.RezultatTestStudentService;
import com.qdemy.servicii.ServiceBuilder;
import com.qdemy.servicii.TestService;
import com.qdemy.servicii.VariantaRaspunsService;

import org.greenrobot.greendao.query.Query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
    private IntrebareGrila intrebareGrila;
    private List<VariantaRaspuns> varianteRaspuns;
    private VariantaRaspuns variantaRaspuns;
    private List<EvidentaIntrebariTeste> evidentaIntrebariTeste;
    private Test test;
    private RezultatTestStudent rezultatTestStudent;
    private VariantaRaspuns variantaRaspuns2;
    private VariantaRaspuns variantaRaspuns3;
    private VariantaRaspuns variantaRaspuns4;
    private boolean internetIsAvailable;

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
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if ( activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting()) {
            internetIsAvailable = true;
        }
        initializare();
        initializareIntrebare(0);
    }

    private void initializareIntrebare(int index) {

        //verificare exista index
        if (index >= intrebariId.length) {
            if (estePublic) {
                int nota = (100 / puncteMaxime) * puncteObtinute;
                Toast.makeText(getApplicationContext(), getString(R.string.nota) + " " + nota / 10, Toast.LENGTH_LONG).show();
                finish();
            } else rezultatTest();

            return;
        }


        //verificare ultima intrebare
        if (raspunsuri.size() == intrebariId.length - 1)
            sariPeste.setVisibility(View.INVISIBLE);


        //selectare intrebare


        if (internetIsAvailable) {
            IntrebareGrilaService intrebareGrilaService = ServiceBuilder.buildService(IntrebareGrilaService.class);
            Call<IntrebareGrila> intrebareGrilaRequest = intrebareGrilaService.getIntrebareGrilaById((int) (long) intrebariId[index]);
            intrebareGrilaRequest.enqueue(new Callback<IntrebareGrila>() {
                @Override
                public void onResponse(Call<IntrebareGrila> call, Response<IntrebareGrila> response) {
                    intrebareGrila = response.body();
                }

                @Override
                public void onFailure(Call<IntrebareGrila> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Nu s-a putut gasi intrebarea", Toast.LENGTH_SHORT).show();
                }
            });

            intrebare = intrebareGrila;
            textIntrebare.setText(intrebare.getText());
            //selectare variante
            final VariantaRaspunsService variantaRaspunsService = ServiceBuilder.buildService(VariantaRaspunsService.class);
            Call<List<VariantaRaspuns>> varianteRaspunsRequest = variantaRaspunsService.getVarianteRaspunsByIntrebareId((int) (long) intrebare.getId());
            varianteRaspunsRequest.enqueue(new Callback<List<VariantaRaspuns>>() {
                @Override
                public void onResponse(Call<List<VariantaRaspuns>> call, Response<List<VariantaRaspuns>> response) {
                    varianteRaspuns = response.body();
                }

                @Override
                public void onFailure(Call<List<VariantaRaspuns>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Nu s-au putut gasi variantele de raspuns", Toast.LENGTH_SHORT).show();

                }
            });
            nrRaspunsuriSelectate = 0;
            nrRaspunsuriCorecte = 0;
            long[] varianteId = new long[varianteRaspuns.size()];
            Random generator = new Random();
            for (int i = 0; i < varianteRaspuns.size(); i++) {
                varianteId[i] = varianteRaspuns.get(i).getId();
                if (varianteRaspuns.get(i).getCorect()) nrRaspunsuriCorecte++;
            }
            //permutare variante
            for (int i = 0; i < varianteId.length; i++) {
                int position = i + generator.nextInt(varianteId.length - i);
                long aux = varianteId[i];
                varianteId[i] = varianteId[position];
                varianteId[position] = aux;
            }

            //initializare variante
            float weight = varianteLayout.getWeightSum() / varianteId.length;
            butoaneVariante.clear();
            varianteLayout.removeAllViews();

            for (int i = 0; i < varianteId.length; i++) {


                Call<VariantaRaspuns> variantaRaspunsRequest = variantaRaspunsService.getVariantaRaspunsById((int) (long) varianteId[i]);
                variantaRaspunsRequest.enqueue(new Callback<VariantaRaspuns>() {
                    @Override
                    public void onResponse(Call<VariantaRaspuns> call, Response<VariantaRaspuns> response) {
                        variantaRaspuns = response.body();
                    }

                    @Override
                    public void onFailure(Call<VariantaRaspuns> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Nu s-a putut gasi varianta de raspuns", Toast.LENGTH_SHORT).show();
                    }
                });


                final Button btn = new Button(this);
                btn.setId(variantaRaspuns.getId().intValue());
                btn.setText(variantaRaspuns.getText());
                btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, weight));
                btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.bej));
                btn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.bleu_marin));

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((ColorDrawable) btn.getBackground()).getColor() == ContextCompat.getColor(getApplicationContext(), R.color.bej)) {
                            if (nrRaspunsuriSelectate + 1 > nrRaspunsuriCorecte)
                                Toast.makeText(getApplicationContext(), getString(R.string.selectare_maxima_variante_raspuns, nrRaspunsuriCorecte), Toast.LENGTH_LONG).show();
                            else {
                                nrRaspunsuriSelectate++;
                                btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.portocaliu));
                            }
                        } else {
                            btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.bej));
                            nrRaspunsuriSelectate--;
                        }
                    }
                });

                varianteLayout.addView(btn);
                butoaneVariante.add(btn);
            }


            //marcare progres
            progres.setProgress(unitateProgres * raspunsuri.size());

        } else {

            Query<IntrebareGrila> queryIntrebare = ((App) getApplication()).getDaoSession().getIntrebareGrilaDao().queryBuilder().where(
                    IntrebareGrilaDao.Properties.Id.eq(intrebariId[index])).build();
            intrebare = queryIntrebare.list().get(0);
            textIntrebare.setText(intrebare.getText());


            //selectare variante

            Query<VariantaRaspuns> queryVariante = ((App) getApplication()).getDaoSession().getVariantaRaspunsDao().queryBuilder().where(
                    VariantaRaspunsDao.Properties.IntrebareId.eq(intrebare.getId())).build();
            nrRaspunsuriSelectate = 0;
            nrRaspunsuriCorecte = 0;
            long[] varianteId = new long[queryVariante.list().size()];
            Random generator = new Random();
            for (int i = 0; i < queryVariante.list().size(); i++) {
                varianteId[i] = queryVariante.list().get(i).getId();
                if (queryVariante.list().get(i).getCorect()) nrRaspunsuriCorecte++;
            }


            //permutare variante
            for (int i = 0; i < varianteId.length; i++) {
                int position = i + generator.nextInt(varianteId.length - i);
                long aux = varianteId[i];
                varianteId[i] = varianteId[position];
                varianteId[position] = aux;
            }


            //initializare variante
            float weight = varianteLayout.getWeightSum() / varianteId.length;
            butoaneVariante.clear();
            varianteLayout.removeAllViews();

            for (int i = 0; i < varianteId.length; i++) {
                //COSMIN - TO DO SELECT VARIANTA RASPUNS CURENTA
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
                        if (((ColorDrawable) btn.getBackground()).getColor() == ContextCompat.getColor(getApplicationContext(), R.color.bej)) {
                            if (nrRaspunsuriSelectate + 1 > nrRaspunsuriCorecte)
                                Toast.makeText(getApplicationContext(), getString(R.string.selectare_maxima_variante_raspuns, nrRaspunsuriCorecte), Toast.LENGTH_LONG).show();
                            else {
                                nrRaspunsuriSelectate++;
                                btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.portocaliu));
                            }
                        } else {
                            btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.bej));
                            nrRaspunsuriSelectate--;
                        }
                    }
                });

                varianteLayout.addView(btn);
                butoaneVariante.add(btn);
            }


            //marcare progres
            progres.setProgress(unitateProgres * raspunsuri.size());
        }
    }



    private void initializare() {

        //region Initializare test

        //selectare intrebari test
        idTest = getIntent().getLongExtra(Constante.CHEIE_TRANSFER, -1);
        if(getIntent().getStringExtra(Constante.CHEIE_TRANSFER2).equals(getString(R.string.public1)))
            estePublic=true;

        if (internetIsAvailable) {
            EvidentaIntrebariTesteService evidentaIntrebariTesteService = ServiceBuilder.buildService(EvidentaIntrebariTesteService.class);
            Call<List<EvidentaIntrebariTeste>> evidentaIntrebariTesteRequest = evidentaIntrebariTesteService.getEvidentaIntrebariTesteByTestId((int) (long) idTest);
            evidentaIntrebariTesteRequest.enqueue(new Callback<List<EvidentaIntrebariTeste>>() {
                @Override
                public void onResponse(Call<List<EvidentaIntrebariTeste>> call, Response<List<EvidentaIntrebariTeste>> response) {
                    evidentaIntrebariTeste = response.body();
                }

                @Override
                public void onFailure(Call<List<EvidentaIntrebariTeste>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Nu s-a putut gasi evidenta testelor", Toast.LENGTH_SHORT).show();
                }
            });
            unitateProgres = 100 / evidentaIntrebariTeste.size();
            intrebariId = new long[evidentaIntrebariTeste.size()];
            Random generator = new Random();
            for(int i=0;i<evidentaIntrebariTeste.size();i++) {
                intrebariId[i] = evidentaIntrebariTeste.get(i).getIntrebareId();
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
            TestService testService = ServiceBuilder.buildService(TestService.class);
            Call<Test> testRequest = testService.getTestById((int)(long)idTest);
            testRequest.enqueue(new Callback<Test>() {
                @Override
                public void onResponse(Call<Test> call, Response<Test> response) {
                    test = response.body();
                }

                @Override
                public void onFailure(Call<Test> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Testul n a putut fi gasit", Toast.LENGTH_SHORT).show();
                }
            });


            new CountDownTimer((test.getTimpDisponibil())*59*1000, 1000){
                int minute = test.getTimpDisponibil()-1;
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

        } else {

            Query<EvidentaIntrebariTeste> queryIntrebari = ((App) getApplication()).getDaoSession().getEvidentaIntrebariTesteDao().queryBuilder().where(
                    EvidentaIntrebariTesteDao.Properties.TestId.eq(idTest)).build();
            unitateProgres = 100 / queryIntrebari.list().size();
            intrebariId = new long[queryIntrebari.list().size()];
            Random generator = new Random();
            for (int i = 0; i < queryIntrebari.list().size(); i++) {
                intrebariId[i] = queryIntrebari.list().get(i).getIntrebareId();
            }

            //permutare intrebari
            for (int i = 0; i < intrebariId.length; i++) {
                int position = i + generator.nextInt(intrebariId.length - i);
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
            new CountDownTimer((queryTest.list().get(0).getTimpDisponibil()) * 59 * 1000, 1000) {
                int minute = queryTest.list().get(0).getTimpDisponibil() - 1;
                int secunde = 59;

                @Override
                public void onTick(long millisUntilFinished) {
                    if (secunde < 10)
                        timer.setText(String.valueOf(minute) + ":0" + String.valueOf(secunde));
                    else timer.setText(String.valueOf(minute) + ":" + String.valueOf(secunde));
                    secunde--;
                    if (secunde == 0) {
                        minute--;
                        secunde = 59;
                        minuteTrecute++;
                    }
                }

                @Override
                public void onFinish() {
                    int nota = (100 / puncteMaxime) * puncteObtinute;
                    Toast.makeText(getApplicationContext(), getString(R.string.nota) + " " + nota / 10, Toast.LENGTH_LONG).show();
                    finish();
                }
            }.start();
            //endregion


            sariPeste.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listaIntrebariSarite.add(indexIntrebare);

                    if (intrebariSarite == false) {
                        indexIntrebare++;
                        if (indexIntrebare == intrebariId.length) {
                            if (listaIntrebariSarite.size() > 0) {
                                intrebariSarite = true;
                                initializareIntrebare(listaIntrebariSarite.get(0));
                                indexIntrebare = listaIntrebariSarite.get(0);
                                listaIntrebariSarite.remove(0);
                            }
                        } else initializareIntrebare(indexIntrebare);
                    } else {
                        initializareIntrebare(listaIntrebariSarite.get(0));
                        indexIntrebare = listaIntrebariSarite.get(0);
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
    }

    private void verificareRaspuns(){

        //calculare punctaj
        double punctajRaspunsCorect = intrebare.getDificultate()/nrRaspunsuriCorecte;
        double punctajObtinut=0;



        if (internetIsAvailable) {

            for ( Button buton : butoaneVariante ) {
                if(((ColorDrawable)buton.getBackground()).getColor()==ContextCompat.getColor(getApplicationContext(), R.color.portocaliu)) {

                    // select varianta raspuns dupa id
                    VariantaRaspunsService variantaRaspunsService = ServiceBuilder.buildService(VariantaRaspunsService.class);
                    Call<VariantaRaspuns> variantaRaspunsRequest = variantaRaspunsService.getVariantaRaspunsById((buton.getId()));
                    variantaRaspunsRequest.enqueue(new Callback<VariantaRaspuns>() {
                        @Override
                        public void onResponse(Call<VariantaRaspuns> call, Response<VariantaRaspuns> response) {
                            variantaRaspuns2 = response.body();
                        }

                        @Override
                        public void onFailure(Call<VariantaRaspuns> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Varianta de raspuns nu a putut fi gasita", Toast.LENGTH_SHORT).show();
                        }
                    });


                    if(variantaRaspuns2.getCorect()) punctajObtinut+=punctajRaspunsCorect;
                }
            }
            if(estePublic){
                puncteMaxime+=intrebare.getDificultate();
                puncteObtinute+=punctajObtinut;
            }
            else
            {


                RaspunsIntrebareGrila raspuns = new RaspunsIntrebareGrila(intrebare.getId(), (float)punctajObtinut);
                RaspunsIntrebareGrilaService raspunsIntrebareGrilaService = ServiceBuilder.buildService(RaspunsIntrebareGrilaService.class);
                Call<RaspunsIntrebareGrila> insertRaspunsIntrebareGrila = raspunsIntrebareGrilaService.saveRaspunsIntrebareGrila(raspuns);

                raspunsuri.add(raspuns);
            }


            //feedback raspuns
            for ( Button buton : butoaneVariante ) {
                if(((ColorDrawable)buton.getBackground()).getColor()==ContextCompat.getColor(getApplicationContext(), R.color.portocaliu)) {
                    VariantaRaspunsService variantaRaspunsService = ServiceBuilder.buildService(VariantaRaspunsService.class);
                    Call<VariantaRaspuns> variantaRaspunsRequest = variantaRaspunsService.getVariantaRaspunsById(buton.getId());
                    variantaRaspunsRequest.enqueue(new Callback<VariantaRaspuns>() {
                        @Override
                        public void onResponse(Call<VariantaRaspuns> call, Response<VariantaRaspuns> response) {
                            variantaRaspuns3 = response.body();
                        }

                        @Override
                        public void onFailure(Call<VariantaRaspuns> call, Throwable t) {

                        }
                    });


                    if (!(variantaRaspuns3.getCorect()))
                        buton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.rosu));
                }

                VariantaRaspunsService variantaRaspunsService = ServiceBuilder.buildService(VariantaRaspunsService.class);
                Call<VariantaRaspuns> variantaRaspunsRequest = variantaRaspunsService.getVariantaRaspunsById(buton.getId());
                variantaRaspunsRequest.enqueue(new Callback<VariantaRaspuns>() {
                    @Override
                    public void onResponse(Call<VariantaRaspuns> call, Response<VariantaRaspuns> response) {
                        variantaRaspuns4 = response.body();
                    }

                    @Override
                    public void onFailure(Call<VariantaRaspuns> call, Throwable t) {

                    }
                });

                if(variantaRaspuns4.getCorect())  buton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.verde));
            }
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    indexIntrebare++;
                    initializareIntrebare(indexIntrebare);
                }
            }, 1000);

        } else {


        for ( Button buton : butoaneVariante ) {
            if(((ColorDrawable)buton.getBackground()).getColor()==ContextCompat.getColor(getApplicationContext(), R.color.portocaliu)) {

        // select varianta raspuns dupa id

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
    }


    private void rezultatTest() {

        Student student = ((App) getApplication()).getStudent();
        String data = new SimpleDateFormat(Constante.DATE_FORMAT).format(Calendar.getInstance().getTime());


        if (internetIsAvailable) {
            RezultatTestStudentService rezultatTestStudentService = ServiceBuilder.buildService(RezultatTestStudentService.class);
            Call<RezultatTestStudent> insertRezultatTestStudentRequest = rezultatTestStudentService.saveRezultatTestStudent(
                    new RezultatTestStudent(idTest, data, minuteTrecute, raspunsuri,
                            ((App) getApplication()).getPunctajTest(raspunsuri) > 50 ? true : false, student.getId()));
            insertRezultatTestStudentRequest.enqueue(new Callback<RezultatTestStudent>() {
                @Override
                public void onResponse(Call<RezultatTestStudent> call, Response<RezultatTestStudent> response) {
                    Toast.makeText(getApplicationContext(), "Rezultatul testului a fost salvat cu succes", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<RezultatTestStudent> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Rezultatul testului nu a putut fi salvat", Toast.LENGTH_SHORT).show();
                }
            });

            Call<RezultatTestStudent> rezultatTestStudentRequest = rezultatTestStudentService.getRezultatTestStudentByTestIdDataAndStudentId((int)(long)idTest, data, (int)(long)student.getId());
            rezultatTestStudentRequest.enqueue(new Callback<RezultatTestStudent>() {
                @Override
                public void onResponse(Call<RezultatTestStudent> call, Response<RezultatTestStudent> response) {
                    rezultatTestStudent = response.body();
                }

                @Override
                public void onFailure(Call<RezultatTestStudent> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Rezultatul testului nu a putut fi gasit", Toast.LENGTH_SHORT).show();
                }
            });

            for (RaspunsIntrebareGrila raspuns: raspunsuri ) {
                raspuns.setRezultatTestStudentId(rezultatTestStudent.getId());
                RaspunsIntrebareGrilaService raspunsIntrebareGrilaService = ServiceBuilder.buildService(RaspunsIntrebareGrilaService.class);
                Call<RaspunsIntrebareGrila> updateRaspunsIntrebareGrila = raspunsIntrebareGrilaService.updateRaspunsInrebareService((int)(long)raspuns.getId(), raspuns);
                updateRaspunsIntrebareGrila.enqueue(new Callback<RaspunsIntrebareGrila>() {
                    @Override
                    public void onResponse(Call<RaspunsIntrebareGrila> call, Response<RaspunsIntrebareGrila> response) {
                        Toast.makeText(getApplicationContext(), "Raspunsul a fost actualizat", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<RaspunsIntrebareGrila> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Raspunsul nu a putut fi actualizat", Toast.LENGTH_SHORT).show();
                    }
                });


            }
//            Intent intent = new Intent(getApplicationContext(), RezultatStudentActivity.class);
//            intent.putExtra(Constante.CHEIE_TRANSFER, rezultatTestStudent.getId());
//            startActivity(intent);
//            finish();

        }


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
