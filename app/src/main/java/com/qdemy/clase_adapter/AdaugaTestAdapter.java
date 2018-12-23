package com.qdemy.clase_adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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

import com.qdemy.AdaugaTestActivity;
import com.qdemy.Constante;
import com.qdemy.EditeazaTestActivity;
import com.qdemy.IntrebareActivity;
import com.qdemy.R;
import com.qdemy.clase.IntrebareGrila;

import java.util.List;

public class IntrebareTestAdapter extends ArrayAdapter<IntrebareGrila> {

    private Context context;
    private int resource;
    //private List<IntrebareGrila> intrebari;
    private LayoutInflater inflater;
    private boolean adaugaTest;
    private Activity activity;

    public IntrebareTestAdapter(@NonNull Context context, int resource,
                            @NonNull List<IntrebareGrila> objects, LayoutInflater inflater,
                                boolean adaugaTest) {
        super(context, resource, objects);

        this.context=context;
        this.resource=resource;
        //this.intrebari = objects;
        this.inflater=inflater;
        this.adaugaTest = adaugaTest;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {

        View rand = inflater.inflate(resource, parent, false);

        final TextView nume = rand.findViewById(R.id.text_itb);
        final Button selectat = rand.findViewById(R.id.button_itb);
        selectat.setBackgroundResource(R.drawable.ic_promovat);


        final IntrebareGrila intrebare;
        if(adaugaTest) intrebare = ((AdaugaTestActivity) getContext()).intrebari.get(position);
        else intrebare = ((EditeazaTestActivity) getContext()).intrebari.get(position);
        nume.setText(intrebare.getText());

        if(adaugaTest){
        if(((AdaugaTestActivity) getContext()).selectari.get(position)==false) {
            selectat.setVisibility(View.INVISIBLE);
        }
        else rand.setBackgroundColor(Color.LTGRAY);}
        else{
            if(((EditeazaTestActivity) getContext()).selectari.get(position)==false) {
                selectat.setVisibility(View.INVISIBLE);
            }
            else rand.setBackgroundColor(Color.LTGRAY);
        }


        nume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adaugaTest) {
                    if (((AdaugaTestActivity) getContext()).selectari.get(position) == false) {
                        ((AdaugaTestActivity) getContext()).selectari.set(position, true);
                        ((AdaugaTestActivity) getContext()).intrebariSelectate.add(((AdaugaTestActivity) getContext()).intrebari.get(position));
                    } else {
                        ((AdaugaTestActivity) getContext()).selectari.set(position, false);
                        if (((AdaugaTestActivity) getContext()).intrebariSelectate.contains(((AdaugaTestActivity) getContext()).intrebari.get(position)))
                            ((AdaugaTestActivity) getContext()).intrebariSelectate.remove(((AdaugaTestActivity) getContext()).intrebari.get(position));
                    }
                }
                else
                {
                    if (((EditeazaTestActivity) getContext()).selectari.get(position) == false) {
                        ((EditeazaTestActivity) getContext()).selectari.set(position, true);
                        ((EditeazaTestActivity) getContext()).intrebariSelectate.add(((EditeazaTestActivity) getContext()).intrebari.get(position));
                    } else {
                        ((EditeazaTestActivity) getContext()).selectari.set(position, false);
                        if (((EditeazaTestActivity) getContext()).intrebariSelectate.contains(((EditeazaTestActivity) getContext()).intrebari.get(position)))
                            ((EditeazaTestActivity) getContext()).intrebariSelectate.remove(((EditeazaTestActivity) getContext()).intrebari.get(position));
                    }
                }

                notifyDataSetChanged();

            }
        });


        return rand;
    }
}
