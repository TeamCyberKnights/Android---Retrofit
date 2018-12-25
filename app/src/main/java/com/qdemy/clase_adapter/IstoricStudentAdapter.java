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
import com.qdemy.clase.RezultatTestStudent;

import java.util.List;

public class IstoricStudentAdapter extends ArrayAdapter<RezultatTestStudent> {

    private Context context;
    private int resource;
    private List<RezultatTestStudent> teste;
    private LayoutInflater inflater;

    public IstoricStudentAdapter(@NonNull Context context, int resource,
                                  @NonNull List<RezultatTestStudent> objects, LayoutInflater inflater) {
        super(context, resource, objects);

        this.context=context;
        this.resource=resource;
        this.teste = objects;
        this.inflater=inflater;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rand = inflater.inflate(resource, parent, false);

        TextView nume = rand.findViewById(R.id.text1_ittb);
        TextView punctaj = rand.findViewById(R.id.text2_ittb);
        Button promovat = rand.findViewById(R.id.button_ittb);

        final RezultatTestStudent test = teste.get(position);

        //nume.setText(test.getNume());
        //punctaj.setText(Float.toString(test.getPunctajObtinut()));
        promovat.setBackgroundResource(test.isPromovat() ? R.drawable.ic_promovat : R.drawable.ic_picat );


        return rand;
    }
}
