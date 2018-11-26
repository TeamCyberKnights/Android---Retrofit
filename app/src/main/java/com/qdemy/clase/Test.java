package com.qdemy.clase;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Test implements Parcelable {

    private String nume;
    private String descriere;
    private String autor; //numele din clasa Profesor
    private List<IntrebareGrila> intrebari = new ArrayList<>();
    private int minute;
    private Boolean estePublic;

    public Test(String nume, String descriere, String autor, List<IntrebareGrila> intrebari, int minute, Boolean estePublic) {
        this.nume = nume;
        this.descriere = descriere;
        this.autor = autor;
        this.intrebari = intrebari;
        this.minute = minute;
        this.estePublic = estePublic;
    }

    //region GET, SET

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public List<IntrebareGrila> getIntrebari() {
        return intrebari;
    }

    public IntrebareGrila getIntrebari(int index) {
        return intrebari.get(index);
    }

    public void setIntrebari(List<IntrebareGrila> intrebari) {
        this.intrebari = intrebari;
    }

    public void setIntrebari(IntrebareGrila intrebare, int index) {
        this.intrebari.set(index, intrebare);
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public Boolean getEstePublic() {
        return estePublic;
    }

    public void setEstePublic(Boolean estePublic) {
        this.estePublic = estePublic;
    }

    //endregion



    //Parcel

    public static final Creator<Test> CREATOR =
            new Creator<Test>() {
                @Override
                public Test createFromParcel(Parcel parcel) {
                    return new Test(parcel);
                }

                @Override
                public Test[] newArray(int i) {
                    return new Test[i];
                }
            };


    public Test(Parcel parcel) {
        this.nume = parcel.readString();
        this.descriere = parcel.readString();
        this.autor = parcel.readString();
        parcel.readList(this.intrebari, getClass().getClassLoader());
        this.minute = parcel.readInt();
        this.estePublic  = (parcel.readInt() == 0) ? false : true;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {

        parcel.writeString(nume);
        parcel.writeString(descriere);
        parcel.writeString(autor);
        parcel.writeList(intrebari);
        parcel.writeInt(minute);
        parcel.writeInt(estePublic ? 1 : 0);
    }



}
