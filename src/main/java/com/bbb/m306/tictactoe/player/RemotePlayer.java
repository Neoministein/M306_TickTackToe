package com.bbb.m306.tictactoe.player;

import java.beans.PropertyChangeEvent;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import com.bbb.m306.tictactoe.PlayerType;
import com.bbb.m306.tictactoe.Singleton;
import com.bbb.m306.tictactoe.game.GameLogic;

public class RemotePlayer implements Player, Runnable {

	private boolean isMyTurn;
	private PlayerType type;
	private volatile GameLogic gameLogic;
	private Socket socket;
	
	private Thread cliThread;
	
	public RemotePlayer(PlayerType type, GameLogic gameLogic, Socket socket) {
		this.isMyTurn = false;
		this.type = type;
		this.gameLogic = Singleton.getGameLogic();
		
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
				//SendToSocket(GameLogic.NOTIFY_START_REMOTE, propertyChangeEvent.getNewValue());
				break;
				
			case GameLogic.NOTIFY_MOVE:
				if (propertyChangeEvent.getOldValue().equals("LOCAL")) {
					this.isMyTurn = !this.isMyTurn;
					SendToSocket(GameLogic.NOTIFY_MOVE, propertyChangeEvent.getNewValue());
				}
				break;
				
			case GameLogic.NOTIFY_END:
				SendToSocket(GameLogic.NOTIFY_END, propertyChangeEvent.getNewValue());
				break;
			default:
				throw new UnsupportedOperationException(String.format("No implementation for property %s found!", propertyChangeEvent.getPropertyName()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override public synchronized void setGameLogic(GameLogic gameLogic) {
		this.gameLogic = gameLogic;
	}

	public PlayerType getPlayerType() {
		return type;
	}

	@Override public void setPlayerType(PlayerType playerType) {
		this.type = playerType;
	}

	private void SendToSocket(String message, Object arg) throws IOException {
		PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
		//DataOutputStream out = new DataOutputStream(this.socket.getOutputStream());
		
		if(arg != null)  {
			String text = String.format("%s:%s", message, arg);
			//out.writeUTF(text);
			out.println(text);
		} else {
			//out.writeUTF(message);
			out.println(message);
		}

		out.flush();
	}
	
	private void handleIncomingMessage(String message) throws InterruptedException {

		String[] messageParts = message.split(":");
		switch (messageParts[0]) {
			case GameLogic.NOTIFY_START_HOST:
				Singleton.getGameLogic().startGame(PlayerType.valueOf(messageParts[1]));
				break;
			case GameLogic.NOTIFY_START_REMOTE:
				Singleton.getGameLogic().startGame();
				break;
				
			case GameLogic.NOTIFY_MOVE:
				this.isMyTurn = !this.isMyTurn;
				Singleton.getGameLogic().playMove(Integer.valueOf(messageParts[1]), this.type, true);
				break;
				
			case GameLogic.NOTIFY_END:
				//Fucking die...
				Singleton.getGameLogic().playMove(Integer.valueOf(messageParts[1]), this.type, true);
				Singleton.getGameLogic().removePlayer(this);
				this.cliThread.join();
				//System.exit(0);
				break;
	
			default:
				throw new UnsupportedOperationException(String.format("No implementation for message %s found!", messageParts[0]));
		}
	}

	@Override
	public void run() {
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(true) {
				try {
					//String inMessage = in.readUTF();
					try {
						String inMessage = in.readLine();
						System.out.println("Message:" + inMessage);
						if(inMessage != null) {
							handleIncomingMessage(inMessage);
						}
					}catch (SocketTimeoutException e) {

					}
					Thread.sleep(1000);
				} catch (IOException ioEx) {
					if (ioEx instanceof SocketException) {
						socket = new Socket("127.0.0.1",5678);
					}
					try {
						in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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

	@Override
	public synchronized GameLogic getGameLogic() {
		return gameLogic;
	}
}
