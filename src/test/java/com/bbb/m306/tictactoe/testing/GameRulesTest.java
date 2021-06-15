package com.bbb.m306.tictactoe.testing;

import com.bbb.m306.tictactoe.PlayerType;
import com.bbb.m306.tictactoe.game.GameRules;
import com.bbb.m306.tictactoe.game.GameRulesImpl;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elias
 */
public class GameRulesTest {
    
    public PlayerType[] initializePlayingField () {
        PlayerType[] playingField = new PlayerType[GameRules.PLAYING_FIELD_SIZE];
        for (int i = 0; i < playingField.length; i++) {
            playingField[i] = PlayerType.EMPTY;
        }
        
        return playingField;
    }
    
    @Test
    public void testIsTurnValid() {
        PlayerType[] playingField = initializePlayingField();
        GameRules gameRules = new GameRulesImpl();
        int index = 0;
        PlayerType type = PlayerType.O;
        
        assertTrue(gameRules.isTurnValid(playingField, index, type));
    }
    
    @Test
    public void testIsTurnValidFalse() {
        PlayerType[] playingField = initializePlayingField();
        GameRules gameRules = new GameRulesImpl();
        int index = 0;
        PlayerType type = PlayerType.O;
        playingField[index] = type;
        
        assertFalse(gameRules.isTurnValid(playingField, index, type));
    }
    
    @Test
    public void testIsTurnValidOutOfBoundLower() {
        PlayerType[] playingField = initializePlayingField();
        GameRules gameRules = new GameRulesImpl();
        int index = -1;
        PlayerType type = PlayerType.O;
        
        assertFalse(gameRules.isTurnValid(playingField, index, type));
    }
    
    @Test
    public void testIsTurnValidOutOfBoundUpper() {
        PlayerType[] playingField = initializePlayingField();
        GameRules gameRules = new GameRulesImpl();
        int index = GameRules.PLAYING_FIELD_SIZE + 1;
        PlayerType type = PlayerType.O;
        
        assertFalse(gameRules.isTurnValid(playingField, index, type));
    }
    
    @Test
    public void testHasWonHorizontalWin() {
        PlayerType[] playingField = initializePlayingField();
        GameRules gameRules = new GameRulesImpl();
        PlayerType type = PlayerType.O;
        playingField[0] = type;
        playingField[1] = type;
        playingField[2] = type;
        
        assertTrue(gameRules.hasWon(playingField));
    }
    
    @Test
    public void testHasWonVerticalWin() {
        PlayerType[] playingField = initializePlayingField();
        GameRules gameRules = new GameRulesImpl();
        PlayerType type = PlayerType.O;
        playingField[0] = type;
        playingField[3] = type;
        playingField[6] = type;
        
        assertTrue(gameRules.hasWon(playingField));
    }
    
    @Test
    public void testHasWonDiagonalTopLeftToBottomRightWin() {
        PlayerType[] playingField = initializePlayingField();
        GameRules gameRules = new GameRulesImpl();
        PlayerType type = PlayerType.O;
        playingField[0] = type;
        playingField[4] = type;
        playingField[8] = type;
        
        assertTrue(gameRules.hasWon(playingField));
    }
    
    @Test
    public void testHasWonDiagonalBottomLeftTopRightWin() {
        PlayerType[] playingField = initializePlayingField();
        GameRules gameRules = new GameRulesImpl();
        PlayerType type = PlayerType.O;
        playingField[2] = type;
        playingField[4] = type;
        playingField[6] = type;
        
        assertTrue(gameRules.hasWon(playingField));
    }
    
    @Test
    public void testHasWonNoWinOne() {
        PlayerType[] playingField = initializePlayingField();
        GameRules gameRules = new GameRulesImpl();
        PlayerType type = PlayerType.O;
        playingField[1] = type;
        playingField[3] = type;
        playingField[7] = type;
        
        assertFalse(gameRules.hasWon(playingField));
    }
    
    @Test
    public void testHasWonNoWinTwo() {
        PlayerType[] playingField = initializePlayingField();
        GameRules gameRules = new GameRulesImpl();
        PlayerType type = PlayerType.O;
        playingField[6] = type;
        playingField[7] = type;
        playingField[5] = type;
        
        assertFalse(gameRules.hasWon(playingField));
    }
}
