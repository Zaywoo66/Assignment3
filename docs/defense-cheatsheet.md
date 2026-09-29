# Bridge defense cheatsheet

## One-minute explanation

Bridge separates **what** is drawn (`Shape`, `Circle`, `Square`) from **how** it is rendered (`Renderer`, `VectorRenderer`, `RasterRenderer`). `Shape` holds a `Renderer` field and delegates the low-level drawing operation to it. The two sides can therefore vary independently.

Without the Bridge, two shapes and two rendering styles could require four combined classes such as `VectorCircle` and `RasterSquare`. With the Bridge, they require two shape classes and two renderer classes. For *S* shapes and *R* renderers, the combined design tends toward *S × R* classes; the Bridge uses *S + R* concrete classes.

## Live demonstration

1. Run `bridge.Main` and show the first pair: a circle with vector output and a square with raster output.
2. Point to `Shape.renderer` and `Shape.setRenderer(Renderer)`. The renderer field is the bridge; changing it changes the implementation for the same shape object.
3. Show the next output after `setRenderer` swaps the implementations. Then show the 2 × 2 matrix to demonstrate every shape-renderer combination.
4. Show `scale(2.0)` followed by `draw()`: the figure owns its size, and the renderer decides how that size appears in output.

Composition matters because `Shape` **has a** `Renderer`, rather than inheriting from a concrete renderer. A figure can receive another renderer at runtime, and adding a rendering style does not create a new subclass for each figure.

## Bridge and Adapter

Bridge is designed up front to let two related hierarchies grow independently. Adapter is usually introduced afterward to make existing incompatible interfaces work together. Both may delegate through an object, but they solve different design problems.

## Likely questions

1. **Where is the Bridge?** In the `protected Renderer renderer` field of `Shape`; `draw()` in each concrete shape delegates to that interface.
2. **Why is `Shape` abstract?** It defines the shared renderer reference and contract, while `Circle` and `Square` supply their own geometry and drawing calls.
3. **Why is `Renderer` an interface?** Both output styles support the same low-level operations, so shapes can use either through one stable type.
4. **Why does `Renderer` have `renderPolygon` instead of `renderSquare`?** The interface represents drawing primitives. Another polygon shape can reuse that method without changing the interface.
5. **What happens when you call `setRenderer`?** The same shape keeps its geometry; subsequent `draw()` calls go to the new renderer.
6. **How would you add `Triangle`?** Extend `Shape`, calculate three vertices, and delegate to `renderer.renderPolygon(vertices())`; existing renderers stay untouched.
7. **How would you add `AsciiRenderer`?** Implement `Renderer`'s circle, polygon, and name methods; existing shapes stay untouched.
8. **Who owns scaling?** A concrete shape updates its own radius or side. A renderer receives the updated geometry when `draw()` is called.
9. **How do the tests verify the Bridge?** `RecordingRenderer` captures calls and parameters, including calls after a renderer switch, without depending on console output.
10. **What is the tradeoff?** Bridge adds classes and an extra delegation step. It is most useful when the abstraction and implementation are genuinely independent dimensions.
