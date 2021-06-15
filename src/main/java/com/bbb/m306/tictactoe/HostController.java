package com.bbb.m306.tictactoe;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class HostController {

	@FXML
	private Button cancel_btn;
	@FXML
	private Label ip_lbl;

	@FXML
	public void initialize() {

		// TODO: Get Own IP
		ip_lbl.setText("192.168.1.1");
		System.out.println("JADADAD");

	}

}
