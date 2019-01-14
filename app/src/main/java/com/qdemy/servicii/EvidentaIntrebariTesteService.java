package com.qdemy.servicii;

import com.qdemy.clase.EvidentaIntrebariTeste;
import com.qdemy.clase.TestPartajat;
import com.qdemy.clase.VariantaRaspuns;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EvidentaIntrebariTesteService {

    @GET("evidentaIntrebariTeste")
    Call<List<EvidentaIntrebariTeste>> getEvidentaIntrebariTesteByTestId(@Query("testId") Integer  id);

    @GET("evidentaIntrebariTeste")
    Call<List<EvidentaIntrebariTeste>>   getEvidentaIntrebariTesteByIntrebareId(@Query("intrebareId") Integer  id);

    @DELETE("evidentaIntrebariTeste/{id}")
    Call<EvidentaIntrebariTeste> deleteEvidentaIntrebariTesteById(@Path("id") Integer id);

    @GET("evidentaIntrebariTeste")
    Call<EvidentaIntrebariTeste>    getEvidentaIntrebariTesteByIntrebareIdAndTestId(@Query("intrebareId") Integer  intrebareId, @Query("testId")Integer testId);

    @POST("evidentaIntrebariTeste")
    Call<EvidentaIntrebariTeste>    saveEvidentaIntrebariTeste(@Body EvidentaIntrebariTeste evidentaIntrebariTeste);





}
