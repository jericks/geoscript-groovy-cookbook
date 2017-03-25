package geoscript.cookbook

import geoscript.geom.Bounds
import geoscript.layer.Layer
import geoscript.style.Fill
import geoscript.style.Shape
import geoscript.style.Stroke
import geoscript.style.Symbolizer
import geoscript.workspace.GeoPackage
import geoscript.workspace.Workspace

class StyleRecipes extends Recipes {

    // Stroke

    Stroke createStrokeWithColor() {
        // tag::createStrokeWithColor[]
        Stroke stroke = new Stroke("#1E90FF")
        // end::createStrokeWithColor[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer rivers = workspace.get("rivers")
        rivers.style = stroke
        drawOnBasemap("style_stroke_color", [rivers], new Bounds(-169.541016,29.382175,-45.615234,68.236823))
        stroke
    }

    Stroke createStrokeWithColorAndWidth() {
        // tag::createStrokeWithColorAndWidth[]
        Stroke stroke = new Stroke("#1E90FF", 0.5)
        // end::createStrokeWithColorAndWidth[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer rivers = workspace.get("rivers")
        rivers.style = stroke
        drawOnBasemap("style_stroke_color_width", [rivers], new Bounds(-169.541016,29.382175,-45.615234,68.236823))
        stroke
    }

    Stroke createStrokeWithDashes() {
        // tag::createStrokeWithDashes[]
        Stroke stroke = new Stroke("#1E90FF", 0.75, [5,5], "round", "bevel")
        // end::createStrokeWithDashes[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer rivers = workspace.get("rivers")
        rivers.style = stroke
        drawOnBasemap("style_stroke_dashes", [rivers], new Bounds(-169.541016,29.382175,-45.615234,68.236823))
        stroke
    }

    // Fill

    Fill createFillWithColor() {
        // tag::createFillWithColor[]
        Fill fill = new Fill("#6B8E23")
        // end::createFillWithColor[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer states = workspace.get("states")
        states.style = fill
        drawOnBasemap("style_fill_color", [states], states.bounds.expandBy(3.0))
        fill
    }

    Symbolizer createFillWithStroke() {
        // tag::createFillWithStroke[]
        Symbolizer symbolizer = new Fill("#6B8E23") + new Stroke("black", 0.1)
        // end::createFillWithStroke[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer states = workspace.get("states")
        states.style = symbolizer
        drawOnBasemap("style_fill_stroke", [states], states.bounds.expandBy(3.0))
        symbolizer
    }

    Fill createFillWithColorAndOpacity() {
        // tag::createFillWithColorAndOpacity[]
        Fill fill = new Fill("#6B8E23", 0.35)
        // end::createFillWithColorAndOpacity[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer states = workspace.get("states")
        states.style = fill
        drawOnBasemap("style_fill_color_opacity", [states], states.bounds.expandBy(3.0))
        fill
    }

    Fill createFillWithMap() {
        // tag::createFillWithMap[]
        Fill fill = new Fill(color: "wheat", opacity: 0.75)
        // end::createFillWithMap[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer states = workspace.get("states")
        states.style = fill
        drawOnBasemap("style_fill_map", [states], states.bounds.expandBy(3.0))
        fill
    }

    Fill createFillWithIcon() {
        // tag::createFillWithIcon[]
        Fill fill = new Fill("green").icon('src/main/resources/trees.png', 'image/png')
        // end::createFillWithIcon[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer states = workspace.get("states")
        states.style = fill
        drawOnBasemap("style_fill_icon", [states], states.bounds.expandBy(3.0))
        fill
    }

    Fill createFillWithHatch() {
        // tag::createFillWithHatch[]
        Fill fill = new Fill("green").hatch("slash", new Stroke("green", 0.25), 8)
        // end::createFillWithHatch[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer states = workspace.get("states")
        states.style = fill
        drawOnBasemap("style_fill_hatch", [states], states.bounds.expandBy(3.0))
        fill
    }

    Symbolizer createFillWithRandom() {
        // tag::createFillWithRandom[]
        Symbolizer symbolizer = new Fill("white").hatch("circle", new Fill("black"), 2).random(
            random: "free",
            seed: 0,
            symbolCount: 50,
            tileSize: 50,
            rotation: "none"
        ) + new Stroke("black", 0.25)
        // end::createFillWithRandom[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer states = workspace.get("states")
        states.style = symbolizer
        drawOnBasemap("style_fill_random", [states], states.bounds.expandBy(3.0))
        symbolizer
    }

    // Shape

    Shape createShapeWithColor() {
        // tag::createShapeWithColor[]
        Shape shape = new Shape("navy")
        // end::createShapeWithColor[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer places = workspace.get("places")
        places.style = shape
        drawOnBasemap("style_shape_color", [places])
        shape
    }

    Shape createShape() {
        // tag::createShape[]
        Shape shape = new Shape("#9370DB", 8, "triangle", 0.75, 45)
        // end::createShape[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer places = workspace.get("places")
        places.style = shape
        drawOnBasemap("style_shape", [places])
        shape
    }

    Shape createShapeWithNamedParams() {
        // tag::createShapeWithNamedParams[]
        Shape shape = new Shape(color: "#8B4513", size: 10, type: "star", opacity: 1.0, rotation: 0)
        // end::createShapeWithNamedParams[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer places = workspace.get("places")
        places.style = shape
        drawOnBasemap("style_shape_params", [places])
        shape
    }

    Symbolizer createShapeWithStroke() {
        // tag::createShapeWithStroke[]
        Symbolizer symbolizer = new Shape("white", 10).stroke("navy", 0.5)
        // end::createShapeWithStroke[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer places = workspace.get("places")
        places.style = symbolizer
        drawOnBasemap("style_shape_stroke", [places])
        symbolizer
    }



}
