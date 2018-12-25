package com.qdemy.clase;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.ArrayList;
import java.util.List;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.ToOne;

@Entity
public class Test {

    @Id (autoincrement = true) private Long id;
    @Index(unique = true) private String nume;
    @NotNull private String descriere;
    @NotNull private int timp_disponibil; //minute
    @NotNull private Boolean estePublic;
    @NotNull private String materie;

    @ToMany(referencedJoinProperty = "testId")
    private List<TestPartajat> testePartajate;

    @ToMany
    @JoinEntity( entity = EvidentaIntrebariTeste.class, sourceProperty = "testId", targetProperty = "intrebareId")
    private List<IntrebareGrila> intrebari;

    @NotNull private long profesorId;



    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1043090224)
    private transient TestDao myDao;
    

    //region Constructori

    public Test() {}

    public Test(String nume, String descriere, int timp_disponibil, Boolean estePublic, String materie, long profesorId) {
        this.nume = nume;
        this.descriere = descriere;
        this.timp_disponibil = timp_disponibil;
        this.estePublic = estePublic;
        this.materie=materie;
        this.profesorId=profesorId;
    }

    @Generated(hash = 1384776144)
    public Test(Long id, String nume, @NotNull String descriere, int timp_disponibil, @NotNull Boolean estePublic,
            @NotNull String materie, long profesorId) {
        this.id = id;
        this.nume = nume;
        this.descriere = descriere;
        this.timp_disponibil = timp_disponibil;
        this.estePublic = estePublic;
        this.materie = materie;
        this.profesorId = profesorId;
    }

    

    //endregion

    //region GET, SET

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }


    public IntrebareGrila getIntrebari(int index) {
        return intrebari.get(index);
    }

    public void setIntrebari(List<IntrebareGrila> intrebari) {
        this.intrebari = intrebari;
    }

    public void setIntrebari(IntrebareGrila intrebare, int index) {
        this.intrebari.set(index, intrebare);
    }

    public int getTimpDisponibil() {
        return timp_disponibil;
    }

    public void setTimpDisponibil(int minute) {
        this.timp_disponibil = minute;
    }

    public Boolean getEstePublic() {
        return estePublic;
    }

    public void setEstePublic(Boolean estePublic) {
        this.estePublic = estePublic;
    }

    public long getProfesorId() {
        return profesorId;
    }

    public void setProfesorId(long profesorId) {
        this.profesorId = profesorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTimp_disponibil() {
        return this.timp_disponibil;
    }

    public void setTimp_disponibil(int timp_disponibil) {
        this.timp_disponibil = timp_disponibil;
    }

    public String getMaterie() {
        return materie;
    }

    public void setMaterie(String materie) {
        this.materie = materie;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 970963607)
    public List<TestPartajat> getTestePartajate() {
        if (testePartajate == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TestPartajatDao targetDao = daoSession.getTestPartajatDao();
            List<TestPartajat> testePartajateNew = targetDao._queryTest_TestePartajate(id);
            synchronized (this) {
                if (testePartajate == null) {
                    testePartajate = testePartajateNew;
                }
            }
        }
        return testePartajate;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 724840730)
    public synchronized void resetTestePartajate() {
        testePartajate = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 869514377)
    public List<IntrebareGrila> getIntrebari() {
        if (intrebari == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            IntrebareGrilaDao targetDao = daoSession.getIntrebareGrilaDao();
            List<IntrebareGrila> intrebariNew = targetDao._queryTest_Intrebari(id);
            synchronized (this) {
                if (intrebari == null) {
                    intrebari = intrebariNew;
                }
            }
        }
        return intrebari;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1293106293)
    public synchronized void resetIntrebari() {
        intrebari = null;
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
    @Generated(hash = 670123039)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTestDao() : null;
    }

    //endregion

}
