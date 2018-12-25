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
import com.qdemy.clase.TestSustinut;
import com.qdemy.clase.Student;

import java.util.List;

public class RezultatTestAdapter extends ArrayAdapter<Student> {

    private Context context;
    private int resource;
    private List<Student> studenti;
    private LayoutInflater inflater;
    private String numeTest;
    private String data;
    private TestSustinut test;

    public RezultatTestAdapter(@NonNull Context context, int resource,
                               @NonNull List<Student> objects, LayoutInflater inflater,
                               TestSustinut test) {
        super(context, resource, objects);

        this.context=context;
        this.resource=resource;
        this.studenti = objects;
        this.inflater=inflater;
        this.test=test;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rand = inflater.inflate(resource, parent, false);

        TextView nume = rand.findViewById(R.id.text1_itt);
        TextView punctaj = rand.findViewById(R.id.text2_itt);

        final Student student = studenti.get(position);

        nume.setText(student.getNume());
        //punctaj.setText(Float.toString(student.getTesteSustinute(test.getNume(), test.getData()).getPunctajObtinut()));



        return rand;
    }
}
