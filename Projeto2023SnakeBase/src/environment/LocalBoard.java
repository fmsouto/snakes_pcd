package environment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import game.GameElement;
import game.Goal;
import game.Obstacle;
import game.ObstacleMover;
import game.Server;
import game.Snake;
import game.AutomaticSnake;
import game.HumanSnake;

/**
 * Class representing the state of a game running locally
 * 
 * @author luismota
 *
 */
public class LocalBoard extends Board {

	private static final int NUM_SNAKES = 2;
	private static final int NUM_OBSTACLES = 20;
	private static final int NUM_SIMULTANEOUS_MOVING_OBSTACLES = 3;
	public static ExecutorService obstaclesExec = Executors.newFixedThreadPool(3);
	public Semaphore semaphore = new Semaphore(NUM_SIMULTANEOUS_MOVING_OBSTACLES);
	//public static HumanSnake human = new HumanSnake(100,this);
	
	public LocalBoard() {
		
		for (int i = 0; i < NUM_SNAKES; i++) {
			AutomaticSnake snake = new AutomaticSnake(i, this);
			Thread snakeThread = new Thread(snake);
			snakes.add(snake);
			snakesThread.add(snakeThread);
		}

		addObstacles(NUM_OBSTACLES);
	
			moveObstacles1();
		
		//startNextObstacleMovers();
		
		
		Goal goal = addGoal();
//		System.err.println("All elements placed");
	}
	
	public void addHumanSnake(int i){
		HumanSnake snake = new HumanSnake(i, this);
		snakes.add(snake);
		Thread snakeThread = new Thread(snake);
	}
	
	
	public void moveObstacles1(){
		
		for(int i = 0; i < NUM_SIMULTANEOUS_MOVING_OBSTACLES;i++){
					
					Obstacle movingObstacle = this.getObstacles().get(i);
					ObstacleMover mover = new ObstacleMover(movingObstacle, this, semaphore);
					obstaclesExec.execute(mover);
					System.err.println("Obstaculo: " + i);
				}
	}

	
	public int numberOfMovers = 0;
	public void startNextObstacleMovers() {
		numberOfMovers++;
		int moverIndex = NUM_SIMULTANEOUS_MOVING_OBSTACLES + numberOfMovers;
		Obstacle obstacle = this.getObstacles().get(moverIndex);
        ObstacleMover mover = new ObstacleMover(obstacle, this, semaphore);
        obstaclesExec.execute(mover);
            
    }
	

	public synchronized void startObstacleMover() {
		for (int i = 0; i < NUM_SIMULTANEOUS_MOVING_OBSTACLES; i++) {
			
		    for (Obstacle obstacle : this.getObstacles()) {
		        if (!obstacle.isMoving()) {
		            ObstacleMover mover = new ObstacleMover(obstacle, this, semaphore);
		            obstaclesExec.execute(mover);
		            obstacle.setMoving(true);
		            break;
		        }
		    }
		}
    }
	
	public void init() {
		for (Snake s : snakes)
			s.start();
		// TODO: launch other threads
		setChanged();
	}

	@Override
	public void handleKeyPress(int keyCode) {
		// do nothing... No keys relevant in local game
	}

	@Override
	public void handleKeyRelease() {
		// do nothing... No keys relevant in local game
	}

}
