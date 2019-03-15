package geoscript.cookbook

import geoscript.filter.Color
import geoscript.filter.Expression
import geoscript.filter.Expressions
import geoscript.filter.Filter
import geoscript.filter.Function
import geoscript.filter.ProcessFunction
import geoscript.filter.Property
import geoscript.geom.Polygon
import org.junit.Test

import java.awt.image.BufferedImage

import static org.junit.Assert.*

class FilterRecipesTest {

    // Filter

    @Test void createFilterFromCql() {
        FilterRecipes recipes = new FilterRecipes()
        Filter filter = recipes.createFilterFromCql()
        assertEquals("[ name = Seattle ]", filter.toString())
    }

    @Test void createFilterFromXml() {
        FilterRecipes recipes = new FilterRecipes()
        Filter filter = recipes.createFilterFromXml()
        assertEquals("[ soilType = Mollisol ]", filter.toString())
    }

    @Test void createPassFilter() {
        FilterRecipes recipes = new FilterRecipes()
        Filter filter = recipes.createPassFilter()
        assertEquals("Filter.INCLUDE", filter.toString())
    }

    @Test void createFailFilter() {
        FilterRecipes recipes = new FilterRecipes()
        Filter filter = recipes.createFailFilter()
        assertEquals("Filter.EXCLUDE", filter.toString())
    }

    @Test void createBboxFilter() {
        FilterRecipes recipes = new FilterRecipes()
        Filter filter = recipes.createBboxFilter()
        assertEquals("[ the_geom bbox POLYGON ((-102 43.5, -102 47.5, -100 47.5, -100 43.5, -102 43.5)) ]", filter.toString())
    }

    @Test void createContainsFilter() {
        FilterRecipes recipes = new FilterRecipes()
        Filter filter = recipes.createContainsFilter()
        assertEquals("[ the_geom contains POLYGON ((-104 45, -95 45, -95 50, -104 50, -104 45)) ]", filter.toString())
    }

    @Test void createDistanceWithinFilter() {
        FilterRecipes recipes = new FilterRecipes()
        Filter filter = recipes.createDistanceWithinFilter()
        assertEquals("[ the_geom dwithin POINT (-100 47), distance: 10.2 ]", filter.toString())
    }

    @Test void createCrossesFilter() {
        FilterRecipes recipes = new FilterRecipes()
        Filter filter = recipes.createCrossesFilter()
    }

    @Test void createIntersectsFilter() {
        FilterRecipes recipes = new FilterRecipes()
        Filter filter = recipes.createIntersectsFilter()
        assertEquals("[ the_geom intersects POLYGON ((-104 45, -95 45, -95 50, -104 50, -104 45)) ]", filter.toString())
    }

    @Test void createIdFilter() {
        FilterRecipes recipes = new FilterRecipes()
        Filter filter = recipes.createIdFilter()
        assertEquals("[ points.1 ]", filter.toString())
    }

    @Test void createIdsFilter() {
        FilterRecipes recipes = new FilterRecipes()
        Filter filter = recipes.createIdsFilter()
        assertEquals("[ points.1, points.2, points.3 ]", filter.toString())
    }

    @Test void createEqualsFilter() {
        FilterRecipes recipes = new FilterRecipes()
        Filter filter = recipes.createEqualsFilter()
        assertEquals("[ name = Washington ]", filter.toString())
    }

    @Test void getCqlAndXmlFromFilter() {
        FilterRecipes recipes = new FilterRecipes()
        Map<String,String> values = recipes.getCqlAndXmlFromFilter()
        assertEquals("name = 'Seattle'", values.cql)
        assertEquals("""<ogc:Filter xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:gml="http://www.opengis.net/gml" xmlns:ogc="http://www.opengis.net/ogc">
<ogc:PropertyIsEqualTo>
<ogc:PropertyName>name</ogc:PropertyName>
<ogc:Literal>Seattle</ogc:Literal>
</ogc:PropertyIsEqualTo>
</ogc:Filter>
""".normalize(), values.xml.normalize())
    }

    @Test void evaluateFilters() {
        FilterRecipes recipes = new FilterRecipes()
        Map<String, Boolean> values = recipes.evaluateFilters()
        assertTrue(values.isName)
        assertFalse(values.isNotName)
        assertTrue(values.isId)
        assertFalse(values.isNotId)
        assertTrue(values.isInBbox)
        assertFalse(values.isNotInBbox)
    }

    @Test void combineFiltersWithAnd() {
        FilterRecipes recipes = new FilterRecipes()
        Filter filter = recipes.combineFiltersWithAnd()
        assertEquals("[[ city = Seattle ] AND [ state = WA ]]", filter.toString())
    }

    @Test void combineFiltersWithPlus() {
        FilterRecipes recipes = new FilterRecipes()
        Filter filter = recipes.combineFiltersWithPlus()
        assertEquals("[[ city = Seattle ] AND [ state = WA ]]", filter.toString())
    }

    @Test void combineFiltersWithOr() {
        FilterRecipes recipes = new FilterRecipes()
        Filter filter = recipes.combineFiltersWithOr()
        assertEquals("[[ city = Seattle ] OR [ city = Tacoma ]]", filter.toString())
    }

    @Test void notFilter() {
        FilterRecipes recipes = new FilterRecipes()
        Filter filter = recipes.notFilter()
        assertEquals("[ NOT [ city = Seattle ] ]", filter.toString())
    }

    @Test void negativeFilter() {
        FilterRecipes recipes = new FilterRecipes()
        Filter filter = recipes.negativeFilter()
        assertEquals("[ NOT [ city = Seattle ] ]", filter.toString())
    }

    @Test void simplifyFilter() {
        FilterRecipes recipes = new FilterRecipes()
        Filter filter = recipes.simplifyFilter()
        assertEquals("[ city = Seattle ]", filter.toString())
    }


    // CQL

    @Test void getLiteralNumberFromCQL() {
        FilterRecipes recipes = new FilterRecipes()
        Expression expression = recipes.getLiteralNumberFromCQL()
        assertEquals("12", expression.toString())
    }

    @Test void getLiteralStringFromCQL() {
        FilterRecipes recipes = new FilterRecipes()
        Expression expression = recipes.getLiteralStringFromCQL()
        assertEquals("Washington", expression.toString())
    }

    @Test void getPropertyFromCql() {
        FilterRecipes recipes = new FilterRecipes()
        Expression expression = recipes.getPropertyFromCql()
        assertEquals("NAME", expression.toString())
    }

    @Test void getFunctionFromCql() {
        FilterRecipes recipes = new FilterRecipes()
        Expression expression = recipes.getFunctionFromCql()
        assertEquals("centroid([the_geom])", expression.toString())
    }

    // Literal

    @Test void createLiteralFromNumber() {
        FilterRecipes recipes = new FilterRecipes()
        Expression expression = recipes.createLiteralFromNumber()
        assertEquals("3.56", expression.toString())
    }

    @Test void createLiteralFromString() {
        FilterRecipes recipes = new FilterRecipes()
        Expression expression = recipes.createLiteralFromString()
        assertEquals("Seattle", expression.toString())
    }

    @Test void evaluateLiteral() {
        FilterRecipes recipes = new FilterRecipes()
        Object value = recipes.evaluateLiteral()
        assertEquals(3.56, value as double, 0.01)
    }


    // Property

    @Test void createPropertyFromString() {
        FilterRecipes recipes = new FilterRecipes()
        Property property = recipes.createPropertyFromString()
        assertEquals("name", property.toString())
    }

    @Test void createPropertyFromField() {
        FilterRecipes recipes = new FilterRecipes()
        Property property = recipes.createPropertyFromField()
        assertEquals("geom", property.toString())
    }

    @Test void evaluateProperty() {
        FilterRecipes recipes = new FilterRecipes()
        Map<String, Object> values = recipes.evaluateProperty()
        assertEquals(1, values.id)
        assertEquals("Seattle", values.name)
        assertEquals("POINT (-122.3204 47.6024)", values.geometry.wkt)
    }

    // Function

    @Test void createFunctionFromCql() {
        FilterRecipes recipes = new FilterRecipes()
        Function function = recipes.createFunctionFromCql()
        assertEquals("centroid([the_geom])", function.toString())
    }

    @Test void createFromNameAndExpressions() {
        FilterRecipes recipes = new FilterRecipes()
        Function function = recipes.createFromNameAndExpressions()
        assertEquals("centroid([the_geom])", function.toString())
    }

    @Test void createFromNameClosureAndExpressions() {
        FilterRecipes recipes = new FilterRecipes()
        Function function = recipes.createFromNameClosureAndExpressions()
        assertEquals("my_centroid([the_geom])", function.toString())
    }

    @Test void createFromCqlAndClosure() {
        FilterRecipes recipes = new FilterRecipes()
        Function function = recipes.createFromCqlAndClosure()
        assertEquals("my_centroid([the_geom])", function.toString())
    }

    @Test void evaulateFunctions() {
        FilterRecipes recipes = new FilterRecipes()
        Map<String,Object> values = recipes.evaulateFunctions()
        assertTrue(values.polygon instanceof Polygon)
        assertEquals("seattle", values.lowerCaseName)
    }

    @Test void getFunctionNames() {
        FilterRecipes recipes = new FilterRecipes()
        List<String> names = recipes.getFunctionNames()
        assertTrue(names.size() > 0)
        assertTrue(names.contains("buffer"))
    }

    // Process Function

    @Test void createFunctionProcess() {
        FilterRecipes recipes = new FilterRecipes()
        Function function = recipes.createFunctionProcess()
        assertNotNull(function)
        assertTrue(function instanceof Function)
    }

    @Test void createProcessFunction() {
        FilterRecipes recipes = new FilterRecipes()
        Function function = recipes.createProcessFunction()
        assertNotNull(function)
        assertTrue(function instanceof ProcessFunction)
    }

    // Color

    @Test void createColorFromRBGString() {
        FilterRecipes recipes = new FilterRecipes()
        Color color = recipes.createColorFromRBGString()
        assertEquals("#00ff00", color.hex)
    }

    @Test void createColorFromCSSColorName() {
        FilterRecipes recipes = new FilterRecipes()
        Color color = recipes.createColorFromCSSColorName()
        assertEquals("#c0c0c0", color.hex)
    }

    @Test void createColorFromHexadecimal() {
        FilterRecipes recipes = new FilterRecipes()
        Color color = recipes.createColorFromHexadecimal()
        assertEquals("#0000ff", color.hex)
    }

    @Test void createColorFromRGBList() {
        FilterRecipes recipes = new FilterRecipes()
        Color color = recipes.createColorFromRGBList()
        assertEquals("#ff0000", color.hex)
    }

    @Test void createColorFromRGBMap() {
        FilterRecipes recipes = new FilterRecipes()
        Color color = recipes.createColorFromRGBMap()
        assertEquals("#05232d", color.hex)
    }

    @Test void createColorFromHLSMap() {
        FilterRecipes recipes = new FilterRecipes()
        Color color = recipes.createColorFromHLSMap()
        assertEquals("#ff0000", color.hex)
    }

    @Test void getRandomColor() {
        FilterRecipes recipes = new FilterRecipes()
        Color color = recipes.getRandomColor()
        assertNotNull(color)
    }

    @Test void getRandomPastelColor() {
        FilterRecipes recipes = new FilterRecipes()
        Color color = recipes.getRandomPastelColor()
        assertNotNull(color)
    }

    // Color Palettes

    @Test void getColorPalettes() {
        FilterRecipes recipes = new FilterRecipes()
        Map<String, List<String>> palettes = recipes.getColorPalettes()
        assertTrue(palettes.containsKey('all'))
        assertTrue(palettes.containsKey('diverging'))
        assertTrue(palettes.containsKey('qualitative'))
        assertTrue(palettes.containsKey('sequential'))
    }

    @Test void getPaletteColors() {
        FilterRecipes recipes = new FilterRecipes()
        Map<String, List<Color>> palettes = recipes.getPaletteColors()
        assertTrue(palettes.containsKey('BuGn'))
        assertTrue(palettes.containsKey('Purples'))
        assertTrue(palettes.containsKey('MutedTerrain'))
        assertTrue(palettes.containsKey('BlueToYellowToRedHeatMap'))
    }

    @Test void getColorProperties() {
        FilterRecipes recipes = new FilterRecipes()
        Map<String, String> colors = recipes.getColorProperties()
        assertEquals("#f5deb3", colors.hex)
        assertEquals([245, 222, 179], colors.rgb)
        assertEquals(0.10858, colors.hsl[0], 0.001)
        assertEquals(0.76744, colors.hsl[1], 0.001)
        assertEquals(0.83137, colors.hsl[2], 0.001)
        assertNotNull(colors.awt)
    }

    // Manipulating Colors

    @Test void getDarkerColor() {
        FilterRecipes recipes = new FilterRecipes()
        Color color = recipes.getDarkerColor()
        assertEquals("#7997a1", color.hex)
    }

    @Test void getBrighterColor() {
        FilterRecipes recipes = new FilterRecipes()
        Color color = recipes.getBrighterColor()
        assertEquals("#b600b6", color.hex)
    }

    @Test void interpolateColors() {
        FilterRecipes recipes = new FilterRecipes()
        List<Color> colors = recipes.interpolateColors()
        assertEquals(10, colors.size())
    }

    @Test void interpolateColorsStatic() {
        FilterRecipes recipes = new FilterRecipes()
        List<Color> colors = recipes.interpolateColorsStatic()
        assertEquals(8, colors.size())
    }

    // Displaying colors

    @Test void drawColorToImage() {
        FilterRecipes recipes = new FilterRecipes()
        BufferedImage image = recipes.drawColorToImage()
        assertNotNull(image)
    }

    // Static Imports
    @Test
    void staticImports() {
        FilterRecipes recipes = new FilterRecipes()
        List<Expressions> expressions = recipes.staticImports()
        assertTrue(expressions[0] instanceof Expression)
        assertTrue(expressions[1] instanceof Color)
        assertTrue(expressions[2] instanceof Property)
        assertTrue(expressions[3] instanceof Function)
    }
    
}