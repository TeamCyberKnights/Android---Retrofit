package com.qdemy.clase;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Materie {

    @Id(autoincrement = true) private Long id;
    @NotNull private String nume;

    @NotNull private long profesorId;

    //region Constructori

    public Materie() {}

    public Materie(String nume) {
        this.nume = nume;
    }

    @Generated(hash = 659149784)
    public Materie(Long id, @NotNull String nume, long profesorId) {
        this.id = id;
        this.nume = nume;
        this.profesorId = profesorId;
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

    public long getProfesorId() {
        return profesorId;
    }

    public void setProfesorId(long profesorId) {
        this.profesorId = profesorId;
    }


    //endregion
}
