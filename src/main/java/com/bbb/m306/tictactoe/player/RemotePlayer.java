package com.bbb.m306.tictactoe.player;

import java.beans.PropertyChangeEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.bbb.m306.tictactoe.PlayerType;
import com.bbb.m306.tictactoe.game.GameLogic;

public class RemotePlayer implements Player, Runnable {

	private boolean isMyTurn;
	private PlayerType type;
	private GameLogic gameLogic;
	private Socket socket;
	
	private Thread cliThread;
	
	public RemotePlayer(PlayerType type, GameLogic gameLogic, Socket socket) {
		this.isMyTurn = false;
		this.type = type;
		this.gameLogic = gameLogic;
		
		this.socket = socket;
		
		this.cliThread = new Thread(this, "RemotePlayer");
		this.cliThread.start();
	}
	
	public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
		try {
			switch (propertyChangeEvent.getPropertyName()) {
			case GameLogic.NOTIFY_START_HOST:
				SendToSocket(GameLogic.NOTIFY_START_HOST, propertyChangeEvent.getNewValue());
				break;
				
			case GameLogic.NOTIFY_START_REMOTE:
				SendToSocket(GameLogic.NOTIFY_START_REMOTE, propertyChangeEvent.getNewValue());
				break;
				
			case GameLogic.NOTIFY_MOVE:
				this.isMyTurn = !this.isMyTurn;
				SendToSocket(GameLogic.NOTIFY_MOVE, propertyChangeEvent.getNewValue());
				break;
				
			case GameLogic.NOTIFY_END:
				SendToSocket(GameLogic.NOTIFY_END, propertyChangeEvent.getNewValue());

			default:
				throw new UnsupportedOperationException(String.format("No implementation for property %s found!", propertyChangeEvent.getPropertyName()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PlayerType getPlayerType() {
		return type;
	}
	
	private void SendToSocket(String message, Object arg) throws IOException {
		DataOutputStream out = new DataOutputStream(this.socket.getOutputStream());
		
		if(arg != null) {
			out.writeUTF(String.format("%s:%s", message, arg));
		} else {
			out.writeUTF(message);
		}
		
		out.close();
	}
	
	private void HandleIncomingMessage(String message) throws InterruptedException {

		String[] messageParts = message.split(":");
		switch (messageParts[0]) {
			case GameLogic.NOTIFY_START_HOST:
				gameLogic.startGame();
				break;
			case GameLogic.NOTIFY_START_REMOTE:
				gameLogic.startGame(PlayerType.valueOf(messageParts[1]));
				break;
				
			case GameLogic.NOTIFY_MOVE:
				this.isMyTurn = !this.isMyTurn;
				gameLogic.playMove(Integer.getInteger(messageParts[1]), this.type);
				break;
				
			case GameLogic.NOTIFY_END:
				//Fucking die...
				gameLogic.playMove(Integer.getInteger(messageParts[1]), this.type);
				gameLogic.removePlayer(this);
				this.cliThread.join();
				System.exit(0);
				break;
	
			default:
				throw new UnsupportedOperationException(String.format("No implementation for message %s found!", messageParts[0]));
		}
	}

	@Override
	public void run() {
		DataInputStream in;
		try {
			in = new DataInputStream(socket.getInputStream());
			while(true) {
				try {
					String inMessage = in.readUTF();
					
					if(inMessage != null && isMyTurn) {
						HandleIncomingMessage(inMessage);
					}
					
					Thread.sleep(100);
				} catch (IOException ioEx) {
					try {
						in = new DataInputStream(socket.getInputStream());
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
