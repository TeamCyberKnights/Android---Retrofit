package com.qdemy.clase;

import android.os.Parcel;
import android.os.Parcelable;

public class Profesor implements Parcelable{

    private String nume;
    private String parola;
    private String mail;

    public Profesor(String nume, String parola, String mail) {
        this.nume = nume;
        this.parola = parola;
        this.mail = mail;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


    //////////////////////////////////////////////////////////////////////////////
    //Parcel

    public static final Parcelable.Creator<Profesor> CREATOR =
            new Parcelable.Creator<Profesor>() {
                @Override
                public Profesor createFromParcel(Parcel parcel) {
                    return new Profesor(parcel);
                }

                @Override
                public Profesor[] newArray(int i) {
                    return new Profesor[i];
                }
            };

    private Profesor(Parcel parcel) {

        this.nume = parcel.readString();
        this.parola = parcel.readString();
        this.mail = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {

        parcel.writeString(nume);
        parcel.writeString(parola);
        parcel.writeString(mail);
    }
}
