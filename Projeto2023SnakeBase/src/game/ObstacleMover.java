package game;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import environment.BoardPosition;
import environment.Cell;
import environment.LocalBoard;

public class ObstacleMover extends Thread {
	private Obstacle obstacle;
	private LocalBoard board;
	public Lock lock = new ReentrantLock();
	private Semaphore semaphore;
	
	public ObstacleMover(Obstacle obstacle, LocalBoard board, Semaphore semaphore) {
		super();
		this.obstacle = obstacle;
		this.board = board;
		this.semaphore = semaphore;
	}
	
	public void move() {
		BoardPosition at = board.getRandomPosition();
		Cell cell = board.getCell(at);
		lock.lock();
		
		
		while(true){
			
			
			if(!cell.isOcupied()){
				cell.lock.lock();
				board.getCell(obstacle.getCurrentPos()).removeObstacle();
				obstacle.setCurrentPos(cell.getPosition());
				cell.setGameElement(obstacle);
				board.setChanged();
				cell.lock.unlock();
				lock.unlock();			
				break;
			}
		}
	}

	@Override
	public void run() {
	    try {
	        //semaphore.acquire();
	        while (obstacle.getRemainingMoves() > 0) {
	            try {
	                Thread.sleep(1000); // Use Thread.sleep em vez de sleep diretamente
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	                // Opcional: você pode querer sair do loop se a thread for interrompida
	                break;
	            }
	            //System.err.println("Obstaculo em EXEC");
	            
	            move();
	            obstacle.decrementMoves();
	        }
	    //} catch (InterruptedException e) {
	    //    e.printStackTrace();
	    } finally {
	        //semaphore.release();
	        //obstacle.setMoving(false);
	    	
	        //System.err.println("Semáforo: " + semaphore.availablePermits() + "do obstaculo: " + this.getId());	        
	        
	    	//board.startObstacleMover();
	        board.startNextObstacleMovers();
	    }
	}
}
