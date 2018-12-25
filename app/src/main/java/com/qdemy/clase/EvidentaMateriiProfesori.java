package com.qdemy.clase;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class EvidentaMateriiProfesori {

    @Id(autoincrement = true) private Long id;
    private Long materieId;
    private Long profesorId;

    public EvidentaMateriiProfesori() {}

    public EvidentaMateriiProfesori(Long materieId, Long profesorId) {
        this.materieId = materieId;
        this.profesorId = profesorId;
    }

    @Generated(hash = 1816883538)
    public EvidentaMateriiProfesori(Long id, Long materieId, Long profesorId) {
        this.id = id;
        this.materieId = materieId;
        this.profesorId = profesorId;
    }


    //region GET, SET

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMaterieId() {
        return materieId;
    }

    public void setMaterieId(Long materieId) {
        this.materieId = materieId;
    }

    public Long getProfesorId() {
        return profesorId;
    }

    public void setProfesorId(Long profesorId) {
        this.profesorId = profesorId;
    }

    //endregion
}
