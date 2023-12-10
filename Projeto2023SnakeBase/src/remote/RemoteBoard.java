package remote;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.LinkedList;

import javax.swing.JFrame;

import environment.LocalBoard;
import environment.Board;
import environment.BoardPosition;
import environment.Cell;
import game.Goal;
import game.HumanSnake;
import game.Obstacle;
import game.Snake;
import gui.SnakeGui;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import environment.Board;

/** Remote representation of the game, no local threads involved.
 * Game state will be changed when updated info is received from Srver.
 * Only for part II of the project.
 * @author luismota
 *
 */
public class RemoteBoard extends Board{
	
	public HumanSnake player;
	private int playerID;
	
	public RemoteBoard(int i){
		playerID = i;
		init();
	}
	
	private BoardPosition getDirectionFromKey(int key) {
		debug(String.valueOf(key));
        switch (key) {
            case KeyEvent.VK_UP: return new BoardPosition(0, -1); // Para cima
            case KeyEvent.VK_LEFT: return new BoardPosition(-1, 0); // Para esquerda
            case KeyEvent.VK_DOWN: return new BoardPosition(0, 1);  // Para baixo
            case KeyEvent.VK_RIGHT: return new BoardPosition(1, 0);  // Para direita
            default: return new BoardPosition(0, 0); // Nenhuma direção
        }
    }
	
	
	@Override
	public void handleKeyPress(int keyCode) {
		System.out.println("FFFFFFFFFFFFFFF");
		
		
		debug(String.valueOf(keyCode));//TODO
		player.humanMove(getDirectionFromKey(keyCode));		
	}

	@Override
	public void handleKeyRelease() {
		//debug(String.valueOf(keyCode));// TODO
	}
	
	
	private class RemoteBoardKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
        	
            BoardPosition nextPosition = player.getNextCellHuman(getDirectionFromKey(e.getKeyCode()));
            Cell nextCell = getCell(nextPosition); // Agora pode usar diretamente
            if (nextCell != null) {
                try {
					player.move(nextCell);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} // Atualiza a posição da HumanSnake
            }
        }
    }

    @Override
    public void init() {
        player = new HumanSnake(playerID, this); // Cria a HumanSnake
        addSnake(player); // Adiciona a HumanSnake ao tabuleiro
        player.start();
        //setChanged();
        JFrame frame = new JFrame();
        frame.addKeyListener(new RemoteBoardKeyListener());

        frame.setFocusable(true);
        frame.requestFocusInWindow();
        // Configurações adicionais do frame...
    }

    public void debug(String n){
    	 System.out.println("TECLA pressionada: " + n);
	}


    
	

}
