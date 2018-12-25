package com.qdemy.clase;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Echipa {

    @Id(autoincrement = true) private Long id;
    @NotNull private String nume;

    //region Constructori

    public Echipa() {}

    public Echipa(String nume) {
        this.nume = nume;
    }

    @Generated(hash = 694943713)
    public Echipa(Long id, @NotNull String nume) {
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
