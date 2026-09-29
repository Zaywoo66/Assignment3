package bridge;

import bridge.abstraction.Circle;
import bridge.abstraction.Shape;
import bridge.abstraction.Square;
import bridge.implementor.Point;
import bridge.implementor.RasterRenderer;
import bridge.implementor.Renderer;
import bridge.implementor.VectorRenderer;
import java.util.List;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        Renderer vector = new VectorRenderer();
        Renderer raster = new RasterRenderer();
        Shape circle = new Circle(new Point(10, 10), 5, vector);
        Shape square = new Square(new Point(0, 0), 4, raster);

        System.out.println("1. Composition at runtime");
        circle.draw();
        square.draw();

        System.out.println("2. Switching implementation without changing the abstraction");
        circle.setRenderer(raster);
        square.setRenderer(vector);
        circle.draw();
        square.draw();

        System.out.println("3. Full matrix");
        for (Shape shape : List.of(circle, square)) {
            for (Renderer renderer : List.of(vector, raster)) {
                shape.setRenderer(renderer);
                System.out.printf("%s with %s:%n", shape.name(), renderer.name());
                shape.draw();
            }
        }

        System.out.println("4. Scale by 2.0");
        circle.scale(2.0);
        square.scale(2.0);
        circle.draw();
        square.draw();
    }
}
