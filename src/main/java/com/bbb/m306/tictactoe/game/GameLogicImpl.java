package com.bbb.m306.tictactoe.game;

import com.bbb.m306.tictactoe.PlayerType;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Random;

public class GameLogicImpl implements GameLogic {

    private GameRules gameRules;
    private PlayerType[] playingField;

    protected final PropertyChangeSupport changes = new PropertyChangeSupport(this);

    public GameLogicImpl(GameRules gameRules) {
        this.playingField = new PlayerType[GameRules.PLAYING_FIELD_SIZE];
        this.gameRules = gameRules;
    }

    public void startGame() {
        changes.firePropertyChange(GameLogic.NOTIFY_START_HOST,null, choseFirstPlayer());
    }

    public void startGame(PlayerType playerType) {
        changes.firePropertyChange(GameLogic.NOTIFY_START_REMOTE,null, playerType);
    }

    public boolean playMove(int i, PlayerType type) {
        if (!gameRules.isTurnValid(playingField, i, type)) {
            return false;
        }
        if(gameRules.hasWon(playingField)) {
            changes.firePropertyChange(GameLogic.NOTIFY_END, null, i);
            return true;
        }
        changes.firePropertyChange(GameLogic.NOTIFY_MOVE, null, i);
        return true;
    }

    public void addPlayer(PropertyChangeListener pcl) {
        changes.addPropertyChangeListener(pcl);
    }

    public void removePlayer(PropertyChangeListener pcl) {
        changes.removePropertyChangeListener(pcl);
    }

    private PlayerType choseFirstPlayer() {
        if (new Random().nextBoolean()) {
          return PlayerType.X;
        }
        return PlayerType.O;
    }
}
