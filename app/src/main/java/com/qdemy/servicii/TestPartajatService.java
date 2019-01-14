package com.qdemy.servicii;


import com.qdemy.clase.TestPartajat;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TestPartajatService {

    @GET("testePartajate/{id}")
    Call<List<TestPartajat>> getTestePartajatByTestId(@Query("testId") Integer testId);

    @GET("testePartajate")
    Call<List<TestPartajat>> getTestePartajateByProfesorId(@Query("profesorId") Integer  profesorId);

    @GET("testePartajate")
    Call<TestPartajat> getTestPartajatByTestIdAndProfesorId(@Query("testId") Integer  testId, @Query("profesorId") Integer profesorId);

    @POST("testePartajate")
    Call<TestPartajat> saveTestPartajat(@Body TestPartajat testPartajat);

    @DELETE("testePartajate/{id}")
    Call<TestPartajat> deleteTestPartajatById(@Path("id") Integer  id);
}
