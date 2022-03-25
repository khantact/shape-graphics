import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.geom.RectangularShape;
import java.awt.Graphics2D;
import java.util.Random;

public abstract class MyShape extends JPanel {
    // JPanel extends JComponent
    // http://docs.oracle.com/javase/7/docs/api/javax/swing/JPanel.html

    // JComponent is an abstract class
    // http://docs.oracle.com/javase/7/docs/api/javax/swing/JComponent.html

    protected static final int DEFAULT_SIZE = 50;
    protected static final double SIZE_INC = 5;
    protected static final int RGBMAX = 255;
    private final Color OUTLINE_COLOR = Color.BLACK;

    // determines if this shape is currently selected or not
    private boolean isSelected = false;

    public static Random rand = new Random();

    protected Color fillColor;
    protected RectangularShape shape;
    // RectangularShape parent of both Rectangle2D and Ellipse2D
    // http://docs.oracle.com/javase/7/docs/api/java/awt/geom/RectangularShape.html

    public MyShape() {
        super();
        setRandomFillColor();
    }

    public void scale(int fac) {

        double sizeChange = fac * SIZE_INC;

        // updates the attributes of the shape. The 4 arguments represent (in order):
        // 1.) new x coordinate to redraw shape at
        // 2.) new y coord to draw shape at
        // 3.) new width of shape
        // 4.) new height of shape
        shape.setFrame(getShapeX() - (sizeChange / 2), getShapeY() - (sizeChange / 2),
                getShapeWidth() + sizeChange,
                getShapeHeight() + sizeChange);
        // Important: Make the scale invariant the center! (see lab writeup, you
        // don't need any other methods besides the ones already here!)
    }

    // Translates the shape by the argument x and y amounts
    public void moveFrame(float moveX, float moveY) {
        shape.setFrame(getShapeX() + moveX, getShapeY() + moveY,
                getShapeWidth(), getShapeHeight());
    }

    // ***** Accessor methods, may be useful! ******

    public Color getFillColor() {
        return fillColor;
    }

    public void setColor(Color c) {
        this.fillColor = c;
    }

    public void setSelected(boolean b) {
        isSelected = b;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public boolean contains(int x, int y) {
        return shape.contains(x, y);
    }

    public double getShapeX() {
        return shape.getX();
    }

    public double getShapeY() {
        return shape.getY();
    }

    public double getShapeWidth() {
        return shape.getWidth();
    }

    public double getShapeHeight() {
        return shape.getHeight();
    }

    // *****************************************************

    // ***** Class Functions ******

    // Updates the fill color of this shape
    // Comment the below method out for part 2.2.4! and...
    /*
     * public void setRandomFillColor() {
     * this.fillColor = Color.GREEN;
     * }
     */

    // ...also uncomment the below declaration for 2.2.4!
    // This will trigger a syntax error when trying to compile -- fix it!
    public abstract void setRandomFillColor();

    // This overrides the paintComponent method of JPanel
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setPaint(fillColor);
        g2.fill(shape);

        if (isSelected) {
            g2.setPaint(OUTLINE_COLOR);
            g2.draw(shape);
        }
    }

}
