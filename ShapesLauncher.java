import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

// Driver class
//
// ***** YOU DO NOT NEED TO MODIFY ANY CODE IN THIS FILE *****

public class ShapesLauncher {
    
    private static final int DEFAULT_NUM_OF_SHAPES = 12;
    
    public static final int FRAME_WIDTH = 600;
    public static final int FRAME_HEIGHT = 600;
    

    
    public static void main (String[] args) {
        
        //default incase no/invalid argument is entered
        int numOfShapes = DEFAULT_NUM_OF_SHAPES;
        if (args.length > 0){
            try{
                //only care about the first command line argument
                int temp = Integer.parseInt(args[0]);
                //can't have negative shapes!
                if (temp < 0)
                    System.err.println("Invalid argument entered! Using default value for number of shapes.");
                else
                    numOfShapes = temp;
            }
            catch (NumberFormatException nfe){
                //what would trigger this?
                System.err.println("Non-numeric argument entered!  Using default value for number of shapes.");
            }
        }
        
        
        //Instantiate our Drawing Canvas
        DrawingCanvas myCanvas = new DrawingCanvas(numOfShapes, FRAME_WIDTH, FRAME_HEIGHT);
        
        //Create and display the graphical window w/ our canvas
        myCanvas.launchWindow();
        
    }
}
