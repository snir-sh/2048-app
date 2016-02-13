package ex.game2048;

import android.util.Log;

import java.util.Random;


public class SlidesBoard {
    public static final int EMPTY = 0;
    private final int SIZE, N;
    private int[][] board;
    private int emptyRow, emptyCol;
	private int score;



	// Create a board object by the size that ben given
    public SlidesBoard(int s)
    {
        SIZE = s;
        board = new int[SIZE][SIZE];
        N = s*s;
        int[] randomBatch = generateRandom();
        for(int i = 0; i < board.length; i++)
            for(int j = 0; j < board[i].length; j++)
            {
                board[i][j] = randomBatch[SIZE*i + j];
                if(board[i][j] == EMPTY)
                {
                    emptyRow = i;
                    emptyCol = j;
                }
            }

		score = 0;
    }
       
    public int[][] getBoard()
    {
        return board;
    }
    

	// Move the board down
    public void doDown()
    {
    	boolean canMove = false;
    	boolean canMove1 = false;
    	canMove = moveDown();
    	canMove1 = mergeDown();
    	moveDown();
    	
    	if(canMove || canMove1 )
			generateRandomNumber();
    }
	// Move the board up
    public void doUp()
    {
    	boolean canMove = false;
    	boolean canMove1 = false;
    	canMove = moveUp();
    	canMove1 = mergeUp();
    	moveUp();
    	
    	if(canMove || canMove1 )
			generateRandomNumber();
    }
	// Move the board left
    public void doLeft()
    {
    	boolean canMove = false;
    	boolean canMove1 = false;
    	canMove = moveLeft();
    	canMove1 = mergeLeft();
    	moveLeft();
    	
    	if(canMove || canMove1 )
			generateRandomNumber();
    }

	// Move the board right
    public void doRight()
    {
    	boolean canMove = false;
    	boolean canMove1 = false;
    	canMove = moveRight();
    	canMove1 = mergeRight();
    	moveRight();
    	
    	if(canMove || canMove1 )
			generateRandomNumber();
    }
    
   // Attach the board up
    private boolean moveUp()
    {
    	int row=-1, col=-1;
    	boolean canMove = false;
    	for(int i=1; i < SIZE; i++)
    		for (int j=0; j < SIZE ; j++)
    		{
    			
    			if(board[i][j]!=EMPTY)
    			{
    				
    				if(board[i-1][j]==EMPTY)
    				{
    					int temp = board[i][j];
    					board[i][j]=EMPTY;
    					row = i-1;
    					col=j;
    					while(row-1 > 0 && board[row-1][col]==EMPTY)
    						row--;
    					if(row!=-1 && col!=-1)
    					{
    						board[row][col] = temp;
    						row=-1;
    						col=-1;
    						canMove = true;
    					}
    					
    				}
    			}
    		}
    	return canMove;
    }
	// Attach the board down
    private boolean moveDown()
    {
    	int row=-1, col=-1;
    	boolean canMove = false;
    	for(int i=SIZE-2; i > -1; i--)
    		for (int j=0; j < SIZE ; j++)
    		{
    			if(board[i][j]!=EMPTY)
    			{
    				if(board[i+1][j]==EMPTY)
    				{
    					int temp = board[i][j];
    					board[i][j] = EMPTY;
    					row =i+1;
    					col =j;
    					while(row+1 < SIZE-1 && board[row+1][col]==EMPTY)
    						row++;
    					if(row!=-1 && col!=-1)
    					{
    						board[row][col] = temp;		
    						row=-1;
    						col=-1;
    						canMove = true;
    					}	
    			
    				}
    			}
    		}
    	
    	return canMove;        	
    }
	// Attach the board left
    private boolean moveLeft()
    {
    	int row=-1, col=-1;
    	boolean canMove = false;
    	for(int i=0; i < SIZE ; i++)
    		for (int j=1; j < SIZE ; j++)
    		{
    			if(board[i][j] != EMPTY)
    			{
    				if(board[i][j-1] == EMPTY)
    				{
    					int temp = board[i][j];
    					board[i][j] = EMPTY;
    					row =i;
    					col = j-1;		
    					while(col-1 >= 0 && board[row][col-1] == EMPTY)
    						col--;
    					if(row!=-1 && col!=-1)
    					{
    						board[row][col] = temp;
    						row =-1;
    						col=-1;
    						canMove = true;
    					}
    				}
    			}
    		}
    	return canMove;
    }
	// Attach the board right
    private boolean moveRight()
    {
    	int row =-1, col =-1;
    	boolean canMove = false;
    	for(int i=0; i < SIZE ;i++)
    		for (int j = SIZE-2; j >= 0; j--)
    		{
    			if(board[i][j] != EMPTY)
    			{
    				if(board[i][j+1] == EMPTY)
    				{
    					int temp = board[i][j];
    					board[i][j] = EMPTY;
    					row = i;
    					col = j+1;		
    					while(col+1 < SIZE-1 && board[row][col+1] == EMPTY)
    						col++;
    					if(row!=-1 && col!=-1)
    					{
    						board[row][col] = temp;
    						row =-1;
    						col=-1;
    						canMove = true;
    					}
    				}
    			}
    		}
    	return canMove;
    	
    }
    // Merge numbers down
    public boolean mergeDown()
    {
    	boolean canMove =false;
    	for(int i=SIZE-1; i > 0; i--)
    		for (int j=0; j < SIZE; j++)
    		{
    			if(board[i][j] != EMPTY)
    			{
    				if(board[i-1][j] == board[i][j])
    				{
    					board[i][j] = 2*board[i][j];
						score +=  board[i][j];
    					board[i-1][j] = EMPTY;
    					canMove = true;
    				}
    			}
    		}
    	return canMove;
    }
	// Merge numbers left
    private boolean mergeLeft()
    {
    		
    	boolean canMove = false;
    	for(int i=0; i < SIZE; i++)
    		for (int j=0; j < SIZE-1; j++)
    		{
    			if(board[i][j]!=EMPTY)
    			{
    				if(board[i][j+1] == board[i][j])
    				{
    					board[i][j] = 2*board[i][j];
						score +=  board[i][j];
    					board[i][j+1] = EMPTY;
    					canMove = true;
    				}
    			}
    		}
    	return canMove;
    }
	// Merge numbers right
    private boolean mergeRight()
    {
    	boolean canMove = false;
    	for(int i=0; i < SIZE; i++)
    		for (int j = SIZE-1; j > 0; j--)
    		{
    			if(board[i][j] != EMPTY)
    			{
    				if(board[i][j-1] == board[i][j])
    				{
    					board[i][j] = 2*board[i][j];
						score +=  board[i][j];
    					board[i][j-1] = EMPTY;
    					canMove = true;
    				}
    			}
    		}
    	return canMove;
    	
    	
    }
	// Merge numbers up
    private boolean mergeUp()
    {
    	boolean canMove = false;
    	for(int i=0; i < SIZE-1; i++)
    		for (int j=0; j < SIZE; j++)
    		{
    			if(board[i][j] != EMPTY)
    			{
    				if(board[i+1][j] == board[i][j])
    				{
    					board[i][j] = 2*board[i][j];
						score +=  board[i][j];
    					board[i+1][j] = EMPTY;
    					canMove = true;
    				}
    			}
    		}
    	return canMove;
    }


	// Generate numbers on the board
    private void generateRandomNumber()
    {
    	Random rand = new Random();
    	int num = getTwoOrFour();
    	int i=0,j=0;
    	i = rand.nextInt(SIZE);
    	j = rand.nextInt(SIZE);
    	
    	while(board[i][j]!=EMPTY)
    	{
    		i = rand.nextInt(SIZE);
        	j = rand.nextInt(SIZE);
    	}
    	board[i][j] = num;
    	
    }

	// Place the number on board
    private int[] generateRandom()
    {
        int[] res = new int[N];
        Random rand = new Random();
        
        for(int i = 0; i < N; i++)
        {
        	if(i<2)
        		res[i] = getTwoOrFour();
        	else 
        		res[i]=EMPTY;
        }
       
        
        for(int i = 0; i < N; i++)
        {
            int pos = rand.nextInt(N);
            int temp = res[i];
            res[i] = res[pos];
            res[pos] = temp;
        }
  
        return res;       
    }

	// Return random number 2 or 4
	private int getTwoOrFour()
	{
		int num = 0;
		Random rand = new Random();
		while (num!=2 && num!=4)
		{
			num = rand.nextInt(5);
		}
		return num;
	}


	// Print the board
    public void printBoard()
    {
    	for(int i=0; i < SIZE; i++)
    	{
    		for(int j=0; j < SIZE; j++)
    			System.out.print(board[i][j]+"\t");
    		System.out.println();
    	}
    	System.out.println();
    }

	// Check if the game is over
    public boolean gameOver()
    {
    	boolean isFull = checkIfFull();
    	if(!isFull)
    		return false;
    	else
    		for(int i=0; i < SIZE-1; i++)
    			for(int j=0; j < SIZE-1; j++)
    			{
    				if(board[i][j] == board[i+1][j])
    					return false;
    				if(board[i][j]==board[i][j+1])
    					return false;			
    			}
    	return true;
    
    }
	// Check if the board is full
    private boolean checkIfFull()
    {
    	for(int i=0; i < SIZE; i++)
    		for(int j=0; j < SIZE; j++)
    			if(board[i][j]==EMPTY)
    				return false;
    	return true;
    }

	public int getScore()
	{
		return score;
	}
}