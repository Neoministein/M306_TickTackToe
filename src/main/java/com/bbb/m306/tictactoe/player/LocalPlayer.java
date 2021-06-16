package com.bbb.m306.tictactoe.player;

import com.bbb.m306.tictactoe.PlayerType;
import com.bbb.m306.tictactoe.Singleton;
import com.bbb.m306.tictactoe.game.GameLogic;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.util.HashMap;
import java.util.Map;

public class LocalPlayer implements Player {

    private PlayerType playerType;
    private boolean myTurn;


    private StringProperty playerLB = new SimpleStringProperty();
    private Map<Integer, StringProperty> buttonTextMap = new HashMap<>();

    public LocalPlayer() {
        for(int i = 0; i < 9;i ++) {
            buttonTextMap.put(i,new SimpleStringProperty());
        }
    }

    @Override
    public void setGameLogic(GameLogic gameLogic) {

    }

    @Override
    public GameLogic getGameLogic() {
        return null;
    }

    @Override
    public PlayerType getPlayerType() {
        return playerType;
    }

    @Override
    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
        playerLB.setValue("Player: " + playerType.toString());
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        try {
            switch (propertyChangeEvent.getPropertyName()) {
            case GameLogic.NOTIFY_START_HOST:
            case GameLogic.NOTIFY_START_REMOTE:
                PlayerType startType = (PlayerType) propertyChangeEvent.getNewValue();

                if (playerType.equals(startType)) {
                    myTurn = true;
                }
                break;

            case GameLogic.NOTIFY_MOVE:
                updateDisplay((int) propertyChangeEvent.getNewValue());
                break;

            case GameLogic.NOTIFY_END:
                updateDisplay((int) propertyChangeEvent.getNewValue());
                updateWin();
                break;
            default:
                throw new UnsupportedOperationException(String.format("No implementation for property %s found!",
                        propertyChangeEvent.getPropertyName()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateDisplay(int index) {
        Platform.runLater(() -> {
            StringProperty stringProperty = buttonTextMap.get(index);
            if (myTurn) {
                stringProperty.setValue(playerType.toString());
            } else {
                stringProperty.setValue(getOtherPlayerType(playerType).toString());
            }
            myTurn = !myTurn;
        });

    }

    private void updateWin() {
        Platform.runLater(() -> {
            if (!myTurn) {
                playerLB.setValue("You Won!");
            } else {
                playerLB.setValue("You lose!");
            }
            myTurn = false;
        });

    }

    private PlayerType getOtherPlayerType(PlayerType playerType) {
        if (PlayerType.X.equals(playerType)) {
            return PlayerType.O;
        }
        return PlayerType.X;
    }

    public StringProperty getPlayerLB() {
        return playerLB;
    }

    public StringProperty getButtonProperty(int index) {
        return buttonTextMap.get(index);
    }

    public void buttonHasBeenPressed(int i) {
        if (myTurn) {
            Singleton.getGameLogic().playMove(i, playerType, false);
        }
    }
}
