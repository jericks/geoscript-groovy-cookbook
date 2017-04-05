package geoscript.cookbook

import geoscript.style.Fill
import geoscript.style.Shape
import geoscript.style.Stroke
import geoscript.style.Symbolizer
import geoscript.style.io.Reader
import geoscript.style.io.Writer
import org.junit.Test
import static org.junit.Assert.*

class StyleRecipesTest {

    // Stroke

    @Test void createStrokeWithColor() {
        StyleRecipes recipes = new StyleRecipes()
        Stroke stroke = recipes.createStrokeWithColor()
        assertNotNull(stroke)
    }

    @Test void createStrokeWithColorAndWidth() {
        StyleRecipes recipes = new StyleRecipes()
        Stroke stroke = recipes.createStrokeWithColorAndWidth()
        assertNotNull(stroke)
    }

    @Test void createStrokeWithCasing() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer stroke = recipes.createStrokeWithCasing()
        assertNotNull(stroke)
    }

    @Test void createStrokeWithDashes() {
        StyleRecipes recipes = new StyleRecipes()
        Stroke stroke = recipes.createStrokeWithDashes()
        assertNotNull(stroke)
    }

    @Test void createStrokeWithHatch() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer stroke = recipes.createStrokeWithHatch()
        assertNotNull(stroke)
    }

    @Test void createStrokeWithSpacedSymbols() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer stroke = recipes.createStrokeWithSpacedSymbols()
        assertNotNull(stroke)
    }
    
    @Test void createStrokeWithAlternatingSymbols() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer stroke = recipes.createStrokeWithAlternatingSymbols()
        assertNotNull(stroke)
    }

    // Fill

    @Test void createFillWithColor() {
        StyleRecipes recipes = new StyleRecipes()
        Fill fill = recipes.createFillWithColor()
        assertNotNull(fill)
    }

    @Test void createFillWithStroke() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createFillWithStroke()
        assertNotNull(symbolizer)
    }

    @Test void createFillWithColorAndOpacity() {
        StyleRecipes recipes = new StyleRecipes()
        Fill fill = recipes.createFillWithColorAndOpacity()
        assertNotNull(fill)
    }

    @Test void createFillWithMap() {
        StyleRecipes recipes = new StyleRecipes()
        Fill fill = recipes.createFillWithMap()
        assertNotNull(fill)
    }

    @Test void createFillWithIcon() {
        StyleRecipes recipes = new StyleRecipes()
        Fill fill = recipes.createFillWithIcon()
        assertNotNull(fill)
    }

    @Test void createFillWithHatch() {
        StyleRecipes recipes = new StyleRecipes()
        Fill fill = recipes.createFillWithHatch()
        assertNotNull(fill)
    }

    @Test void createFillWithRandom() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer fill = recipes.createFillWithRandom()
        assertNotNull(fill)
    }

    // Shape

    @Test void createShapeWithColor() {
        StyleRecipes recipes = new StyleRecipes()
        Shape shape = recipes.createShapeWithColor()
        assertNotNull(shape)
    }

    @Test void createShape() {
        StyleRecipes recipes = new StyleRecipes()
        Shape shape = recipes.createShape()
        assertNotNull(shape)
    }

    @Test void createShapeWithNamedParams() {
        StyleRecipes recipes = new StyleRecipes()
        Shape shape = recipes.createShapeWithNamedParams()
        assertNotNull(shape)
    }

    @Test void createShapeWithStroke() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createShapeWithStroke()
        assertNotNull(symbolizer)
    }

    // Icon

    @Test void createIcon() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createIcon()
        assertNotNull(symbolizer)
    }

    @Test void createIconWithParams() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createIconWithParams()
        assertNotNull(symbolizer)
    }

    // Labels

    @Test void createLabelForPoints() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createLabelForPoints()
        assertNotNull(symbolizer)
    }

    @Test void createLabelForPolygons() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createLabelForPolygons()
        assertNotNull(symbolizer)
    }

    @Test void createLabelForLines() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createLabelForLines()
        assertNotNull(symbolizer)
    }

    // Gradient

    @Test void createGradientOnFieldWithQuantile() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createGradientOnFieldWithQuantile()
        assertNotNull(symbolizer)
    }

    @Test void createGradientOnFieldWithEqualInterval() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createGradientOnFieldWithEqualInterval()
        assertNotNull(symbolizer)
    }

    @Test void createGradientCustom() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createGradientCustom()
        assertNotNull(symbolizer)
    }

    // Unique Values

    @Test void createUniqueValues() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createUniqueValues()
        assertNotNull(symbolizer)
    }

    @Test void createUniqueValuesWithClosure() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createUniqueValuesWithClosure()
        assertNotNull(symbolizer)
    }

    @Test void createUniqueValuesWithPalette() {
        StyleRecipes recipes = new StyleRecipes()
        Symbolizer symbolizer = recipes.createUniqueValuesWithPalette()
        assertNotNull(symbolizer)
    }

    // Style IO

    @Test void listStyleReaders() {
        StyleRecipes recipes = new StyleRecipes()
        List<Reader> readers = recipes.listStyleReaders()
        assertTrue(readers.size() > 0)
    }

    @Test void findStyleReader() {
        StyleRecipes recipes = new StyleRecipes()
        Reader reader = recipes.findStyleReader()
        assertNotNull(reader)
    }

    @Test void listStyleWriters() {
        StyleRecipes recipes = new StyleRecipes()
        List<Writer> writers = recipes.listStyleWriters()
        assertTrue(writers.size() > 0)
    }

    @Test void findStyleWriter() {
        StyleRecipes recipes = new StyleRecipes()
        Writer writer = recipes.findStyleWriter()
        assertNotNull(writer)
    }

}
