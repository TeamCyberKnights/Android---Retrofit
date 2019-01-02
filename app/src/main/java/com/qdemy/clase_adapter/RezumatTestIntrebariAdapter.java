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
import com.qdemy.RezumatTestActivity;
import com.qdemy.clase.IntrebareGrila;
import com.qdemy.clase.RaspunsIntrebareGrila;
import com.qdemy.clase.RaspunsIntrebareGrilaDao;
import com.qdemy.clase.RezultatTestStudent;
import com.qdemy.clase.RezultatTestStudentDao;
import com.qdemy.clase.Student;
import com.qdemy.clase.TestSustinut;
import com.qdemy.db.App;

import org.greenrobot.greendao.query.Query;

import java.util.List;

public class RezumatTestIntrebariAdapter extends ArrayAdapter<IntrebareGrila> {

    private Context context;
    private int resource;
    private List<IntrebareGrila> intrebari;
    private LayoutInflater inflater;
    private TestSustinut test;
    private RezumatTestActivity activity;
    private long testSustinutId;

    public RezumatTestIntrebariAdapter(@NonNull Context context, int resource,
                                       @NonNull List<IntrebareGrila> objects, LayoutInflater inflater,
                                       RezumatTestActivity activity, long testSustinutId) {
        super(context, resource, objects);

        this.context=context;
        this.resource=resource;
        this.intrebari = objects;
        this.inflater=inflater;
        this.activity=activity;
        this.testSustinutId=testSustinutId;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rand = inflater.inflate(resource, parent, false);

        TextView nume = rand.findViewById(R.id.text1_itt);
        TextView corecte = rand.findViewById(R.id.text2_itt);
        int nrCorecte=0;

        try {
            IntrebareGrila intrebare = intrebari.get(position);
            nume.setText(intrebare.getText());

            //COSMIN - TO DO SELECT REZULTAT TEST STUDENT CURENT
            Query<RezultatTestStudent> queryRezultate = ((App) activity.getApplication()).getDaoSession().getRezultatTestStudentDao().queryBuilder().where(
                    RezultatTestStudentDao.Properties.Id.eq(testSustinutId)).build();
            for (RezultatTestStudent rezultat : queryRezultate.list()) {
                //COSMIN - TO DO SELECT RASPUNS INTREBARE GRILA CURENTA
                Query<RaspunsIntrebareGrila> queryRaspuns = ((App) activity.getApplication()).getDaoSession().getRaspunsIntrebareGrilaDao().queryBuilder().where(
                        RaspunsIntrebareGrilaDao.Properties.IntrebareId.eq(intrebare.getId()),
                        RaspunsIntrebareGrilaDao.Properties.RezultatTestStudentId.eq(rezultat.getId())).build();
                if (queryRaspuns.list().get(0).getPunctajObtinut() == intrebare.getDificultate())
                    nrCorecte++;
            }
            corecte.setText(nrCorecte + "/" + queryRezultate.list().size());
        }
        catch (Exception e) {}

        return rand;
    }
}
