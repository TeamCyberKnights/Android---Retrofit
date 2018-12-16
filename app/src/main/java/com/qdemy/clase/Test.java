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
public class Test implements Parcelable {

    @Id (autoincrement = true) private Long id;
    @Index(unique = true) private String nume;
    @NotNull private String descriere;
    @NotNull private String autor; //numele din clasa Profesor //!!!!!!!
    @NotNull private int minute;
    @NotNull private Boolean estePublic;

    @ToMany(referencedJoinProperty = "testId")
    private List<IntrebareGrila> intrebari;


    @NotNull private long profesorId;

    //region Constructori

    public Test() {}

    public Test(String nume, String descriere, String autor, List<IntrebareGrila> intrebari, int minute, Boolean estePublic) {
        this.nume = nume;
        this.descriere = descriere;
        this.autor = autor;
        this.intrebari = intrebari;
        this.minute = minute;
        this.estePublic = estePublic;
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

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
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

    public IntrebareGrila getIntrebari(int index) {
        return intrebari.get(index);
    }

    public void setIntrebari(List<IntrebareGrila> intrebari) {
        this.intrebari = intrebari;
    }

    public void setIntrebari(IntrebareGrila intrebare, int index) {
        this.intrebari.set(index, intrebare);
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
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

    //endregion

    //region Parcel

    public static final Creator<Test> CREATOR =
            new Creator<Test>() {
                @Override
                public Test createFromParcel(Parcel parcel) {
                    return new Test(parcel);
                }

                @Override
                public Test[] newArray(int i) {
                    return new Test[i];
                }
            };
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1043090224)
    private transient TestDao myDao;


    public Test(Parcel parcel) {
        this.nume = parcel.readString();
        this.descriere = parcel.readString();
        this.autor = parcel.readString();
        parcel.readList(this.intrebari, getClass().getClassLoader());
        this.minute = parcel.readInt();
        this.estePublic  = (parcel.readInt() == 0) ? false : true;
    }

    @Generated(hash = 1583639366)
    public Test(Long id, String nume, @NotNull String descriere, @NotNull String autor, int minute, @NotNull Boolean estePublic,
            long profesorId) {
        this.id = id;
        this.nume = nume;
        this.descriere = descriere;
        this.autor = autor;
        this.minute = minute;
        this.estePublic = estePublic;
        this.profesorId = profesorId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {

        parcel.writeString(nume);
        parcel.writeString(descriere);
        parcel.writeString(autor);
        parcel.writeList(intrebari);
        parcel.writeInt(minute);
        parcel.writeInt(estePublic ? 1 : 0);
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
