package game;

import java.util.LinkedList;
import java.util.List;

import javax.swing.text.Position;

import environment.LocalBoard;
import gui.SnakeGui;
import environment.Cell;
import environment.Board;
import environment.BoardPosition;

public class AutomaticSnake extends Snake implements Runnable{
	public AutomaticSnake(int id, LocalBoard board) {
		super(id,board);

	}
	
	
	public int randomMoveSnakeAI(){
		double number = 1 + Math.random()*4;
        System.out.println("NUMERO: " + (int) number);
        return (int) number;
	}
	
	// movimento Y
		public BoardPosition getNextCellY(){
			
			Cell first = cells.getLast();
			BoardPosition currPos = first.getPosition(); 
			BoardPosition goalPos = board.getGoalPosition(); 
			
			//compara os 2 valores e devolve 1,-1 ou 0;
			int dy = Integer.compare(goalPos.y, currPos.y);  
			
			//System.out.println("DY: " + dy);
			
			BoardPosition nextPosition = new BoardPosition(currPos.x , currPos.y + dy);
			return nextPosition;
		}
		
		//movimento X
		public BoardPosition getNextCellX(){
				
				Cell first = cells.getLast();
				BoardPosition currPos = first.getPosition(); 
				BoardPosition goalPos = board.getGoalPosition(); 
				
				//compara os 2 valores e devolve 1,-1 ou 0;
				int dx = Integer.compare(goalPos.x, currPos.x);  
				
				//System.out.println("DX: " + dx);
				BoardPosition nextPosition = new BoardPosition(currPos.x + dx, currPos.y);
				return nextPosition;
				
		}
		
	
	@Override
	public void run() {
		doInitialPositioning();
		//System.err.println("initial size:"+cells.size());
		try {
			
			//cells.getLast().request(this);
			
			while(true){
			
			BoardPosition x = getNextCellY();
			Cell cX = this.getBoard().getCell(x);
			//debug(1);
			move(cX);
			this.getBoard().setChanged();
			
			sleep(200);
			BoardPosition y = getNextCellX();
			Cell cY = this.getBoard().getCell(y);
			//debug(2);
			move(cY);
			
			this.getBoard().setChanged();
		
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		//TODO: automatic movement
	}
	
	public void debug(String n){
		System.out.println("ESTOU AQUI:" + n);
	}
	
}
