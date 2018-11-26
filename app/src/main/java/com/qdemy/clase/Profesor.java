package com.qdemy.clase;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Profesor implements Parcelable{

    private String nume;
    private String utilizator;
    private String parola;
    private String mail;
    private List<String> materii = new ArrayList<>();
    private List<IntrebareGrila> intrebari = new ArrayList<>();
    private List<Test> teste = new ArrayList<>();
    private List<RezultatTestProfesor> testeSustinute = new ArrayList<>();

    public Profesor( String nume, String utilizator, String parola, String mail) {
        this.nume = nume;
        this.utilizator = utilizator;
        this.parola = parola;
        this.mail = mail;
        this.materii=null;
        this.intrebari=null;
        this.teste=null;
        testeSustinute=null;
    }

    public Profesor(String nume, String utilizator, String parola, String mail, List<String> materii,
                    List<IntrebareGrila> intrebari, List<Test> teste, List<RezultatTestProfesor> testeSustinute) {
        this.nume = nume;
        this.utilizator = utilizator;
        this.parola = parola;
        this.mail = mail;
        this.materii = materii;
        this.intrebari = intrebari;
        this.teste = teste;
        this.testeSustinute=testeSustinute;
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

    public List<String> getMaterii() {
        return materii;
    }

    public String getMaterii(int index) {
        return materii.get(index);
    }

    public void setMaterii(List<String> materii) {
        this.materii = materii;
    }

    public void setMaterii(String materie, int index) {
        this.materii.set(index, materie);
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
        this.intrebari.set(index,intrebare);
    }

    public List<Test> getTeste() {
        return teste;
    }

    public Test getTeste(int index) {
        return teste.get(index);
    }

    public void setTeste(List<Test> teste) {
        this.teste = teste;
    }

    public void setTeste(Test test, int index) {
        this.teste.set(index, test);
    }

    public List<RezultatTestProfesor> getTesteSustinute() {
        return testeSustinute;
    }

    public RezultatTestProfesor getTesteSustinute(int index) {
        return testeSustinute.get(index);
    }

    public void setTesteSustinute(List<RezultatTestProfesor> testeSustinute) {
        this.testeSustinute = testeSustinute;
    }

    public void setTesteSustinute(RezultatTestProfesor testSustinut, int index) {
        this.testeSustinute.set(index, testSustinut);
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
        parcel.readList(this.materii, getClass().getClassLoader());
        parcel.readList(this.intrebari, getClass().getClassLoader());
        parcel.readList(this.teste, getClass().getClassLoader());
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
        parcel.writeList(materii);
        parcel.writeList(intrebari);
        parcel.writeList(teste);
        parcel.writeList(testeSustinute);
    }
}
