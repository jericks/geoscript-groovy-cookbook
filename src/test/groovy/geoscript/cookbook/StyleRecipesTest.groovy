package geoscript.cookbook

import geoscript.style.Fill
import geoscript.style.Symbolizer
import org.junit.Test
import static org.junit.Assert.*

class StyleRecipesTest {

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

}
