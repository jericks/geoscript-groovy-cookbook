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

}
