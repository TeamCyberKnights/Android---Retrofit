package com.qdemy.clase;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class RezultatTestProfesor implements Parcelable {

    private List<RezultatTestStudent> rezultate = new ArrayList<>();

    public RezultatTestProfesor(List<RezultatTestStudent> rezultate) {
        this.rezultate = rezultate;
    }

    public List<RezultatTestStudent> getRezultate() {
        return rezultate;
    }

    public void setRezultate(List<RezultatTestStudent> rezultate) {
        this.rezultate = rezultate;
    }

    public float getMedia()
    {
        float media=0;
        for(int i=0;i<rezultate.size();i++)
            media+=rezultate.get(i).getPunctajObtinut();

        return media/rezultate.size();
    }

    public String getNume()
    {
        //preluat din prima inregistare din lista
        return rezultate.get(0).getNume();
    }

    public String getData()
    {
        //preluat din prima inregistare din lista
        return rezultate.get(0).getData();
    }

    public int getPromovati()
    {
        int promovati=0;
        for(int i=0;i<rezultate.size();i++)
            if(rezultate.get(i).isPromovat()) promovati++;

        return promovati;
    }

    public int getRaspunsuriCorecte(String nume_intrebare)
    {
        int raspunsuriCorecte=0;
        for(int i=0;i<rezultate.size();i++)
            if(rezultate.get(i).getRaspunsuri(nume_intrebare).Corect()) raspunsuriCorecte++;
        return raspunsuriCorecte;
    }

    public int getNumarStudenti()
    {
        return rezultate.size();
    }



    //Parcel

    public static final Creator<RezultatTestProfesor> CREATOR =
            new Creator<RezultatTestProfesor>() {
                @Override
                public RezultatTestProfesor createFromParcel(Parcel parcel) {
                    return new RezultatTestProfesor(parcel);
                }

                @Override
                public RezultatTestProfesor[] newArray(int i) {
                    return new RezultatTestProfesor[i];
                }
            };


    public RezultatTestProfesor(Parcel parcel) {
        parcel.readList(this.rezultate, getClass().getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {

        parcel.writeList(rezultate);
    }
}
