package com.ciaston.przemek.psr;

/**
 * Created by Przemek on 2018-01-09.
 */

public class Game {

    private int id;
    private String player;
    private int win;
    private int loose;

    public Game(String player, int win, int loose) {
        this.player = player;
        this.win = win;
        this.loose = loose;
    }

    public Game() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLoose() {
        return loose;
    }

    public void setLoose(int loose) {
        this.loose = loose;
    }
}
