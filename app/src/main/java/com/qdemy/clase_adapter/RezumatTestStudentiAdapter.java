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
import com.qdemy.RezumatTestActivity;
import com.qdemy.clase.RezultatTestStudent;
import com.qdemy.clase.Student;
import com.qdemy.clase.StudentDao;
import com.qdemy.clase.TestSustinut;
import com.qdemy.db.App;
import com.qdemy.servicii.NetworkConnectionService;
import com.qdemy.servicii.ServiceBuilder;
import com.qdemy.servicii.StudentService;

import org.greenrobot.greendao.query.Query;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RezumatTestStudentiAdapter extends ArrayAdapter<RezultatTestStudent> {

    private Context context;
    private int resource;
    private List<RezultatTestStudent> rezultate;
    private LayoutInflater inflater;
    private TestSustinut test;
    private RezumatTestActivity activity;
    private Student student ;
    private NetworkConnectionService networkConnectionService = new NetworkConnectionService();

    public RezumatTestStudentiAdapter(@NonNull Context context, int resource,
                                      @NonNull List<RezultatTestStudent> objects, LayoutInflater inflater,
                                      RezumatTestActivity activity) {
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

        TextView nume = rand.findViewById(R.id.text1_itt);
        TextView punctaj = rand.findViewById(R.id.text2_itt);

        try {
            RezultatTestStudent rezultat = rezultate.get(position);


            if (networkConnectionService.isInternetAvailable()) {
                StudentService studentService = ServiceBuilder.buildService(StudentService.class);
                Call<Student> studentRequest = studentService.getStudentById((int)(long)rezultat.getStudentId());
                studentRequest.enqueue(new Callback<Student>() {
                    @Override
                    public void onResponse(Call<Student> call, Response<Student> response) {
                        student = response.body();
                    }

                    @Override
                    public void onFailure(Call<Student> call, Throwable t) {

                    }
                });
                nume.setText(student.getNume() + " " + student.getPrenume());

            } else {
                Query<Student> queryStudent = ((App) activity.getApplication()).getDaoSession().getStudentDao().queryBuilder().where(
                StudentDao.Properties.Id.eq(rezultat.getStudentId())).build();
                nume.setText(queryStudent.list().get(0).getNume() + " " + queryStudent.list().get(0).getPrenume());

            }

            float punctajStudent = ((App) activity.getApplication()).getPunctajTest(rezultat.getRaspunsuri());
            punctaj.setText(String.valueOf(punctajStudent));
            punctaj.setTextColor(punctajStudent < 50 ? ContextCompat.getColor(rand.getContext(), R.color.rosu) :
                    ContextCompat.getColor(rand.getContext(), R.color.verde));
        }
        catch (Exception e) {}

        return rand;
    }
}
