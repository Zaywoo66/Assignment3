package bridge.abstraction;

import bridge.implementor.Point;
import bridge.implementor.Renderer;
import java.util.List;
import java.util.Objects;

public class Square extends Shape {
    private final Point topLeft;
    private double side;

    public Square(Point topLeft, double side, Renderer renderer) {
        super(renderer);
        this.topLeft = Objects.requireNonNull(topLeft, "topLeft must not be null");
        if (!(side > 0)) {
            throw new IllegalArgumentException("side must be positive");
        }
        this.side = side;
    }

    @Override
    public void draw() {
        renderer.renderPolygon(vertices());
    }

    @Override
    public void scale(double factor) {
        if (!(factor > 0)) {
            throw new IllegalArgumentException("factor must be positive");
        }
        side *= factor;
    }

    @Override
    public String name() {
        return "Square";
    }

    private List<Point> vertices() {
        double x = topLeft.x();
        double y = topLeft.y();
        // Screen coordinates have a downward-growing Y axis.
        return List.of(topLeft, new Point(x + side, y),
                new Point(x + side, y + side), new Point(x, y + side));
    }
}
