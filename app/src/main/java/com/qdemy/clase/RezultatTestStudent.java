package com.qdemy.clase;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.ArrayList;
import java.util.List;
import org.greenrobot.greendao.DaoException;

@Entity
public class RezultatTestStudent {

    @Id (autoincrement = true) private Long id;
    @NotNull private long testId;
    @NotNull private String data;
    //private float punctajObtinut; //PUNCTAJ RELATIV LA 100  //punctajul tuturor intrebarilor * coef obtinut prin impartirea 100/punctaj maxim posibil
    //de facut metoda pentru calculat punctajulObtinut (in loc de campul asta) !!!!
    @NotNull private int minuteTrecute;
    @NotNull private boolean promovat;

    @ToMany(referencedJoinProperty = "rezultatTestStudentId")
    private List<RaspunsIntrebareGrila> raspunsuri;


    @NotNull private long testSustinutId;
    @NotNull private long studentId;

    //region Constructori

    public RezultatTestStudent() {}

    public RezultatTestStudent(long testId, String data, int minuteTrecute, List<RaspunsIntrebareGrila> raspunsuri,
                               boolean promovat, long studentId) {
        this.testId = testId;
        this.data = data;
        this.minuteTrecute = minuteTrecute;
        this.promovat=promovat;
        this.raspunsuri = raspunsuri;
        this.studentId = studentId;
    }

    @Generated(hash = 1291890339)
    public RezultatTestStudent(Long id, long testId, @NotNull String data, int minuteTrecute, boolean promovat, long testSustinutId, long studentId) {
        this.id = id;
        this.testId = testId;
        this.data = data;
        this.minuteTrecute = minuteTrecute;
        this.promovat = promovat;
        this.testSustinutId = testSustinutId;
        this.studentId = studentId;
    }

    //endregion

    //region GET, SET

    public long getTestId() {
        return testId;
    }

    public void setTestId(long testId) {
        this.testId = testId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getMinuteTrecute() {
        return minuteTrecute;
    }

    public void setMinuteTrecute(int minuteTrecute) {
        this.minuteTrecute = minuteTrecute;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1933906260)
    public List<RaspunsIntrebareGrila> getRaspunsuri() {
        if (raspunsuri == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RaspunsIntrebareGrilaDao targetDao = daoSession.getRaspunsIntrebareGrilaDao();
            List<RaspunsIntrebareGrila> raspunsuriNew = targetDao._queryRezultatTestStudent_Raspunsuri(id);
            synchronized (this) {
                if (raspunsuri == null) {
                    raspunsuri = raspunsuriNew;
                }
            }
        }
        return raspunsuri;
    }

    public RaspunsIntrebareGrila getRaspunsuri(int index) {
        return raspunsuri.get(index);
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getTestSustinutId() {
        return testSustinutId;
    }

    public void setTestSustinutId(long testSustinutId) {
        this.testSustinutId = testSustinutId;
    }

    public boolean getPromovat() {
        return this.promovat;
    }


    //METODA CALCULARE PUNCTAJ RAPORTAT LA 100

    //endregion



    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1404480056)
    private transient RezultatTestStudentDao myDao;




    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 527297346)
    public synchronized void resetRaspunsuri() {
        raspunsuri = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1181539833)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRezultatTestStudentDao() : null;
    }

    //endregion

}
