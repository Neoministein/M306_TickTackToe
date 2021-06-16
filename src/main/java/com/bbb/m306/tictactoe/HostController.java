package com.bbb.m306.tictactoe;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HostController {

	@FXML
	private Button cancel_btn;
	@FXML
	private Label ip_lbl;

	@FXML
	public void initialize() {

		// TODO: Get Own IP
		ip_lbl.setText("127.0.0.1");

		cancel_btn.setOnAction(e -> ((Stage)(((Button)e.getSource()).getScene().getWindow())).close());

	}

}
