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
public class IntrebareGrila implements Parcelable {

    @Id (autoincrement = true) private Long id;
    @Index(unique = true) private String nume;
    @NotNull private String continut;
    @NotNull private String materie;
    @NotNull private double punctaj; // asociat dificultatii: 1-usor, 2-mediu, 3-greu

    @ToMany(referencedJoinProperty = "intrebareId")
    private List<VariantaRaspuns> variante;


    @NotNull private long profesorId;
    @NotNull private long testId;

    //region Constructori

    public IntrebareGrila() {}

    public IntrebareGrila(String nume, String continut, String materie, List<VariantaRaspuns> variante, double punctaj) {
        this.nume = nume;
        this.continut = continut;
        this.materie = materie;
        this.variante = variante;
        this.punctaj = punctaj;
    }

    //endregion

    //region GET, SET

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getContinut() {
        return continut;
    }

    public void setContinut(String continut) {
        this.continut = continut;
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

    public VariantaRaspuns getVariante(int index) {
        return variante.get(index);
    }

    public void setVariante(List<VariantaRaspuns> variante) {
        this.variante = variante;
    }

    public void setVariante(VariantaRaspuns varianta, int index) {
        this.variante.set(index, varianta);
    }

    public double getPunctaj() {
        return punctaj;
    }

    public void setPunctaj(double punctaj) {
        this.punctaj = punctaj;
    }

    public long getProfesorId() {
        return profesorId;
    }

    public void setProfesorId(long profesorId) {
        this.profesorId = profesorId;
    }

    public long getTestId() {
        return testId;
    }

    public void setTestId(long testId) {
        this.testId = testId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //endregion

    //region Parcel

    public static final Creator<IntrebareGrila> CREATOR =
            new Creator<IntrebareGrila>() {
                @Override
                public IntrebareGrila createFromParcel(Parcel parcel) {
                    return new IntrebareGrila(parcel);
                }

                @Override
                public IntrebareGrila[] newArray(int i) {
                    return new IntrebareGrila[i];
                }
            };
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1431844353)
    private transient IntrebareGrilaDao myDao;


    public IntrebareGrila(Parcel parcel) {
        this.nume = parcel.readString();
        this.continut = parcel.readString();
        this.materie = parcel.readString();
        parcel.readList(this.variante, getClass().getClassLoader());
        this.punctaj = parcel.readDouble();
    }

    @Generated(hash = 932140341)
    public IntrebareGrila(Long id, String nume, @NotNull String continut, @NotNull String materie, double punctaj,
            long profesorId, long testId) {
        this.id = id;
        this.nume = nume;
        this.continut = continut;
        this.materie = materie;
        this.punctaj = punctaj;
        this.profesorId = profesorId;
        this.testId = testId;
    }

//    @Generated(hash = 932140341)
//    public IntrebareGrila(Long id, String nume, @NotNull String continut, @NotNull String materie, double punctaj,
//            long profesorId, long testId) {
//        this.id = id;
//        this.nume = nume;
//        this.continut = continut;
//        this.materie = materie;
//        this.punctaj = punctaj;
//        this.profesorId = profesorId;
//        this.testId = testId;
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {

        parcel.writeString(nume);
        parcel.writeString(continut);
        parcel.writeString(materie);
        parcel.writeList(variante);
        parcel.writeDouble(punctaj);
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
