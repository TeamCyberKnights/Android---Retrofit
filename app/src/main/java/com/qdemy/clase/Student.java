package com.qdemy.clase;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;

public class Student implements Parcelable {

    private String nume;
    private String parola;
    private String mail;

    public Student(String nume, String parola, String mail) {
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

    public static final Creator<Student> CREATOR =
            new Creator<Student>() {
                @Override
                public Student createFromParcel(Parcel parcel) {
                    return new Student(parcel);
                }

                @Override
                public Student[] newArray(int i) {
                    return new Student[i];
                }
            };

    private Student(Parcel parcel) {

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
