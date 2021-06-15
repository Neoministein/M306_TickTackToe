package com.bbb.m306.tictactoe;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controller {

	@FXML
	private Button host_btn;

	@FXML
	public void initialize() {
		host_btn.setOnAction(e -> {
			System.out.println("Jada");
		});
	}

}
