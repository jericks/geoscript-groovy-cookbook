package geoscript.cookbook

import org.junit.Test
import java.awt.image.BufferedImage
import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertTrue

class CartoRecipesTest {

    @Test void map() {
        CartoRecipes recipes = new CartoRecipes()
        BufferedImage image = recipes.map()
        assertNotNull(image)
    }

    @Test void overViewMap() {
        CartoRecipes recipes = new CartoRecipes()
        BufferedImage image = recipes.overViewMap()
        assertNotNull(image)
    }

    @Test void text() {
        CartoRecipes recipes = new CartoRecipes()
        BufferedImage image = recipes.text()
        assertNotNull(image)
    }

    @Test void rectangle() {
        CartoRecipes recipes = new CartoRecipes()
        BufferedImage image = recipes.rectangle()
        assertNotNull(image)
    }

    @Test void northArrow() {
        CartoRecipes recipes = new CartoRecipes()
        BufferedImage image = recipes.northArrow()
        assertNotNull(image)
    }

    @Test void northArrow2() {
        CartoRecipes recipes = new CartoRecipes()
        BufferedImage image = recipes.northArrow2()
        assertNotNull(image)
    }

    @Test void legendFromMap() {
        CartoRecipes recipes = new CartoRecipes()
        BufferedImage image = recipes.legendFromMap()
        assertNotNull(image)
    }

    @Test void dateText() {
        CartoRecipes recipes = new CartoRecipes()
        BufferedImage image = recipes.dateText()
        assertNotNull(image)
    }

    @Test void scaleText() {
        CartoRecipes recipes = new CartoRecipes()
        BufferedImage image = recipes.scaleText()
        assertNotNull(image)
    }

    @Test void line() {
        CartoRecipes recipes = new CartoRecipes()
        BufferedImage image = recipes.line()
        assertNotNull(image)
    }

    @Test void grid() {
        CartoRecipes recipes = new CartoRecipes()
        BufferedImage image = recipes.grid()
        assertNotNull(image)
    }

    @Test void paragraph() {
        CartoRecipes recipes = new CartoRecipes()
        BufferedImage image = recipes.paragraph()
        assertNotNull(image)
    }

    @Test void image() {
        CartoRecipes recipes = new CartoRecipes()
        BufferedImage image = recipes.image()
        assertNotNull(image)
    }

    @Test void imageBuilder() {
        CartoRecipes recipes = new CartoRecipes()
        BufferedImage image = recipes.imageBuilder()
        assertNotNull(image)
    }

    @Test void pdfBuilder() {
        CartoRecipes recipes = new CartoRecipes()
        File file = recipes.pdfBuilder()
        assertNotNull(file)
        assertTrue(file.length() > 0)
    }

    @Test void svgBuilder() {
        CartoRecipes recipes = new CartoRecipes()
        File file = recipes.svgBuilder()
        assertNotNull(file)
        assertTrue(file.length() > 0)
    }

}
