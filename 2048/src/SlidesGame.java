/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
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
