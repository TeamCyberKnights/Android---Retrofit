package com.qdemy.db;

import android.app.Application;

import com.qdemy.clase.DaoMaster;
import com.qdemy.clase.DaoSession;
import com.qdemy.clase.Profesor;
import com.qdemy.clase.ProfesorDao;

public class App extends Application {

    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mDaoSession =
                new DaoMaster(new DbOpenHelper(this, "Qdemy.db").getWritableDb()).newSession();

        if(mDaoSession.getProfesorDao().loadAll().size() == 2){
            //mDaoSession.deleteAll(ProfesorDao.class);
            mDaoSession.getProfesorDao().insert(
                    new Profesor("Dita Alexandru", "alex.dita","1234", "alexandru.dita@csie.ase.ro"));
        }
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

}
