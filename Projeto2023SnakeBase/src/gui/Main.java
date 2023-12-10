package gui;

import java.io.Console;
import java.io.IOException;

import javax.net.ssl.StandardConstants;

import environment.LocalBoard;
import game.Server;
import remote.RemoteBoard;

public class Main {
	public static void main(String[] args) {
		
		//LocalBoard board=new LocalBoard();
		Server server = new Server();
		server.startServer(12345);
		//SnakeGui game = new SnakeGui(board,600,0);
		//game.init();
		// Launch server
		// TODO
		
	}
}
