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
import com.qdemy.RezultatStudentActivity;
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

public class RezultatStudentAdapter extends ArrayAdapter<RaspunsIntrebareGrila> {

    private Context context;
    private int resource;
    private List<RaspunsIntrebareGrila> raspunsuri;
    private LayoutInflater inflater;
    private RezultatStudentActivity activity;
    private IntrebareGrila intrebareGrila;
    private NetworkConnectionService networkConnectionService = new NetworkConnectionService();


    public RezultatStudentAdapter(@NonNull Context context, int resource,
                                  @NonNull List<RaspunsIntrebareGrila> objects, LayoutInflater inflater,
                                  RezultatStudentActivity activity) {
        super(context, resource, objects);

        this.context=context;
        this.resource=resource;
        this.raspunsuri = objects;
        this.inflater=inflater;
        this.activity=activity;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rand = inflater.inflate(resource, parent, false);

        TextView nume = rand.findViewById(R.id.text_itb);
        Button corect = rand.findViewById(R.id.button_itb);

        try {
            final RaspunsIntrebareGrila raspuns = raspunsuri.get(position);


            if (networkConnectionService.isInternetAvailable()) {
                IntrebareGrilaService intrebareGrilaService = ServiceBuilder.buildService(IntrebareGrilaService.class);
                final Call<IntrebareGrila> intrebareGrilaRequest = intrebareGrilaService.getIntrebareGrilaById((int) (long) raspuns.getIntrebareId());
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
                corect.setBackgroundResource(raspuns.getPunctajObtinut() == 0 ? R.drawable.ic_picat :
                        raspuns.getPunctajObtinut() == intrebareGrila.getDificultate() ? R.drawable.ic_promovat :
                                R.drawable.ic_mediu);
            } else {


                Query<IntrebareGrila> queryIntrebare = ((App) activity.getApplication()).getDaoSession().getIntrebareGrilaDao().queryBuilder().where(
                        IntrebareGrilaDao.Properties.Id.eq(raspuns.getIntrebareId())).build();
                nume.setText(queryIntrebare.list().get(0).getText());
                corect.setBackgroundResource(raspuns.getPunctajObtinut() == 0 ? R.drawable.ic_picat :
                        raspuns.getPunctajObtinut() == queryIntrebare.list().get(0).getDificultate() ? R.drawable.ic_promovat :
                                R.drawable.ic_mediu);
            }
        }
        catch (Exception e) {}

        return rand;
    }
}
