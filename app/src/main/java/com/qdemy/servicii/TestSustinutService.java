package com.qdemy.servicii;

import com.qdemy.clase.Test;
import com.qdemy.clase.TestPartajat;
import com.qdemy.clase.TestSustinut;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TestSustinutService {

    @POST("testeSustinute")
    Call<TestSustinut> saveTestSustinut(@Body TestSustinut testSustinut);

    @GET("testeSustinute")
    Call<List<TestSustinut>> getTesteSustinutByProfesorId(@Query("profesorId") Integer  profesorId);

    @GET("testeSustinute/{id}")
    Call<TestSustinut> getTestSustinutById(@Path("id") Integer  id);



}
