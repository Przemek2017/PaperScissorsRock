package com.ciaston.przemek.psr.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Przemek on 2018-01-08.
 */

public class DBManager extends SQLiteOpenHelper {

    public DBManager(Context context) {
        super(context, DBConst.DB_NAME, null, DBConst.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBConst.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DBConst.DROP_TABLE);
        onCreate(db);
    }


}
