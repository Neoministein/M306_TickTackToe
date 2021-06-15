package com.bbb.m306.tictactoe.game;

import com.bbb.m306.tictactoe.PlayerType;

import java.beans.PropertyChangeListener;

public interface GameLogic {

    String NOTIFY_MOVE = "NOTIFY_MOVE";
    String NOTIFY_START = "NOTIFY_START";
    String NOTIFY_END = "NOTIFY_END";

    void startGame();

    boolean playMove(int i, PlayerType type);

    void addPlayer(PropertyChangeListener pcl);

    void removePlayer(PropertyChangeListener pcl);
}
