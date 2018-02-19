package com.ciaston.przemek.psr.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ciaston.przemek.psr.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Przemek on 2018-01-08.
 */

public class DataBaseManager extends SQLiteOpenHelper {

    public DataBaseManager(Context context) {
        super(context, DataBaseConst.DB_NAME, null, DataBaseConst.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBaseConst.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DataBaseConst.DROP_TABLE);
        onCreate(db);
    }

    public boolean insertData(Game game) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues insertValue = new ContentValues();
        insertValue.put(DataBaseConst.PLAYER, game.getPlayer());
        insertValue.put(DataBaseConst.WIN, game.getWin());
        insertValue.put(DataBaseConst.LOOSE, game.getLoose());
        long result = database.insert(DataBaseConst.USER_TABLE, null, insertValue);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public List<String> getPlayers() {
        String query = "SELECT DISTINCT " + DataBaseConst.PLAYER + " FROM " + DataBaseConst.USER_TABLE;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        //Game game;

        List<String> playerList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String player = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConst.PLAYER));
                playerList.add(player);
            }
        }
        database.close();
        return playerList;
    }

    public List<Game> getArrayList() {
        List<Game> gameArrayList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT " + DataBaseConst.ID + ", "
                + DataBaseConst.PLAYER + ", "
                + "SUM(" + DataBaseConst.WIN + ") AS " + DataBaseConst.WIN + ", "
                + "SUM(" + DataBaseConst.LOOSE + ") AS " + DataBaseConst.LOOSE
                + "FROM " + DataBaseConst.USER_TABLE
                + " GROUP BY " + DataBaseConst.PLAYER
                + " ORDER BY " + DataBaseConst.WIN + " DESC, "
                + DataBaseConst.LOOSE + " ASC";
        Cursor cursor = database.rawQuery(query, null);
        Game game;

        while (cursor.moveToNext()) {

            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseConst.ID));
            String player = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConst.PLAYER));
            int win = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseConst.WIN));
            int loose = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseConst.LOOSE));

            game = new Game();
            game.setId(id);
            game.setPlayer(player);
            game.setWin(win);
            game.setLoose(loose);

            gameArrayList.add(game);
        }
        return gameArrayList;
    }


    public void insertRandomGame() {
        insertData(new Game("Przemek", 1, 0));
        insertData(new Game("Przemek", 0, 1));
        insertData(new Game("Julia", 1, 0));
        insertData(new Game("Julia", 1, 0));
        insertData(new Game("Magda", 1, 0));
        insertData(new Game("Magda", 0, 1));
    }

}
