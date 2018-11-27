package com.qdemy.clase;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class RezultatTestStudent implements Parcelable {

    private String nume; // preluat de la testul original
    private String data;
    private float punctajObtinut; //PUNCTAJ RELATIV LA 100  //punctajul tuturor intrebarilor * coef obtinut prin impartirea 100/punctaj maxim posibil
    //de facut metoda pentru calculat punctajulObtinut (in loc de campul asta) !!!!
    private int minuteTrecute;
    private boolean promovat;
    private List<RaspunsIntrebareGrila> raspunsuri = new ArrayList<>();


    public RezultatTestStudent(String nume, String data, float punctajObtinut, int minuteTrecute, boolean promovat,
                               List<RaspunsIntrebareGrila> raspunsuri) {
        this.nume = nume;
        this.data = data;
        this.punctajObtinut = punctajObtinut;
        this.minuteTrecute = minuteTrecute;
        this.promovat=promovat;
        this.raspunsuri = raspunsuri;
    }

    //region GET, SET

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public float getPunctajObtinut() {
        return punctajObtinut;
    }

    public void setPunctajObtinut(float punctajObtinut) {
        this.punctajObtinut = punctajObtinut;
    }

    public int getMinuteTrecute() {
        return minuteTrecute;
    }

    public void setMinuteTrecute(int minuteTrecute) {
        this.minuteTrecute = minuteTrecute;
    }

    public List<RaspunsIntrebareGrila> getRaspunsuri() {
        return raspunsuri;
    }

    public RaspunsIntrebareGrila getRaspunsuri(int index) {
        return raspunsuri.get(index);
    }

    public RaspunsIntrebareGrila getRaspunsuri(String nume_intrebare) {
        for(int i=0;i<raspunsuri.size();i++)
            if(raspunsuri.get(i).getNume().equals(nume_intrebare))  return raspunsuri.get(i);

        return null;
    }

    public void setRaspunsuri(List<RaspunsIntrebareGrila> raspunsuri) {
        this.raspunsuri = raspunsuri;
    }

    public void setRaspunsuri(RaspunsIntrebareGrila raspuns, int index) {
        this.raspunsuri.set(index, raspuns);
    }

    public boolean isPromovat() {
        return promovat;
    }

    public void setPromovat(boolean promovat) {
        this.promovat = promovat;
    }

    //endregion


    //Parcel

    public static final Creator<RezultatTestStudent> CREATOR =
            new Creator<RezultatTestStudent>() {
                @Override
                public RezultatTestStudent createFromParcel(Parcel parcel) {
                    return new RezultatTestStudent(parcel);
                }

                @Override
                public RezultatTestStudent[] newArray(int i) {
                    return new RezultatTestStudent[i];
                }
            };


    public RezultatTestStudent(Parcel parcel) {
        this.nume = parcel.readString();
        this.data = parcel.readString();
        this.punctajObtinut = parcel.readFloat();
        this.minuteTrecute = parcel.readInt();
        this.promovat= (parcel.readInt() == 0) ? false : true;
        parcel.readList(this.raspunsuri, getClass().getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {

        parcel.writeString(nume);
        parcel.writeString(data);
        parcel.writeFloat(punctajObtinut);
        parcel.writeInt(minuteTrecute);
        parcel.writeInt(promovat ? 1 : 0);
        parcel.writeList(raspunsuri);
    }


}
