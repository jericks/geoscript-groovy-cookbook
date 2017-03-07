package geoscript.cookbook

import geoscript.layer.Layer
import geoscript.render.GIF
import geoscript.render.GeoTIFF
import geoscript.render.Image
import geoscript.render.JPEG
import geoscript.render.Map
import geoscript.render.PNG
import geoscript.style.Fill
import geoscript.style.Stroke
import geoscript.workspace.GeoPackage
import geoscript.workspace.Workspace
import java.awt.image.BufferedImage

class RenderRecipes extends Recipes {

    Map createMap() {
        // tag::createMap[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new Fill("#ffffff") + new Stroke("#b2b2b2", 0.5)
        Layer ocean = workspace.get("ocean")
        ocean.style = new Fill("#a5bfdd")
        Map map = new Map(
            width: 800,
            height: 300,
            layers: [ocean, countries]
        )
        File file = new File("map.png")
        map.render(file)
        // end::createMap[]
        moveFile(file, new File("src/docs/asciidoc/images/map_create.png"))
        map
    }

    // Image

    BufferedImage renderToImage() {
        // tag::renderToImage[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new Fill("#ffffff") + new Stroke("#b2b2b2", 0.5)
        Layer ocean = workspace.get("ocean")
        ocean.style = new Fill("#a5bfdd")
        Map map = new Map(
                width: 800,
                height: 300,
                layers: [ocean, countries]
        )
        Image png = new Image("png")
        BufferedImage image = png.render(map)
        // end::renderToImage[]
        saveImage("map_image_image", image)
        image
    }

    File renderToFile() {
        // tag::renderToFile[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new Fill("#ffffff") + new Stroke("#b2b2b2", 0.5)
        Layer ocean = workspace.get("ocean")
        ocean.style = new Fill("#a5bfdd")
        Map map = new Map(
                width: 800,
                height: 300,
                layers: [ocean, countries]
        )
        Image jpeg = new Image("jpeg")
        File file = new File("map.jpeg")
        jpeg.render(map, new FileOutputStream(file))
        // end::renderToFile[]
        moveFile(file, new File("src/docs/asciidoc/images/map_image_file.jpeg"))
        file
    }

    // PNG

    BufferedImage renderToPngImage() {
        // tag::renderToPngImage[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new Fill("#ffffff") + new Stroke("#b2b2b2", 0.5)
        Layer ocean = workspace.get("ocean")
        ocean.style = new Fill("#a5bfdd")
        Map map = new Map(
            width: 800,
            height: 300,
            layers: [ocean, countries]
        )
        PNG png = new PNG()
        BufferedImage image = png.render(map)
        // end::renderToPngImage[]
        saveImage("map_png_image", image)
        image
    }

    File renderToPngFile() {
        // tag::renderToPngFile[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new Fill("#ffffff") + new Stroke("#b2b2b2", 0.5)
        Layer ocean = workspace.get("ocean")
        ocean.style = new Fill("#a5bfdd")
        Map map = new Map(
            width: 800,
            height: 300,
            layers: [ocean, countries]
        )
        PNG png = new PNG()
        File file = new File("map.png")
        png.render(map, new FileOutputStream(file))
        // end::renderToPngFile[]
        moveFile(file, new File("src/docs/asciidoc/images/map_png_file.png"))
        file
    }

    // JPEG

    BufferedImage renderToJpegImage() {
        // tag::renderToJpegImage[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new Fill("#ffffff") + new Stroke("#b2b2b2", 0.5)
        Layer ocean = workspace.get("ocean")
        ocean.style = new Fill("#a5bfdd")
        Map map = new Map(
                width: 800,
                height: 300,
                layers: [ocean, countries]
        )
        JPEG jpeg = new JPEG()
        BufferedImage image = jpeg.render(map)
        // end::renderToJpegImage[]
        saveImage("map_jpeg_image", image, "jpeg")
        image
    }

    File renderToJpegFile() {
        // tag::renderToJpegFile[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new Fill("#ffffff") + new Stroke("#b2b2b2", 0.5)
        Layer ocean = workspace.get("ocean")
        ocean.style = new Fill("#a5bfdd")
        Map map = new Map(
                width: 800,
                height: 300,
                layers: [ocean, countries]
        )
        JPEG jpeg = new JPEG()
        File file = new File("map.jpeg")
        jpeg.render(map, new FileOutputStream(file))
        // end::renderToJpegFile[]
        moveFile(file, new File("src/docs/asciidoc/images/map_jpeg_file.jpeg"))
        file
    }

    // GIF

    BufferedImage renderToGifImage() {
        // tag::renderToGifImage[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new Fill("#ffffff") + new Stroke("#b2b2b2", 0.5)
        Layer ocean = workspace.get("ocean")
        ocean.style = new Fill("#a5bfdd")
        Map map = new Map(
                width: 800,
                height: 300,
                layers: [ocean, countries]
        )
        GIF gif = new GIF()
        BufferedImage image = gif.render(map)
        // end::renderToGifImage[]
        saveImage("map_gif_image", image, "gif")
        image
    }

    File renderToGifFile() {
        // tag::renderToGifFile[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new Fill("#ffffff") + new Stroke("#b2b2b2", 0.5)
        Layer ocean = workspace.get("ocean")
        ocean.style = new Fill("#a5bfdd")
        Map map = new Map(
                width: 800,
                height: 300,
                layers: [ocean, countries]
        )
        GIF gif = new GIF()
        File file = new File("map.gif")
        gif.render(map, new FileOutputStream(file))
        // end::renderToGifFile[]
        moveFile(file, new File("src/docs/asciidoc/images/map_gif_file.gif"))
        file
    }

    // GEOTIF

    BufferedImage renderToGeoTiffImage() {
        // tag::renderToGeoTiffImage[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new Fill("#ffffff") + new Stroke("#b2b2b2", 0.5)
        Layer ocean = workspace.get("ocean")
        ocean.style = new Fill("#a5bfdd")
        Map map = new Map(
                width: 800,
                height: 300,
                layers: [ocean, countries]
        )
        GeoTIFF geotiff = new GeoTIFF()
        BufferedImage image = geotiff.render(map)
        // end::renderToGeoTiffImage[]
        saveImage("map_geotiff_image", image, "tif")
        image
    }

    File renderToGeoTiffFile() {
        // tag::renderToGeoTiffFile[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new Fill("#ffffff") + new Stroke("#b2b2b2", 0.5)
        Layer ocean = workspace.get("ocean")
        ocean.style = new Fill("#a5bfdd")
        Map map = new Map(
                width: 800,
                height: 300,
                layers: [ocean, countries]
        )
        GeoTIFF geotiff = new GeoTIFF()
        File file = new File("map.tif")
        geotiff.render(map, new FileOutputStream(file))
        // end::renderToGeoTiffFile[]
        moveFile(file, new File("src/docs/asciidoc/images/map_geotiff_file.tif"))
        file
    }
}
