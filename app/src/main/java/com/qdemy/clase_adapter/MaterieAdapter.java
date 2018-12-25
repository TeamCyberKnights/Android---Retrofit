package com.qdemy.clase_adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.qdemy.Constante;
import com.qdemy.IntrebariMaterieActivity;
import com.qdemy.MateriiActivity;
import com.qdemy.R;
import com.qdemy.TesteActivity;
import com.qdemy.clase.DaoMaster;
import com.qdemy.clase.DaoSession;
import com.qdemy.clase.IntrebareGrila;
import com.qdemy.clase.Profesor;
import com.qdemy.db.App;
import com.qdemy.db.DbOpenHelper;

import java.util.List;

import static android.provider.Settings.System.getString;

public class MaterieAdapter extends ArrayAdapter<String> {

    private Context context;
    private int resource;
    private List<String> materii;
    private LayoutInflater inflater;
    private MateriiActivity activity;
    private String continut;

    public MaterieAdapter(@NonNull Context context, int resource,
                            @NonNull List<String> objects, LayoutInflater inflater,
                            MateriiActivity activity, String continut) {
        super(context, resource, objects);

        this.context=context;
        this.resource=resource;
        this.materii = objects;
        this.inflater=inflater;
        this.activity=activity;
        this.continut = continut;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rand = inflater.inflate(resource, parent, false);

        TextView nume = rand.findViewById(R.id.text_itb);
        Button sterge = rand.findViewById(R.id.button_itb);
        sterge.setBackgroundResource(R.drawable.ic_close_black_24dp);
        if(materii!=null) nume.setText(materii.get(position));


        nume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(continut.equals(v.getContext().getResources().getString(R.string.testele_mele)))
                    intent = new Intent(v.getContext(), TesteActivity.class);
                else
                    intent = new Intent(v.getContext(), IntrebariMaterieActivity.class);

                intent.putExtra(Constante.CHEIE_TRANSFER, materii.get(position));
                v.getContext().startActivity(intent);

            }
        });

        sterge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(v.getContext());
                    dlgAlert.setMessage(R.string.stergere_materie_message);
                    //dlgAlert.setTitle("Ștergere materie");
                    dlgAlert.setPositiveButton(R.string.da, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            activity.stergeMaterieProfesor(materii.get(position));
                            materii.remove(position);
                            activity.actualizeazaNomenclator();
                            notifyDataSetChanged();
                        }
                    });
                    dlgAlert.setNegativeButton(R.string.nu, null);
                    AlertDialog dialog = dlgAlert.create();
                    dialog.show();
                }

        });

        return rand;
    }
}
