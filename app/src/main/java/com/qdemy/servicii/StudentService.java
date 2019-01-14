package com.qdemy.servicii;


import com.qdemy.clase.Profesor;
import com.qdemy.clase.Student;
import com.qdemy.clase.VariantaRaspuns;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StudentService {
    @GET("studenti/{id}")
    Call<Student> getStudentById(@Path("id") Integer id);

    @POST("api/studenti/login")
    Call<Student> getStudentByUtilizatorAndParola(@Query("utilizator") String utilizator, @Query("parola") String parola);

    @POST("api/studenti/inregistrare")
    Call<Student> saveStudent(@Body Student student);

    @GET("studenti")
    Call<Student> getStudentByUtilizator(@Query("utilizator") String utilizator);

}

  //  api/studenti/inregistrare