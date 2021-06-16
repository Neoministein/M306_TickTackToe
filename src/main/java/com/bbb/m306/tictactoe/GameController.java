package com.bbb.m306.tictactoe;

import com.bbb.m306.tictactoe.game.GameLogic;
import com.bbb.m306.tictactoe.player.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

public class GameController implements Player {

	private GameLogic gameLogic;
	private PlayerType playerType;
	private boolean myTurn = false;

	private List<Button> buttonList;

	public Button btn0;
	public Button btn1;
	public Button btn2;
	public Button btn3;
	public Button btn4;
	public Button btn5;
	public Button btn6;
	public Button btn7;
	public Button btn8;

	@FXML
	private Label player_lbl;


	@FXML
	public void initialize() {
		buttonList = new ArrayList<>();
		buttonList.add(btn0);
		buttonList.add(btn1);
		buttonList.add(btn2);
		buttonList.add(btn3);
		buttonList.add(btn4);
		buttonList.add(btn5);
		buttonList.add(btn6);
		buttonList.add(btn7);

		for (Button button: buttonList) {
			button.setOnAction(e -> buttonPress(((Button) e.getSource()).getId()));
		}

	}

	private void buttonPress(String buttonId) {
		if (myTurn) {
			int id = Integer.valueOf(buttonId.substring(3));
			gameLogic.playMove(id,playerType);
		}

	}

	@Override
	public PlayerType getPlayerType() {
		return playerType;
	}

	public void setPlayerType(PlayerType playerType) {
		this.playerType = playerType;
		player_lbl.setText("Player: " + playerType.toString());
	}

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
				if (myTurn) {
					player_lbl.setText("You Won!");
				} else {
					player_lbl.setText("You lose!");
				}
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
		for (Button button: buttonList) {
			if (("btn" + index).equals(button.getId())) {
				if (myTurn) {
					button.setText(playerType.toString());
				} else {
					button.setText(getOtherPlayerType(playerType).toString());
				}
				myTurn = false;
				return;
			}
		}
	}

	private PlayerType getOtherPlayerType(PlayerType playerType) {
		if (PlayerType.X.equals(playerType)) {
			return PlayerType.O;
		}
		return PlayerType.X;
	}

	public void setGameLogic(GameLogic gameLogic) {
		this.gameLogic = gameLogic;
	}
}
