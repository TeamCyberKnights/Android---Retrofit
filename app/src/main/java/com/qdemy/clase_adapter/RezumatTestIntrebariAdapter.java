package com.qdemy.clase_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.qdemy.R;
import com.qdemy.clase.IntrebareGrila;
import com.qdemy.clase.Student;
import com.qdemy.clase.TestSustinut;

import java.util.List;

public class RezumatTestIntrebariAdapter extends ArrayAdapter<IntrebareGrila> {

    private Context context;
    private int resource;
    private List<IntrebareGrila> intrebari;
    private LayoutInflater inflater;
    private TestSustinut test;

    public RezumatTestIntrebariAdapter(@NonNull Context context, int resource,
                                       @NonNull List<IntrebareGrila> objects, LayoutInflater inflater,
                                       TestSustinut test) {
        super(context, resource, objects);

        this.context=context;
        this.resource=resource;
        this.intrebari = objects;
        this.inflater=inflater;
        this.test = test;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rand = inflater.inflate(resource, parent, false);

        TextView nume = rand.findViewById(R.id.text1_itt);
        TextView corecte = rand.findViewById(R.id.text2_itt);

        final IntrebareGrila intrebare = intrebari.get(position);

        nume.setText(intrebare.getText());
        //corecte.setText(test.getRaspunsuriCorecte(intrebare.getNume()));



        return rand;
    }
}
