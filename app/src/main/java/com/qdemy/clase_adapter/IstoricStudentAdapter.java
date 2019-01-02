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
import com.qdemy.clase.Test;
import com.qdemy.clase.TestDao;
import com.qdemy.db.App;

import org.greenrobot.greendao.query.Query;

import java.util.List;

public class IstoricStudentAdapter extends ArrayAdapter<RezultatTestStudent> {

    private Context context;
    private int resource;
    private List<RezultatTestStudent> rezultate;
    private LayoutInflater inflater;
    private IstoricStudentActivity activity;

    public IstoricStudentAdapter(@NonNull Context context, int resource,
                                 @NonNull List<RezultatTestStudent> objects, LayoutInflater inflater,
                                 IstoricStudentActivity activity) {
        super(context, resource, objects);

        this.context=context;
        this.resource=resource;
        this.rezultate = objects;
        this.inflater=inflater;
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rand = inflater.inflate(resource, parent, false);

        TextView nume = rand.findViewById(R.id.text1_ittt);
        TextView data = rand.findViewById(R.id.text3_ittt);
        TextView punctaj = rand.findViewById(R.id.text2_ittt);

        try {
            final RezultatTestStudent rezultat = rezultate.get(position);
            //COSMIN - TO DO SELECT TEST CUREMT
            Query<Test> queryTest = ((App) activity.getApplication()).getDaoSession().getTestDao().queryBuilder().where(
                    TestDao.Properties.Id.eq(rezultat.getTestId())).build();

            nume.setText(queryTest.list().get(0).getNume());
            data.setText(rezultat.getData());
            punctaj.setText(String.valueOf((int) ((App) activity.getApplication()).getPunctajTest(rezultat.getRaspunsuri())));

            if (Integer.parseInt(punctaj.getText().toString()) < 50)
                punctaj.setTextColor(ContextCompat.getColor(rand.getContext(), R.color.rosu));
            else punctaj.setTextColor(ContextCompat.getColor(rand.getContext(), R.color.verde));
        }
        catch (Exception e) {}

        return rand;
    }
}
