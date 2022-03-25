import java.awt.Point;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

public class MySquare extends MyShape {

    private static final Color[] SQUARE_COLORS = { Color.RED, Color.YELLOW, Color.ORANGE };

    public MySquare() {
        this(0, 0);
    }

    public MySquare(double x, double y) {
        this(x, y, DEFAULT_SIZE);
    }

    public MySquare(MyShape s) {
        this(s.getShapeX(), s.getShapeY(), s.getShapeWidth());
    }

    public MySquare(double x, double y, double size) {
        super();
        shape = new Rectangle2D.Double(x, y, size, size);
    }

    public String toString() {
        return "rectangle " + shape.toString() + " color " + fillColor;
    }

    public void setRandomFillColor() {
        this.fillColor = SQUARE_COLORS[rand.nextInt(3)];
    }

}
