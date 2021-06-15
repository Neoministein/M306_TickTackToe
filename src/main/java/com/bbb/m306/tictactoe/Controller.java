package com.bbb.m306.tictactoe;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

	@FXML
	private Button host_btn;
	@FXML
	private Button join_btn;
	@FXML
	private TextField ip_input;


	@FXML
	public void initialize() {

		host_btn.setOnAction(e -> {
			System.out.println("HOST");
			openWindow("hostScreen.fxml");
		});

		join_btn.setOnAction(e -> {
			System.out.println("JOIN");
			openWindow("gameScreen.fxml");
		});

	}


	private void openWindow(String target) {

		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource(target));

			Scene scene = new Scene(fxmlLoader.load(), 600, 500);
			scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

			Stage stage = new Stage();
			stage.setTitle("TicTacToe");
			stage.setScene(scene);

			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to create new Window.");
		}

	}


}
