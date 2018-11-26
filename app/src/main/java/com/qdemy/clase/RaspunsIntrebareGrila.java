package com.qdemy.clase;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class RaspunsIntrebareGrila extends IntrebareGrila {

    private float punctajObtinut; //punctajul intrebarii impartit la nr de raspunsuri corecte (daca este =0 => raspuns gresit)
    private int secunde; //timpul in care a raspuns

    public RaspunsIntrebareGrila(String nume, String continut, String materie, List<String> variante, List<Boolean> raspunsuri, float punctaj,
                                 float punctajObtinut, int secunde) {
        super(nume, continut, materie, variante, raspunsuri, punctaj);
        this.punctajObtinut=punctajObtinut;
        this.secunde=secunde;
    }

    public RaspunsIntrebareGrila(IntrebareGrila intrebare, float punctajObtinut, int secunde) {
        super(intrebare.getNume(), intrebare.getContinut(), intrebare.getMaterie(),
              intrebare.getVariante(), intrebare.getRaspunsuri(), intrebare.getPunctaj());
        this.punctajObtinut=punctajObtinut;
        this.secunde=secunde;
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


    public boolean Corect()
    {
        if(punctajObtinut==this.getPunctaj()) return true;
        else return false;
    }


}
