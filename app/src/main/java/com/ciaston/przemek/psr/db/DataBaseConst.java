package com.ciaston.przemek.psr.db;

/**
 * Created by Przemek on 2018-01-09.
 */

public class DBConst {

    static final int VERSION = 2;

    static final String DB_NAME = "psr.db";
    public static final String USER_TABLE = "score";
    public static final String ID = "id";
    public static final String PLAYER = "player";
    public static final String WIN = "win" ;
    public static final String LOOSE = "loose";

    static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + USER_TABLE + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PLAYER + " STRING, " +
            WIN + " INTEGER, " +
            LOOSE + " INTEGER;";

    static final String DROP_TABLE = "DROP TABLE IF EXISTS " + DB_NAME;
}
