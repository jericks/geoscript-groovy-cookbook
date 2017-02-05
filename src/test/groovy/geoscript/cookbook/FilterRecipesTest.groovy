package geoscript.cookbook

import geoscript.filter.Color
import org.junit.Test
import static org.junit.Assert.*

class FilterRecipesTest {

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

}
