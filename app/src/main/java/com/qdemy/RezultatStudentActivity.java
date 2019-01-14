package com.qdemy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.qdemy.clase.RaspunsIntrebareGrila;
import com.qdemy.clase.RezultatTestStudent;
import com.qdemy.clase.TestSustinut;
import com.qdemy.clase.TestSustinutDao;
import com.qdemy.clase_adapter.MaterieAdapter;
import com.qdemy.clase_adapter.RezultatStudentAdapter;
import com.qdemy.db.App;
import com.qdemy.servicii.NetworkConnectionService;
import com.qdemy.servicii.RaspunsIntrebareGrilaService;
import com.qdemy.servicii.ServiceBuilder;

import org.greenrobot.greendao.query.Query;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RezultatStudentActivity extends AppCompatActivity {

    private TextView scor;
    private Button salveaza;
    private RezultatTestStudent rezultat;
    private List<RaspunsIntrebareGrila> raspunsuri;
    private ListView intrebariList;
    private boolean internetIsAvailable;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezultat_student);
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

        scor = findViewById(R.id.scor_text_rezultatStudent);
        salveaza = findViewById(R.id.salveaza_button_rezultatStudent);
        intrebariList = findViewById(R.id.intrebari_list_rezultatStudent);

        rezultat = ((App) getApplication()).getRezultatTest(getIntent().getLongExtra(Constante.CHEIE_TRANSFER, -1));
        if (internetIsAvailable) {
            RaspunsIntrebareGrilaService raspunsIntrebareGrilaService = ServiceBuilder.buildService(RaspunsIntrebareGrilaService.class);
            Call<List<RaspunsIntrebareGrila>> raspunsuriInrebareGrila = raspunsIntrebareGrilaService.getRaspunsIntrebareGrilaByRezultatTestStudentId((int) (long) rezultat.getId());
            raspunsuriInrebareGrila.enqueue(new Callback<List<RaspunsIntrebareGrila>>() {
                @Override
                public void onResponse(Call<List<RaspunsIntrebareGrila>> call, Response<List<RaspunsIntrebareGrila>> response) {
                    raspunsuri = response.body();
                }

                @Override
                public void onFailure(Call<List<RaspunsIntrebareGrila>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Nu s-au putut gasi raspnsuri", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else raspunsuri = rezultat.getRaspunsuri();


        scor.setText(String.valueOf((int)((App) getApplication()).getPunctajTest(raspunsuri)));
        scor.setBackgroundColor(rezultat.getPromovat()?ContextCompat.getColor(getApplicationContext(), R.color.verde):
                ContextCompat.getColor(getApplicationContext(), R.color.rosu));

        RezultatStudentAdapter adapter = new RezultatStudentAdapter(getApplicationContext(),
                R.layout.item_text_button, raspunsuri, getLayoutInflater(), RezultatStudentActivity.this);
        intrebariList.setAdapter(adapter);

        // VEZI AICI LA FEL CA IN ScorLiveActivity DOAR AICI !!!
        //COSMIN - TO DO ADAUGARE ACEST REZULTAT LA LISTA REZULTATELOR DIN TEST SUSTINUT + UPDATE TEST SUSTINUT
        /*//ACTUALIZARE TEST SUSTINUT CU REZULTATUL ACESTUI STUDENT
        long id=-1; //= preluat de pe server
        Query<TestSustinut> queryTestSustinut = ((App) getApplication()).getDaoSession().getTestSustinutDao().queryBuilder().where(
                TestSustinutDao.Properties.Id.eq(id)).build();
        TestSustinut testSustinut = queryTestSustinut.list().get(0);
        List<RezultatTestStudent> rezultate = testSustinut.getRezultate();
        rezultate.add(rezultat);
        testSustinut.setRezultate(rezultate);
        ((App) getApplication()).getDaoSession().getTestSustinutDao().update(testSustinut);*/

        salveaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
