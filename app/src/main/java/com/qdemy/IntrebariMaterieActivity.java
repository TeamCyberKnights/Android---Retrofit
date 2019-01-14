package com.qdemy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qdemy.clase.EvidentaIntrebariTeste;
import com.qdemy.clase.EvidentaIntrebariTesteDao;
import com.qdemy.clase.EvidentaMateriiProfesori;
import com.qdemy.clase.EvidentaMateriiProfesoriDao;
import com.qdemy.clase.IntrebareGrila;
import com.qdemy.clase.IntrebareGrilaDao;
import com.qdemy.clase.Materie;
import com.qdemy.clase.MaterieDao;
import com.qdemy.clase.Profesor;
import com.qdemy.clase.VariantaRaspuns;
import com.qdemy.clase.VariantaRaspunsDao;
import com.qdemy.clase_adapter.IntrebareAdapter;
import com.qdemy.db.App;
import com.qdemy.servicii.EvidentaIntrebariTesteService;
import com.qdemy.servicii.IntrebareGrilaService;
import com.qdemy.servicii.NetworkConnectionService;
import com.qdemy.servicii.ProfesorService;
import com.qdemy.servicii.ServiceBuilder;
import com.qdemy.servicii.VariantaRaspunsService;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class IntrebariMaterieActivity extends AppCompatActivity {

    private ImageView inapoi;
    private ImageView acasa;
    private ImageView istoric;
    private ImageView teste;
    private ImageView intrebarileMele;
    private FloatingActionButton adauga;
    private ListView intrebariList;
    private TextView materieTitlu;
    private SearchView cautaIntrebare;
    private String materie;
    private List<IntrebareGrila> intrebari = new ArrayList<>();
    private IntrebareAdapter adapter;
    private List<IntrebareGrila> intrebariGrila2;
    private IntrebareGrila intrebareGrila;
    private List<VariantaRaspuns> varianteRaspuns;
    private List<EvidentaIntrebariTeste> evidentaIntrebariTeste;
    private List<IntrebareGrila> intrebariActualizate = new ArrayList<>();
    private boolean internetIsAvailable;

    private Profesor profesor;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intrebari_materie);
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

        //region Intializare componente vizuale
        inapoi = findViewById(R.id.back_image_intrebariMaterie);
        acasa = findViewById(R.id.acasa_image_intrebariMaterie);
        istoric = findViewById(R.id.istoric_image_intrebariMaterie);
        intrebarileMele = findViewById(R.id.intrebari_image_intrebariMaterie);
        teste = findViewById(R.id.teste_image_intrebariMaterie);
        adauga = findViewById(R.id.adauga_button_intrebariMaterie);
        intrebariList = findViewById(R.id.intrebari_list_intrebariMaterie);
        materieTitlu = findViewById(R.id.titlu_text_intrebariMaterie);
        cautaIntrebare = findViewById(R.id.search_intrebariMaterie);
        int id = cautaIntrebare.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchEditText = (EditText) cautaIntrebare.findViewById(id);
        searchEditText.setTextColor(Color.WHITE);
        searchEditText.setHintTextColor(getResources().getColor(R.color.gri));
        //endregion

        //region Initializare intrebari
        profesor = ((App) getApplication()).getProfesor();

        materie = getIntent().getStringExtra(Constante.CHEIE_TRANSFER);
        materieTitlu.setText(materie);

        if (internetIsAvailable) {

            IntrebareGrilaService intrebareGrilaService = ServiceBuilder.buildService(IntrebareGrilaService.class);
            Call<List<IntrebareGrila>> intrebariGrilaRequest = intrebareGrilaService.getIntrebariGrilaByProfesorId((int)(long)profesor.getId());
            intrebariGrilaRequest.enqueue(new Callback<List<IntrebareGrila>>() {
                @Override
                public void onResponse(Call<List<IntrebareGrila>> call, Response<List<IntrebareGrila>> response) {
                    intrebariGrila2 = response.body();
                }

                @Override
                public void onFailure(Call<List<IntrebareGrila>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Nu s-au putut incarca intrebarile", Toast.LENGTH_SHORT).show();
                }
            });
            for(int i=0;i<intrebariGrila2.size();i++)
                if(intrebariGrila2.get(i).getMaterie().equals(materie))

                    intrebari.add(intrebariGrila2.get(i));
            adapter = new IntrebareAdapter(getApplicationContext(),
                    R.layout.item_text_button, intrebari, getLayoutInflater(), IntrebariMaterieActivity.this);
            intrebariList.setAdapter(adapter);


        } else {

            for (int i = 0; i < profesor.getIntrebari().size(); i++)
                if (profesor.getIntrebari(i).getMaterie().equals(materie))

                    intrebari.add(profesor.getIntrebari(i));
            adapter = new IntrebareAdapter(getApplicationContext(),
                    R.layout.item_text_button, intrebari, getLayoutInflater(), IntrebariMaterieActivity.this);
            intrebariList.setAdapter(adapter);
        }
        //endregion



        adauga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdaugaIntrebareActivity.class);
                intent.putExtra(Constante.CHEIE_TRANSFER, materie);
                startActivity(intent);
                finish();
            }
        });

        cautaIntrebare.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)){
                    adapter.filter("");
                    intrebariList.clearTextFilter();
                }
                else {
                    adapter.filter(newText);
                }
                return true;
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

        acasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfesorActivity.class);
                startActivity(intent);
                finish();
            }
        });

        istoric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IstoricProfesorActivity.class);
                startActivity(intent);
                finish();
            }
        });

        teste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MateriiActivity.class);
                intent.putExtra(Constante.CHEIE_TRANSFER, getString(R.string.testele_mele));
                startActivity(intent);
                finish();
            }
        });

        intrebarileMele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MateriiActivity.class);
                intent.putExtra(Constante.CHEIE_TRANSFER, getString(R.string.ntreb_rile_mele));
                startActivity(intent);
                finish();
            }
        });

        //endregion

    }


    public void stergeIntrebare(String textIntrebare)
    {

        //selectareintrebare

        if (internetIsAvailable) {

            IntrebareGrilaService intrebareGrilaService = ServiceBuilder.buildService(IntrebareGrilaService.class);
            final Call<IntrebareGrila> intrebareGrilaRequest = intrebareGrilaService
                    .getIntrebareGrilaByTextMaterieAndProfesorId(textIntrebare, materie, (int)(long)profesor.getId());
            intrebareGrilaRequest.enqueue(new Callback<IntrebareGrila>() {
                @Override
                public void onResponse(Call<IntrebareGrila> call, Response<IntrebareGrila> response) {
                    intrebareGrila = response.body();
                }

                @Override
                public void onFailure(Call<IntrebareGrila> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Intrebarea nu a putut fi gasita", Toast.LENGTH_SHORT).show();
                }
            });

            VariantaRaspunsService variantaRaspunsService = ServiceBuilder.buildService(VariantaRaspunsService.class);
            Call<List<VariantaRaspuns>> varianteRaspunsRequest = variantaRaspunsService.getVarianteRaspunsByIntrebareId((int)(long)intrebareGrila.getId());
            varianteRaspunsRequest.enqueue(new Callback<List<VariantaRaspuns>>() {
                @Override
                public void onResponse(Call<List<VariantaRaspuns>> call, Response<List<VariantaRaspuns>> response) {
                    varianteRaspuns = response.body();
                }

                @Override
                public void onFailure(Call<List<VariantaRaspuns>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Nu au fost gasite raspunsuri pentru intrebare", Toast.LENGTH_SHORT).show();
                }
            });


            for ( VariantaRaspuns varianta : varianteRaspuns) {

                Call<VariantaRaspuns> deleteVarianteRaspunsRequest = variantaRaspunsService.deleteVariantaRaspunsById((int)(long)varianta.getId());
                deleteVarianteRaspunsRequest.enqueue(new Callback<VariantaRaspuns>() {
                    @Override
                    public void onResponse(Call<VariantaRaspuns> call, Response<VariantaRaspuns> response) {
                        Toast.makeText(getApplicationContext(), "Variantele de raspuns au fost sterse cu succes", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<VariantaRaspuns> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Variantele de raspuns nu au putut fi sterse", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            EvidentaIntrebariTesteService evidentaIntrebariTesteService = ServiceBuilder.buildService(EvidentaIntrebariTesteService.class);
            Call<List<EvidentaIntrebariTeste>> evidentaIntrebariTesteRequest = evidentaIntrebariTesteService
                    .getEvidentaIntrebariTesteByIntrebareId((int)(long)intrebareGrila.getId());
            evidentaIntrebariTesteRequest.enqueue(new Callback<List<EvidentaIntrebariTeste>>() {
                @Override
                public void onResponse(Call<List<EvidentaIntrebariTeste>> call, Response<List<EvidentaIntrebariTeste>> response) {
                    evidentaIntrebariTeste = response.body();
                }

                @Override
                public void onFailure(Call<List<EvidentaIntrebariTeste>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Nu au putut fi gasite intrebari in evidenta", Toast.LENGTH_SHORT).show();
                }
            });
            for ( EvidentaIntrebariTeste evidenta : evidentaIntrebariTeste) {

            Call<EvidentaIntrebariTeste> deleteEvidentaIntrebariTeste = evidentaIntrebariTesteService.
                    deleteEvidentaIntrebariTesteById((int)(long)evidenta.getId());
            deleteEvidentaIntrebariTeste.enqueue(new Callback<EvidentaIntrebariTeste>() {
                @Override
                public void onResponse(Call<EvidentaIntrebariTeste> call, Response<EvidentaIntrebariTeste> response) {
                    Toast.makeText(getApplicationContext(), "Evidenta a fost stearsa cu succes", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<EvidentaIntrebariTeste> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Evidenta nu a putut fi stearsa", Toast.LENGTH_SHORT).show();
                }
            });
            }



            Call<List<IntrebareGrila>> intrebariGrilaRequest = intrebareGrilaService.getIntrebariGrilaByProfesorId((int)(long)profesor.getId());
            intrebariGrilaRequest.enqueue(new Callback<List<IntrebareGrila>>() {
                @Override
                public void onResponse(Call<List<IntrebareGrila>> call, Response<List<IntrebareGrila>> response) {
                    intrebariActualizate = response.body();
                }

                @Override
                public void onFailure(Call<List<IntrebareGrila>> call, Throwable t) {

                }
            });


            intrebariActualizate.remove(intrebareGrila);
            profesor.setIntrebari(intrebariActualizate);


            ProfesorService profesorService = ServiceBuilder.buildService(ProfesorService.class);
            Call<Profesor> updateProfesor = profesorService.updateProfesor((int) (long)profesor.getId(), profesor);
            updateProfesor.enqueue(new Callback<Profesor>() {
                @Override
                public void onResponse(Call<Profesor> call, Response<Profesor> response) {
                    Toast.makeText(getApplicationContext(), "Datele au fost modificate cu succes", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Profesor> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Datele nu au putut fi modificate", Toast.LENGTH_SHORT).show();
                }
            });

            Call<IntrebareGrila> deleteIntrebareGrila = intrebareGrilaService.deleteIntrebareGrilaById((int)(long)intrebareGrila.getId());
            deleteIntrebareGrila.enqueue(new Callback<IntrebareGrila>() {
                @Override
                public void onResponse(Call<IntrebareGrila> call, Response<IntrebareGrila> response) {
                    Toast.makeText(getApplicationContext(), "Intrebarea a fost stearsa cu succces", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<IntrebareGrila> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Intrebarea nu a putut fi stearsa", Toast.LENGTH_SHORT).show();
                }
            });

        }

        Query<IntrebareGrila> queryIntrebare = ((App) getApplication()).getDaoSession().getIntrebareGrilaDao().queryBuilder().where(
                IntrebareGrilaDao.Properties.Text.eq(textIntrebare),
                IntrebareGrilaDao.Properties.Materie.eq(materie),
                IntrebareGrilaDao.Properties.ProfesorId.eq(profesor.getId())).build();

        Query<VariantaRaspuns> queryVarianteRaspuns = ((App) getApplication()).getDaoSession().getVariantaRaspunsDao()
                .queryBuilder().where(VariantaRaspunsDao.Properties.IntrebareId.eq(queryIntrebare.list().get(0).getId())).build();
        for ( VariantaRaspuns varianta : queryVarianteRaspuns.list()) {
            ((App) getApplication()).getDaoSession().getVariantaRaspunsDao().deleteByKey(varianta.getId());
        }


        Query<EvidentaIntrebariTeste> queryEvidenta = ((App) getApplication()).getDaoSession().getEvidentaIntrebariTesteDao()
                .queryBuilder().where(EvidentaIntrebariTesteDao.Properties.IntrebareId.eq(queryIntrebare.list().get(0).getId())).build();

        for ( EvidentaIntrebariTeste evidenta : queryEvidenta.list()) {
            ((App) getApplication()).getDaoSession().getEvidentaIntrebariTesteDao().deleteByKey(evidenta.getId()); }


        List<IntrebareGrila> intrebariActualizate = profesor.getIntrebari();
        intrebariActualizate.remove(queryIntrebare.list().get(0));
        profesor.setIntrebari(intrebariActualizate);
        ((App) getApplication()).getDaoSession().getProfesorDao().update(profesor);

        ((App) getApplication()).getDaoSession().getIntrebareGrilaDao().deleteByKey(queryIntrebare.list().get(0).getId());
    }

}
