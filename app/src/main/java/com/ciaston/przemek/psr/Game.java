package com.ciaston.przemek.psr.db;

/**
 * Created by Przemek on 2018-01-09.
 */

public class Game {

    private String nickName;
    private int playerScore;
    private int androidScore;
    private int playerWin;
    private int androidWin;

    public Game() {}

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public int getAndroidScore() {
        return androidScore;
    }

    public void setAndroidScore(int androidScore) {
        this.androidScore = androidScore;
    }

    public int getPlayerWin() {
        return playerWin;
    }

    public void setPlayerWin(int playerWin) {
        this.playerWin = playerWin;
    }

    public int getAndroidWin() {
        return androidWin;
    }

    public void setAndroidWin(int androidWin) {
        this.androidWin = androidWin;
    }
}
