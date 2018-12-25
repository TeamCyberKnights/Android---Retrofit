package com.qdemy.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.qdemy.clase.DaoMaster;
import com.qdemy.clase.EchipaDao;
import com.qdemy.clase.EvidentaIntrebariTesteDao;
import com.qdemy.clase.EvidentaMateriiProfesori;
import com.qdemy.clase.EvidentaMateriiProfesoriDao;
import com.qdemy.clase.EvidentaStudentiEchipeDao;
import com.qdemy.clase.IntrebareGrilaDao;
import com.qdemy.clase.MaterieDao;
import com.qdemy.clase.ProfesorDao;
import com.qdemy.clase.RaspunsIntrebareGrilaDao;
import com.qdemy.clase.RezultatTestStudentDao;
import com.qdemy.clase.StudentDao;
import com.qdemy.clase.TestDao;
import com.qdemy.clase.TestPartajatDao;
import com.qdemy.clase.TestSustinutDao;
import com.qdemy.clase.VariantaRaspunsDao;

import org.greenrobot.greendao.database.Database;

public class DbOpenHelper extends DaoMaster.OpenHelper {

//    public DbOpenHelper(Context context, String name) {
//        super(context, name);
//    }
//
//    @Override
//    public void onUpgrade(Database db, int oldVersion, int newVersion) {
//        super.onUpgrade(db, oldVersion, newVersion);
//        Log.d("DEBUG", "DB_OLD_VERSION : " + oldVersion + ", DB_NEW_VERSION : " + newVersion);
////        switch (oldVersion) {
////            case 1:
////            case 2:
////                //db.execSQL("ALTER TABLE " + UserDao.TABLENAME + " ADD COLUMN " + UserDao.Properties.Name.columnName + " TEXT DEFAULT 'DEFAULT_VAL'");
////        }
//    }

    public DbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
            }
            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
            }
        },EchipaDao.class, EvidentaMateriiProfesoriDao.class, EvidentaIntrebariTesteDao.class, EvidentaStudentiEchipeDao.class,
          IntrebareGrilaDao.class, MaterieDao.class, ProfesorDao.class, RaspunsIntrebareGrilaDao.class, RezultatTestStudentDao.class,
                StudentDao.class, TestDao.class, TestPartajatDao.class, TestSustinutDao.class, VariantaRaspunsDao.class);
    }


}
