package com.bbb.m306.tictactoe.game;

import com.bbb.m306.tictactoe.PlayerType;

import java.beans.PropertyChangeListener;

public interface GameLogic {

    String NOTIFY_MOVE = "NOTIFY_MOVE";
    String NOTIFY_START_HOST = "NOTIFY_START_HOST";
    String NOTIFY_START_REMOTE = "NOTIFY_START_REMOTE";
    String NOTIFY_END = "NOTIFY_END";

    void startGame();

    void startGame(PlayerType p);

    boolean playMove(int i, PlayerType type, boolean isRemote);

    void addPlayer(PropertyChangeListener pcl);

    void removePlayer(PropertyChangeListener pcl);
}
