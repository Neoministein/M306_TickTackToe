package com.bbb.m306.tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("startScreen.fxml"));
		root.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		primaryStage.setTitle("TicTacToe");
		primaryStage.setScene(new Scene(root, 600, 500));

		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}
}
