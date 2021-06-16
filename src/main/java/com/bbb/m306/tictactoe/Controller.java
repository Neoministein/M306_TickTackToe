package com.bbb.m306.tictactoe;

import com.bbb.m306.tictactoe.game.GameLogic;
import com.bbb.m306.tictactoe.game.GameLogicImpl;
import com.bbb.m306.tictactoe.game.GameRules;
import com.bbb.m306.tictactoe.game.GameRulesImpl;
import com.bbb.m306.tictactoe.player.LocalPlayer;
import com.bbb.m306.tictactoe.player.Player;
import com.bbb.m306.tictactoe.player.RemotePlayer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Controller {

	@FXML
	private Button host_btn;
	@FXML
	private Button join_btn;
	@FXML
	private TextField ip_input;

	private GameLogic gameLogic = Singleton.getGameLogic();


	@FXML
	public void initialize() {
 		//gameLogic = new GameLogicImpl(new GameRulesImpl());
		host_btn.setOnAction(e -> host());

		join_btn.setOnAction(e -> join());

	}

	private void host() {
		//GameLogic gameLogic = new GameLogicImpl(new GameRulesImpl());
		try {
			ServerSocket serverSocket = new ServerSocket(5678);
			Socket socket = serverSocket.accept();
			new Thread(new SocketThread(serverSocket),"SocketServer Thread").start();
			//Socket socket = new Socket("127.0.0.1",5678);
			socket.setTcpNoDelay(true);
			socket.setSoTimeout(200);
			Player remotePlayer = new RemotePlayer(PlayerType.O,Singleton.getGameLogic(),socket);
			Player localPlayer = createGameScene();
			localPlayer.setGameLogic(Singleton.getGameLogic());
			localPlayer.setPlayerType(PlayerType.X);
			gameLogic.addPlayer(localPlayer);
			gameLogic.addPlayer(remotePlayer);
			gameLogic.startGame();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private void join() {
		//GameLogic gameLogic = new GameLogicImpl(new GameRulesImpl());
		try {
			Socket socket = new Socket(ip_input.getText(),5678);
			socket.setTcpNoDelay(true);
			socket.setSoTimeout(200);
			if (!socket.isConnected()) {
				throw new IOException();
			}

			Player remotePlayer = new RemotePlayer(PlayerType.X, Singleton.getGameLogic(),socket);
			Player localPlayer = createGameScene();
			remotePlayer.setGameLogic(Singleton.getGameLogic());
			localPlayer.setPlayerType(PlayerType.O);
			Singleton.getGameLogic().addPlayer(localPlayer);
			Singleton.getGameLogic().addPlayer(remotePlayer);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Player createGameScene() {
		LocalPlayer player;
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("gameScreen.fxml"));
			Scene scene = new Scene(fxmlLoader.load(), 600, 500);
			scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

			Stage stage = new Stage();
			stage.setTitle("TicTacToe");
			stage.setScene(scene);

			stage.show();
			player = new LocalPlayer();
			GameController gameController = fxmlLoader.getController();
			gameController.setLocalPlayer(player);
			gameController.init();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return player;
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
