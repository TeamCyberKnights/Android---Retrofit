package com.qdemy.clase_adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.qdemy.Constante;
import com.qdemy.EditeazaTestActivity;
import com.qdemy.R;
import com.qdemy.TesteActivity;
import com.qdemy.clase.Test;
import com.qdemy.clase.TestPartajat;
import com.qdemy.clase.TestPartajatDao;
import com.qdemy.db.App;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

public class TestAdapter extends ArrayAdapter<Test> {

    private Context context;
    private int resource;
    private List<Test> teste;
    private ArrayList<Test> testeOriginal;
    private LayoutInflater inflater;
    private Boolean removable;
    private TesteActivity activity;
    private long profesorId;
    private boolean estePartajat = true;

    public TestAdapter(@NonNull Context context, int resource,
                       @NonNull List<Test> objects, LayoutInflater inflater, Boolean removable,
                       TesteActivity activity, long profesorId) {
        super(context, resource, objects);

        this.context=context;
        this.resource=resource;
        this.teste = objects;
        this.testeOriginal = new ArrayList<Test>();
        this.testeOriginal.addAll(teste);

        this.inflater = inflater;
        this.removable = removable;
        this.activity = activity;
        this.profesorId = profesorId;
    }


    //filter
    public void filter(String charText){
        charText = charText.toLowerCase();
        teste.clear();
        if (charText.equals("")){
            teste.addAll(testeOriginal);
        }
        else {
            for (Test test : testeOriginal){
                if (test.getNume().toLowerCase().contains(charText)){
                    teste.add(test);
                }
            }
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rand = inflater.inflate(resource, parent, false);

        TextView nume = rand.findViewById(R.id.text_itbb);
        Button partajat = rand.findViewById(R.id.button1_itbb);
        Button sterge = rand.findViewById(R.id.button2_itbb);

        try {
            nume.setText(teste.get(position).getNume());

            //verificare test partajat
            Query<TestPartajat> queryTestPartajat = ((App) activity.getApplication()).getDaoSession().getTestPartajatDao().queryBuilder().where(
                    TestPartajatDao.Properties.TestId.eq(teste.get(position).getId()),
                    TestPartajatDao.Properties.ProfesorId.eq(profesorId)).build();
            if (queryTestPartajat.list().size() < 1) {
                partajat.setVisibility(View.INVISIBLE);
                estePartajat = false;
            } else sterge.setVisibility(View.INVISIBLE);


            nume.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!estePartajat) {
                        Intent intent = new Intent(v.getContext(), EditeazaTestActivity.class);
                        intent.putExtra(Constante.CHEIE_TRANSFER, teste.get(position).getId());
                        v.getContext().startActivity(intent);
                    }
                }
            });


            sterge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(v.getContext());
                    dlgAlert.setMessage(R.string.stergere_test_message);
                    //dlgAlert.setTitle("Ștergere test");
                    dlgAlert.setPositiveButton(R.string.da, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            activity.stergeTest(teste.get(position).getNume());
                            teste.remove(position);
                            notifyDataSetChanged();
                        }
                    });
                    dlgAlert.setNegativeButton(R.string.nu, null);
                    AlertDialog dialog = dlgAlert.create();
                    dialog.show();

                }
            });
        }
        catch (Exception e) {}



        return rand;
    }
}
