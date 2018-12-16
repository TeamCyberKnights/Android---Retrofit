package com.qdemy.clase;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.greendao.DaoException;

@Entity
public class Student implements Parcelable {

    @Id (autoincrement = true) private Long id;
    @Index(unique = true) private String nume;
    @NotNull private String utilizator;
    @NotNull private String parola;
    @NotNull private String mail;

    @ToMany(referencedJoinProperty = "studentId")
    private List<RezultatTestStudent> testeSustinute;

    //region Constructori

    public Student () {}

    public Student(String nume, String utilizator, String parola, String mail) {
        this.nume = nume;
        this.utilizator = utilizator;
        this.parola = parola;
        this.mail = mail;
        this.testeSustinute = null;
    }

    public Student(String nume, String utilizator, String parola, String mail, List<RezultatTestStudent> testeSustinute) {
        this.nume = nume;
        this.utilizator = utilizator;
        this.parola = parola;
        this.mail = mail;
        this.testeSustinute = testeSustinute;
    }

    //endregion

    //region GET, SET

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUtilizator() {
        return utilizator;
    }

    public void setUtilizator(String utilizator) {
        this.utilizator = utilizator;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 175651899)
    public List<RezultatTestStudent> getTesteSustinute() {
        if (testeSustinute == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RezultatTestStudentDao targetDao = daoSession.getRezultatTestStudentDao();
            List<RezultatTestStudent> testeSustinuteNew = targetDao._queryStudent_TesteSustinute(id);
            synchronized (this) {
                if (testeSustinute == null) {
                    testeSustinute = testeSustinuteNew;
                }
            }
        }
        return testeSustinute;
    }

    public RezultatTestStudent getTesteSustinute(int index) {
        return testeSustinute.get(index);
    }

    public RezultatTestStudent getTesteSustinute(String nume, String data) {

        for (int i=0;i<testeSustinute.size();i++)
            if(testeSustinute.get(i).getNume().equals(nume) &&
               testeSustinute.get(i).getData().equals(data)) return testeSustinute.get(i);

        return null;
    }

    public void setTesteSustinute(List<RezultatTestStudent> testeSustinute) {
        this.testeSustinute = testeSustinute;
    }

    public void setTesteSustinute(RezultatTestStudent testSustinut, int index) {
        this.testeSustinute.set(index, testSustinut);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //endregion


    //region Parcel

    public static final Creator<Student> CREATOR =
            new Creator<Student>() {
                @Override
                public Student createFromParcel(Parcel parcel) {
                    return new Student(parcel);
                }

                @Override
                public Student[] newArray(int i) {
                    return new Student[i];
                }
            };
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1943931642)
    private transient StudentDao myDao;

    private Student(Parcel parcel) {

        this.nume = parcel.readString();
        this.utilizator = parcel.readString();
        this.parola = parcel.readString();
        this.mail = parcel.readString();
        parcel.readList(this.testeSustinute, getClass().getClassLoader());
    }

    @Generated(hash = 562938018)
    public Student(Long id, String nume, @NotNull String utilizator, @NotNull String parola, @NotNull String mail) {
        this.id = id;
        this.nume = nume;
        this.utilizator = utilizator;
        this.parola = parola;
        this.mail = mail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {

        parcel.writeString(nume);
        parcel.writeString(utilizator);
        parcel.writeString(parola);
        parcel.writeString(mail);
        parcel.writeList(testeSustinute);
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 64673083)
    public synchronized void resetTesteSustinute() {
        testeSustinute = null;
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
    @Generated(hash = 1701634981)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getStudentDao() : null;
    }

    //endregion
}
