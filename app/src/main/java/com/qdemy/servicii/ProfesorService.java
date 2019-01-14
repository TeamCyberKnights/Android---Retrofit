package com.qdemy.servicii;

import com.qdemy.clase.IntrebareGrila;
import com.qdemy.clase.Materie;
import com.qdemy.clase.Profesor;
import com.qdemy.clase.Student;
import com.qdemy.clase.Test;
import com.qdemy.clase.TestPartajat;
import com.qdemy.clase.TestSustinut;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProfesorService {
    @GET("profesori")
    Call<List<Profesor>> getProfesori();

    @GET("profesori/{id}")
    Call<Profesor> getProfesorById(@Path("id") Integer  id);

    @GET("profesori")
    Call<Profesor> getProfesorByUtilizator(@Query("utilizator") String utilizator);

    @PUT("profesori/{id}")
    Call<Profesor> updateProfesor(@Path("id") Integer  id,
                                @Body Profesor profesor);

    @GET("profesori")
    Call<Profesor>    getProfesorByUtilizatorAndParola(@Query("utilizator") String utilizator, @Query("utilizator") String parola);



}
