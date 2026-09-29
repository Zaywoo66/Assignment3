package bridge.abstraction;

import bridge.implementor.Point;
import bridge.implementor.Renderer;
import java.util.Objects;

public class Circle extends Shape {
    private final Point center;
    private double radius;

    public Circle(Point center, double radius, Renderer renderer) {
        super(renderer);
        this.center = Objects.requireNonNull(center, "center must not be null");
        if (!(radius > 0)) {
            throw new IllegalArgumentException("radius must be positive");
        }
        this.radius = radius;
    }

    @Override
    public void draw() {
        renderer.renderCircle(center, radius);
    }

    @Override
    public void scale(double factor) {
        if (!(factor > 0)) {
            throw new IllegalArgumentException("factor must be positive");
        }
        radius *= factor;
    }

    @Override
    public String name() {
        return "Circle";
    }
}
