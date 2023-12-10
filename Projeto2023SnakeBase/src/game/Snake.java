package game;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import environment.LocalBoard;
import gui.SnakeGui;
import environment.Board;
import environment.BoardPosition;
import environment.Cell;
import environment.Direction;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.*;
/** Base class for representing Snakes.
 * Will be extended by HumanSnake and AutomaticSnake.
 * Common methods will be defined here.
 * @author luismota
 *
 */
public abstract class Snake extends Thread implements Serializable{
	
  
	
	private static final int DELTA_SIZE = 10;
	protected LinkedList<Cell> cells = new LinkedList<Cell>();
	protected int size = 5;
	private int id;
	public Board board;
	public Lock lock = new ReentrantLock();
	public Cell cell;
	public CountDownLatch endgame = new CountDownLatch(DELTA_SIZE - 5);
	
	public Snake(int id,Board board) {
		this.id = id;
		this.board=board;
	}

	public int getSize() {
		return size;
	}

	public int getIdentification() {
		return id;
	}

	public int getLength() {
		return cells.size();
	}
	public void setLength(int i) {
		cells.size();
	}
	
	public LinkedList<Cell> getCells() {
		return cells;
	}
	

	
	
	public LinkedList<BoardPosition> getPath() {
		LinkedList<BoardPosition> coordinates = new LinkedList<BoardPosition>();
		for (Cell cell : cells) {
			coordinates.add(cell.getPosition());
		}

		return coordinates;
	}	
	
	
	private BoardPosition boardPosInicial(){
		int posX = 0;
		int posY = (int) (Math.random() * Board.NUM_ROWS);
		BoardPosition at = new BoardPosition(posX, posY);
		return at;
	}
	
	public void doInitialPositioning() {
			BoardPosition at = boardPosInicial();
			Cell first = board.getCell(at);
			
			try {
				while(true){
					
					first.request(this);
			
					cells.add(board.getCell(at));
					cell = board.getCell(at);
					board.setChanged();
					
					break;
					
				}
				System.err.println("SAI DO WHILE: COBRA TAMANHO: " + getLength());		

			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
			System.err.println("Snake "+getIdentification()+" starting at: "+ getCells().getLast().getX() + "  " + getCells().getLast().getY());		
		}
	
	
////////////////////////
/// 	MOVIMENTO
////////////////////////

	
	
	public void move(Cell c) throws InterruptedException{
		
		
		if(!c.isOcupied()){ // corrigir o erro de não mover unidirecionalmente
			
			if(c.isOcupiedByGoal())
				captureGoal(c);
				
			c.request(this);
			
			cells.add(c);
			
			if(cells.size() > size){			
			cells.getFirst().release();
			cells.removeFirst();

			
			
			this.getBoard().setChanged();
			sleep(100);
			}
		}
	}
	
	
	public void canMove(Cell c) throws InterruptedException{
		if(!c.isOcupied() || c.isOcupiedByGoal()){	
			canMove(c);
		} else {
			List<BoardPosition> pos = board.getNeighboringPositions(cells.getLast());
			
			for(BoardPosition bp: pos){
				
				if(!board.getCell(bp).isOcupied()){
					canMove(board.getCell(bp));
				}
			}
		}
	}
	/*
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
	*/
	
	
	
////////////////////////
///	 	GOAL
////////////////////////
	
	public void captureGoal(Cell c){
		
		size = this.getLength() + c.getGoal().getValue();
		
		c.removeGoal();
		
		
		BoardPosition bp = board.getRandomPosition();

		this.board.setGoalPosition(bp);
		
		endgame.countDown();
		this.board.setChanged();
		endGame();
		
	}
	public void endGame() {
	    if (endgame.getCount() == 0) {
	        System.exit(0);
	    }
	}	

////////////////////////
///	 	BOTÃO RESET
////////////////////////	
	
	public void resetDirection(){
		 lock.lock(); 
	     try {
	         Cell currentCell = getCells().getLast(); // localizacao
	         List<BoardPosition> neighboringPositions = board.getNeighboringPositions(currentCell);
	
	         // iterador para remover posiçoes ocupadas
	         Iterator<BoardPosition> iterator = neighboringPositions.iterator();
	         while (iterator.hasNext()) {
	             BoardPosition pos = iterator.next();
	             if (board.getCell(pos).isOcupied()) {
	                 iterator.remove(); 
	             }
	         }
	         
	         // 
	         if (!neighboringPositions.isEmpty()) {
	         	int randomIndex = (int) (Math.random() * neighboringPositions.size());
	             BoardPosition newPosition = neighboringPositions.get(randomIndex);
	             Cell newCell = board.getCell(newPosition);
	         	 try {
						move(newCell);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	         }
	         // Verifica se a cobra está bloqueada (sem movimentos válidos)
	         if (neighboringPositions.isEmpty()) {
	            
	         	Iterator<Cell> iteratorRemove = getCells().iterator();
	             while (iterator.hasNext()) {
	                 iteratorRemove.remove();
	             }
	         	doInitialPositioning(); //Refaz o posicionamento inicial da cobra
	            
	         }
	     } finally {
	         lock.unlock(); //unlock da cobra
	     }
	}
	
	
	public Board getBoard() {
		return board;
	}
	
	public Cell getCell(){
		return this.cell;
	}
	
	public void setCell(Cell c){
		this.cell = c;
	}
	
	public void debug(){
		
		try {
			sleep(150);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("A COBRA: "+ id +" CELL OCUPADA");
	}
}
