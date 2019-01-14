package com.qdemy.servicii;

import com.qdemy.clase.IntrebareGrila;
import com.qdemy.clase.TestPartajat;
import com.qdemy.clase.TestSustinut;
import com.qdemy.clase.VariantaRaspuns;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface VariantaRaspunsService {

    @GET("varianteRaspuns")
    Call<List<VariantaRaspuns>> getVarianteRaspunsByIntrebareId (@Query("intrebareId") Integer intrebareId);

    @DELETE("varianteRaspuns/{id}")
    Call<VariantaRaspuns> deleteVariantaRaspunsById(@Path("id") Integer id);

    @GET("varianteRaspuns")
    Call<VariantaRaspuns> getVariantaRaspunsById (@Query("id") Integer id);

    @POST("varianteRaspuns")
    Call<VariantaRaspuns>  saveVariantaRaspuns(@Body VariantaRaspuns variantaRaspuns);


}
