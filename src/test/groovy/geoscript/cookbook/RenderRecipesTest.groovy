package geoscript.cookbook

import geoscript.render.Displayer
import geoscript.render.Map
import geoscript.render.Renderer

import java.awt.image.BufferedImage
import org.junit.Test
import static org.junit.Assert.*

class RenderRecipesTest {

    @Test void createMap() {
        RenderRecipes recipes = new RenderRecipes()
        Map map = recipes.createMap()
        assertNotNull(map)
    }

    // Renderer

    @Test void getRenderers() {
        RenderRecipes recipes = new RenderRecipes()
        List<Renderer> renderers = recipes.getRenderers()
        assertTrue(renderers.size() > 0)
    }

    @Test void getRenderer() {
        RenderRecipes recipes = new RenderRecipes()
        Renderer renderer = recipes.getRenderer()
        assertNotNull(renderer)
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

    // ASCII

    @Test void renderToAsciiString() {
        RenderRecipes recipes = new RenderRecipes()
        String str = recipes.renderToAsciiString()
        assertNotNull(str)
    }

    @Test void renderToAsciiFile() {
        RenderRecipes recipes = new RenderRecipes()
        File file = recipes.renderToAsciiFile()
        assertNotNull(file)
    }

    // Base64

    @Test void renderToBase64String() {
        RenderRecipes recipes = new RenderRecipes()
        String str = recipes.renderToBase64String()
        assertNotNull(str)
    }

    @Test void renderToBase64File() {
        RenderRecipes recipes = new RenderRecipes()
        File file = recipes.renderToBase64File()
        assertNotNull(file)
    }

    // PDF

    @Test void renderToPdfDocument() {
        RenderRecipes recipes = new RenderRecipes()
        com.lowagie.text.Document document = recipes.renderToPdfDocument()
        assertNotNull(document)
    }

    @Test void renderToPdfFile() {
        RenderRecipes recipes = new RenderRecipes()
        File file = recipes.renderToPdfFile()
        assertNotNull(file)
    }

    // SVG

    @Test void renderToSvgDocument() {
        RenderRecipes recipes = new RenderRecipes()
        org.w3c.dom.Document document = recipes.renderToSvgDocument()
        assertNotNull(document)
    }

    @Test void renderToSvgFile() {
        RenderRecipes recipes = new RenderRecipes()
        File file = recipes.renderToSvgFile()
        assertNotNull(file)
    }

    // Displayer

    @Test void getDisplayers() {
        RenderRecipes recipes = new RenderRecipes()
        List<Displayer> displayers = recipes.getDisplayers()
        assertTrue(displayers.size() > 0)
    }

    @Test void getDisplayer() {
        RenderRecipes recipes = new RenderRecipes()
        Displayer displayer = recipes.getDisplayer()
        assertNotNull(displayer)
    }

    // Draw

    @Test void drawGeometryToFile() {
        RenderRecipes recipes = new RenderRecipes()
        File file = recipes.drawGeometryToFile()
        assertNotNull(file)
    }

    @Test void drawGeometriesToOutputStream() {
        RenderRecipes recipes = new RenderRecipes()
        File file = recipes.drawGeometriesToOutputStream()
        assertNotNull(file)
    }

    @Test void drawFeatureToFileName() {
        RenderRecipes recipes = new RenderRecipes()
        File file = recipes.drawFeatureToFileName()
        assertNotNull(file)
    }

    @Test void drawRasterToFile() {
        RenderRecipes recipes = new RenderRecipes()
        File file = recipes.drawRasterToFile()
        assertNotNull(file)
    }

    // Draw to image

    @Test void drawGeometryToImage() {
        RenderRecipes recipes = new RenderRecipes()
        BufferedImage image = recipes.drawGeometryToImage()
        assertNotNull(image)
    }

    @Test void drawGeometriesToImage() {
        RenderRecipes recipes = new RenderRecipes()
        BufferedImage image = recipes.drawGeometriesToImage()
        assertNotNull(image)
    }

    @Test void drawFeatureToImage() {
        RenderRecipes recipes = new RenderRecipes()
        BufferedImage image = recipes.drawFeatureToImage()
        assertNotNull(image)
    }

    @Test void drawLayerToImage() {
        RenderRecipes recipes = new RenderRecipes()
        BufferedImage image = recipes.drawLayerToImage()
        assertNotNull(image)
    }

    @Test void drawRasterToImage() {
        RenderRecipes recipes = new RenderRecipes()
        BufferedImage image = recipes.drawRasterToImage()
        assertNotNull(image)
    }

}
