package com.qdemy.clase;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Materie {

    @Id(autoincrement = true) private Long id;
    @NotNull private String nume;



    //region Constructori

    public Materie() {}

    public Materie(String nume) {
        this.nume = nume;
    }

    @Generated(hash = 1738787040)
    public Materie(Long id, @NotNull String nume) {
        this.id = id;
        this.nume = nume;
    }

    //endregion

    //region GET, SET

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }


    //endregion
}
