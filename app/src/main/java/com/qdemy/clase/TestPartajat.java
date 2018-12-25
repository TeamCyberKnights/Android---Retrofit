package com.qdemy.clase;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class TestPartajat {

    @Id(autoincrement = true) private Long id;
    @NotNull private long testId;
    @NotNull private long profesorId;

    public TestPartajat() {}

    public TestPartajat(long testId, long profesorId) {
        this.testId=testId;
        this.profesorId=profesorId;
    }

    @Generated(hash = 1258018466)
    public TestPartajat(Long id, long testId, long profesorId) {
        this.id = id;
        this.testId = testId;
        this.profesorId = profesorId;
    }

    //region GET, SET

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTestId() {
        return testId;
    }

    public void setTestId(long testId) {
        this.testId = testId;
    }

    public long getProfesorId() {
        return profesorId;
    }

    public void setProfesorId(long profesorId) {
        this.profesorId = profesorId;
    }

    //endregion
}
