package game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import environment.LocalBoard;
import gui.SnakeGui;
import remote.RemoteBoard;

public class Server {
	
	private ServerSocket serverSocket;
	private volatile boolean isRunning;
	public ArrayList<GameElement> elements = new ArrayList<>();
	
	
	public void startServer(int port) {
			
		 	isRunning = true;
		 	
	        try {
	            serverSocket = new ServerSocket(port);
	            System.out.println("Servidor iniciado na porta " + port);
	
	            while (true) {
	                Socket clientSocket = serverSocket.accept();
	              
	                
	                System.out.println("Cliente conectado: " + clientSocket.getInetAddress().getHostAddress());
	                start();
	            }
	        } catch (IOException e) {
	        	if (!isRunning) {
	                System.out.println("Servidor parado.");
	            } else {
	                e.printStackTrace();
	            }
	        }
	 }
	 
	 public void stopServer() {
		    isRunning = false;
		    
		    if (serverSocket != null && !serverSocket.isClosed()) {
		        try {
		            serverSocket.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		}
	 
	 public void start() {
			LocalBoard board = new LocalBoard();
			SnakeGui game = new SnakeGui(board,600,0);
			
			RemoteBoard remoteBoard = new RemoteBoard(100);
			SnakeGui remoteGame = new SnakeGui(remoteBoard,600,0);
			game.init();
			remoteGame.init();
			// TODO 
			
		}
	// TODO
}
