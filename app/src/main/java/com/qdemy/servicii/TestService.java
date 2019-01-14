package com.qdemy.servicii;

import com.qdemy.clase.IntrebareGrila;
import com.qdemy.clase.Materie;
import com.qdemy.clase.Profesor;
import com.qdemy.clase.RezultatTestStudent;
import com.qdemy.clase.Test;
import com.qdemy.clase.TestPartajat;
import com.qdemy.clase.TestSustinut;
import com.qdemy.clase.VariantaRaspuns;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TestService {

    @GET("teste")
    Call<List<Test>> getTeste();

    @GET("teste")
    Call<List<Test>> getTesteByProfesorId(@Query("profesorId") Integer profesorId);

    @GET("teste/{id}")
    Call<Test> getTestById(@Path("id") Integer  id);

    @GET("teste")
    Call<Test> getTestByNumeMaterieAndProfesorId(@Query("nume") String nume, @Query("materie") String materie, @Query("profesorId") Integer  profesorId);

    @GET("teste")
    Call<Test> getTestByNume(@Query("nume") String nume);

    @PUT("teste/{id}")
    Call<Test> updateTest(@Path("id") Integer  id,
                      @Body Test test);
    @GET("teste")
    Call<Test> getTestByTestSustinutId(@Query("testSustinutId") Integer testSustinutId);

    @POST("teste")
    Call<Test>  saveTest(@Body Test test);



}
