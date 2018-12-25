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
import com.qdemy.clase.RaspunsIntrebareGrila;
import com.qdemy.clase.RezultatTestStudent;

import java.util.List;

public class RezumatStudentAdapter extends ArrayAdapter<RaspunsIntrebareGrila> {

    private Context context;
    private int resource;
    private List<RaspunsIntrebareGrila> intrebari;
    private LayoutInflater inflater;

    public RezumatStudentAdapter(@NonNull Context context, int resource,
                                 @NonNull List<RaspunsIntrebareGrila> objects, LayoutInflater inflater) {
        super(context, resource, objects);

        this.context=context;
        this.resource=resource;
        this.intrebari = objects;
        this.inflater=inflater;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rand = inflater.inflate(resource, parent, false);

        TextView nume = rand.findViewById(R.id.text1_ittt);
        TextView timp = rand.findViewById(R.id.text2_ittt);
        TextView punctaj = rand.findViewById(R.id.text3_ittt);

        final RaspunsIntrebareGrila intrebare = intrebari.get(position);

        //nume.setText(intrebare.getText());
        timp.setText(intrebare.getSecunde());
        punctaj.setText(Float.toString(intrebare.getPunctajObtinut()));

        return rand;
    }
}
