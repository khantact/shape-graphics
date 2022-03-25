import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class DrawingCanvas extends JComponent implements KeyListener,
        MouseListener, MouseMotionListener, ActionListener {

    private static final int NONE = -1;
    // Frequency that the window "repaints" itself, in milliseconds
    private static final int REPAINT_INTERVAL = 33;

    private static Random rand = MyShape.rand;

    private int canvasWidth, canvasHeight;
    ArrayList<MyShape> shapes;
    // 'ticks' elapsed since program was launched
    private int ticksElapsed = 0;

    // Provided: two hard-coded shapes
    // For Part 2.2, you will only draw these two hard coded shapes!
    private MyShape shapeOne;
    private MyShape shapeTwo;

    // For Part 2.3, you will instead need to draw a dynamic number of shapes!
    // hmmm... what data structure would work well here?

    // Keep track of which shape is selected (especially useful for Part 2.3)
    // will make some of these functions easier to implement AND more efficient!
    private int selectedIndex = NONE;

    // used to track the position of the Mouse cursor in the window
    private int lastKnownMouseX, lastKnownMouseY;

    public DrawingCanvas(int numOfShapes, int canvasWidth, int canvasHeight) {
        super();
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        System.out.println("Canvas created, number of shapes = " + numOfShapes);

        // initializes the hard-coded shapes for Part 2.2
        initDynamicShapes(numOfShapes);
        // In Part 2.3, you will use the initDynamicShapes() method instead!

    }

    // This function is no longer necessary in Part 2.2
    private void initHardCodedShapes() {
        shapeOne = new MySquare(this.getRandomXCoord(), this.getRandomYCoord());
        shapeTwo = new MyCircle(this.getRandomXCoord(), this.getRandomYCoord());
    }

    // initializes your dynamic collection of Shapes
    // Your window starts with the specified num of shapes (see constructor)
    // Of these shapes, 50% will be squares and 50% circles
    private void initDynamicShapes(int numOfShapes) {
        shapes = new ArrayList<MyShape>(numOfShapes);
        for (int i = 0; i < numOfShapes; i++) {
            if (i % 2 == 0) {
                shapes.add(new MySquare(this.getRandomXCoord(), this.getRandomYCoord()));
            } else {
                shapes.add(new MyCircle(this.getRandomXCoord(), this.getRandomYCoord()));
            }
        }
    }

    // refreshes the canvas, repainting all shapes in the window
    public void refreshCanvas() {
        // printDebugText("Find Me #1!");

        // For Part 2.2, this is for painting the two hard coded shapes
        // repaintShape(shapeOne);
        // repaintShape(shapeTwo);
        for (int i = 0; i < shapes.size(); i++) {
            repaintShape(this.shapes.get(i));
        }

    }

    // Scales the currently selected shape by a specific vector
    public void scaleSelectedShape(int vector) {

        // For Part 2.2, determines which of the hard-coded shapes is selected
        for (int i = 0; i < shapes.size(); i++) {
            if (shapes.get(i).getSelected())
                shapes.get(i).scale(vector);
        }

    }

    // Deselects the currently seleted shape
    public void deselect() {
        // For Part 2.2, this just needs to look at the two hard coded shapes
        for (int i = 0; i < shapes.size(); i++) {
            if (shapes.get(i).getSelected())
                shapes.get(i).setSelected(false);
        }
        selectedIndex = NONE;

    }

    // Switches the currently selected shape to be the opposite shape
    // retains the original shape's color and size
    // (ex: if the shape is a Circle it becomes a Square)
    public void switchSelectedShape() {
        // Complete this method!
        // For Part 2.1, this just looks at the two hard coded shapes
        // For Part 2.2, you will need to look through your collection
        // (instanceof will be handy here!)
        if (selectedIndex != NONE) {
            for (int i = 0; i < shapes.size(); i++) {
                if (shapes.get(i).getSelected()) {
                    if (shapes.get(i) instanceof MySquare)
                        shapes.set(i, new MyCircle(shapes.get(i)));
                    else
                        shapes.set(i, new MySquare(shapes.get(i)));
                    shapes.get(i).setSelected(true);
                    selectedIndex = 0;
                }
            }
        }
    }

    public void newShape() {
        int cflip = rand.nextInt(3);
        int randomX = rand.nextInt(canvasWidth);
        int randomY = rand.nextInt(canvasHeight);
        if (cflip == 1) {
            shapes.add(new MyCircle(randomX, randomY));
            deselect();
            shapes.get(shapes.size() - 1).setSelected(true);
            selectedIndex = shapes.size() - 1;
        } else {
            shapes.add(new MySquare(randomX, randomY));
            deselect();
            shapes.get(shapes.size() - 1).setSelected(true);
            selectedIndex = shapes.size() - 1;
        }
    }

    public void delShape() {
        for (int i = 0; i < shapes.size(); i++) {
            if (shapes.get(i).getSelected()) {
                shapes.remove(i);
                deselect();
            }
        }
    }

    public void refreshCircles() {
        for (int i = 0; i < shapes.size(); i++) {
            if (shapes.get(i) instanceof MyCircle) {
                shapes.get(i).setRandomFillColor();
            }
        }
    }

    public void refreshSquares() {
        for (int i = 0; i < shapes.size(); i++) {
            if (shapes.get(i) instanceof MySquare) {
                shapes.get(i).setRandomFillColor();
            }
        }
    }

    public void refreshAll() {
        for (int i = 0; i < shapes.size(); i++) {
            shapes.get(i).setRandomFillColor();
        }
    }

    // This method is called anytime the mouse is clicked inside the window
    public void mousePressed(MouseEvent event) {
        // printDebugText("Find Me #2!", false);
        lastKnownMouseX = event.getX();
        lastKnownMouseY = event.getY();
        for (int i = shapes.size() - 1; i >= 0; i--) {
            if (shapes.get(i).contains(lastKnownMouseX, lastKnownMouseY)) {
                deselect();
                shapes.get(i).setSelected(true);
                shapes.add(shapes.get(i));
                shapes.remove(i);
                selectedIndex = shapes.size() - 1;
                return;
            }
            selectedIndex = NONE;

        }

        // what happens if you click inside the window but not on any shape?
        deselect();

    }

    // This method is called anytime _____________________
    public void mouseDragged(MouseEvent event) {
        // printDebugText("Find Me #3!");
        if (selectedIndex != NONE) {
            int moveX = event.getX() - lastKnownMouseX;
            int moveY = event.getY() - lastKnownMouseY;

            for (int i = 0; i < shapes.size(); i++) {
                if (shapes.get(i).getSelected())
                    shapes.get(i).moveFrame(moveX, moveY);
            }
            lastKnownMouseX = event.getX();
            lastKnownMouseY = event.getY();
        }
    }

    // This method is called anytime ______________________
    public void keyPressed(KeyEvent e) {
        // printDebugText("Find Me #4!", false);
        // retrieves the "key code" of the last key pressed,
        // this is a unique number corresponding to the pressed key
        int keyCode = e.getKeyCode();

        // 'Q' quits the program
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            System.exit(1);
        // '+' Grows the shape
        else if (e.getKeyCode() == KeyEvent.VK_EQUALS)
            scaleSelectedShape(1);
        // '-' Shrinks the Shape
        else if (e.getKeyCode() == KeyEvent.VK_MINUS)
            scaleSelectedShape(-1);
        // 'T' switches the selected item to the opposite shape
        else if (e.getKeyCode() == KeyEvent.VK_T)
            switchSelectedShape();
        // 'N, 'D', 'C', 'S', and 'A' keys will....
        else if (e.getKeyCode() == KeyEvent.VK_N)
            newShape();
        else if (e.getKeyCode() == KeyEvent.VK_D)
            delShape();
        else if (e.getKeyCode() == KeyEvent.VK_C)
            refreshCircles();
        else if (e.getKeyCode() == KeyEvent.VK_S)
            refreshSquares();
        else if (e.getKeyCode() == KeyEvent.VK_A)
            refreshAll();

    }

    // Generates random X and Y coordinates within the canvas space
    private double getRandomXCoord() {
        return rand.nextDouble() * (canvasWidth - MyShape.DEFAULT_SIZE);
    }

    private double getRandomYCoord() {
        return rand.nextDouble() * (canvasHeight - MyShape.DEFAULT_SIZE);
    }

    // ********************************************************
    // * *
    // * DO NOT MODIFY THE CODE BELOW *
    // * *
    // ********************************************************

    // Graphics objects, necessary for drawing shapes to the screen
    private Graphics2D g2D;

    // interval for how frequently debug print messagees are output
    // (to avoid spamming the console with a billion print messages)
    private static final int DEBUG_PRINT_INTERVAL = 10;

    // (re)Paints all of the shapes in the window
    // *** This gets called everytime repaint() is called! ***
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        refreshCanvas();

    }

    // repaints (i.e., "refreshes") the argument MyShape object on the canvas
    private void repaintShape(MyShape s) {
        s.paintComponent(g2D);
    }

    // Initializes and displays our window (represented by a JFrame object)
    public void launchWindow() {

        // ** FYI ** Feel free to trace this code if you like, but
        // don't worry if doesn't make total sense yet
        JFrame window = new JFrame("Shape Graphics");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(this.canvasWidth, this.canvasHeight);
        window.add(this);

        window.setBackground(Color.WHITE);
        window.setVisible(true);
        this.requestFocus();

        // Let's the window know that the methods to react to keyboard/mouse actions
        // are implemented in this class
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        // Instantiation of our timer -- look at this API page:
        // http://docs.oracle.com/javase/7/docs/api/javax/swing/Timer.html
        Timer timer = new Timer(0, this);
        timer.setDelay(REPAINT_INTERVAL);
        timer.start();
    }

    // These below function helps the canvas refresh automatically at a set interval
    public void actionPerformed(ActionEvent ae) {
        repaint();
        ticksElapsed++;
    }

    // prints out the argument debug message. Uses ticksElapsed to
    // suppress how many times the message gets printed (so it doesn't spam
    // the console)... If suppress is passed as false, it ignores this and
    // always prints the message.
    private void printDebugText(String message) {
        printDebugText(message, true);
    }

    private void printDebugText(String message, boolean suppress) {
        if (!suppress || ticksElapsed % DEBUG_PRINT_INTERVAL == 0)
            System.out.println(message);
    }

    // These functions are required by various interfaces, but are not used
    public void mouseReleased(MouseEvent event) {
    }

    public void mouseClicked(MouseEvent event) {
    }

    public void mouseEntered(MouseEvent event) {
    }

    public void mouseExited(MouseEvent event) {
    }

    public void mouseMoved(MouseEvent event) {
    }

    public void keyReleased(KeyEvent event) {
    }

    public void keyTyped(KeyEvent event) {
    }
}
