package geoscript.cookbook

import geoscript.style.Fill
import geoscript.style.Shape
import geoscript.style.Stroke
import geoscript.style.Symbolizer
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

    @Test void createStrokeWithDashes() {
        StyleRecipes recipes = new StyleRecipes()
        Stroke stroke = recipes.createStrokeWithDashes()
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


}
