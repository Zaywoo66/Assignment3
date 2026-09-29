package bridge;

import bridge.abstraction.Circle;
import bridge.abstraction.Square;
import bridge.implementor.Point;
import bridge.implementor.RasterRenderer;
import bridge.implementor.VectorRenderer;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShapeBridgeTest {
    @Test
    void circleDelegatesToRenderCircle() {
        RecordingRenderer renderer = new RecordingRenderer();
        Point center = new Point(10, 20);

        new Circle(center, 5, renderer).draw();

        assertEquals(1, renderer.circleCalls);
        assertEquals(0, renderer.polygonCalls);
        assertEquals(center, renderer.lastCircleCenter);
        assertEquals(5, renderer.lastCircleRadius);
    }

    @Test
    void squareDelegatesToRenderPolygonWithFourVertices() {
        RecordingRenderer renderer = new RecordingRenderer();

        new Square(new Point(2, 3), 4, renderer).draw();

        assertEquals(1, renderer.polygonCalls);
        assertEquals(0, renderer.circleCalls);
        assertEquals(List.of(
                new Point(2, 3), new Point(6, 3),
                new Point(6, 7), new Point(2, 7)), renderer.lastPolygon);
    }

    @Test
    void rendererCanBeSwitchedAtRuntime() {
        RecordingRenderer first = new RecordingRenderer();
        RecordingRenderer second = new RecordingRenderer();
        Circle circle = new Circle(new Point(1, 2), 3, first);

        circle.draw();
        circle.setRenderer(second);
        circle.draw();

        assertEquals(1, first.circleCalls);
        assertEquals(1, second.circleCalls);
        assertEquals(new Point(1, 2), second.lastCircleCenter);
        assertEquals(3, second.lastCircleRadius);
    }

    @Test
    void scaleChangesShapeState() {
        RecordingRenderer renderer = new RecordingRenderer();
        Circle circle = new Circle(new Point(0, 0), 3, renderer);

        circle.scale(2.0);
        circle.draw();

        assertEquals(6, renderer.lastCircleRadius);
    }

    @Test
    void scaleChangesSquareState() {
        RecordingRenderer renderer = new RecordingRenderer();
        Square square = new Square(new Point(1, 2), 3, renderer);

        square.scale(2.0);
        square.draw();

        assertEquals(List.of(
                new Point(1, 2), new Point(7, 2),
                new Point(7, 8), new Point(1, 8)), renderer.lastPolygon);
    }

    @Test
    void constructorRejectsNullRenderer() {
        assertThrows(NullPointerException.class,
                () -> new Circle(new Point(0, 0), 1, null));
        assertThrows(NullPointerException.class,
                () -> new Square(new Point(0, 0), 1, null));
    }

    @Test
    void constructorRejectsNonPositiveSize() {
        RecordingRenderer renderer = new RecordingRenderer();

        assertThrows(IllegalArgumentException.class,
                () -> new Circle(new Point(0, 0), 0, renderer));
        assertThrows(IllegalArgumentException.class,
                () -> new Square(new Point(0, 0), -1, renderer));
    }

    @Test
    void vectorAndRasterProduceDifferentOutput() {
        ByteArrayOutputStream vectorBytes = new ByteArrayOutputStream();
        ByteArrayOutputStream rasterBytes = new ByteArrayOutputStream();
        Point center = new Point(1, 2);

        new VectorRenderer(new PrintStream(vectorBytes, true, StandardCharsets.UTF_8))
                .renderCircle(center, 3);
        new RasterRenderer(new PrintStream(rasterBytes, true, StandardCharsets.UTF_8))
                .renderCircle(center, 3);

        assertTrue(vectorBytes.toString(StandardCharsets.UTF_8).contains("[Vector]"));
        assertTrue(rasterBytes.toString(StandardCharsets.UTF_8).contains("[Raster]"));
    }
}
