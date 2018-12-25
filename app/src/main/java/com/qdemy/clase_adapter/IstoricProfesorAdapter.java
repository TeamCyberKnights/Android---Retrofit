package com.qdemy.clase_adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.qdemy.R;
import com.qdemy.clase.TestSustinut;

import java.util.List;

public class IstoricProfesorAdapter extends ArrayAdapter<TestSustinut> {

    private Context context;
    private int resource;
    private List<TestSustinut> teste;
    private LayoutInflater inflater;

    public IstoricProfesorAdapter(@NonNull Context context, int resource,
                            @NonNull List<TestSustinut> objects, LayoutInflater inflater) {
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

        TextView nume = rand.findViewById(R.id.text1_ittt);
        TextView data = rand.findViewById(R.id.text2_ittt);
        TextView media = rand.findViewById(R.id.text3_ittt);

        final TestSustinut test = teste.get(position);

        //nume.setText(test.getNume());
        data.setText(test.getData());
        media.setText(Float.toString(test.getMedia()));



        return rand;
    }
}
