package com.qdemy.servicii;

import com.qdemy.clase.IntrebareGrila;
import com.qdemy.clase.Profesor;
import com.qdemy.clase.VariantaRaspuns;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IntrebareGrilaService {

    @GET("intrebariGrila/{id}")
    Call<IntrebareGrila>  getIntrebareGrilaById(@Path("id") Integer  id);

    @GET("intrebariGrila")
    Call<List<IntrebareGrila>> getIntrebariGrilaByTestId(@Query("testId") Integer testId);

    @GET("intrebariGrila")
    Call<List<IntrebareGrila>>  getIntrebariGrilaByProfesorId (@Query("profesorId") Integer profesorId);

    @GET("intrebariGrila")
    Call<IntrebareGrila>  getIntrebareGrilaByTextMaterieAndProfesorId (@Query("text") String text, @Query("materie") String materie, @Query("profesorId") Integer profesorId);

    @DELETE("intrebariGrila")
    Call<IntrebareGrila>  deleteIntrebareGrilaById (@Path("id") Integer  id);

    @GET("intrebariGrila")
    Call<List<IntrebareGrila>>  getIntrebareGrilaByMaterieAndProfesorId ( @Query("materie") String materie, @Query("profesorId") Integer profesorId);


    @PUT("intrebariGrila/{id}")
    Call<IntrebareGrila>  updateIntrebareGrila ( @Path("id") Integer id, @Body IntrebareGrila intrebareGrila);

    @POST("varianteRaspuns")
    Call<IntrebareGrila>  saveIntrebareGrila(@Body IntrebareGrila intrebareGrila);








}
