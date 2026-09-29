package bridge.implementor;

import java.io.PrintStream;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public final class RasterRenderer implements Renderer {
    private final PrintStream out;

    public RasterRenderer() {
        this(System.out);
    }

    public RasterRenderer(PrintStream out) {
        this.out = Objects.requireNonNull(out, "out must not be null");
    }

    @Override
    public void renderCircle(Point center, double radius) {
        out.printf(Locale.ROOT, "[Raster] Filling pixels of circle: center=(%.1f, %.1f), radius=%.1f px%n",
                center.x(), center.y(), radius);
    }

    @Override
    public void renderPolygon(List<Point> vertices) {
        String points = vertices.stream().map(Formatting::coords)
                .map(coords -> "(" + coords + ")").collect(Collectors.joining(" "));
        out.printf("[Raster] Filling pixels inside polygon with %d vertices: %s%n",
                vertices.size(), points);
    }

    @Override
    public String name() {
        return "Raster";
    }
}
