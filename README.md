# Shape–Renderer Bridge

A Java 17 console example of the Bridge design pattern (Assignment 3, Option A). Shapes own their geometry and delegate drawing primitives to a `Renderer`. The same shape can switch renderers at runtime.

| Bridge role | Project type |
| --- | --- |
| Abstraction | `Shape` |
| Refined abstractions | `Circle`, `Square` |
| Implementor | `Renderer` |
| Concrete implementors | `VectorRenderer`, `RasterRenderer` |
| Client | `Main` |

## Project layout

```text
src/main/java/bridge/                 Client demo
src/main/java/bridge/abstraction/     Shape hierarchy
src/main/java/bridge/implementor/     Renderer hierarchy and Point
src/test/java/bridge/                 JUnit tests and RecordingRenderer
docs/                                 UML, Clean Code notes, defense guide
```

## Build and run

Use JDK 17 or newer and Maven:

```sh
mvn test
mvn -q compile exec:java -Dexec.mainClass=bridge.Main
```

In PowerShell, quote the property argument: `mvn -q compile exec:java '-Dexec.mainClass=bridge.Main'`. You can also run `bridge.Main` directly from IntelliJ IDEA.

## Demo output

The following is the actual output from `bridge.Main`:

```text
1. Composition at runtime
[Vector] <circle cx="10.0" cy="10.0" r="5.0"/>
[Raster] Filling pixels inside polygon with 4 vertices: (0.0,0.0) (4.0,0.0) (4.0,4.0) (0.0,4.0)
2. Switching implementation without changing the abstraction
[Raster] Filling pixels of circle: center=(10.0, 10.0), radius=5.0 px
[Vector] <polygon points="0.0,0.0 4.0,0.0 4.0,4.0 0.0,4.0"/>
3. Full matrix
Circle with Vector:
[Vector] <circle cx="10.0" cy="10.0" r="5.0"/>
Circle with Raster:
[Raster] Filling pixels of circle: center=(10.0, 10.0), radius=5.0 px
Square with Vector:
[Vector] <polygon points="0.0,0.0 4.0,0.0 4.0,4.0 0.0,4.0"/>
Square with Raster:
[Raster] Filling pixels inside polygon with 4 vertices: (0.0,0.0) (4.0,0.0) (4.0,4.0) (0.0,4.0)
4. Scale by 2.0
[Raster] Filling pixels of circle: center=(10.0, 10.0), radius=10.0 px
[Raster] Filling pixels inside polygon with 4 vertices: (0.0,0.0) (8.0,0.0) (8.0,8.0) (0.0,8.0)
```

See [the UML source](docs/uml.puml), [Clean Code notes](docs/clean-code-notes.md), and [defense guide](docs/defense-cheatsheet.md).
