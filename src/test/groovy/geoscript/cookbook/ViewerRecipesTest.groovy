package geoscript.cookbook

import org.junit.Test
import java.awt.image.BufferedImage
import static org.junit.Assert.*

class ViewerRecipesTest {

    @Test void drawToImage() {
        ViewerRecipes recipes = new ViewerRecipes()
        BufferedImage image = recipes.drawToImage()
        assertNotNull image
    }

    @Test void drawToImageWithOptions() {
        ViewerRecipes recipes = new ViewerRecipes()
        BufferedImage image = recipes.drawToImageWithOptions()
        assertNotNull image
    }

}
