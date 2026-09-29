# Clean Code in the Shape–Renderer Bridge

The `before` snippets below are examples of designs to avoid. The `after` snippets show the intended project code and the corresponding responsibility in the Bridge.

## 1. Separate abstraction from implementation

`Circle` knows its center and radius, while `Renderer` knows how to present drawing primitives. Delegating through the interface keeps rendering details out of the shape and lets the same object change its renderer at runtime.

Before:

```java
if (renderer instanceof VectorRenderer) {
    System.out.println("<circle .../>");
} else {
    System.out.println("Fill circle pixels");
}
```

After (`Circle.draw()`):

```java
renderer.renderCircle(center, radius);
```

## 2. Use names that express roles

`Shape`, `Circle`, and `Square` name the abstraction side; `Renderer`, `VectorRenderer`, and `RasterRenderer` name the implementation side. The method name `renderCircle` also says which primitive it handles, so a reader need not infer its meaning from a call site.

Before:

```java
interface Drawer {
    void draw1(Point p, double value);
}
```

After (`Renderer`):

```java
interface Renderer {
    void renderCircle(Point center, double radius);
    void renderPolygon(List<Point> vertices);
    String name();
}
```

## 3. Keep classes and methods focused

`Shape` stores the renderer and defines the contract; each concrete shape computes only its own geometry. `Square.draw()` delegates vertex calculation to `vertices()`, leaving the drawing step short and easy to explain.

Before:

```java
public void draw() {
    // Calculate corners, choose an output format, format coordinates,
    // and print the rendering result here.
}
```

After (`Square.draw()`):

```java
public void draw() {
    renderer.renderPolygon(vertices());
}
```

## 4. Avoid duplication between renderers

The two concrete renderers should share a single coordinate-formatting helper when both need the same formatting rule. Keeping that rule in `Formatting.coords` means a change to coordinate formatting has one place to update, while each renderer remains responsible for its distinct output.

Before:

```java
// Repeated independently in both renderers:
String coordinates = String.format(Locale.ROOT, "(%.1f, %.1f)", p.x(), p.y());
```

After (shared package-private helper used by both renderers):

```java
final class Formatting {
    static String coords(Point point) {
        return String.format(Locale.ROOT, "%.1f,%.1f", point.x(), point.y());
    }
}
```

## 5. Extend either side without changing the other

`Renderer` exposes circle and polygon primitives instead of a method for each named shape. A `Triangle` can compute three vertices and call `renderPolygon`, while an `AsciiRenderer` can implement the existing interface; neither addition requires edits to the existing shape or renderer classes.

Before:

```java
interface Renderer {
    void renderSquare(Square square);
    void renderTriangle(Triangle triangle); // Every new shape changes this interface.
}
```

After (`Square.draw()` and `Renderer`):

```java
renderer.renderPolygon(vertices());
```

```java
void renderPolygon(List<Point> vertices);
```

Input validation in constructors and `scale` rejects invalid sizes early. `Point` is an immutable record, and a constructor accepting `PrintStream` makes each renderer's output testable.
