package com.qdemy.clase_adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.qdemy.IstoricStudentActivity;
import com.qdemy.R;
import com.qdemy.clase.RezultatTestStudent;
import com.qdemy.clase.RezultatTestStudentDao;
import com.qdemy.clase.Student;
import com.qdemy.clase.Test;
import com.qdemy.clase.TestDao;
import com.qdemy.db.App;

import org.greenrobot.greendao.query.Query;

import java.util.List;

public class StartingQuizAdapter extends ArrayAdapter<Student> {

    private Context context;
    private int resource;
    private List<Student> studenti;
    private LayoutInflater inflater;

    public StartingQuizAdapter(@NonNull Context context, int resource,
                                 @NonNull List<Student> objects, LayoutInflater inflater) {
        super(context, resource, objects);

        this.context=context;
        this.resource=resource;
        this.studenti = objects;
        this.inflater=inflater;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rand = inflater.inflate(resource, parent, false);

        TextView nume = rand.findViewById(R.id.text1_itt);
        TextView utilizator = rand.findViewById(R.id.text2_itt);

        try {
            final Student student = studenti.get(position);

            nume.setText(student.getNume() + " " + student.getPrenume());
            utilizator.setText(student.getUtilizator());
            utilizator.setTextColor(ContextCompat.getColor(rand.getContext(), R.color.bleu));
        }
        catch (Exception e) {}

        return rand;
    }
}
