package game;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import environment.Board;
import environment.BoardPosition;
import environment.Cell;
import environment.LocalBoard;

public class Obstacle extends GameElement implements Runnable {
	
	
	private static final int NUM_MOVES=3;
	private static final int OBSTACLE_MOVE_INTERVAL = 400;
	private int remainingMoves=NUM_MOVES;
	private Board board;
	public Lock lock = new ReentrantLock();
	public BoardPosition currentPos;
	private boolean isMoving = false;
	
	public Obstacle(Board board) {
		super();
		this.board = board;
	}
	
	public int getRemainingMoves() {
		return remainingMoves;
	}
	public void decrementMoves() {
		remainingMoves--;
	}
	public void setObstaclePosition(BoardPosition bp){
		currentPos = bp;
	}
	
	public BoardPosition getCurrentPos(){
		return currentPos;
	}
	public void setCurrentPos(BoardPosition bp){
		currentPos = bp;
	}
	 public boolean isMoving() {
	        return isMoving;
	    }

	    public void setMoving(boolean isMoving) {
	        this.isMoving = isMoving;
	    }
	public void move1() {
		BoardPosition at = board.getRandomPosition();
		Cell cell = board.getCell(at);
		lock.lock();
		
		
		while(true){
			cell.lock.lock();
			if(!cell.isOcupied()){
			board.getCell(currentPos).removeObstacle();
			currentPos = cell.getPosition();
			cell.setGameElement(this);
			board.setChanged();
			cell.lock.unlock();
			lock.unlock();			
			break;
			}
		}
	}
	
	
	@Override
	public void run() {
		
	}
}
