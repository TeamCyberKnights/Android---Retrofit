package com.qdemy.db;

import android.app.Application;
import android.content.SharedPreferences;

import com.qdemy.Constante;
import com.qdemy.R;
import com.qdemy.clase.DaoMaster;
import com.qdemy.clase.DaoSession;
import com.qdemy.clase.IntrebareGrila;
import com.qdemy.clase.IntrebareGrilaDao;
import com.qdemy.clase.Materie;
import com.qdemy.clase.Profesor;
import com.qdemy.clase.ProfesorDao;
import com.qdemy.clase.RaspunsIntrebareGrila;
import com.qdemy.clase.RezultatTestStudent;
import com.qdemy.clase.RezultatTestStudentDao;
import com.qdemy.clase.Student;
import com.qdemy.clase.StudentDao;
import com.qdemy.clase.Test;
import com.qdemy.clase.TestDao;

import org.greenrobot.greendao.query.Query;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class App extends Application {

    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        //font
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/ubuntu.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );



        DbOpenHelper helper = new DbOpenHelper(this, "Qdemy.db", null);
        mDaoSession = new DaoMaster(helper.getWritableDatabase()).newSession();
        //mDaoSession.getIntrebareGrilaDao().deleteAll();
        //mDaoSession.getEvidentaMateriiProfesoriDao().deleteAll();
        if(mDaoSession.getProfesorDao().loadAll().size() == 0){
            mDaoSession.getProfesorDao().insert(
                    new Profesor("alex.dita","Dita", "Alexandru","1234", "alexandru.dita@csie.ase.ro"));
            mDaoSession.getProfesorDao().insert(
                    new Profesor("catalin.boja","Boja", "Catalin","1234", "catalin.boja@csie.ase.ro"));
            mDaoSession.getProfesorDao().insert(
                    new Profesor("alin.zamfiroiu","Zamfiroiu", "Alin","1234", "alin.zamfiroiu@csie.ase.ro"));
            mDaoSession.getProfesorDao().insert(
                    new Profesor("bogdan.iancu","Iancu", "Bogdan","1234", "bogdan.iancu@csie.ase.ro"));
        }

        //region Nomenclator materii
        if(getDaoSession().getMaterieDao().queryBuilder().build().list().size()==0) {
            getDaoSession().getMaterieDao().insert(new Materie(getString(R.string.poo)));
            getDaoSession().getMaterieDao().insert(new Materie(getString(R.string.sdd)));
            getDaoSession().getMaterieDao().insert(new Materie(getString(R.string.java)));
            getDaoSession().getMaterieDao().insert(new Materie(getString(R.string.paw)));
            getDaoSession().getMaterieDao().insert(new Materie(getString(R.string.dam)));
            getDaoSession().getMaterieDao().insert(new Materie(getString(R.string.tw)));
        }
        //endregion
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public Profesor getProfesor()
    {
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences(Constante.FISIER_PREFERINTA_UTILIZATOR, MODE_PRIVATE);
        String utilizator = sharedPreferences.getString(getString(R.string.utilizator), "");

        Query<Profesor> query = getDaoSession().getProfesorDao().queryBuilder().where(
                ProfesorDao.Properties.Utilizator.eq(utilizator)).build();

        return query.list().get(0);
    }

    public Student getStudent()
    {
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences(Constante.FISIER_PREFERINTA_UTILIZATOR, MODE_PRIVATE);
        String utilizator = sharedPreferences.getString(getString(R.string.utilizator), "");

        Query<Student> query = getDaoSession().getStudentDao().queryBuilder().where(
                StudentDao.Properties.Utilizator.eq(utilizator)).build();

        return query.list().get(0);
    }

    public IntrebareGrila getIntrebareGrila(long idIntrebare)
    {
        Query<IntrebareGrila> query = getDaoSession().getIntrebareGrilaDao().queryBuilder().where(
                IntrebareGrilaDao.Properties.Id.eq(idIntrebare)).build();
        return query.list().get(0);
    }

    public Test getTest(long idTest)
    {
        Query<Test> query = getDaoSession().getTestDao().queryBuilder().where(
                TestDao.Properties.Id.eq(idTest)).build();
        return query.list().get(0);
    }

    public RezultatTestStudent getRezultatTest(long idRezultatTest)
    {
        Query<RezultatTestStudent> query = getDaoSession().getRezultatTestStudentDao().queryBuilder().where(
                RezultatTestStudentDao.Properties.Id.eq(idRezultatTest)).build();
        return query.list().get(0);
    }


    public float getPunctajTest(List<RaspunsIntrebareGrila> raspunsuri)
    {
        float punctaj = 0;
        float punctajMaxim = 0;
        for (RaspunsIntrebareGrila raspuns: raspunsuri ) {
            punctaj += raspuns.getPunctajObtinut();

            Query<IntrebareGrila> query = getDaoSession().getIntrebareGrilaDao().queryBuilder().where(
                    IntrebareGrilaDao.Properties.Id.eq(raspuns.getIntrebareId())).build();
            punctajMaxim+=query.list().get(0).getDificultate();
        }
        float coef = 100/punctajMaxim;
        return punctaj*coef;
    }

    public Test getTestLive()
    {
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences(Constante.FISIER_PREFERINTA_UTILIZATOR, MODE_PRIVATE);
        long testId = sharedPreferences.getLong(getString(R.string.testLive), -1);

        Query<Test> query = getDaoSession().getTestDao().queryBuilder().where(
                TestDao.Properties.Id.eq(testId)).build();
        return query.list().get(0);
    }

}
