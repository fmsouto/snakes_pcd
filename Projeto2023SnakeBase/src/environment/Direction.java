package environment;

import java.awt.event.KeyEvent;
import java.io.Serializable;

public enum Direction implements Serializable {
	UP(0,-1),DOWN(0,1),LEFT(-1,0),RIGHT(1,0);
	
	
	private BoardPosition vector;
	Direction(int x, int y) {
		vector=new BoardPosition(x, y);
	}
	public BoardPosition getDirection() {
		return vector;
	}
	
}
