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
    }
}