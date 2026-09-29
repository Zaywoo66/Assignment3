package bridge.implementor;

import java.util.List;

/** Low-level drawing primitives. Knows nothing about concrete shapes. */
public interface Renderer {
    void renderCircle(Point center, double radius);

    void renderPolygon(List<Point> vertices);

    String name();
}
