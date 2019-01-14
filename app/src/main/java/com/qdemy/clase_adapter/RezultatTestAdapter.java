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
import com.qdemy.ScorLiveActivity;
import com.qdemy.clase.RezultatTestStudent;
import com.qdemy.clase.StudentDao;
import com.qdemy.clase.TestSustinut;
import com.qdemy.clase.Student;
import com.qdemy.db.App;
import com.qdemy.servicii.NetworkConnectionService;
import com.qdemy.servicii.ServiceBuilder;
import com.qdemy.servicii.StudentService;

import org.greenrobot.greendao.query.Query;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RezultatTestAdapter extends ArrayAdapter<RezultatTestStudent> {

    private Context context;
    private int resource;
    private List<RezultatTestStudent> rezultate;
    private LayoutInflater inflater;
    private String numeTest;
    private String data;
    private ScorLiveActivity activity;
    private Student student2;
    private NetworkConnectionService networkConnectionService = new NetworkConnectionService();


    public RezultatTestAdapter(@NonNull Context context, int resource,
                               @NonNull List<RezultatTestStudent> objects, LayoutInflater inflater,
                               ScorLiveActivity activity) {
        super(context, resource, objects);

        this.context=context;
        this.resource=resource;
        this.rezultate = objects;
        this.inflater=inflater;
        this.activity=activity;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rand = inflater.inflate(resource, parent, false);

        TextView nume = rand.findViewById(R.id.text1_itt);
        TextView punctaj = rand.findViewById(R.id.text2_itt);

        try {

            if (networkConnectionService.isInternetAvailable()) {
                StudentService studentService = ServiceBuilder.buildService(StudentService.class);
                Call<Student> studentRequest = studentService.getStudentById((int) (long) rezultate.get(position).getStudentId());
                studentRequest.enqueue(new Callback<Student>() {
                    @Override
                    public void onResponse(Call<Student> call, Response<Student> response) {
                        student2 = response.body();
                    }

                    @Override
                    public void onFailure(Call<Student> call, Throwable t) {

                    }
                });
                final Student student = student2;
                nume.setText(student.getNume());
                punctaj.setText(String.valueOf(((App) activity.getApplication()).getPunctajTest(rezultate.get(position).getRaspunsuri())));
            } else {
                Query<Student> queryStudent = ((App) activity.getApplication()).getDaoSession().getStudentDao().queryBuilder().where(
                        StudentDao.Properties.Id.eq(rezultate.get(position).getStudentId())).build();
                final Student student = queryStudent.list().get(0);

                nume.setText(student.getNume());
                punctaj.setText(String.valueOf(((App) activity.getApplication()).getPunctajTest(rezultate.get(position).getRaspunsuri())));
            }
        }
        catch (Exception e) {}


        return rand;
    }
}
