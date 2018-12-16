package com.qdemy.clase;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.ArrayList;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class RaspunsIntrebareGrila extends IntrebareGrila {

    @Id (autoincrement = true) private Long id;
    @NotNull private float punctajObtinut; //punctajul intrebarii impartit la nr de raspunsuri corecte (daca este =0 => raspuns gresit)
    @NotNull private int secunde; //timpul in care a raspuns

    @NotNull private long rezultatTestStudentId;

    //region Constructori

    public RaspunsIntrebareGrila() {}

    public RaspunsIntrebareGrila(String nume, String continut, String materie, List<VariantaRaspuns> variante, List<Boolean> raspunsuri, float punctaj,
                                 float punctajObtinut, int secunde) {
        super(nume, continut, materie, variante, punctaj);
        this.punctajObtinut=punctajObtinut;
        this.secunde=secunde;
    }

    public RaspunsIntrebareGrila(IntrebareGrila intrebare, float punctajObtinut, int secunde) {
        super(intrebare.getNume(), intrebare.getContinut(), intrebare.getMaterie(),
              intrebare.getVariante(), intrebare.getPunctaj());
        this.punctajObtinut=punctajObtinut;
        this.secunde=secunde;
    }

    @Generated(hash = 965281344)
    public RaspunsIntrebareGrila(Long id, float punctajObtinut, int secunde, long rezultatTestStudentId) {
        this.id = id;
        this.punctajObtinut = punctajObtinut;
        this.secunde = secunde;
        this.rezultatTestStudentId = rezultatTestStudentId;
    }

    //endregion

    //region GET, SET

    public float getPunctajObtinut() {
        return punctajObtinut;
    }

    public void setPunctajObtinut(float punctajObtinut) {
        this.punctajObtinut = punctajObtinut;
    }

    public int getSecunde() {
        return secunde;
    }

    public void setSecunde(int secunde) {
        this.secunde = secunde;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public long getRezultatTestStudentId() {
        return rezultatTestStudentId;
    }

    public void setRezultatTestStudentId(long rezultatTestStudentId) {
        this.rezultatTestStudentId = rezultatTestStudentId;
    }

    //endregion


    public void partialCorect()
    {
        //raspunsurile  partial corect
    }


    public boolean Corect()
    {
        //de luat in calcul raspunsurile partiale
        if(punctajObtinut==this.getPunctaj()) return true;
        else return false;
    }



}
