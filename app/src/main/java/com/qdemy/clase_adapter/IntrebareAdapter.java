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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.qdemy.Constante;
import com.qdemy.EditeazaIntrebareActivity;
import com.qdemy.IntrebariMaterieActivity;
import com.qdemy.R;
import com.qdemy.clase.IntrebareGrila;

import java.util.ArrayList;
import java.util.List;

public class IntrebareAdapter extends ArrayAdapter<IntrebareGrila> {

    private Context context;
    private int resource;
    private List<IntrebareGrila> intrebari;
    private ArrayList<IntrebareGrila> intrebariOriginal;
    private LayoutInflater inflater;
    private IntrebariMaterieActivity activity;

    public IntrebareAdapter(@NonNull Context context, int resource,
                            @NonNull List<IntrebareGrila> objects, LayoutInflater inflater,
                            IntrebariMaterieActivity activity) {
        super(context, resource, objects);

        this.context=context;
        this.resource=resource;
        this.intrebari = objects;
        this.intrebariOriginal = new ArrayList<IntrebareGrila>();
        this.intrebariOriginal.addAll(intrebari);

        this.inflater=inflater;
        this.activity=activity;
    }



    //filter
    public void filter(String charText){
        charText = charText.toLowerCase();
        intrebari.clear();
        if (charText.equals("")){
            intrebari.addAll(intrebariOriginal);
        }
        else {
            for (IntrebareGrila intrebare : intrebariOriginal){
                if (intrebare.getText().toLowerCase().contains(charText)){
                    intrebari.add(intrebare);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rand = inflater.inflate(resource, parent, false);

        TextView nume = rand.findViewById(R.id.text_itb);
        Button sterge = rand.findViewById(R.id.button_itb);

        try {
            nume.setText(intrebari.get(position).getText());

            nume.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), EditeazaIntrebareActivity.class);
                    intent.putExtra(Constante.CHEIE_TRANSFER, intrebari.get(position).getId());
                    v.getContext().startActivity(intent);
                }
            });

            sterge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(v.getContext());
                    dlgAlert.setMessage(R.string.stergere_intrebare_message);
                    //dlgAlert.setTitle("Ștergere întrebare");
                    dlgAlert.setPositiveButton(R.string.da, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            activity.stergeIntrebare(intrebari.get(position).getText());
                            intrebari.remove(position);
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
