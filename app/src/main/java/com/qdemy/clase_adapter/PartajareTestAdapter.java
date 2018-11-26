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
import com.qdemy.clase.Profesor;

import java.util.List;

public class PartajareTestAdapter extends ArrayAdapter<Profesor> {

    private Context context;
    private int resource;
    private List<Profesor> profesori;
    private LayoutInflater inflater;

    public PartajareTestAdapter(@NonNull Context context, int resource,
                                @NonNull List<Profesor> objects, LayoutInflater inflater) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.profesori = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rand = inflater.inflate(resource, parent, false);

        TextView nume = rand.findViewById(R.id.text_itb);
        Button sterge = rand.findViewById(R.id.button_itb);

        nume.setText(profesori.get(position).getNume());
        sterge.setBackgroundResource(R.drawable.ic_close_black_24dp);

        sterge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(v.getContext());
                dlgAlert.setMessage(context.getString(R.string.stergere_partajare_message) + profesori.get(position).getNume() + " ?");
                //dlgAlert.setTitle("Ștergere profesor");
                dlgAlert.setPositiveButton(R.string.da, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //editare drepturi acces profesor
                        profesori.remove(position);
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