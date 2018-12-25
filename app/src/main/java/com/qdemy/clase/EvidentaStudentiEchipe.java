package com.qdemy.clase;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class EvidentaStudentiEchipe {

    @Id(autoincrement = true) private Long id;
    private Long echipaId;
    private Long studentId;

    public EvidentaStudentiEchipe() {}

    @Generated(hash = 1536100022)
    public EvidentaStudentiEchipe(Long id, Long echipaId, Long studentId) {
        this.id = id;
        this.echipaId = echipaId;
        this.studentId = studentId;
    }

    //region GET, SET

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEchipaId() {
        return echipaId;
    }

    public void setEchipaId(Long echipaId) {
        this.echipaId = echipaId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }


    //endregion
}
