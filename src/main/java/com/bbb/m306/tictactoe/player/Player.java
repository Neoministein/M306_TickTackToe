package com.bbb.m306.tictactoe.player;

import com.bbb.m306.tictactoe.PlayerType;
import com.bbb.m306.tictactoe.game.GameLogic;

import java.beans.PropertyChangeListener;

public interface Player extends PropertyChangeListener {

    void setGameLogic(GameLogic gameLogic);

    GameLogic getGameLogic();

    PlayerType getPlayerType();

    void setPlayerType(PlayerType playerType);
}
