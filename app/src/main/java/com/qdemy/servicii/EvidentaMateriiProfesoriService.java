package com.qdemy.servicii;

import com.qdemy.clase.EvidentaMateriiProfesori;
import com.qdemy.clase.TestPartajat;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EvidentaMateriiProfesoriService {

    @POST("evidentaMateriiProfesori")
    Call<EvidentaMateriiProfesori> saveEvidentaMateriiProfesori(@Body EvidentaMateriiProfesori evidentaMateriiProfesori);

    @DELETE("evidentaMateriiProfesori/{id}")
    Call<EvidentaMateriiProfesori> deleteEvidentaMateriiProfesoriById(@Path("id") Integer  id);

    @GET("evidentaMateriiProfesori")
    Call<EvidentaMateriiProfesori>  getEvidentaByMaterieIdAndProfesorId(@Query("materieId") Integer testId, @Query("profesorId") Integer profesorId);


}
