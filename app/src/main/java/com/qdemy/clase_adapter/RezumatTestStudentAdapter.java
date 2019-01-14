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
import android.widget.TextView;

import com.qdemy.R;
import com.qdemy.RezumatStudentActivity;
import com.qdemy.RezumatTestStudentActivity;
import com.qdemy.clase.IntrebareGrila;
import com.qdemy.clase.IntrebareGrilaDao;
import com.qdemy.clase.RaspunsIntrebareGrila;
import com.qdemy.clase.RezultatTestStudent;
import com.qdemy.db.App;
import com.qdemy.servicii.IntrebareGrilaService;
import com.qdemy.servicii.NetworkConnectionService;
import com.qdemy.servicii.ServiceBuilder;

import org.greenrobot.greendao.query.Query;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RezumatTestStudentAdapter extends ArrayAdapter<RaspunsIntrebareGrila> {

    private Context context;
    private int resource;
    private List<RaspunsIntrebareGrila> raspunsuri;
    private LayoutInflater inflater;
    private RezumatTestStudentActivity activity;
    private IntrebareGrila intrebareGrila;
    private NetworkConnectionService networkConnectionService = new NetworkConnectionService();

    public RezumatTestStudentAdapter(@NonNull Context context, int resource,
                                 @NonNull List<RaspunsIntrebareGrila> objects, LayoutInflater inflater,
                                     RezumatTestStudentActivity activity) {
        super(context, resource, objects);

        this.context=context;
        this.resource=resource;
        this.raspunsuri = objects;
        this.inflater=inflater;
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rand = inflater.inflate(resource, parent, false);

        TextView nume = rand.findViewById(R.id.text1_itt);
        TextView punctaj = rand.findViewById(R.id.text2_itt);

        try {
            final RaspunsIntrebareGrila raspuns = raspunsuri.get(position);

            if (networkConnectionService.isInternetAvailable()) {
                IntrebareGrilaService intrebareGrilaService = ServiceBuilder.buildService(IntrebareGrilaService.class);
                final Call<IntrebareGrila> intrebareGrilaRequest = intrebareGrilaService.getIntrebareGrilaById((int)(long)raspuns.getIntrebareId());
                intrebareGrilaRequest.enqueue(new Callback<IntrebareGrila>() {
                    @Override
                    public void onResponse(Call<IntrebareGrila> call, Response<IntrebareGrila> response) {
                        intrebareGrila = response.body();
                    }

                    @Override
                    public void onFailure(Call<IntrebareGrila> call, Throwable t) {

                    }
                });
                nume.setText(intrebareGrila.getText());
                punctaj.setText(String.valueOf(raspuns.getPunctajObtinut()));
                punctaj.setTextColor(raspuns.getPunctajObtinut() == 0 ? ContextCompat.getColor(rand.getContext(), R.color.rosu) :
                        raspuns.getPunctajObtinut() == intrebareGrila.getDificultate() ? ContextCompat.getColor(rand.getContext(), R.color.verde) :
                                ContextCompat.getColor(rand.getContext(), R.color.galben));
            } else {
                Query<IntrebareGrila> queryIntrebare = ((App) activity.getApplication()).getDaoSession().getIntrebareGrilaDao().queryBuilder().where(
                        IntrebareGrilaDao.Properties.Id.eq(raspuns.getIntrebareId())).build();
                nume.setText(queryIntrebare.list().get(0).getText());
                punctaj.setText(String.valueOf(raspuns.getPunctajObtinut()));
                punctaj.setTextColor(raspuns.getPunctajObtinut() == 0 ? ContextCompat.getColor(rand.getContext(), R.color.rosu) :
                        raspuns.getPunctajObtinut() == queryIntrebare.list().get(0).getDificultate() ? ContextCompat.getColor(rand.getContext(), R.color.verde) :
                                ContextCompat.getColor(rand.getContext(), R.color.galben));
            }


        }
        catch (Exception e) {}

        return rand;
    }
}
