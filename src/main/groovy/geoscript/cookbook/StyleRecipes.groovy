package geoscript.cookbook

import geoscript.filter.Color
import geoscript.filter.Expression
import geoscript.filter.Function
import geoscript.filter.Property
import geoscript.geom.Bounds
import geoscript.geom.GeometryCollection
import geoscript.layer.Format
import geoscript.layer.GeoTIFF
import geoscript.layer.Layer
import geoscript.layer.MBTiles
import geoscript.layer.Raster
import geoscript.process.Process
import geoscript.style.ChannelSelection
import geoscript.style.ColorMap
import geoscript.style.Composite
import geoscript.style.ContrastEnhancement
import geoscript.style.DatabaseStyleRepository
import geoscript.style.DirectoryStyleRepository
import geoscript.style.Fill
import geoscript.style.Font
import geoscript.style.Gradient
import geoscript.style.Halo
import geoscript.style.Hatch
import geoscript.style.Icon
import geoscript.style.Label
import geoscript.style.NestedDirectoryStyleRepository
import geoscript.style.Shape
import geoscript.style.Stroke
import geoscript.style.Style
import geoscript.style.StyleRepository
import geoscript.style.Symbolizer
import geoscript.style.Transform
import geoscript.style.UniqueValues
import geoscript.style.io.CSSReader
import geoscript.style.io.ColorTableReader
import geoscript.style.io.ColorTableWriter
import geoscript.style.io.Reader
import geoscript.style.io.Readers
import geoscript.style.io.SLDReader
import geoscript.style.io.SLDWriter
import geoscript.style.io.SimpleStyleReader
import geoscript.style.io.UniqueValuesReader
import geoscript.style.io.Writer
import geoscript.style.io.Writers
import geoscript.style.io.YSLDReader
import geoscript.style.io.YSLDWriter
import geoscript.workspace.Directory
import geoscript.workspace.GeoPackage
import geoscript.workspace.H2
import geoscript.workspace.Workspace
import groovy.sql.Sql

class StyleRecipes extends Recipes {

    // Basics

    Symbolizer createBasicSymbolizer() {
        // tag::createBasicSymbolizer[]
        Fill fill = new Fill("#6B8E23")
        // end::createBasicSymbolizer[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer states = workspace.get("states")
        states.style = fill
        drawOnBasemap("style_basic_symbolizer", [states], states.bounds.expandBy(3.0))
        fill
    }

    Composite createBasicComposite() {
        // tag::createBasicComposite[]
        Composite composite = new Fill("#6B8E23") + new Stroke("black", 0.75)
        // end::createBasicComposite[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer states = workspace.get("states")
        states.style = composite
        drawOnBasemap("style_basic_composite", [states], states.bounds.expandBy(3.0))
        composite
    }

    // Title

    List<String> createWithTitle() {
        // tag::createWithTitle[]
        Fill fill = new Fill("#6B8E23").title("States")
        println "${fill.title}"
        Composite composite = new Fill("#6B8E23") + new Stroke("black", 0.75).title("States with Outline")
        println "${composite.title}"
        // end::createWithTitle[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer states = workspace.get("states")
        states.style = fill
        drawOnBasemap("style_basic_symbolizer_title1", [states], states.bounds.expandBy(3.0))
        states.style = composite
        drawOnBasemap("style_basic_symbolizer_title2", [states], states.bounds.expandBy(3.0))
        writeFile("style_basic_symbolizer_title", "${fill.title}\n${composite.title}")
        [fill.title, composite.title]
    }

    // Where

    Symbolizer createBasicSymbolizerWithWhere() {
        // tag::createBasicSymbolizerWithWhere[]
        Symbolizer symbolizer = new Fill("#ffffcc").where("POP_EST < 4504128.33") +
                new Fill("#41b6c4").where("POP_EST BETWEEN 4504128.33 AND 16639804.33") +
                new Fill("#253494").where("POP_EST > 16639804.33")
        // end::createBasicSymbolizerWithWhere[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = symbolizer
        drawOnBasemap("style_basic_symbolizer_where", [countries])
        symbolizer
    }

    // Zindex

    Symbolizer createBasicSymbolizerWithZindex() {
        // tag::createBasicSymbolizerWithZindex[]
        Symbolizer symbolizer = new Stroke("black", 2.0).zindex(0) + new Stroke("white", 0.1).zindex(1)
        // end::createBasicSymbolizerWithZindex[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer states = workspace.get("states")
        states.style = symbolizer
        drawOnBasemap("style_basic_symbolizer_zindex", [states], states.bounds.expandBy(2.0))
        symbolizer
    }

    // Scale
    
    Symbolizer createBasicSymbolizerWithScale() {
        // tag::createBasicSymbolizerWithScale[]
        Symbolizer symbolizer = (new Fill("white") + new Stroke("black", 0.1)) + new Label("name")
                .point(anchor: [0.5,0.5])
                .polygonAlign("mbr")
                .range(max: 16000000)
        // end::createBasicSymbolizerWithScale[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer states = workspace.get("states")
        states.style = symbolizer
        drawOnBasemap("style_basic_symbolizer_scale1", [states], states.bounds.expandBy(3.0))
        drawOnBasemap("style_basic_symbolizer_scale2", [states], new Bounds(-105, 35, -95, 45))
        symbolizer
    }

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

    Symbolizer createStrokeWithCasing() {
        // tag::createStrokeWithCasing[]
        Symbolizer stroke = new Stroke(color: "#333333", width: 5, cap: "round").zindex(0) +
                new Stroke(color: "#6699FF", width: 3, cap: "round").zindex(1)
        // end::createStrokeWithCasing[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer rivers = workspace.get("rivers")
        rivers.style = stroke
        drawOnBasemap("style_stroke_casing", [rivers], new Bounds(-169.541016,29.382175,-45.615234,68.236823))
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

    Symbolizer createStrokeWithHatch() {
        // tag::createStrokeWithHatch[]
        Symbolizer stroke = new Stroke("#1E90FF", 1) + new Hatch("vertline", new Stroke("#1E90FF", 0.5), 6).zindex(1)
        // end::createStrokeWithHatch[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer rivers = workspace.get("rivers")
        rivers.style = stroke
        drawOnBasemap("style_stroke_hatch", [rivers], new Bounds(-169.541016,29.382175,-45.615234,68.236823))
        stroke
    }

    Symbolizer createStrokeWithSpacedSymbols() {
        // tag::createStrokeWithSpacedSymbols[]
        Symbolizer stroke = new Stroke(width: 0, dash: [4, 4]).shape(new Shape("#1E90FF", 6, "circle").stroke("navy", 0.75))
        // end::createStrokeWithSpacedSymbols[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer rivers = workspace.get("rivers")
        rivers.style = stroke
        drawOnBasemap("style_stroke_space_symbols", [rivers], new Bounds(-107.402344,24.786735,-76.420898,38.307181))
        stroke
    }

    Symbolizer createStrokeWithAlternatingSymbols() {
        // tag::createStrokeWithAlternatingSymbols[]
        Symbolizer stroke = new Stroke("#0000FF", 1, [10,10]).zindex(0) + new Stroke(null, 0, [[5,15],7.5])
                .shape(new Shape(null, 5, "circle").stroke("#000033",1)).zindex(1)
        // end::createStrokeWithAlternatingSymbols[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer rivers = workspace.get("rivers")
        rivers.style = stroke
        drawOnBasemap("style_stroke_alternating_symbols", [rivers], new Bounds(-171.123047,56.145550,-109.160156,70.199994))
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

    Symbolizer createFillWithRecodeFunction() {
        // tag::createFillWithRecodeFunction[]
        Function recodeFunction = new Function("Recode(region," +
                "'West','#B0C4DE'," +
                "'South','#00FFFF'," +
                "'Northeast','#9ACD32'," +
                "'Midwest','#6495ED')")
        Symbolizer symbolizer = new Fill(recodeFunction) + new Stroke("#999999",0.1) + new Label("postal").point([0.5,0.5])
        // end::createFillWithRecodeFunction[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer states = workspace.get("states")
        states.style = symbolizer
        drawOnBasemap("style_fill_recode_function", [states], states.bounds.expandBy(3.0))
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

    // Icon

    Symbolizer createIcon() {
        // tag::createIcon[]
        Symbolizer symbolizer = new Icon("src/main/resources/place.png", "image/png", 12)
        // end::createIcon[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer places = workspace.get("places")
        places.style = symbolizer
        drawOnBasemap("style_icon", [places])
        symbolizer
    }

    Symbolizer createIconWithParams() {
        // tag::createIconWithParams[]
        Symbolizer symbolizer = new Icon(url: "src/main/resources/place.png", format: "image/png", size: 10)
        // end::createIconWithParams[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer places = workspace.get("places")
        places.style = symbolizer
        drawOnBasemap("style_icon_params", [places])
        symbolizer
    }

    // Labels

    Symbolizer createLabelForPoints() {
        // tag::createLabelForPoints[]
        Symbolizer symbolizer = new Shape("blue", 6).stroke("navy", 0.5) + new Label("NAME").point(
                [0.5,0.5],  // <1>
                [0, 5.0],   // <2>
                0           // <3>
        )
        // end::createLabelForPoints[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer places = workspace.get("places")
        places.style = symbolizer
        drawOnBasemap("style_label_points", [places], new Bounds(-169.541016,29.382175,-45.615234,68.236823))
        symbolizer
    }

    Symbolizer createLabelForPointsWithFont() {
        // tag::createLabelForPointsWithFont[]
        Symbolizer symbolizer = new Shape("blue", 6).stroke("navy", 0.5) + new Label("NAME").point(
                [0.5,0.5],
                [0, 5.0],
                0
        ) + new Font(
                "normal",   // <1>
                "bold",     // <2>
                12,         // <3>
                "Arial"     // <4>
        )
        // end::createLabelForPointsWithFont[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer places = workspace.get("places")
        places.style = symbolizer
        drawOnBasemap("style_label_points_font", [places], new Bounds(-169.541016,29.382175,-45.615234,68.236823))
        symbolizer
    }

    Symbolizer createLabelForPointsWithHalo() {
        // tag::createLabelForPointsWithHalo[]
        Symbolizer symbolizer = new Shape("blue", 6).stroke("navy", 0.5) + new Label("NAME").point(
                [0.5,0.5],
                [0, 5.0],
                0
        ).fill(new Fill("white")) + new Halo(new Fill("navy"), 2.5)
        // end::createLabelForPointsWithHalo[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer places = workspace.get("places")
        places.style = symbolizer
        drawOnBasemap("style_label_points_halo", [places], new Bounds(-169.541016,29.382175,-45.615234,68.236823))
        symbolizer
    }

    Symbolizer createLabelForPolygons() {
        // tag::createLabelForPolygons[]
        Symbolizer symbolizer = new Fill("white") + new Stroke("black", 0.1) + new Label("name")
            .point(anchor: [0.5,0.5])
            .polygonAlign("mbr")
        // end::createLabelForPolygons[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer states = workspace.get("states")
        states.style = symbolizer
        drawOnBasemap("style_label_polygons", [states], new Bounds(-137.416992,40.896906,-105.842285,51.835778))
        symbolizer
    }

    Symbolizer createLabelForPolygonsWithExpression() {
        // tag::createLabelForPolygonsWithExpression[]
        Symbolizer symbolizer = new Fill("white") + new Stroke("black", 0.1) + new Label(Expression.fromCQL("strToLowerCase(name)"))
                .point(anchor: [0.5,0.5])
                .polygonAlign("mbr")
        // end::createLabelForPolygonsWithExpression[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer states = workspace.get("states")
        states.style = symbolizer
        drawOnBasemap("style_label_polygons_expression", [states], new Bounds(-137.416992,40.896906,-105.842285,51.835778))
        symbolizer
    }

    Symbolizer createLabelForPolygonsWithStrikeThrough() {
        // tag::createLabelForPolygonsWithStrikeThrough[]
        Symbolizer symbolizer = new Fill("white") + new Stroke("black", 0.1) + new Label("name")
                .point(anchor: [0.5,0.5])
                .polygonAlign("mbr")
                .strikethrough(true)
        // end::createLabelForPolygonsWithStrikeThrough[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer states = workspace.get("states")
        states.style = symbolizer
        drawOnBasemap("style_label_polygons_strikethrough", [states], new Bounds(-137.416992,40.896906,-105.842285,51.835778))
        symbolizer
    }

    Symbolizer createLabelForPolygonsWithUnderline() {
        // tag::createLabelForPolygonsWithUnderline[]
        Symbolizer symbolizer = new Fill("white") + new Stroke("black", 0.1) + new Label("name")
                .point(anchor: [0.5,0.5])
                .polygonAlign("mbr")
                .underline(true)
        // end::createLabelForPolygonsWithUnderline[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer states = workspace.get("states")
        states.style = symbolizer
        drawOnBasemap("style_label_polygons_underline", [states], new Bounds(-137.416992,40.896906,-105.842285,51.835778))
        symbolizer
    }

    Symbolizer createLabelForPolygonsWithSpacing() {
        // tag::createLabelForPolygonsWithSpacing[]
        Symbolizer symbolizer = new Fill("white") + new Stroke("black", 0.1) + new Label("name")
                .point(anchor: [0.5,0.5])
                .polygonAlign("mbr")
                .wordSpacing(8)
                .characterSpacing(5)
        // end::createLabelForPolygonsWithSpacing[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer states = workspace.get("states")
        states.style = symbolizer
        drawOnBasemap("style_label_polygons_spacing", [states], new Bounds(-116.608887,40.027614,-85.012207,51.399206))
        symbolizer
    }

    Symbolizer createLabelForLines() {
        // tag::createLabelForLines[]
        Symbolizer symbolizer = new Stroke("blue", 0.75)  + new Label("name")
                .fill(new Fill("navy"))
                .linear(follow: true, offset: 50, displacement: 200, repeat: 150).maxDisplacement(400).maxAngleDelta(90)
                .halo(new Fill("white"), 2.5)
                .font(new Font(size: 10, weight: "bold"))
        // end::createLabelForLines[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer rivers = workspace.get("rivers")
        rivers.style = symbolizer
        drawOnBasemap("style_label_lines", [rivers], new Bounds(-137.416,40.896,-105.842,51.835))
        symbolizer
    }

    Symbolizer createLabelWithExpression() {
        File file = new File("src/main/resources/tiles.mbtiles")
        MBTiles mbtiles = new MBTiles(file)
        Layer layer = mbtiles.getLayer(mbtiles.tiles(1))
        // tag::createLabelWithExpression[]
        Expression expression = Expression.fromCQL("Concatenate(z, '/', x, '/', y)")
        Symbolizer symbolizer = new Stroke("black", 1.0) + new Label(expression)
        // end::createLabelWithExpression[]
        layer.style = symbolizer
        drawOnBasemapInWebMercator("createLabelWithExpression", [layer], mbtiles.bounds)
        symbolizer
    }


    // Gradient

    Symbolizer createGradientOnFieldWithQuantile() {
        // tag::createGradientOnFieldWithQuantile[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        Gradient gradient = new Gradient(countries, "POP_EST", "quantile", 8, "Greens")
        countries.style = gradient
        // end::createGradientOnFieldWithQuantile[]
        drawOnBasemap("style_gradient_field_quantile", [countries])
        gradient
    }

    Symbolizer createGradientOnFieldWithEqualInterval() {
        // tag::createGradientOnFieldWithEqualInterval[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        Gradient gradient = new Gradient(countries, "POP_EST", "equalinterval", 3, "Reds")
        countries.style = gradient
        // end::createGradientOnFieldWithEqualInterval[]
        drawOnBasemap("style_gradient_field_equalinterval", [countries])
        gradient
    }

    Symbolizer createGradientCustom() {
        // tag::createGradientCustom[]
        Gradient gradient = new Gradient(
                new Property("POP2020"),
                [0, 10000, 20000, 30000],
                [
                        new Shape("white", 4).stroke("black", 0.5),
                        new Shape("#b0d2e8", 8).stroke("black", 0.5),
                        new Shape("#3e8ec4", 16).stroke("black", 0.5),
                        new Shape("#08306b", 24).stroke("black", 0.5)
                ],
                5,
                "linear"
        )
        // end::createGradientCustom[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer places = workspace.get("places")
        places.style = gradient
        drawOnBasemap("style_gradient_custom", [places])
        gradient
    }

    // UniqueValues

    Symbolizer createUniqueValues() {
        // tag::createUniqueValues[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        UniqueValues uniqueValues = new UniqueValues(countries, "NAME")
        countries.style = uniqueValues
        // end::createUniqueValues[]
        drawOnBasemap("style_uniquevalues", [countries])
        uniqueValues
    }

    Symbolizer createUniqueValuesWithClosure() {
        // tag::createUniqueValuesWithClosure[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        UniqueValues uniqueValues = new UniqueValues(countries, "NAME", {int index, String value -> Color.getRandom()})
        countries.style = uniqueValues
        // end::createUniqueValuesWithClosure[]
        drawOnBasemap("style_uniquevalues_closure", [countries])
        uniqueValues
    }

    Symbolizer createUniqueValuesWithPalette() {
        // tag::createUniqueValuesWithPalette[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        UniqueValues uniqueValues = new UniqueValues(countries, "NAME", "LightPurpleToDarkPurpleHeatMap")
        countries.style = uniqueValues
        // end::createUniqueValuesWithPalette[]
        drawOnBasemap("style_uniquevalues_palette", [countries])
        uniqueValues
    }

    Symbolizer createUniqueValuesReader() {
        // tag::createUniqueValuesReader[]
        Workspace workspace = new Directory("src/main/resources/mars")
        Layer marsGeology = workspace.get("geo_units_oc_dd")

        File uniqueValuesFile = new File("src/main/resources/mars/I1802ABC_geo_units_RGBlut.txt")
        UniqueValuesReader styleReader = new UniqueValuesReader("UnitSymbol", "polygon")
        Symbolizer symbolizer = styleReader.read(uniqueValuesFile)
        // end::createUniqueValuesReader[]
        marsGeology.style = symbolizer
        writeFile("style_mars_geology", uniqueValuesFile.text.substring(0,200))
        draw("style_mars_geology", [marsGeology])
        symbolizer
    }

    // Transform

    Symbolizer createNormalTransform() {
        // tag::createNormalTransform[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        Symbolizer symbolizer = new Transform("centroid(the_geom)") +
                new Shape(color: "red", size: 10, type: "star")
        countries.style = symbolizer
        // end::createNormalTransform[]
        drawOnBasemap("style_transform_normal", [countries])
        symbolizer
    }

    Symbolizer createRenderingTransform() {
        // tag::createRenderingTransform[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer places = workspace.get("places")
        Process process = new Process("convexhull",
                "Create a convexhull around the features",
                [features: geoscript.layer.Cursor],
                [result: geoscript.layer.Cursor],
                { inputs ->
                    def geoms = new GeometryCollection(inputs.features.collect{ f -> f.geom})
                    def output = new Layer()
                    output.add([geoms.convexHull])
                    [result: output]
                }
        )
        Function function = new Function(process, new Function("parameter", new Expression("features")))
        Symbolizer symbolizer =  new Transform(function, Transform.RENDERING) + new Fill("aqua", 0.75) + new Stroke("navy", 0.5)
        places.style = symbolizer
        // end::createRenderingTransform[]
        drawOnBasemap("style_transform_rendering", [workspace.get("places"),places])
        symbolizer
    }


    // Raster

    Symbolizer createRasterColorMap() {
        // tag::createRasterColorMap[]
        Format format = new GeoTIFF(new File('src/main/resources/pc.tif'))
        Raster raster = format.read()
        ColorMap colorMap = new ColorMap([
                [color: "#9fd182", quantity:25],
                [color: "#3e7f3c", quantity:470],
                [color: "#133912", quantity:920],
                [color: "#08306b", quantity:1370],
                [color: "#fffff5", quantity:1820],
        ])
        raster.style = colorMap
        // end::createRasterColorMap[]
        draw("style_raster_colormap_colors", [raster], raster.bounds)
        colorMap
    }

    Symbolizer createRasterColorMapWithOpacity() {
        // tag::createRasterColorMapWithOpacity[]
        Format format = new GeoTIFF(new File('src/main/resources/pc.tif'))
        Raster raster = format.read()
        ColorMap colorMap = new ColorMap([
                [color: "#9fd182", quantity:25],
                [color: "#3e7f3c", quantity:470],
                [color: "#133912", quantity:920],
                [color: "#08306b", quantity:1370],
                [color: "#fffff5", quantity:1820],
        ]).opacity(0.25)
        raster.style = colorMap
        // end::createRasterColorMapWithOpacity[]
        draw("style_raster_colormap_colors_opacity", [raster], raster.bounds)
        colorMap
    }

    Symbolizer createRasterColorMapWithPalette() {
        // tag::createRasterColorMapWithPalette[]
        Format format = new GeoTIFF(new File('src/main/resources/pc.tif'))
        Raster raster = format.read()
        ColorMap colorMap = new ColorMap(
                25,             // <1>
                1820,           // <2>
                "MutedTerrain", // <3>
                5               // <4>
        )
        println colorMap
        raster.style = colorMap
        // end::createRasterColorMapWithPalette[]
        draw("style_raster_colormap_palette", [raster], raster.bounds)
        colorMap
    }

    Symbolizer createRasterColorMapWithIntervals() {
        // tag::createRasterColorMapWithIntervals[]
        Format format = new GeoTIFF(new File('src/main/resources/pc.tif'))
        Raster raster = format.read()
        ColorMap colorMap = new ColorMap([
                [color: "#9fd182", quantity:25],
                [color: "#3e7f3c", quantity:470],
                [color: "#133912", quantity:920],
                [color: "#08306b", quantity:1370],
                [color: "#fffff5", quantity:1820],
        ], "intervals", true)
        raster.style = colorMap
        // end::createRasterColorMapWithIntervals[]
        draw("style_raster_colormap_intervals", [raster], raster.bounds)
        colorMap
    }

    Symbolizer createRasterChannelContrastEnhancementNormalize() {
        // tag::createRasterChannelContrastEnhancementNormalize[]
        Format format = new GeoTIFF(new File('src/main/resources/pc.tif'))
        Raster raster = format.read()
        Symbolizer symbolizer = new ChannelSelection()
                .gray("1", new ContrastEnhancement("normalize"))
        raster.style = symbolizer
        // end::createRasterChannelContrastEnhancementNormalize[]
        draw("style_raster_channel_constrast_normalize", [raster], raster.bounds)
        symbolizer
    }

    Symbolizer createRasterChannelContrastEnhancementHistogram() {
        // tag::createRasterChannelContrastEnhancementHistogram[]
        Format format = new GeoTIFF(new File('src/main/resources/pc.tif'))
        Raster raster = format.read()
        Symbolizer symbolizer = new ChannelSelection()
                .gray("1", new ContrastEnhancement("histogram", 0.65))
        raster.style = symbolizer
        // end::createRasterChannelContrastEnhancementHistogram[]
        draw("style_raster_channel_constrast_histogram", [raster], raster.bounds)
        symbolizer
    }

    // Style IO

    List<Reader> listStyleReaders() {
        // tag::listStyleReaders[]
        List<Reader> readers = Readers.list()
        readers.each { Reader reader ->
            println reader.class.simpleName
        }
        // end::listStyleReaders[]
        writeFile("style_readers_list", "${readers.collect{it.class.simpleName}.join(NEW_LINE)}")
        readers
    }

    Reader findStyleReader() {
        // tag::findStyleReader[]
        Reader reader = Readers.find("sld")
        println reader.class.simpleName
        // end::findStyleReader[]
        writeFile("style_readers_find", "${reader.class.simpleName}")
        reader
    }

    List<Writer> listStyleWriters() {
        // tag::listStyleWriters[]
        List<Writer> writers = Writers.list()
        writers.each { Writer writer ->
            println writer.class.simpleName
        }
        // end::listStyleWriters[]
        writeFile("style_writers_list", "${writers.collect{it.class.simpleName}.join(NEW_LINE)}")
        writers
    }

    Writer findStyleWriter() {
        // tag::findStyleWriter[]
        Writer writer = Writers.find("sld")
        println writer.class.simpleName
        // end::findStyleWriter[]
        writeFile("style_writers_find", "${writer.class.simpleName}")
        writer
    }

    // SLD

    String writeSld() {
        // tag::writeSld[]
        Symbolizer symbolizer = new Fill("white") + new Stroke("black", 0.5)
        SLDWriter writer = new SLDWriter()
        String sld = writer.write(symbolizer)
        println sld
        // end::writeSld[]
        writeFile("style_write_sld", sld)
        sld
    }

    String writeSldWithNamedLayer() {
        // tag::writeSldWithNamedLayer[]
        Symbolizer symbolizer = new Fill("white") + new Stroke("black", 0.5)
        SLDWriter writer = new SLDWriter()
        String sld = writer.write(symbolizer, type: "NamedLayer") // <1>
        println sld
        // end::writeSldWithNamedLayer[]
        writeFile("style_write_namedlayer_sld", sld)
        sld
    }

    Style readSld() {
        // tag::readSld[]
        String sld = """<?xml version="1.0" encoding="UTF-8"?>
<sld:StyledLayerDescriptor xmlns="http://www.opengis.net/sld" xmlns:sld="http://www.opengis.net/sld" xmlns:ogc="http://www.opengis.net/ogc" xmlns:gml="http://www.opengis.net/gml" version="1.0.0">
  <sld:UserLayer>
    <sld:LayerFeatureConstraints>
      <sld:FeatureTypeConstraint/>
    </sld:LayerFeatureConstraints>
    <sld:UserStyle>
      <sld:Name>Default Styler</sld:Name>
      <sld:FeatureTypeStyle>
        <sld:Name>name</sld:Name>
        <sld:Rule>
          <sld:PolygonSymbolizer>
            <sld:Fill>
              <sld:CssParameter name="fill">#ffffff</sld:CssParameter>
            </sld:Fill>
          </sld:PolygonSymbolizer>
          <sld:LineSymbolizer>
            <sld:Stroke>
              <sld:CssParameter name="stroke">#000000</sld:CssParameter>
              <sld:CssParameter name="stroke-width">0.5</sld:CssParameter>
            </sld:Stroke>
          </sld:LineSymbolizer>
        </sld:Rule>
      </sld:FeatureTypeStyle>
    </sld:UserStyle>
  </sld:UserLayer>
</sld:StyledLayerDescriptor>
"""
        SLDReader reader = new SLDReader()
        Style style = reader.read(sld)

        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = style
        // end::readSld[]
        drawOnBasemap("style_read_sld", [countries])
        style
    }

    // CSS

    Style readCss() {
        // tag::readCss[]
        String css = """
* {
   fill: #eeeeee;
   fill-opacity: 1.0;
   stroke: #000000;
   stroke-width: 1.2;
}
"""
        CSSReader reader = new CSSReader()
        Style style = reader.read(css)

        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = style
        // end::readCss[]
        drawOnBasemap("style_read_css", [countries])
        style
    }

    // YSLD

    String writeYSLD() {
        // tag::writeYSLD[]
        Symbolizer symbolizer = new Fill("white") + new Stroke("black", 0.5)
        YSLDWriter writer = new YSLDWriter()
        String ysld = writer.write(symbolizer)
        println ysld
        // end::writeYSLD[]
        writeFile("style_write_ysld", ysld)
        ysld
    }

    Style readYSLD() {
        // tag::readYSLD[]
        String ysld = """
name: Default Styler
feature-styles:
- name: name
  rules:
  - scale: [min, max]
    symbolizers:
    - polygon:
        fill-color: '#FFFFFF'
    - line:
        stroke-color: '#000000'
        stroke-width: 0.5
"""
        YSLDReader reader = new YSLDReader()
        Style style = reader.read(ysld)

        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = style
        // end::readYSLD[]
        drawOnBasemap("style_read_ysld", [countries])
        style
    }

    // Simple

    Style readSimpleStyleFillStrokeString() {
        // tag::readSimpleStyleFillStrokeString[]
        String str = "fill=#555555 fill-opacity=0.6 stroke=#555555 stroke-width=0.5"
        SimpleStyleReader reader = new SimpleStyleReader()
        Style style = reader.read(str)

        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = style
        // end::readSimpleStyleFillStrokeString[]
        drawOnBasemap("style_read_fill_stroke_str", [countries])
        style
    }

    Style readSimpleStyleFillStrokeAndLabelString() {
        // tag::readSimpleStyleFillStrokeAndLabelString[]
        String str = "fill=white stroke=navy label=NAME label-size=10"
        SimpleStyleReader reader = new SimpleStyleReader()
        Style style = reader.read(str)

        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = style
        // end::readSimpleStyleFillStrokeAndLabelString[]
        drawOnBasemap("style_read_fill_stroke_label_str", [countries], new Bounds(-4.416504,39.215231,27.180176,50.764259,"EPSG:4326"))
        style
    }

    Style readSimpleStyleShapeString() {
        // tag::readSimpleStyleShapeString[]
        String str = "shape-type=circle shape-size=8 shape=orange"
        SimpleStyleReader reader = new SimpleStyleReader()
        Style style = reader.read(str)
        println style

        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer places = workspace.get("places")
        places.style = style
        // end::readSimpleStyleShapeString[]
        drawOnBasemap("style_read_shape_str", [places])
        style
    }

    Style readSimpleStyleFillStrokeMap() {
        // tag::readSimpleStyleFillStrokeMap[]
        Map map = [
                'fill': '#555555',
                'fill-opacity': 0.6,
                'stroke': '#555555',
                'stroke-width': 0.5
        ]
        SimpleStyleReader reader = new SimpleStyleReader()
        Style style = reader.read(map)

        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = style
        // end::readSimpleStyleFillStrokeMap[]
        drawOnBasemap("style_read_fill_stroke_map", [countries])
        style
    }

    // ColorTable

    String writeColorTable() {
        // tag::writeColorTable[]
        ColorMap colorMap = new ColorMap(25, 1820, "BoldLandUse", 5)
        ColorTableWriter writer = new ColorTableWriter()
        String str = writer.write(colorMap)
        println str
        // end::writeColorTable[]
        writeFile("style_colortable_writer_str", str)
        str
    }

    Symbolizer readColorTable() {
        // tag::readColorTable[]
        Format format = new GeoTIFF(new File('src/main/resources/pc.tif'))
        Raster raster = format.read()
        ColorTableReader reader = new ColorTableReader()
        ColorMap colorMap = reader.read("""25.0 178 156 195
473.75 79 142 187
922.5 143 146 56
1371.25 193 132 55
1820.0 181 214 177
""")
        raster.style = colorMap
        // end::readColorTable[]
        draw("style_colortable_read_str", [raster], raster.bounds)
        colorMap
    }

    // Style Repository

    StyleRepository useDirectoryStyleRepository() {

        // tag::useDirectoryStyleRepository[]
        File directory = new File("target/styles")
        directory.mkdir()
        File file = new File("src/main/resources/states.sld")

        StyleRepository styleRepository = new DirectoryStyleRepository(directory)
        styleRepository.save("states", "states", file.text)

        String sld = styleRepository.getDefaultForLayer("states")
        println sld

        List<Map<String, String>> stylesForLayer = styleRepository.getForLayer("states")
        println stylesForLayer

        List<Map<String, String>> allStyles = styleRepository.getAll()
        println stylesForLayer

        styleRepository.delete("states","states")
        // end::useDirectoryStyleRepository[]
        writeFile("style_directory_style_repo", """
${sld}

${stylesForLayer}

${allStyles}
""")
        styleRepository
    }

    StyleRepository useNestedDirectoryStyleRepository() {

        // tag::useNestedDirectoryStyleRepository[]
        File directory = new File("target/styles")
        directory.mkdir()
        File file = new File("src/main/resources/states.sld")

        StyleRepository styleRepository = new NestedDirectoryStyleRepository(directory)
        styleRepository.save("states", "states", file.text)

        String sld = styleRepository.getDefaultForLayer("states")
        println sld

        List<Map<String, String>> stylesForLayer = styleRepository.getForLayer("states")
        println stylesForLayer

        List<Map<String, String>> allStyles = styleRepository.getAll()
        println stylesForLayer

        styleRepository.delete("states","states")
        // end::useNestedDirectoryStyleRepository[]
        writeFile("style_nested_directory_style_repo", """
${sld}

${stylesForLayer}

${allStyles}
""")
        styleRepository
    }

    StyleRepository useSqliteDatabaseStyleRepository() {

        // tag::useSqliteDatabaseStyleRepository[]
        File databaseFile = new File("target/styles_sqlite.db")
        File file = new File("src/main/resources/states.sld")

        Sql sql = Sql.newInstance("jdbc:sqlite:${databaseFile.absolutePath}", "org.sqlite.JDBC")
        StyleRepository styleRepository = DatabaseStyleRepository.forSqlite(sql)
        styleRepository.save("states", "states", file.text)

        String sld = styleRepository.getDefaultForLayer("states")
        println sld

        List<Map<String, String>> stylesForLayer = styleRepository.getForLayer("states")
        println stylesForLayer

        List<Map<String, String>> allStyles = styleRepository.getAll()
        println stylesForLayer

        styleRepository.delete("states","states")
        // end::useSqliteDatabaseStyleRepository[]
        writeFile("style_sqlite_database_style_repo", """
${sld}

${stylesForLayer}

${allStyles}
""")
        styleRepository
    }

    StyleRepository useH2DatabaseStyleRepository() {

        // tag::useH2DatabaseStyleRepository[]
        File databaseFile = new File("target/styles_h2.db")
        File file = new File("src/main/resources/states.sld")

        H2 h2 = new H2(databaseFile)
        Sql sql = h2.sql
        StyleRepository styleRepository = DatabaseStyleRepository.forH2(sql)
        styleRepository.save("states", "states", file.text)

        String sld = styleRepository.getDefaultForLayer("states")
        println sld

        List<Map<String, String>> stylesForLayer = styleRepository.getForLayer("states")
        println stylesForLayer

        List<Map<String, String>> allStyles = styleRepository.getAll()
        println stylesForLayer

        styleRepository.delete("states","states")
        // end::useH2DatabaseStyleRepository[]
        writeFile("style_h2_database_style_repo", """
${sld}

${stylesForLayer}

${allStyles}
""")
        styleRepository
    }

    StyleRepository usePostGISDatabaseStyleRepository() {

        // tag::usePostGISDatabaseStyleRepository[]
        File databaseFile = new File("target/styles_h2.db")
        File file = new File("src/main/resources/states.sld")

        Sql sql = Sql.newInstance("jdbc:postgres://localhost/world", "user", "pass", "org.postgresql.Driver")
        StyleRepository styleRepository = DatabaseStyleRepository.forPostgres(sql)
        styleRepository.save("states", "states", file.text)

        String sld = styleRepository.getDefaultForLayer("states")
        println sld

        List<Map<String, String>> stylesForLayer = styleRepository.getForLayer("states")
        println stylesForLayer

        List<Map<String, String>> allStyles = styleRepository.getAll()
        println stylesForLayer

        styleRepository.delete("states","states")
        // end::usePostGISDatabaseStyleRepository[]
        writeFile("style_postgis_database_style_repo", """
${sld}

${stylesForLayer}

${allStyles}
""")
        styleRepository
    }

}
