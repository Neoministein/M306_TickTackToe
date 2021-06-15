package com.bbb.m306.tictactoe.game;

import com.bbb.m306.tictactoe.PlayerType;

public interface GameRules {

    boolean isTurnValid(PlayerType[] playingField, int index, PlayerType actingPlayer);

    boolean hasWon(PlayerType[] playingFiled);
}