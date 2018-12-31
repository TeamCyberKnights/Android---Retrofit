package com.qdemy.clase;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.ArrayList;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;

@Entity
public class RaspunsIntrebareGrila  {

    @Id (autoincrement = true) private Long id;
    @NotNull private long intrebareId;
    @NotNull private float punctajObtinut; //punctajul intrebarii impartit la nr de raspunsuri corecte (daca este =0 => raspuns gresit)
    @NotNull private int secunde; //timpul in care a raspuns

    @NotNull private long rezultatTestStudentId;

    //region Constructori

    public RaspunsIntrebareGrila() {}

    public RaspunsIntrebareGrila(long intrebareId, float punctajObtinut) {
        this.intrebareId = intrebareId;
        this.punctajObtinut=punctajObtinut;
    }

    public RaspunsIntrebareGrila(long intrebareId, float punctajObtinut, int secunde) {
        this.intrebareId = intrebareId;
        this.punctajObtinut=punctajObtinut;
        this.secunde=secunde;
    }

    @Generated(hash = 416581354)
    public RaspunsIntrebareGrila(Long id, long intrebareId, float punctajObtinut, int secunde, long rezultatTestStudentId) {
        this.id = id;
        this.intrebareId = intrebareId;
        this.punctajObtinut = punctajObtinut;
        this.secunde = secunde;
        this.rezultatTestStudentId = rezultatTestStudentId;
    }

    //endregion

    //region GET, SET

    public long getIntrebareId() {
        return intrebareId;
    }

    public void setIntrebareId(long intrebareId) {
        this.intrebareId = intrebareId;
    }

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

    public Long getId() {
        return id;
    }

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


}
