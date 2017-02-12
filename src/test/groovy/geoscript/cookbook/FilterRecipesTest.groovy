package geoscript.cookbook

import geoscript.filter.Color
import geoscript.filter.Expression
import geoscript.filter.Function
import geoscript.filter.Property
import geoscript.geom.Polygon
import org.junit.Test

import java.awt.image.BufferedImage

import static org.junit.Assert.*

class FilterRecipesTest {

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

}
