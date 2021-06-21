package com.bbb.m306.tictactoe.game;

import com.bbb.m306.tictactoe.PlayerType;

/**
 * Implements the game rules that determine if a move is valid or/and a winning move  
 * @author elias
 */
public class GameRulesImpl implements GameRules {

    public boolean isTurnValid(PlayerType[] playingField, int index, PlayerType actingPlayer) {
        if (index < 0) {
            return false;
        } else if (index > playingField.length - 1) {
            return false;
        }
        
        return playingField[index].equals(PlayerType.EMPTY);
    }

    public boolean hasWon(PlayerType[] playingField) {
        boolean hasWon = checkWinHorizontal(playingField);
        hasWon |= checkWinVertical(playingField);
        hasWon |= checkWinBottomLeftTopRight(playingField);
        hasWon |= checkWinTopLeftBottomRight(playingField);
        return hasWon;
    }
    
    private boolean checkWinHorizontal(PlayerType[] playingField) {
        for (int x = 0; x < GameRules.PLAYING_FIELD_HEIGHT; x++) {
            int index = x * GameRules.PLAYING_FIELD_WIDTH;
            boolean hasWon = true;
            PlayerType type = playingField[index];
            if (!type.equals(PlayerType.EMPTY)) {
                for (int y = 1; y < GameRules.PLAYING_FIELD_WIDTH; y++) {
                    if (!playingField[index + y].equals(type)) {
                        hasWon = false;
                    }
                }
                if (hasWon) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean checkWinVertical(PlayerType[] playingField) {
        for (int x = 0; x < GameRules.PLAYING_FIELD_WIDTH; x++) {
            int index = x;
            boolean hasWon = true;
            PlayerType type = playingField[index];
            if (!type.equals(PlayerType.EMPTY)) {
                for (int y = 1; y < GameRules.PLAYING_FIELD_HEIGHT; y++) {
                    if (!playingField[index + y * GameRules.PLAYING_FIELD_WIDTH].equals(type)) {
                        hasWon = false;
                    }
                }
                if (hasWon) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean checkWinTopLeftBottomRight(PlayerType[] playingField) {
        boolean hasWon = true;
        int index = 0;
        PlayerType type = playingField[index];
        if (!type.equals(PlayerType.EMPTY)) {
            for (int x = 0; x < GameRules.PLAYING_FIELD_HEIGHT; x++) {
                index = x * GameRules.PLAYING_FIELD_WIDTH;
                if (!playingField[index + x].equals(type)) {
                    hasWon = false;
                }
            }
            return hasWon;
        }
        return false;
    }
    
    private boolean checkWinBottomLeftTopRight(PlayerType[] playingField) {
        boolean hasWon = true;
        int index = GameRules.PLAYING_FIELD_WIDTH * (GameRules.PLAYING_FIELD_HEIGHT -1);
        PlayerType type = playingField[index];

        if (!type.equals(PlayerType.EMPTY)) {
            if(playingField[4].equals(type) && playingField[2].equals(type)) {
                return hasWon;
            }
            /*
            for (int x = 0; x < GameRules.PLAYING_FIELD_HEIGHT - 1; x++) {
                index = GameRules.PLAYING_FIELD_WIDTH * (GameRules.PLAYING_FIELD_HEIGHT - 1 - x);
                if (!playingField[index + x].equals(type)) {
                    hasWon = false;
                }
            }
            return hasWon;*/
        }

        return false;
    }
}
