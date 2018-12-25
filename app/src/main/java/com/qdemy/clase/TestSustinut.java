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
public class TestSustinut {

    @Id (autoincrement = true) private Long id;
    @ToMany(referencedJoinProperty = "testSustinutId")
    private List<RezultatTestStudent> rezultate;

    @NotNull private long profesorId;


    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 242359434)
    private transient TestSustinutDao myDao;

    //region Constructori

    public TestSustinut() {}


    @Generated(hash = 666652173)
    public TestSustinut(Long id, long profesorId) {
        this.id = id;
        this.profesorId = profesorId;
    }


    //endregion

    //region GET, SET

    public void setRezultate(List<RezultatTestStudent> rezultate) {
        this.rezultate = rezultate;
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

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1325749065)
    public List<RezultatTestStudent> getRezultate() {
        if (rezultate == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RezultatTestStudentDao targetDao = daoSession.getRezultatTestStudentDao();
            List<RezultatTestStudent> rezultateNew = targetDao._queryTestSustinut_Rezultate(id);
            synchronized (this) {
                if (rezultate == null) {
                    rezultate = rezultateNew;
                }
            }
        }
        return rezultate;
    }


    


    ////////////////////////////////////////////////////////////////////////////////////////////////

    public float getMedia()
    {
//        float media=0;
//        for(int i=0;i<rezultate.size();i++)
//            media+=rezultate.get(i).getPunctajObtinut();
//
//        return media/rezultate.size();
        return 0;
    }

    public long getTestId()
    {
        //preluat din prima inregistare din lista
        return rezultate.get(0).getTestId();
    }

    public String getData()
    {
        //preluat din prima inregistare din lista
        return rezultate.get(0).getData();
    }

    public int getPromovati()
    {
        int promovati=0;
        for(int i=0;i<rezultate.size();i++)
            if(rezultate.get(i).isPromovat()) promovati++;

        return promovati;
    }

//    public int getRaspunsuriCorecte(String nume_intrebare)
//    {
//        int raspunsuriCorecte=0;
//        for(int i=0;i<rezultate.size();i++)
//            if(rezultate.get(i).getRaspunsuri(nume_intrebare).Corect()) raspunsuriCorecte++;
//        return raspunsuriCorecte;
//    }

    public int getNumarStudenti()
    {
        return rezultate.size();
    }


    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1446559007)
    public synchronized void resetRezultate() {
        rezultate = null;
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
    @Generated(hash = 1116706449)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTestSustinutDao() : null;
    }

    //endregion

}
