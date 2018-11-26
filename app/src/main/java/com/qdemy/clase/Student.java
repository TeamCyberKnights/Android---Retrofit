package com.qdemy.clase;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Student implements Parcelable {

    private String nume;
    private String utilizator;
    private String parola;
    private String mail;
    private List<RezultatTestStudent> testeSustinute = new ArrayList<>();

    public Student(String nume, String utilizator, String parola, String mail) {
        this.nume = nume;
        this.utilizator = utilizator;
        this.parola = parola;
        this.mail = mail;
        this.testeSustinute = null;
    }

    public Student(String nume, String utilizator, String parola, String mail, List<RezultatTestStudent> testeSustinute) {
        this.nume = nume;
        this.utilizator = utilizator;
        this.parola = parola;
        this.mail = mail;
        this.testeSustinute = testeSustinute;
    }


    //region GET, SET

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

    public String getUtilizator() {
        return utilizator;
    }

    public void setUtilizator(String utilizator) {
        this.utilizator = utilizator;
    }

    public List<RezultatTestStudent> getTesteSustinute() {
        return testeSustinute;
    }

    public RezultatTestStudent getTesteSustinute(int index) {
        return testeSustinute.get(index);
    }

    public RezultatTestStudent getTesteSustinute(String nume, String data) {

        for (int i=0;i<testeSustinute.size();i++)
            if(testeSustinute.get(i).getNume().equals(nume) &&
               testeSustinute.get(i).getData().equals(data)) return testeSustinute.get(i);

        return null;
    }

    public void setTesteSustinute(List<RezultatTestStudent> testeSustinute) {
        this.testeSustinute = testeSustinute;
    }

    public void setTesteSustinute(RezultatTestStudent testSustinut, int index) {
        this.testeSustinute.set(index, testSustinut);
    }


    //endregion

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
        this.utilizator = parcel.readString();
        this.parola = parcel.readString();
        this.mail = parcel.readString();
        parcel.readList(this.testeSustinute, getClass().getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {

        parcel.writeString(nume);
        parcel.writeString(utilizator);
        parcel.writeString(parola);
        parcel.writeString(mail);
        parcel.writeList(testeSustinute);
    }
}
