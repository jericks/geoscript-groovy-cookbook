package geoscript.cookbook

import org.junit.Test
import java.awt.image.BufferedImage
import static org.junit.Assert.assertNotNull

class CartoRecipesTest {

    @Test void map() {
        CartoRecipes recipes = new CartoRecipes()
        BufferedImage image = recipes.map()
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

}
