
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class SlidesGUI extends JPanel {
    private SlidesGame game;
    private int width;
    private boolean win;
    
    public SlidesGUI()
    {
        game = new SlidesGame(4);
        addKeyListener(new Listener());
        setFocusable(true);
        requestFocus();
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawBoard(g2d);
        win = game.gameOver();
        if(win)
            gameOver(g2d);
    }
    
    private void drawBoard(Graphics2D g2d)
    {
        int w = getWidth();
        int h = getHeight();
        width = w / (game.SIZE + 2);
        int[][] a = game.getBoard();
        int x, y;
        x = y = width;
        drawBoard(g2d,x);
        for(int i = 0; i < a.length; i++, y += width)
        {
            x = width;
            for(int j = 0; j < a.length; j++, x += width)
                if(a[i][j] != SlidesBoard.EMPTY)
                    drawSquare(g2d, a[i][j], x, y);
                else
                	drawEmptySquare(g2d,x, y);
        }
    }
    private void drawBoard(Graphics2D g2d,int j)
    {
//    	g2d.setColor(Color.decode("#F4A460"));
    	g2d.setColor(Color.BLUE);
    	g2d.fillRect(j-game.SIZE, j-game.SIZE, game.SIZE*66, game.SIZE*66);
    }
    private void drawSquare(Graphics2D g2d, int num, int x, int y)
    {
        g2d.setColor(getColor(num));
        g2d.fillRect(x+1, y+1, width-2, width-2);
        g2d.setFont(new Font("arial", Font.BOLD, 20));
        if(num<8)
        	 g2d.setColor(Color.decode("#A52A2A"));
        else
        	g2d.setColor(Color.WHITE);
        
        g2d.drawString("" + num, x + width/2 - 5, y + width/2 + 5);
        
    }
    public void drawEmptySquare(Graphics2D g2d, int x, int y)
    {
    	 g2d.setColor(Color.LIGHT_GRAY);
         g2d.fillRect(x+1, y+1, width-2, width-2);
	
    }
    
    private void gameOver(Graphics2D g2d)
    {
        g2d.setColor(Color.GREEN);
        g2d.drawString("Well Done :)", getWidth() / 2 - 50, 30);
    }
    private Color getColor(int num)
    {
    	Color color = Color.BLUE;
    	switch (num) {
		case 2:
			color = Color.decode( "#F5F5F5");		
			break;
		case 4:
			color = Color.decode( "#FFFAFA");
			break;
		case 8:
			color = Color.getHSBColor( 255, 178, 102);
			break;
		case 16:
			color = Color.decode("#F4A460");
			break;
		case 32:
			color = Color.decode("#FA8072");
			break;
		case 64:
			color = Color.decode("#FF6347");
			break;
		case 128:
			color = Color.decode("#FF7F50");
			break;
		case 256:
			color = Color.decode("#FFD700");
			break;
		case 512:
			color = Color.decode("#DAA520");
			break;
		case 1024:
			color = Color.decode("#B8860B0");
			break;
		case 2048:
			color = Color.decode("#FF0000");
			break;
		} 
    	return color;
    }
    
    private class Listener implements KeyListener
    {

		@Override
		public void keyPressed(KeyEvent e) {

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			if(e.getKeyCode() ==KeyEvent.VK_DOWN)
				game.moveDown();
			if(e.getKeyCode() ==KeyEvent.VK_UP)
				game.moveUp();
			if(e.getKeyCode() == KeyEvent.VK_LEFT)
				game.moveLeft();
			if(e.getKeyCode() == KeyEvent.VK_RIGHT)
				game.moveRight();
			repaint();
		}

		@Override
		public void keyTyped(KeyEvent e) {
			
			
			// TODO Auto-generated method stub
		}
    }
}
