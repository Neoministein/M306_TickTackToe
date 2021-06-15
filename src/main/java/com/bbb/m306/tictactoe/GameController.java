package com.bbb.m306.tictactoe;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class GameController {

	@FXML
	private Label player_lbl;


	@FXML
	public void initialize() {

		player_lbl.setText("Player: X");

	}



}
