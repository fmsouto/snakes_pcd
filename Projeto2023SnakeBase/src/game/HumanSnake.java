package game;

import java.awt.event.KeyEvent;

import environment.Board;
import environment.BoardPosition;
import environment.Cell;
import remote.RemoteBoard;
 /** Class for a remote snake, controlled by a human 
  * 
  * @author luismota
  *
  */
// CLASSE ESTAVA abstract!!!
public class HumanSnake extends Snake {
	
	public HumanSnake(int id,Board board) {
		super(id,board);
	}
	
	
	public BoardPosition getNextCellHuman(BoardPosition bp){
		BoardPosition currPos = this.cell.getPosition();
		BoardPosition nextPosition = new BoardPosition(currPos.x + bp.x, currPos.y + bp.y);	
		return nextPosition;
	}
	
	
	public void humanMove(BoardPosition bp){

		Cell first = cells.getLast();
		BoardPosition currPos = first.getPosition(); 
		
		//System.out.println("DX: " + dx);
		BoardPosition nextPosition = new BoardPosition(currPos.x + bp.x, currPos.y + bp.y);
		Cell newCell = this.getBoard().getCell(nextPosition);
		try {
			move(newCell);
			board.setChanged();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return nextPosition;
		
		//setChanged();
	}
	
	
	@Override
	public void run() {

		doInitialPositioning();
	
	}
}
