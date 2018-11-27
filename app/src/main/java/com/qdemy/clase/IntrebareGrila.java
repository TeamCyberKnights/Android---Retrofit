package com.qdemy.clase;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class IntrebareGrila implements Parcelable {

    private String nume;
    private String continut;
    private String materie;
    private List<String> variante = new ArrayList<>();
    private List<Boolean> raspunsuri = new ArrayList<>();  // true=corect, false=gresit
    private float punctaj; // asociat dificultatii: 1-usor, 2-mediu, 3-greu  //ENUM

    public IntrebareGrila(String nume, String continut, String materie, List<String> variante, List<Boolean> raspunsuri, float punctaj) {
        this.nume = nume;
        this.continut = continut;
        this.materie = materie;
        this.variante = variante;
        this.raspunsuri = raspunsuri;
        this.punctaj = punctaj;
    }

    //region GET, SET

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getContinut() {
        return continut;
    }

    public void setContinut(String continut) {
        this.continut = continut;
    }

    public String getMaterie() {
        return materie;
    }

    public void setMaterie(String materie) {
        this.materie = materie;
    }

    public List<String> getVariante() {
        return variante;
    }

    public String getVariante(int index) {
        return variante.get(index);
    }

    public void setVariante(List<String> variante) {
        this.variante = variante;
    }

    public void setVariante(String varianta, int index) {
        this.variante.set(index, varianta);
    }

    public List<Boolean> getRaspunsuri() {
        return raspunsuri;
    }

    public Boolean getRaspunsuri(int index) {
        return raspunsuri.get(index);
    }

    public void setRaspunsuri(List<Boolean> raspunsuri) {
        this.raspunsuri = raspunsuri;
    }

    public void setRaspunsuri(Boolean raspuns, int index) {
        this.raspunsuri.set(index,raspuns);
    }

    public float getPunctaj() {
        return punctaj;
    }

    public void setPunctaj(float punctaj) {
        this.punctaj = punctaj;
    }


    //endregion


    //Parcel

    public static final Creator<IntrebareGrila> CREATOR =
            new Creator<IntrebareGrila>() {
                @Override
                public IntrebareGrila createFromParcel(Parcel parcel) {
                    return new IntrebareGrila(parcel);
                }

                @Override
                public IntrebareGrila[] newArray(int i) {
                    return new IntrebareGrila[i];
                }
            };


    public IntrebareGrila(Parcel parcel) {
        this.nume = parcel.readString();
        this.continut = parcel.readString();
        this.materie = parcel.readString();
        parcel.readList(this.variante, getClass().getClassLoader());
        parcel.readList(this.raspunsuri, getClass().getClassLoader());
        this.punctaj = parcel.readFloat();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {

        parcel.writeString(nume);
        parcel.writeString(continut);
        parcel.writeString(materie);
        parcel.writeList(variante);
        parcel.writeList(raspunsuri);
        parcel.writeFloat(punctaj);
    }
}
