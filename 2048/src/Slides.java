
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.JFrame;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class Slides {
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Slides");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        SlidesGUI gui = new SlidesGUI();
        frame.add(gui);
        frame.setVisible(true);
        
    }
}
