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
import com.qdemy.clase.IntrebareGrila;
import com.qdemy.clase.Test;

import java.util.List;

public class TestAdapter extends ArrayAdapter<Test> {

    private Context context;
    private int resource;
    private List<Test> teste;
    private LayoutInflater inflater;

    public TestAdapter(@NonNull Context context, int resource,
                            @NonNull List<Test> objects, LayoutInflater inflater) {
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

        TextView nume = rand.findViewById(R.id.text_itb);
        Button sterge = rand.findViewById(R.id.button_itb);
        final Test test = teste.get(position);

        nume.setText(test.getNume());

        sterge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(v.getContext());
                dlgAlert.setMessage(R.string.stergere_test_message);
                //dlgAlert.setTitle("Ștergere test");
                dlgAlert.setPositiveButton(R.string.da, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        teste.remove(position);
                        //eliminare din baza de date
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
