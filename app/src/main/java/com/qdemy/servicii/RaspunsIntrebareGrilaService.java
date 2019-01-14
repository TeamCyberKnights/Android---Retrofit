package com.qdemy.servicii;

import com.qdemy.clase.RaspunsIntrebareGrila;
import com.qdemy.clase.VariantaRaspuns;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RaspunsIntrebareGrilaService {


    @GET("varianteRaspuns")
    Call<RaspunsIntrebareGrila> getRaspunsIntrebareGrilaByIntrebareIdAndRezultatTestStudentId (@Query("intrebareId") Integer intrebareId,
                                                                                               @Query("rezultatTestStudentId") Integer rezultatTestStudentId);

    @GET("varianteRaspuns")
    Call<List<RaspunsIntrebareGrila>> getRaspunsIntrebareGrilaByRezultatTestStudentId (@Query("rezultatTestStudentId") Integer rezultatTestStudentId);

    @POST("varianteRaspuns")
    Call<RaspunsIntrebareGrila> saveRaspunsIntrebareGrila (@Body RaspunsIntrebareGrila raspunsIntrebareGrila);


    @GET("varianteRaspuns/{id}")
    Call<RaspunsIntrebareGrila>   getRaspunsIntrebareGrilaById (@Path("id") Integer id);

    @PUT("varianteRaspuns/{id}")
    Call<RaspunsIntrebareGrila>    updateRaspunsInrebareService (@Path("id") Integer intrebareId, @Body  RaspunsIntrebareGrila raspunsIntrebareGrila);






}
