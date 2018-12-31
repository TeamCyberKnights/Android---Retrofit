package com.qdemy.clase;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.ArrayList;
import java.util.List;
import org.greenrobot.greendao.DaoException;

@Entity
public class IntrebareGrila {

    @Id (autoincrement = true) private Long id;
    @NotNull private String text;
    @NotNull private String materie;
    @NotNull private double dificultate; // asociat dificultatii: 1-usor, 2-mediu, 3-greu

    @ToMany(referencedJoinProperty = "intrebareId")
    private List<VariantaRaspuns> variante;

    @NotNull private long profesorId;



    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1431844353)
    private transient IntrebareGrilaDao myDao;

    //region Constructori

    public IntrebareGrila() {}

    public IntrebareGrila(String text, String materie, double dificultate, long profesorId) {
        this.text = text;
        this.materie = materie;
        this.dificultate = dificultate;
        this.profesorId = profesorId;
    }

    @Generated(hash = 1455994296)
    public IntrebareGrila(Long id, @NotNull String text, @NotNull String materie, double dificultate,
            long profesorId) {
        this.id = id;
        this.text = text;
        this.materie = materie;
        this.dificultate = dificultate;
        this.profesorId = profesorId;
    }

    //endregion

    //region GET, SET

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMaterie() {
        return materie;
    }

    public void setMaterie(String materie) {
        this.materie = materie;
    }


    public VariantaRaspuns getVariante(int index) {
        return variante.get(index);
    }

    public void setVariante(List<VariantaRaspuns> variante) {
        this.variante = variante;
    }

    public void setVariante(VariantaRaspuns varianta, int index) {
        this.variante.set(index, varianta);
    }

    public double getDificultate() {
        return dificultate;
    }

    public void setDificultate(double punctaj) {
        this.dificultate = punctaj;
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
    @Generated(hash = 1124239623)
    public List<VariantaRaspuns> getVariante() {
        if (variante == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            VariantaRaspunsDao targetDao = daoSession.getVariantaRaspunsDao();
            List<VariantaRaspuns> varianteNew = targetDao._queryIntrebareGrila_Variante(id);
            synchronized (this) {
                if (variante == null) {
                    variante = varianteNew;
                }
            }
        }
        return variante;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 225130175)
    public synchronized void resetVariante() {
        variante = null;
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
    @Generated(hash = 969716315)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getIntrebareGrilaDao() : null;
    }

    //endregion

}
