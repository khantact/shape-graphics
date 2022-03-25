import java.awt.Point;
import java.awt.Color;
import java.awt.geom.Ellipse2D;

public class MyCircle extends MyShape {

    private static final Color[] CIRCLE_COLORS = { Color.BLUE, Color.GREEN, Color.MAGENTA };

    public MyCircle() {
        this(0, 0);
    }

    public MyCircle(double x, double y) {
        this(x, y, DEFAULT_SIZE);
    }

    public MyCircle(MyShape s) {
        this(s.getShapeX(), s.getShapeY(), s.getShapeWidth());
    }

    public MyCircle(double x, double y, double size) {
        super();
        shape = new Ellipse2D.Double(x, y, size, size);
    }

    public String toString() {
        return "Circle " + shape.toString() + " color " + fillColor;
    }

    public void setRandomFillColor() {

        this.fillColor = CIRCLE_COLORS[rand.nextInt(3)];
    }
}
