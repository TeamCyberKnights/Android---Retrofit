package com.qdemy.clase_adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.qdemy.Constante;
import com.qdemy.EditeazaTestActivity;
import com.qdemy.GrilaQuizActivity;
import com.qdemy.R;
import com.qdemy.TesteActivity;
import com.qdemy.TestePubliceActivity;
import com.qdemy.clase.Materie;
import com.qdemy.clase.MaterieDao;
import com.qdemy.clase.Profesor;
import com.qdemy.clase.ProfesorDao;
import com.qdemy.clase.Test;
import com.qdemy.clase.TestDao;
import com.qdemy.clase.TestPartajat;
import com.qdemy.clase.TestPartajatDao;
import com.qdemy.db.App;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

public class TestePubliceAdapter extends ArrayAdapter<Test> {

    private Context context;
    private int resource;
    private List<Test> teste;
    private ArrayList<Test> testeOriginal;
    private LayoutInflater inflater;
    private TestePubliceActivity activity;

    public TestePubliceAdapter(@NonNull Context context, int resource,
                       @NonNull List<Test> objects, LayoutInflater inflater,
                       TestePubliceActivity activity) {
        super(context, resource, objects);

        this.context=context;
        this.resource=resource;
        this.teste = objects;
        this.testeOriginal = new ArrayList<Test>();
        this.testeOriginal.addAll(teste);

        this.inflater = inflater;
        this.activity = activity;
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
                try {
                    Query<Profesor> queryProfesor = ((App) activity.getApplication()).getDaoSession().getProfesorDao().queryBuilder().where(
                            ProfesorDao.Properties.Id.eq(test.getProfesorId())).build();
                    if (test.getNume().toLowerCase().contains(charText) ||
                            test.getDescriere().toLowerCase().contains(charText) ||
                            test.getMaterie().toLowerCase().contains(charText) ||
                            queryProfesor.list().get(0).getNume().toLowerCase().contains(charText) ||
                            queryProfesor.list().get(0).getPrenume().toLowerCase().contains(charText)) {
                        teste.add(test);
                    }
                }
                catch(Exception e) {}
            }
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rand = inflater.inflate(resource, parent, false);

        TextView nume = rand.findViewById(R.id.nume_text_itestPublic);
        TextView descriere = rand.findViewById(R.id.decriere_text_itestPublic);
        TextView autor = rand.findViewById(R.id.autor_text_itestPublic);
        TextView materie = rand.findViewById(R.id.materie_text_itestPublic);
        ImageView logo = rand.findViewById(R.id.logo_image_itestPublic);

        try {
            nume.setText(teste.get(position).getNume());
            descriere.setText(teste.get(position).getDescriere());
            materie.setText(teste.get(position).getMaterie());

            //COSMIN - TO DO SELECT PROFESOR AL TESTULUI PUBLIC
            Query<Profesor> queryProfesor = ((App) activity.getApplication()).getDaoSession().getProfesorDao().queryBuilder().where(
                    ProfesorDao.Properties.Id.eq(teste.get(position).getProfesorId())).build();
            autor.setText(activity.getString(R.string.autor_test_public, queryProfesor.list().get(0).getNume(), queryProfesor.list().get(0).getPrenume()));


            if (teste.get(position).getMaterie().equals(activity.getString(R.string.poo)))
                logo.setImageResource(R.drawable.poo);
            else if (teste.get(position).getMaterie().equals(activity.getString(R.string.paw)))
                logo.setImageResource(R.drawable.paw);
            else if (teste.get(position).getMaterie().equals(activity.getString(R.string.sdd)))
                logo.setImageResource(R.drawable.sdd);
            else if (teste.get(position).getMaterie().equals(activity.getString(R.string.dam)))
                logo.setImageResource(R.drawable.dam);
            else if (teste.get(position).getMaterie().equals(activity.getString(R.string.tw)))
                logo.setImageResource(R.drawable.web);
            else if (teste.get(position).getMaterie().equals(activity.getString(R.string.java)))
                logo.setImageResource(R.drawable.java);


            rand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), GrilaQuizActivity.class);
                    intent.putExtra(Constante.CHEIE_TRANSFER, teste.get(position).getId());
                    intent.putExtra(Constante.CHEIE_TRANSFER2, activity.getString(R.string.public1));
                    activity.startActivity(intent);
                }
            });
        }
        catch (Exception e) {}

        return rand;
    }
}
