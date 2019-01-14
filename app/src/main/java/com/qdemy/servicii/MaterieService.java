package com.qdemy.servicii;

import com.qdemy.clase.Materie;
import com.qdemy.clase.Profesor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MaterieService {

    @GET("materii")
    Call<List<Materie>> getMaterii();

    @GET("materii")
    Call<Materie>  getMaterieByNume(@Query("nume") String nume);

    @GET("materii")
    Call<List<Materie>>   getMateriiByProfesorId(@Query("profesorId") Integer profesorId);






}
