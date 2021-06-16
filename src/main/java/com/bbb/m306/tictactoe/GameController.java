package com.bbb.m306.tictactoe;

import com.bbb.m306.tictactoe.game.GameLogic;
import com.bbb.m306.tictactoe.player.LocalPlayer;
import com.bbb.m306.tictactoe.player.Player;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

public class GameController {

	private LocalPlayer localPlayer;

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

	public void init() {
		player_lbl.textProperty().bind(localPlayer.getPlayerLB());

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
			button.textProperty().bind(localPlayer.getButtonProperty(getButtonId(button.getId())));
		}

	}

	private int getButtonId(String buttonId) {
		return Integer.valueOf(buttonId.substring(3));
	}

	private void buttonPress(String buttonId) {
		localPlayer.buttonHasBeenPressed(getButtonId(buttonId));
	}

	public LocalPlayer getLocalPlayer() {
		return localPlayer;
	}

	public void setLocalPlayer(LocalPlayer localPlayer) {
		this.localPlayer = localPlayer;
	}
}
