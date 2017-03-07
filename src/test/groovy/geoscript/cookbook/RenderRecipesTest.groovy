package geoscript.cookbook

import geoscript.render.Map
import java.awt.image.BufferedImage
import org.junit.Test
import static org.junit.Assert.*

class RenderRecipesTest {

    @Test void createMap() {
        RenderRecipes recipes = new RenderRecipes()
        Map map = recipes.createMap()
        assertNotNull(map)
    }

    // Image

    @Test void renderToImage() {
        RenderRecipes recipes = new RenderRecipes()
        BufferedImage image = recipes.renderToImage()
        assertNotNull(image)
    }

    @Test void renderToFile() {
        RenderRecipes recipes = new RenderRecipes()
        File file = recipes.renderToFile()
        assertNotNull(file)
    }

    // PNG

    @Test void renderToPngImage() {
        RenderRecipes recipes = new RenderRecipes()
        BufferedImage image = recipes.renderToPngImage()
        assertNotNull(image)
    }

    @Test void renderToPngFile() {
        RenderRecipes recipes = new RenderRecipes()
        File file = recipes.renderToPngFile()
        assertNotNull(file)
    }

    // JPEG

    @Test void renderToJpegImage() {
        RenderRecipes recipes = new RenderRecipes()
        BufferedImage image = recipes.renderToJpegImage()
        assertNotNull(image)
    }

    @Test void renderToJpegFile() {
        RenderRecipes recipes = new RenderRecipes()
        File file = recipes.renderToJpegFile()
        assertNotNull(file)
    }

    // GIF

    @Test void renderToGifImage() {
        RenderRecipes recipes = new RenderRecipes()
        BufferedImage image = recipes.renderToGifImage()
        assertNotNull(image)
    }

    @Test void renderToGifFile() {
        RenderRecipes recipes = new RenderRecipes()
        File file = recipes.renderToGifFile()
        assertNotNull(file)
    }

    // GeoTIFF

    @Test void renderToGeoTiffImage() {
        RenderRecipes recipes = new RenderRecipes()
        BufferedImage image = recipes.renderToGeoTiffImage()
        assertNotNull(image)
    }

    @Test void renderToGeoTiffFile() {
        RenderRecipes recipes = new RenderRecipes()
        File file = recipes.renderToGeoTiffFile()
        assertNotNull(file)
    }

}
