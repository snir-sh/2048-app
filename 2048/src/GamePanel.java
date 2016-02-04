
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;



/**
 *
 * @author Shay Tavor
 */
public class GamePanel extends JPanel implements Runnable {
    public static final int WIDTH = 500, HEIGHT = 400;
    private Image dbImage = null;
    private boolean gameOver, running;
    private int period = 10; // how long each frame in ms
    
    public GamePanel()
    {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
        readyForTermination();
        running = true;
        // create game's components
        // ...
        
        addMouseListener( new MouseAdapter() {
            public void mouseClicked(MouseEvent e)
            { testPress(e.getX(), e.getY()); }
        });
        
    }
    
    
    
    
    public void run()
    {
        while(running)
        {
            gameUpdate();       // Update the game's logic
            gameRender();       // double buffering
            //repaint();          // paint on screen
            paintScreen();   // active rendering
            
            try {
                Thread.sleep(20);
            }
            catch(InterruptedException e){}
            
            /*
            // alternative run - controlling the FPS:
            long before, diff, sleepTime;
            before = System.currentTimeMillis();
            
            while(running)
            {
                gameUpdate();
                gameRender();
                repaint();
                // paintScreen();   // active rendering
                
                diff = System.currentTimeMillis() - before;
                sleepTime = period - before;
                if(sleepTime <= 0)
                    sleepTime = 5;
                
                try {
                    Thread.sleep(sleepTime);
                }
                catch(InterruptedException e){}
            
                before = System.currentTimeMillis();
            }
            * */
        }
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
       
        if(dbImage != null)
            g.drawImage(dbImage, 0, 0, null);
    }
    private void gameRender()
    {
        System.out.println("ff");
        Graphics dbg;
        if(dbImage == null)
            dbImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        
        dbg = dbImage.getGraphics();
        dbg.setColor(Color.WHITE);
        dbg.fillRect(0, 0, WIDTH, HEIGHT);
        
        // draw game elements
        // ...
        // just an example...
        dbg.setColor(Color.BLUE);
        dbg.fill3DRect(50, 50, 100, 200, true);
        
        
        if(gameOver)
            gameOverMessage(dbg);
    }
    
    public void gameUpdate()
    {
    
    }
    private void gameOverMessage(Graphics dbg)
    {
        // g.drawString... prints the game over message
    }
    
    private void readyForTermination()
    {
        addKeyListener( new KeyAdapter() {
           public void keyPressed(KeyEvent e)
           {
               int k = e.getKeyCode();
               if(k == KeyEvent.VK_ESCAPE || k == KeyEvent.VK_Q)
                   running = false;
           }
        });
    }
    
    private void testPress(int x, int y)
    {
        // do something with x and y
    }
    
     private void paintScreen()
    {
        Graphics g;
        try {
            g = getGraphics();
            if(g != null && dbImage != null)
                g.drawImage(dbImage, 0, 0, null);
            g.dispose();
        }
        catch(Exception e)
        {
            System.out.println("Graphics error");
        }
        
    }
}
