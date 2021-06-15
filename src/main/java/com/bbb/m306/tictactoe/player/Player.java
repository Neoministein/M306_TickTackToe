package com.bbb.m306.tictactoe.player;

import com.bbb.m306.tictactoe.PlayerType;

import java.beans.PropertyChangeListener;

public interface Player extends PropertyChangeListener {

    PlayerType getPlayerType();
}
