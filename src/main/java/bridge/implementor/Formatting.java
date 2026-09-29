package bridge.implementor;

import java.util.Locale;

final class Formatting {
    private Formatting() {
    }

    static String coords(Point point) {
        return String.format(Locale.ROOT, "%.1f,%.1f", point.x(), point.y());
    }
}
