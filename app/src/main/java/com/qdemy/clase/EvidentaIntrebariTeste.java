package com.qdemy.clase;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class EvidentaIntrebariTeste {

    @Id(autoincrement = true) private Long id;
    private Long intrebareId;
    private Long testId;

    public EvidentaIntrebariTeste() {}

    public EvidentaIntrebariTeste(Long intrebareId, Long testId) {
        this.intrebareId = intrebareId;
        this.testId = testId;
    }

    @Generated(hash = 1126672679)
    public EvidentaIntrebariTeste(Long id, Long intrebareId, Long testId) {
        this.id = id;
        this.intrebareId = intrebareId;
        this.testId = testId;
    }

    //region GET, SET

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIntrebareId() {
        return intrebareId;
    }

    public void setIntrebareId(Long intrebareId) {
        this.intrebareId = intrebareId;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }


    //endregion
}
