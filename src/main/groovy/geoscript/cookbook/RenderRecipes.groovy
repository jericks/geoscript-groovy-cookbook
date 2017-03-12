package geoscript.cookbook

import geoscript.layer.Layer
import geoscript.render.ASCII
import geoscript.render.Base64
import geoscript.render.Displayer
import geoscript.render.Displayers
import geoscript.render.GIF
import geoscript.render.GeoTIFF
import geoscript.render.Image
import geoscript.render.JPEG
import geoscript.render.Map
import geoscript.render.MapWindow
import geoscript.render.PNG
import geoscript.render.Pdf
import geoscript.render.Renderers
import geoscript.render.Renderer
import geoscript.render.Svg
import geoscript.render.Window
import geoscript.style.Fill
import geoscript.style.Stroke
import geoscript.workspace.GeoPackage
import geoscript.workspace.Workspace

import javax.imageio.ImageIO
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

    // Renderers

    List<Renderer> getRenderers() {
        // tag::getRenderers[]
        List<Renderer> renderers = Renderers.list()
        renderers.each { Renderer renderer ->
            println renderer.class.simpleName
        }
        // end::getRenderers[]
        writeFile("renderer_list", "${renderers.collect{it.class.simpleName}.join(NEW_LINE)}")
        renderers
    }

    Renderer getRenderer() {
        // tag::getRenderer[]
        Renderer renderer = Renderers.find("png")
        println renderer.class.simpleName
        // end::getRenderer[]
        writeFile("renderer_get", "${renderer.class.simpleName}")
        renderer
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
        saveImage("map_gif_image", image, "png")
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
        File file2 = new File("src/docs/asciidoc/images/map_gif_file.png")
        BufferedImage img = ImageIO.read(file)
        ImageIO.write(img, "png", file2)
        file.delete()
        file2
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
        saveImage("map_geotiff_image", image, "png")
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
        File file2 = new File("src/docs/asciidoc/images/map_geotiff_file.png")
        BufferedImage img = ImageIO.read(file)
        ImageIO.write(img, "png", file2)
        file.delete()
        file2
    }

    // ASCII

    String renderToAsciiString() {
        // tag::renderToAsciiString[]
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
        ASCII ascii = new ASCII(width: 60)
        String asciiStr = ascii.render(map)
        println asciiStr
        // end::renderToAsciiString[]
        writeFile("render_ascii_string", "${asciiStr}")
        ascii
    }

    File renderToAsciiFile() {
        // tag::renderToAsciiFile[]
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
        ASCII ascii = new ASCII(width: 60)
        File file = new File("map.txt")
        ascii.render(map, new FileOutputStream(file))
        // end::renderToAsciiFile[]
        moveFile(file, new File("src/docs/asciidoc/output/render_ascii_file.txt"))
        file
    }

    // Base64

    String renderToBase64String() {
        // tag::renderToBase64String[]
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
        Base64 base64 = new Base64()
        String base64Str = base64.render(map)
        println base64Str
        // end::renderToBase64String[]
        writeFile("render_base64_string", "${base64Str.substring(0,50)}...")
        base64Str
    }

    File renderToBase64File() {
        // tag::renderToBase64File[]
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
        Base64 base64 = new Base64()
        File file = new File("map.txt")
        base64.render(map, new FileOutputStream(file))
        // end::renderToBase64File[]
        String base64Str = file.text
        file.delete()
        writeFile("render_base64_file", "${base64Str.substring(0,50)}...")
        file
    }

    // PDF

    com.lowagie.text.Document renderToPdfDocument() {
        // tag::renderToPdfDocument[]
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
        Pdf pdf = new Pdf()
        com.lowagie.text.Document document = pdf.render(map)
        // end::renderToPdfDocument[]
        document
    }

    File renderToPdfFile() {
        // tag::renderToPdfFile[]
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
        Pdf pdf = new Pdf()
        File file = new File("map.pdf")
        pdf.render(map, new FileOutputStream(file))
        // end::renderToPdfFile[]
        moveFile(file, new File("src/docs/asciidoc/output/render_pdf_file.pdf"))
        file
    }

    // SVG

    org.w3c.dom.Document renderToSvgDocument() {
        // tag::renderToSvgDocument[]
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
        Svg svg = new Svg()
        org.w3c.dom.Document document = svg.render(map)
        // end::renderToSvgDocument[]
        document
    }

    File renderToSvgFile() {
        // tag::renderToSvgFile[]
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
        Svg svg = new Svg()
        File file = new File("map.svg")
        svg.render(map, new FileOutputStream(file))
        // end::renderToSvgFile[]
        moveFile(file, new File("src/docs/asciidoc/output/render_svg_file.svg"))
        file
    }

    // Displayers

    List<Displayer> getDisplayers() {
        // tag::getDisplayers[]
        List<Displayer> displayers = Displayers.list()
        displayers.each { Displayer displayer ->
            println displayer.class.simpleName
        }
        // end::getDisplayers[]
        writeFile("displayer_list", "${displayers.collect{it.class.simpleName}.join(NEW_LINE)}")
        displayers
    }

    Displayer getDisplayer() {
        // tag::getDisplayer[]
        Displayer displayer = Displayers.find("window")
        println displayer.class.simpleName
        // end::getDisplayer[]
        writeFile("displayer_get", "${displayer.class.simpleName}")
        displayer
    }

    void openDisplayerWindow() {
        // tag::openDisplayerWindow[]
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
        Window window = new Window()
        window.display(map)
        // end::openDisplayerWindow[]
    }

    void openDisplayerMapWindow() {
        // tag::openDisplayerMapWindow[]
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
        MapWindow window = new MapWindow()
        window.display(map)
        // end::openDisplayerMapWindow[]
    }

}
