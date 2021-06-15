package com.bbb.m306.tictactoe.game;

import com.bbb.m306.tictactoe.PlayerType;

public interface GameRules {
    int PLAYING_FIELD_WIDTH = 3;
    int PLAYING_FIELD_HEIGHT = 3;
    int PLAYING_FIELD_SIZE = PLAYING_FIELD_WIDTH * PLAYING_FIELD_HEIGHT;

    boolean isTurnValid(PlayerType[] playingField, int index, PlayerType actingPlayer);

    boolean hasWon(PlayerType[] playingField);
}