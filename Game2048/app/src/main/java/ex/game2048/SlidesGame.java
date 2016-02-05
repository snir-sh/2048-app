package ex.game2048;/*/

import ex.game2048.SlidesBoard;

/**
 *
 * @author Ilya Shmilove
 */
public class SlidesGame {
    private SlidesBoard board;
    public final int SIZE;
    public SlidesGame(int s)
    {
        SIZE = s;
        board = new SlidesBoard(s);
    }
    
  
    public void moveDown()
    {
    	board.doDown();
    	board.printBoard();
    }
    public void moveUp()
    {
    	board.doUp();
    	board.printBoard();
    }
    public void moveLeft()
    {
    	board.doLeft();
    	board.printBoard();
    	
    }
    public void moveRight()
    {
    	board.doRight();
    	board.printBoard();
    }
    
    public boolean gameOver()
    {
    	return board.gameOver();
    }
    
    public int[][] getBoard()
    {
        return board.getBoard();
    }
}
