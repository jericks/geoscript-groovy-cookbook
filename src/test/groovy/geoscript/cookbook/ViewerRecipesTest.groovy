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

    @Test void drawToImageGeometries() {
        ViewerRecipes recipes = new ViewerRecipes()
        BufferedImage image = recipes.drawToImageGeometries()
        assertNotNull image
    }

    @Test void drawToFile() {
        ViewerRecipes recipes = new ViewerRecipes()
        File file = recipes.drawToFile()
        assertTrue file.exists()
    }

    @Test void plotToImage() {
        ViewerRecipes recipes = new ViewerRecipes()
        BufferedImage image = recipes.plotToImage()
        assertNotNull image
    }

    @Test void plotToImageGeometries() {
        ViewerRecipes recipes = new ViewerRecipes()
        BufferedImage image = recipes.plotToImageGeometries()
        assertNotNull image
    }

    @Test void plotToFile() {
        ViewerRecipes recipes = new ViewerRecipes()
        File file = recipes.plotToFile()
        assertTrue file.exists()
    }
}
