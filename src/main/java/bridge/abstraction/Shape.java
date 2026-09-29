package bridge.abstraction;

import bridge.implementor.Renderer;
import java.util.Objects;

public abstract class Shape {
    protected Renderer renderer; // The bridge.

    protected Shape(Renderer renderer) {
        this.renderer = Objects.requireNonNull(renderer, "renderer must not be null");
    }

    public abstract void draw();

    public abstract void scale(double factor);

    public abstract String name();

    public void setRenderer(Renderer renderer) {
        this.renderer = Objects.requireNonNull(renderer, "renderer must not be null");
    }
}
