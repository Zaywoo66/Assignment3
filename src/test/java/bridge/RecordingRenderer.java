package bridge;

import bridge.implementor.Point;
import bridge.implementor.Renderer;
import java.util.List;

final class RecordingRenderer implements Renderer {
    Point lastCircleCenter;
    double lastCircleRadius;
    List<Point> lastPolygon;
    int circleCalls;
    int polygonCalls;

    @Override
    public void renderCircle(Point center, double radius) {
        lastCircleCenter = center;
        lastCircleRadius = radius;
        circleCalls++;
    }

    @Override
    public void renderPolygon(List<Point> vertices) {
        lastPolygon = List.copyOf(vertices);
        polygonCalls++;
    }

    @Override
    public String name() {
        return "Recording";
    }
}
