package com.qdemy.servicii;

import com.qdemy.clase.RezultatTestStudent;
import com.qdemy.clase.RezultatTestStudentDao;
import com.qdemy.clase.Test;


import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RezultatTestStudentService {

    @GET("rezultateTestStudent/{id}")
    Call<RezultatTestStudent>  getRezultatTestStudentById(@Path("id")Integer id);

    @GET("rezultateTestStudent")
    Call<RezultatTestStudent>  getRezultatTestStudentByTestIdDataAndStudentId(@Query("testId") Integer testId, @Query("data") String data, @Query("studentId") Integer  studentId);

    @GET("rezultateTestStudent/{id}")
    Call<List<RezultatTestStudent>> getRezultateTesteStudentByStudentId(@Path("studentId")Integer studentId);


    @POST("rezultateTestStudent")
    Call<RezultatTestStudent>  saveRezultatTestStudent(@Body RezultatTestStudent rezultatTestStudent);

    @GET("rezultateTestStudent/{testSustinutId}")
    Call<List<RezultatTestStudent>>   getRezultateTesteStudentByTestSustinutId(@Query("testSustinutId") Integer testSustinutId);





}
