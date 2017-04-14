package geoscript.cookbook

import geoscript.feature.Feature
import geoscript.geom.Bounds
import geoscript.geom.Geometry
import geoscript.geom.GeometryCollection
import geoscript.geom.Point
import geoscript.layer.Layer
import geoscript.layer.Raster
import geoscript.render.ASCII
import geoscript.render.Base64
import geoscript.render.Displayer
import geoscript.render.Displayers
import geoscript.render.Draw
import geoscript.render.GIF
import geoscript.render.GeoTIFF
import geoscript.render.Image
import geoscript.render.JPEG
import geoscript.render.Map
import geoscript.render.MapWindow
import geoscript.render.PNG
import geoscript.render.Pdf
import geoscript.render.Plot
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

    File renderAnimatedGif() {
        // tag::renderAnimatedGif[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer states = workspace.get("states")
        states.style = new Fill("") + new Stroke("black", 1.0)
        Layer countries = workspace.get("countries")
        countries.style = new Fill("#ffffff") + new Stroke("#b2b2b2", 0.5)
        Layer ocean = workspace.get("ocean")
        ocean.style = new Fill("#a5bfdd")
        Map map = new Map(
                width: 800,
                height: 300,
                layers: [ocean, countries, states]
        )

        GIF gif = new GIF()
        List images = ["Washington","Oregon","California"].collect { String state ->
            map.bounds = states.getFeatures("NAME_1 = '${state}'")[0].bounds
            def image = gif.render(map)
            image
        }
        File file = new File("states.gif")
        gif.renderAnimated(images, file, 500, true)
        // end::renderAnimatedGif[]
        File movedFile = new File("src/docs/asciidoc/images/render_animated_gif.gif")
        moveFile(file, movedFile)
        images.eachWithIndex { BufferedImage img, int index ->
            saveImage("render_animated_gif_${index}", img, "png")
        }
        movedFile
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

    // Draw

    File drawGeometryToFile() {
        // tag::drawGeometryToFile[]
        File file = new File("geometry.png")
        Geometry geometry = new Point(-122.376, 47.587).buffer(0.5)
        Draw.draw(geometry,
                style: new Fill("#ffffff") + new Stroke("#b2b2b2", 0.5),
                bounds: new Bounds(-122.876,47.087,-121.876,48.087),
                size: [400,400],
                format: "png",
                proj: "EPSG:4326",
                backgroundColor: "#a5bfdd",
                out: file
        )
        // end::drawGeometryToFile[]
        File movedFile = new File("src/docs/asciidoc/images/render_draw_geometry.png")
        moveFile(file, movedFile)
        movedFile
    }

    File drawGeometriesToOutputStream() {
        // tag::drawGeometriesToOutputStream[]
        Point point = new Point(-122.376, 47.587)
        List geometries = [1.5, 1.0, 0.5].collect { double distance ->
            point.buffer(distance)
        }
        File file = new File("geometries.png")
        OutputStream outputStream = new FileOutputStream(file)
        Draw.draw(geometries, out: outputStream, format: "png")
        outputStream.flush()
        outputStream.close()
        // end::drawGeometriesToOutputStream[]
        File movedFile = new File("src/docs/asciidoc/images/render_draw_geometries.png")
        moveFile(file, movedFile)
        movedFile
    }

    File drawFeatureToFileName() {
        // tag::drawFeatureToFileName[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer layer = workspace.get("states")
        Feature feature = layer.first(filter: "NAME_1='Washington'")
        Draw.draw(feature, bounds: feature.bounds, out: "feature.png")
        // end::drawFeatureToFileName[]
        File file = new File("feature.png")
        File movedFile = new File("src/docs/asciidoc/images/render_draw_feature_file.png")
        moveFile(file, movedFile)
        movedFile
    }

    void drawLayerToWindow() {
        // tag::drawLayerToWindow[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer layer = workspace.get("states")
        layer.style = new Fill("LightSteelBlue") + new Stroke("LightSlateGrey", 0.25)
        Draw.draw(layer, bounds: layer.bounds)
        // end::drawLayerToWindow[]
    }

    File drawRasterToFile() {
        // tag::drawRasterToFile[]
        File file = new File("earth.png")
        Raster raster = new geoscript.layer.GeoTIFF(new File('src/main/resources/earth.tif')).read()
        Draw.draw(raster, bounds: raster.bounds, size: [400,200], out: file)
        // end::drawRasterToFile[]
        File movedFile = new File("src/docs/asciidoc/images/render_draw_raster.png")
        moveFile(file, movedFile)
        movedFile
    }

    // Draw to Image

    BufferedImage drawGeometryToImage() {
        // tag::drawGeometryToImage[]
        Geometry geometry = new Point(-122.376, 47.587).buffer(0.5)
        BufferedImage image = Draw.drawToImage(geometry,
                style: new Fill("#ffffff") + new Stroke("#b2b2b2", 0.5),
                bounds: new Bounds(-122.876,47.087,-121.876,48.087),
                size: [400,400],
                imageType: "png",
                proj: "EPSG:4326",
                backgroundColor: "#a5bfdd"
        )
        // end::drawGeometryToImage[]
        saveImage("render_drawtoimage_geometry", image)
        image
    }

    BufferedImage drawGeometriesToImage() {
        // tag::drawGeometriesToImage[]
        Point point = new Point(-122.376, 47.587)
        List geometries = [1.5, 1.0, 0.5].collect { double distance ->
            point.buffer(distance)
        }
        BufferedImage image = Draw.drawToImage(geometries)
        // end::drawGeometriesToImage[]
        saveImage("render_drawtoimage_geometries", image)
        image
    }

    BufferedImage drawFeatureToImage() {
        // tag::drawFeatureToImage[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer layer = workspace.get("states")
        Feature feature = layer.first(filter: "NAME_1='Washington'")
        BufferedImage image = Draw.drawToImage(feature, bounds: feature.bounds)
        // end::drawFeatureToImage[]
        saveImage("render_drawtoimage_feature", image)
        image
    }

    BufferedImage drawLayerToImage() {
        // tag::drawLayerToImage[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer layer = workspace.get("states")
        layer.style = new Fill("LightSteelBlue") + new Stroke("LightSlateGrey", 0.25)
        BufferedImage image = Draw.drawToImage(layer, bounds: layer.bounds)
        // end::drawLayerToImage[]
        saveImage("render_drawtoimage_layer", image)
        image
    }

    BufferedImage drawRasterToImage() {
        // tag::drawRasterToImage[]
        Raster raster = new geoscript.layer.GeoTIFF(new File('src/main/resources/earth.tif')).read()
        BufferedImage image = Draw.drawToImage(raster, bounds: raster.bounds, size: [400,200])
        // end::drawRasterToImage[]
        saveImage("render_drawtoimage_raster", image)
        image
    }

    // Plot

    File plotGeometryToFile() {
        // tag::plotGeometryToFile[]
        File file = new File("geometry.png")
        Geometry geometry = new Point(-122.376, 47.587).buffer(0.5)
        Plot.plot(geometry, out: file)
        // end::plotGeometryToFile[]
        File movedFile = new File("src/docs/asciidoc/images/render_plot_geometry.png")
        moveFile(file, movedFile)
        movedFile
    }

    File plotGeometriesToOutputStream() {
        // tag::plotGeometriesToOutputStream[]
        Point point = new Point(-122.376, 47.587)
        List geometries = [1.5, 1.0, 0.5].collect { double distance ->
            point.buffer(distance)
        }
        File file = new File("geometries.png")
        OutputStream outputStream = new FileOutputStream(file)
        Plot.plot(geometries, out: outputStream)
        outputStream.flush()
        outputStream.close()
        // end::plotGeometriesToOutputStream[]
        File movedFile = new File("src/docs/asciidoc/images/render_plot_geometries.png")
        moveFile(file, movedFile)
        movedFile
    }

    File plotFeatureToFileName() {
        // tag::plotFeatureToFileName[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer layer = workspace.get("states")
        Feature feature = layer.first(filter: "NAME_1='Washington'")
        Plot.plot(feature, out: "feature.png")
        // end::plotFeatureToFileName[]
        File file = new File("feature.png")
        File movedFile = new File("src/docs/asciidoc/images/render_plot_feature_file.png")
        moveFile(file, movedFile)
        movedFile
    }

    void plotLayerToWindow() {
        // tag::plotLayerToWindow[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer layer = workspace.get("states")
        Plot.plot(layer)
        // end::plotLayerToWindow[]
    }

    // Plot to Image

    BufferedImage plotGeometryToImage() {
        // tag::plotGeometryToImage[]
        Geometry geometry = new Point(-122.376, 47.587).buffer(0.5)
        BufferedImage image = Plot.plotToImage(geometry, size: [400,400],)
        // end::plotGeometryToImage[]
        saveImage("render_plottoimage_geometry", image)
        image
    }

    BufferedImage plotGeometriesToImage() {
        // tag::plotGeometriesToImage[]
        Point point = new Point(-122.376, 47.587)
        List geometries = [1.5, 1.0, 0.5].collect { double distance ->
            point.buffer(distance)
        }
        BufferedImage image = Plot.plotToImage(geometries)
        // end::plotGeometriesToImage[]
        saveImage("render_plottoimage_geometries", image)
        image
    }

    BufferedImage plotFeatureToImage() {
        // tag::plotFeatureToImage[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer layer = workspace.get("states")
        Feature feature = layer.first(filter: "NAME_1='Washington'")
        BufferedImage image = Plot.plotToImage(feature, bounds: feature.bounds)
        // end::plotFeatureToImage[]
        saveImage("render_plottoimage_feature", image)
        image
    }

    BufferedImage plotLayerToImage() {
        // tag::plotLayerToImage[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer layer = workspace.get("states")
        BufferedImage image = Plot.plotToImage(layer, bounds: layer.bounds)
        // end::plotLayerToImage[]
        saveImage("render_plottoimage_layer", image)
        image
    }
}
