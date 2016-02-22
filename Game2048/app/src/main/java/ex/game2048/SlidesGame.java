package ex.game2048;
// This class is the game engine, all the function in this class are responsible of the logic.
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
    }
    public void moveUp()
    {
    	board.doUp();
    }
    public void moveLeft()
    {
    	board.doLeft();
    	
    }
    public void moveRight()
    {
    	board.doRight();
    }
    
    public boolean gameOver()
    {
    	return board.gameOver();
    }
    public int getScore()
    {
        return board.getScore();
    }
    
    public int[][] getBoard()
    {
        return board.getBoard();
    }
}
