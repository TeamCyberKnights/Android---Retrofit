package com.qdemy.clase;

import android.os.Parcel;
import android.os.Parcelable;

public class Profesor implements Parcelable{

    private String nume;
    private String utilizator;
    private String parola;
    private String mail;
    private String[] materii;
    private IntrebareGrila[] intrebari;
    private Test[] teste;

    public Profesor( String nume, String utilizator, String parola, String mail) {
        this.nume = nume;
        this.utilizator = utilizator;
        this.parola = parola;
        this.mail = mail;
        this.materii=null;
        this.intrebari=null;
        this.teste=null;
    }

    public Profesor(String nume, String utilizator, String parola, String mail, String[] materii, IntrebareGrila[] intrebari, Test[] teste) {
        this.nume = nume;
        this.utilizator = utilizator;
        this.parola = parola;
        this.mail = mail;
        this.materii = materii;
        this.intrebari = intrebari;
        this.teste = teste;
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

    public String[] getMaterii() {
        return materii;
    }

    public String getMaterii(int index) {
        return materii[index];
    }

    public void setMaterii(String[] materii) {
        this.materii = materii;
    }

    public void setMaterii(String materie, int index) {
        this.materii[index] = materie;
    }

    public IntrebareGrila[] getIntrebari() {
        return intrebari;
    }

    public IntrebareGrila getIntrebari(int index) {
        return intrebari[index];
    }

    public void setIntrebari(IntrebareGrila[] intrebari) {
        this.intrebari = intrebari;
    }

    public void setIntrebari(IntrebareGrila intrebare, int index) {
        this.intrebari[index] = intrebare;
    }

    public Test[] getTeste() {
        return teste;
    }

    public Test getTeste(int index) {
        return teste[index];
    }

    public void setTeste(Test[] teste) {
        this.teste = teste;
    }

    public void setTeste(Test test, int index) {
        this.teste[index] = test;
    }

    //endregion

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
        this.utilizator = parcel.readString();
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
        parcel.writeString(utilizator);
        parcel.writeString(parola);
        parcel.writeString(mail);
    }
}
