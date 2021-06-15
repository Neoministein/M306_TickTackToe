package com.bbb.m306.tictactoe.game;

import com.bbb.m306.tictactoe.PlayerType;

public interface GameLogic {

    void startGame();

    boolean playMove(int i, PlayerType type);
}
