package environment;

import java.io.Serializable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.sound.midi.SysexMessage;

import game.GameElement;
import game.Goal;
import game.Obstacle;
import game.Snake;
import game.AutomaticSnake;
/** Main class for game representation. 
 * 
 * @author luismota
 *
 */
public class Cell {
	private BoardPosition position;
	private Snake ocuppyingSnake = null;
	private GameElement gameElement=null;
	public Lock lock = new ReentrantLock();
	public Condition freeCell = lock.newCondition();
	public GameElement getGameElement() {
		return gameElement;
	}


	public Cell(BoardPosition position) {
		super();
		this.position = position;
	}

	public BoardPosition getPosition() {
		return position;
	}

	public void request(Snake snake) throws InterruptedException {
		lock.lock(); //lock da cell
		try{
			while(isOcupied()){
				freeCell.await(); //espera que fique livre			
			}
		} finally {
		//TODO coordination and mutual exclusion
		freeCell.signal();
		ocuppyingSnake=snake;
		lock.unlock();
		}
	}

	public void release() {
		lock.lock(); //lock da cell
		try{
			ocuppyingSnake=null;
			freeCell.signal();
		} finally {	
			lock.unlock();
		}
	}

	public boolean isOcupiedBySnake() {
		return ocuppyingSnake!=null;
	}
	
	public  void setGameElement(GameElement element) {
		// TODO coordination and mutual exclusion
		gameElement=element;

	}
	
	public void setNull(){
		gameElement = null;
		ocuppyingSnake = null;
	}
	
	public boolean isOcupied() {
		return isOcupiedBySnake() || (gameElement!=null && gameElement instanceof Obstacle);
	}


	public Snake getOcuppyingSnake() {
		return ocuppyingSnake;
	}
	
	public void setOccupyingSnake(Snake snake) {
		ocuppyingSnake = snake;
	}

	
	
	public  void removeGoal() {
		// TODO
		lock.lock();
		if(gameElement instanceof Goal){		
			gameElement = null;
			
			
			//return capturedGoal;
		}
		lock.unlock();
		//return null;
		
	}
	
	public void removeObstacle() {
		
		lock.lock();
		if(gameElement instanceof Obstacle){		
			gameElement = null;
			
			
			//return capturedGoal;
		}
		lock.unlock();
	//TODO
	}


	public Goal getGoal() {
		return (Goal)gameElement;
	}


	public boolean isOcupiedByGoal() {
		return (gameElement!=null && gameElement instanceof Goal);
	}
	
	public int getX(){
		return this.getPosition().x;
	}
	
	public int getY(){
		return this.getPosition().y;
	}
	
	

}
