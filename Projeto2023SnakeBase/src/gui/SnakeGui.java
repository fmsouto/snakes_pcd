package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;

import environment.Board;
import environment.BoardPosition;
import environment.Cell;
import environment.LocalBoard;
import game.Snake;
import remote.RemoteBoard;

/**
 *  Class to create and configure GUI.
 *  Only the listener to the button should be edited, see TODO below.
 * 
 * @author luismota
 *
 */
public class SnakeGui implements Observer {
	public static final int BOARD_WIDTH = 800;
	public static final int BOARD_HEIGHT = 800;
	public static final int NUM_COLUMNS = 40;
	public static final int NUM_ROWS = 30;
	private JFrame frame;
	private BoardComponent boardGui;
	private Board board;

	public SnakeGui(Board board, int x,int y) {
		super();
		this.board=board;
		frame= new JFrame("The Snake Game: "+ (board instanceof LocalBoard?"Local":"Remote"));
		frame.setLocation(x, y);
		buildGui();
	}

	private void buildGui() {

		frame.setLayout(new BorderLayout());
		
		boardGui = new BoardComponent(board); //adiciona o BoardComponent para keys
		boardGui.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
		frame.add(boardGui,BorderLayout.CENTER);
		
		
		JButton resetObstaclesButton=new JButton("Reset snakes' directions");
		
		
		resetObstaclesButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				for (Snake snake : board.getSnakes()) { //lista das cobras
		            snake.resetDirection();
		            boardGui.repaint();
				}
			}
				
		});
		frame.add(resetObstaclesButton,BorderLayout.SOUTH);		
		
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
	}
	
	
	

	public void init() {
		frame.setVisible(true);
		board.addObserver(this);
		board.init();
	}

	@Override
	public void update(Observable o, Object arg) {
		boardGui.repaint();
	}
}
