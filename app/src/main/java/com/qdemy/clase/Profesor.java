package com.qdemy.clase;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Convert;
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

@Entity
public class Profesor {

    @Id (autoincrement = true) private Long id;
    @Index(unique = true) private String utilizator;
    @NotNull private String nume;
    @NotNull private String prenume;
    @NotNull private String parola;
    @NotNull private String mail;

    @ToMany
    @JoinEntity( entity = EvidentaMateriiProfesori.class, sourceProperty = "profesorId", targetProperty = "materieId")
    private List<Materie> materii;

    @ToMany(referencedJoinProperty = "profesorId")
    private List<IntrebareGrila> intrebari;
    @ToMany(referencedJoinProperty = "profesorId")
    private List<Test> teste;
    @ToMany(referencedJoinProperty = "profesorId")
    private List<TestPartajat> testePartajate;
    @ToMany(referencedJoinProperty = "profesorId")
    private List<TestSustinut> testeSustinute;

    //region Constructori

    public Profesor() {
    }

    public Profesor(String utilizator, String nume, String prenume, String parola, String mail) {
        this.nume = nume;
        this.prenume = prenume;
        this.utilizator = utilizator;
        this.parola = parola;
        this.mail = mail;
        this.materii=null;
        this.intrebari=null;
        this.teste=null;
        testeSustinute=null;
    }

    @Generated(hash = 2145845688)
    public Profesor(Long id, String utilizator, @NotNull String nume, @NotNull String prenume, @NotNull String parola,
            @NotNull String mail) {
        this.id = id;
        this.utilizator = utilizator;
        this.nume = nume;
        this.prenume = prenume;
        this.parola = parola;
        this.mail = mail;
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
    @Generated(hash = 1087356248)
    public List<Materie> getMaterii() {
        if (materii == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MaterieDao targetDao = daoSession.getMaterieDao();
            List<Materie> materiiNew = targetDao._queryProfesor_Materii(id);
            synchronized (this) {
                if (materii == null) {
                    materii = materiiNew;
                }
            }
        }
        return materii;
    }

    public List<String> getNumeMaterii() {

        if(this.materii==null) return null;

        List<String> numeMaterii = new ArrayList<String>();
        for(Materie m : this.materii)
            numeMaterii.add(m.getNume());

        return numeMaterii;
    }

    public Materie getMaterii(int index) {
        return materii.get(index);
    }

    public void setMaterii(List<Materie> materii) {
        this.materii = materii;
    }

    public void setNumeMaterii(List<String> numeMaterii) {

        List<Materie> listaMaterii = new ArrayList<>();
        for ( String m: numeMaterii ) {
            listaMaterii.add(new Materie(m));
        }
        this.materii = listaMaterii;
    }

    public void setMaterii(Materie materie, int index) {
        this.materii.set(index, materie);
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1729116790)
    public List<IntrebareGrila> getIntrebari() {
        if (intrebari == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            IntrebareGrilaDao targetDao = daoSession.getIntrebareGrilaDao();
            List<IntrebareGrila> intrebariNew = targetDao._queryProfesor_Intrebari(id);
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

    public void setIntrebare(IntrebareGrila intrebare) {
        for(int i=0;i<this.intrebari.size();i++)
            if(this.intrebari.get(i).getId()== intrebare.getId())
                this.intrebari.set(i,intrebare);
    }


    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 554129501)
    public List<Test> getTeste() {
        if (teste == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TestDao targetDao = daoSession.getTestDao();
            List<Test> testeNew = targetDao._queryProfesor_Teste(id);
            synchronized (this) {
                if (teste == null) {
                    teste = testeNew;
                }
            }
        }
        return teste;
    }

    public Test getTeste(int index) {
        return teste.get(index);
    }

    public void setTeste(List<Test> teste) {
        this.teste = teste;
    }

    public void setTeste(Test test, int index) {
        this.teste.set(index, test);
    }

    public void setTeste(Test test, String numeTest) {
        for(int i=0;i<teste.size();i++)
            if(teste.get(i).getNume().equals(numeTest))
                this.teste.set(i, test);
    }

    public void setTest (Test test) {
        for(int i=0;i<this.teste.size();i++)
            if(this.teste.get(i).getId()== test.getId())
                this.teste.set(i,test);
    }

    public void setTesteSustinute(List<TestSustinut> testeSustinute) {
        this.testeSustinute = testeSustinute;
    }

    public void setTesteSustinute(TestSustinut testSustinut, int index) {
        this.testeSustinute.set(index, testSustinut);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1812081283)
    public List<TestPartajat> getTestePartajate() {
        if (testePartajate == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TestPartajatDao targetDao = daoSession.getTestPartajatDao();
            List<TestPartajat> testePartajateNew = targetDao._queryProfesor_TestePartajate(id);
            synchronized (this) {
                if (testePartajate == null) {
                    testePartajate = testePartajateNew;
                }
            }
        }
        return testePartajate;
    }

    public TestPartajat getTesteSustinute(int index) {
        return testePartajate.get(index);
    }

    public void setTestePartajate(List<TestPartajat> testePartajate) {
        this.testePartajate = testePartajate;
    }

    public void setTestePartajate(TestPartajat testPartajat, int index) {
        this.testePartajate.set(index, testPartajat);
    }

    //endregion



    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1921261089)
    private transient ProfesorDao myDao;



    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1461820496)
    public synchronized void resetMaterii() {
        materii = null;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1293106293)
    public synchronized void resetIntrebari() {
        intrebari = null;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 2085804097)
    public synchronized void resetTeste() {
        teste = null;
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

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 724840730)
    public synchronized void resetTestePartajate() {
        testePartajate = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1997600704)
    public List<TestSustinut> getTesteSustinute() {
        if (testeSustinute == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TestSustinutDao targetDao = daoSession.getTestSustinutDao();
            List<TestSustinut> testeSustinuteNew = targetDao._queryProfesor_TesteSustinute(id);
            synchronized (this) {
                if (testeSustinute == null) {
                    testeSustinute = testeSustinuteNew;
                }
            }
        }
        return testeSustinute;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1408461956)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getProfesorDao() : null;
    }

    //endregion
}
