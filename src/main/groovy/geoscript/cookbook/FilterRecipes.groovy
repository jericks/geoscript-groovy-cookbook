package geoscript.cookbook

import geoscript.feature.Feature
import geoscript.feature.Field
import geoscript.filter.Color
import geoscript.filter.Expression
import geoscript.filter.Filter
import geoscript.filter.Function
import geoscript.filter.Property
import geoscript.geom.Bounds
import geoscript.geom.Geometry
import geoscript.geom.Point

import java.awt.image.BufferedImage

class FilterRecipes extends Recipes {

    // Filter

    Filter createFilterFromCql() {
        // tag::createFilterFromCql[]
        Filter filter = new Filter("name='Seattle'")
        println filter.toString()
        // end::createFilterFromCql[]
        writeFile("filter_from_cql","${filter}")
        filter
    }

    Filter createFilterFromXml() {
        // tag::createFilterFromXml[]
        Filter filter = new Filter("<filter><PropertyIsEqualTo><PropertyName>soilType</PropertyName><Literal>Mollisol</Literal></PropertyIsEqualTo></filter>")
        println filter.toString()
        // end::createFilterFromXml[]
        writeFile("filter_from_xml","${filter}")
        filter
    }

    Map<String,String> getCqlAndXmlFromFilter() {
        Map<String,String> values = [:]

        // tag::getCqlAndXmlFromFilter_cql[]
        Filter filter = new Filter("name='Seattle'")
        String cql = filter.cql
        println cql
        // end::getCqlAndXmlFromFilter_cql[]
        writeFile("filter_cql", "${cql}")
        values.put("cql", cql)

        // tag::getCqlAndXmlFromFilter_xml[]
        String xml = filter.xml
        println xml
        // end::getCqlAndXmlFromFilter_xml[]
        writeFile("filter_xml", "${xml}")
        values.put("xml", xml)

        values
    }

    Map<String, Boolean> evaluateFilters() {

        Map<String, Boolean> values = [:]

        // tag::evaluateFilters_isname[]
        Feature feature = new Feature([
                id: 1,
                name: "Seattle",
                geom: new Point(-122.3204, 47.6024)
        ], "city.1")

        Filter isNameFilter = new Filter("name='Seattle'")
        boolean isName = isNameFilter.evaluate(feature)
        println isName
        // end::evaluateFilters_isname[]
        writeFile("filter_evaluate_isname", "${isName}")
        values.put("isName", isName)

        // tag::evaluateFilters_isnotname[]
        Filter isNotNameFilter = new Filter("name='Tacoma'")
        boolean isNotName = isNotNameFilter.evaluate(feature)
        println isNotName
        // end::evaluateFilters_isnotname[]
        writeFile("filter_evaluate_isnotname", "${isNotName}")
        values.put("isNotName", isNotName)

        // tag::evaluateFilters_isid[]
        Filter isIdFilter = Filter.id("city.1")
        boolean isId = isIdFilter.evaluate(feature)
        println isId
        // end::evaluateFilters_isid[]
        writeFile("filter_evaluate_isid", "${isId}")
        values.put("isId", isId)

        // tag::evaluateFilters_isnotid[]
        Filter isNotIdFilter = Filter.id("city.2")
        boolean isNotId = isNotIdFilter.evaluate(feature)
        println isNotId
        // end::evaluateFilters_isnotid[]
        writeFile("filter_evaluate_isnotid", "${isNotId}")
        values.put("isNotId", isNotId)

        // tag::evaluateFilters_isbbox[]
        Filter isInBboxFilter = Filter.bbox("geom",  new Bounds(-132.539, 42.811, -111.796, 52.268))
        boolean isInBbox = isInBboxFilter.evaluate(feature)
        println isInBbox
        // end::evaluateFilters_isbbox[]
        writeFile("filter_evaluate_isbbox", "${isInBbox}")
        values.put("isInBbox", isInBbox)

        // tag::evaluateFilters_isnotbbox[]
        Filter isNotInBboxFilter = Filter.bbox("geom", new Bounds(-12.656, 18.979, 5.273, 34.597))
        boolean isNotInBbox = isNotInBboxFilter.evaluate(feature)
        println isNotInBbox
        // end::evaluateFilters_isnotbbox[]
        writeFile("filter_evaluate_isnotbbox", "${isNotInBbox}")
        values.put("isNotInBbox", isNotInBbox)

        values
    }

    // CQL

    Expression getLiteralNumberFromCQL() {
        // tag::getLiteralNumberFromCQL[]
        Expression expression = Expression.fromCQL("12")
        println expression
        // end::getLiteralNumberFromCQL[]
        writeFile("filter_cql_literal_number", "${expression}")
        expression
    }

    Expression getLiteralStringFromCQL() {
        // tag::getLiteralStringFromCQL[]
        Expression expression = Expression.fromCQL("'Washington'")
        println expression
        // end::getLiteralStringFromCQL[]
        writeFile("filter_cql_literal_string", "${expression}")
        expression
    }

    Expression getPropertyFromCql() {
        // tag::getPropertyFromCql[]
        Expression expression = Expression.fromCQL("NAME")
        println expression
        // end::getPropertyFromCql[]
        writeFile("filter_cql_property", "${expression}")
        expression
    }

    Expression getFunctionFromCql() {
        // tag::getFunctionFromCql[]
        Expression expression = Expression.fromCQL("centroid(the_geom)")
        println expression
        // end::getFunctionFromCql[]
        writeFile("filter_cql_function", "${expression}")
        expression
    }

    // Expression literals

    Expression createLiteralFromNumber() {
        // tag::createLiteralFromNumber[]
        Expression expression = new Expression(3.56)
        println expression
        // end::createLiteralFromNumber[]
        writeFile("filter_literal_create_number", "${expression}")
        expression
    }

    Expression createLiteralFromString() {
        // tag::createLiteralFromString[]
        Expression expression = new Expression("Seattle")
        println expression
        // end::createLiteralFromString[]
        writeFile("filter_literal_create_string", "${expression}")
        expression
    }

    Object evaluateLiteral() {
        // tag::evaluateLiteral[]
        Expression expression = new Expression(3.56)
        double number = expression.evaluate()
        println number
        // end::evaluateLiteral[]
        writeFile("filter_literal_evaluate", "${number}")
        number
    }

    // Property

    Property createPropertyFromString() {
        // tag::createPropertyFromString[]
        Property property = new Property("name")
        println property
        // end::createPropertyFromString[]
        writeFile("filter_property_create_string", "${property}")
        property
    }

    Property createPropertyFromField() {
        // tag::createPropertyFromField[]
        Field field = new Field("geom", "Polygon")
        Property property = new Property(field)
        println property
        // end::createPropertyFromField[]
        writeFile("filter_property_create_field", "${property}")
        property
    }

    Map<String,Object> evaluateProperty() {
        Map values = [:]

        // tag::evaluateProperty_1[]
        Feature feature = new Feature([
            id: 1,
            name: "Seattle",
            geom: new Point(-122.3204, 47.6024)
        ], "city.1")

        Property idProperty = new Property("id")
        int id = idProperty.evaluate(feature)
        println id
        // end::evaluateProperty_1[]
        writeFile("filter_property_evaluate_1", "${id}")
        values.put("id", id)

        // tag::evaluateProperty_2[]
        Property nameProperty = new Property("name")
        String name = nameProperty.evaluate(feature)
        println name
        // end::evaluateProperty_2[]
        writeFile("filter_property_evaluate_2", "${name}")
        values.put("name", name)

        // tag::evaluateProperty_3[]
        Property geomProperty = new Property("geom")
        Geometry geometry = geomProperty.evaluate(feature)
        println geometry
        // end::evaluateProperty_3[]
        writeFile("filter_property_evaluate_3", "${geometry}")
        values.put("geometry", geometry)

        values
    }

    // Function

    Function createFunctionFromCql() {
        // tag::createFunctionFromCql[]
        Function function = new Function("centroid(the_geom)")
        println function
        // end::createFunctionFromCql[]
        writeFile("filter_function_create_cql","${function}")
        function
    }

    Function createFromNameAndExpressions() {
        // tag::createFromNameAndExpressions[]
        Function function = new Function("centroid", new Property("the_geom"))
        println function
        // end::createFromNameAndExpressions[]
        writeFile("filter_function_create_name_expressions","${function}")
        function
    }

    Function createFromNameClosureAndExpressions() {
        // tag::createFromNameClosureAndExpressions[]
        Function function = new Function("my_centroid", {g-> g.centroid}, new Property("the_geom"))
        println function
        // end::createFromNameClosureAndExpressions[]
        writeFile("filter_function_create_name_closure_expressions","${function}")
        function
    }

    Function createFromCqlAndClosure() {
        // tag::createFromCqlAndClosure[]
        Function function = new Function("my_centroid(the_geom)", {g-> g.centroid})
        println function
        // end::createFromCqlAndClosure[]
        writeFile("filter_function_create_cql_closure","${function}")
        function
    }

    Map<String,Object> evaulateFunctions() {

        Map<String,Object> values = [:]

        // tag::evaulateFunctions_buffer[]
        Feature feature = new Feature([
                id: 1,
                name: "Seattle",
                geom: new Point(-122.3204, 47.6024)
        ], "city.1")

        Function bufferFunction = new Function("buffer(geom, 10)")
        Geometry polygon = bufferFunction.evaluate(feature)
        // end::evaulateFunctions_buffer[]
        drawGeometries("filter_function_evaluate_buffer", [polygon,feature.geom])
        values.put("polygon", polygon)

        // tag::evaulateFunctions_lowercase[]
        Function lowerCaseFunction = new Function("strToLowerCase(name)")
        String lowerCaseName = lowerCaseFunction.evaluate(feature)
        println lowerCaseName
        // end::evaulateFunctions_lowercase[]
        writeFile("filter_function_evaluate_lowercase", lowerCaseName)
        values.put("lowerCaseName", lowerCaseName)

        values
    }

    List<String> getFunctionNames() {
        // tag::getFunctionNames[]
        List<String> functionNames = Function.getFunctionNames()
        println "There are ${functionNames.size()} Functions:"
        functionNames.sort().subList(0,10).each { String name ->
            println name
        }
        // end::getFunctionNames[]
        writeFile("filter_function_names","There are ${functionNames.size()} Functions:${NEW_LINE}${functionNames.sort().subList(0,10).collect { it }.join(NEW_LINE)}")
        functionNames
    }

    // Color

    Color createColorFromRBGString() {
        // tag::createColorFromRBGString[]
        Color color = new Color("0,255,0")
        // end::createColorFromRBGString[]
        saveImage("filter_color_rbgstring", Color.drawToImage([color]))
        color
    }

    Color createColorFromCSSColorName() {
        // tag::createColorFromCSSColorName[]
        Color color = new Color("silver")
        // end::createColorFromCSSColorName[]
        saveImage("filter_color_csscolorname", Color.drawToImage([color]))
        color
    }

    Color createColorFromHexadecimal() {
        // tag::createColorFromHexadecimal[]
        Color color = new Color("#0000ff")
        // end::createColorFromHexadecimal[]
        saveImage("filter_color_hexadecimal", Color.drawToImage([color]))
        color
    }

    Color createColorFromRGBList() {
        // tag::createColorFromRGBList[]
        Color color = new Color([255,0,0])
        // end::createColorFromRGBList[]
        saveImage("filter_color_rgblist", Color.drawToImage([color]))
        color
    }

    Color createColorFromRGBMap() {
        // tag::createColorFromRGBMap[]
        Color color = new Color([r: 5, g: 35, b:45])
        // end::createColorFromRGBMap[]
        saveImage("filter_color_rgbmap", Color.drawToImage([color]))
        color
    }

    Color createColorFromHLSMap() {
        // tag::createColorFromHLSMap[]
        Color color = new Color([h: 0, s: 1.0, l: 0.5])
        // end::createColorFromHLSMap[]
        saveImage("filter_color_hlsmap", Color.drawToImage([color]))
        color
    }

    Color getRandomColor() {
        // tag::getRandomColor[]
        Color color = Color.getRandom()
        // end::getRandomColor[]
        saveImage("filter_color_random", Color.drawToImage([color]))
        color
    }

    Color getRandomPastelColor() {
        // tag::getRandomPastelColor[]
        Color color = Color.getRandomPastel()
        // end::getRandomPastelColor[]
        saveImage("filter_color_random_pastel", Color.drawToImage([color]))
        color
    }

    // Color Palettes

    Map<String, List<String>> getColorPalettes() {
        Map<String, List<String>> paletteMap = [:]

        // tag::getColorPalettes_all[]
        List<String> allPalettes = Color.getPaletteNames("all")
        allPalettes.each { String name ->
            println name
        }
        // end::getColorPalettes_all[]
        writeFile("getColorPalettes_all","${allPalettes.join(NEW_LINE)}")
        paletteMap.put('all', allPalettes)

        // tag::getColorPalettes_diverging[]
        List<String> divergingPalettes = Color.getPaletteNames("diverging")
        divergingPalettes.each { String name ->
            println name
        }
        // end::getColorPalettes_diverging[]
        writeFile("getColorPalettes_diverging","${divergingPalettes.join(NEW_LINE)}")
        paletteMap.put('diverging', divergingPalettes)

        // tag::getColorPalettes_qualitative[]
        List<String> qualitativePalettes = Color.getPaletteNames("qualitative")
        qualitativePalettes.each { String name ->
            println name
        }
        // end::getColorPalettes_qualitative[]
        writeFile("getColorPalettes_qualitative","${qualitativePalettes.join(NEW_LINE)}")
        paletteMap.put('qualitative', qualitativePalettes)

        // tag::getColorPalettes_sequential[]
        List<String> sequentialPalettes = Color.getPaletteNames("sequential")
        sequentialPalettes.each { String name ->
            println name
        }
        // end::getColorPalettes_sequential[]
        writeFile("getColorPalettes_sequential","${sequentialPalettes.join(NEW_LINE)}")
        paletteMap.put('sequential', sequentialPalettes)

        paletteMap
    }

    Map<String, List<Color>> getPaletteColors() {

        Map<String, List<Color>> colorMap = [:]

        // tag::getPaletteColors_BuGn[]
        List colors = Color.getPaletteColors("BuGn")
        // end::getPaletteColors_BuGn[]
        saveImage("color_palette_bugn", Color.drawToImage(colors, "horizontal"))
        colorMap.put("BuGn", colors)

        // tag::getPaletteColors_Purples[]
        colors = Color.getPaletteColors("Purples", 4)
        // end::getPaletteColors_Purples[]
        saveImage("color_palette_purples", Color.drawToImage(colors, "horizontal"))
        colorMap.put("Purples", colors)

        // tag::getPaletteColors_MutedTerrain[]
        colors = Color.getPaletteColors("MutedTerrain")
        // end::getPaletteColors_MutedTerrain[]
        saveImage("color_palette_mutedterrain", Color.drawToImage(colors, "horizontal"))
        colorMap.put("MutedTerrain", colors)

        // tag::getPaletteColors_BlueToYellowToRedHeatMap[]
        colors = Color.getPaletteColors("BlueToYellowToRedHeatMap")
        // end::getPaletteColors_BlueToYellowToRedHeatMap[]
        saveImage("color_palette_bluetoyellowtoredheatmap", Color.drawToImage(colors, "horizontal"))
        colorMap.put("BlueToYellowToRedHeatMap", colors)

        colorMap
    }

    // Color properties

    Map<String, String> getColorProperties() {

        Map<String,String> props = [:]

        // tag::getColorProperties_create[]
        Color color = new Color("wheat")
        // end::getColorProperties_create[]
        saveImage("getColorProperties", Color.drawToImage([color]))

        // tag::getColorProperties_hex[]
        String hex = color.hex
        println hex
        // end::getColorProperties_hex[]
        writeFile("getColorProperties_hex", "${hex}")
        props.put("hex", hex)

        // tag::getColorProperties_rgb[]
        List rgb = color.rgb
        println rgb
        // end::getColorProperties_rgb[]
        writeFile("getColorProperties_rgb", "${rgb}")
        props.put("rgb", rgb)

        // tag::getColorProperties_hsl[]
        List hsl = color.hsl
        println hsl
        // end::getColorProperties_hsl[]
        writeFile("getColorProperties_hsl", "${hsl}")
        props.put("hsl", hsl)

        // tag::getColorProperties_awt[]
        java.awt.Color awtColor = color.asColor()
        println awtColor
        // end::getColorProperties_awt[]
        writeFile("getColorProperties_awt", "${awtColor}")
        props.put("awt", awtColor)

        props
    }

    Color getDarkerColor() {
        // tag::getDarkerColor[]
        Color color = new Color("lightblue")
        Color darkerColor = color.darker()
        // end::getDarkerColor[]
        saveImage("getDarkerColor", Color.drawToImage([color, darkerColor], "horizontal"))
        darkerColor
    }

    Color getBrighterColor() {
        // tag::getBrighterColor[]
        Color color = new Color("purple")
        Color brigtherColor = color.brighter()
        // end::getBrighterColor[]
        saveImage("getBrighterColor", Color.drawToImage([color, brigtherColor], "horizontal"))
        brigtherColor
    }

    List<Color> interpolateColors() {
        // tag::interpolateColors[]
        Color startColor = new Color("red")
        Color endColor = new Color("green")
        List<Color> colors = startColor.interpolate(endColor, 10)
        // end::interpolateColors[]
        saveImage("filter_color_interpolate", Color.drawToImage(colors, "horizontal"))
        colors
    }

    List<Color> interpolateColorsStatic() {
        // tag::interpolateColorsStatic[]
        Color startColor = new Color("wheat")
        Color endColor = new Color("lightblue")
        List<Color> colors = Color.interpolate(startColor, endColor, 8)
        // end::interpolateColorsStatic[]
        saveImage("filter_color_interpolate_static", Color.drawToImage(colors, "horizontal"))
        colors
    }

    // Displaying Colors

    BufferedImage drawColorToImage() {
        // tag::drawColorToImage[]
        Color color = new Color("pink")
        BufferedImage image = Color.drawToImage(
                [color.brighter(), color, color.darker()],
                "vertical",
                40
        )
        // end::drawColorToImage[]
        saveImage("filter_color_draw2img", image)
        image
    }

    void drawColorToGui() {
        // tag::drawColorToGui[]
        List<Color> colors = Color.getPaletteColors("YlOrBr")
        Color.draw(colors, "horizontal", 50)
        // end::drawColorToGui[]
    }

}
