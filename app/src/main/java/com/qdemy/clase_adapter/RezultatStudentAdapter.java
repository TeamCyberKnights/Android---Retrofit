package com.qdemy.clase_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.qdemy.R;
import com.qdemy.clase.RaspunsIntrebareGrila;
import com.qdemy.clase.RezultatTestStudent;

import java.util.List;

public class RezultatStudentAdapter extends ArrayAdapter<RaspunsIntrebareGrila> {

    private Context context;
    private int resource;
    private List<RaspunsIntrebareGrila> intrebari;
    private LayoutInflater inflater;

    public RezultatStudentAdapter(@NonNull Context context, int resource,
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

        TextView nume = rand.findViewById(R.id.text_itb);
        Button corect = rand.findViewById(R.id.button_itb);

        final RaspunsIntrebareGrila intrebare = intrebari.get(position);

        //nume.setText(intrebare.getNume());
        //in lucru
//        corect.setBackgroundResource(intrebare.getPunctajObtinut()==0 ? R.drawable.ic_picat :
//                                     intrebare.getPunctajObtinut()==intrebare.getPunctaj() ? R.drawable.ic_promovat :
//                                     R.drawable.ic_mediu );


        return rand;
    }
}
