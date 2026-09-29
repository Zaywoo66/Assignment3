package bridge.implementor;

import java.io.PrintStream;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public final class VectorRenderer implements Renderer {
    private final PrintStream out;

    public VectorRenderer() {
        this(System.out);
    }

    public VectorRenderer(PrintStream out) {
        this.out = Objects.requireNonNull(out, "out must not be null");
    }

    @Override
    public void renderCircle(Point center, double radius) {
        out.printf(Locale.ROOT, "[Vector] <circle cx=\"%.1f\" cy=\"%.1f\" r=\"%.1f\"/>%n",
                center.x(), center.y(), radius);
    }

    @Override
    public void renderPolygon(List<Point> vertices) {
        String points = vertices.stream().map(Formatting::coords).collect(Collectors.joining(" "));
        out.printf("[Vector] <polygon points=\"%s\"/>%n", points);
    }

    @Override
    public String name() {
        return "Vector";
    }
}
