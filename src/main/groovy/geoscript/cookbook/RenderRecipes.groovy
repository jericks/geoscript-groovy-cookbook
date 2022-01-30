package geoscript.cookbook

import geoscript.feature.Feature
import geoscript.geom.Bounds
import geoscript.geom.Geometry
import geoscript.geom.Point
import geoscript.layer.Layer
import geoscript.layer.Raster
import geoscript.proj.Projection
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
import geoscript.render.MapCube
import geoscript.render.MapWindow
import geoscript.render.PNG
import geoscript.render.Pdf
import geoscript.render.Plot
import geoscript.render.Renderers
import geoscript.render.Renderer
import geoscript.render.Svg
import geoscript.render.Window
import geoscript.render.io.JsonMapReader
import geoscript.render.io.MapReader
import geoscript.render.io.MapReaders
import geoscript.render.io.XmlMapReader
import geoscript.style.Fill
import geoscript.style.Stroke
import geoscript.workspace.Directory
import geoscript.workspace.GeoPackage
import geoscript.workspace.Workspace

import javax.imageio.ImageIO
import java.awt.Graphics2D
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

    Map createMapWithFileName() {
        // tag::createMapWithFileName[]
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
        map.render("map.png")
        // end::createMapWithFileName[]
        moveFile(new File("map.png"), new File("src/docs/asciidoc/images/map_create_filename.png"))
        map
    }

    Map createMapWithOutputStream() {
        // tag::createMapWithOutputStream[]
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
        file.withOutputStream { OutputStream outputStream ->
            map.render(outputStream)
        }
        // end::createMapWithOutputStream[]
        moveFile(file, new File("src/docs/asciidoc/images/map_create_outputstream.png"))
        map
    }

    Map createMapToImage() {
        // tag::createMapToImage[]
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
        BufferedImage image = map.renderToImage()
        // end::createMapToImage[]
        ImageIO.write(image, "png", new File("src/docs/asciidoc/images/map_create_image.png"))
        map
    }

    Map createMapWithGraphics() {
        // tag::createMapWithGraphics[]
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
        BufferedImage image = new BufferedImage(800, 300, BufferedImage.TYPE_INT_ARGB)
        Graphics2D graphics = image.graphics
        map.render(graphics)
        graphics.dispose()
        // end::createMapWithGraphics[]
        ImageIO.write(image, "png", new File("src/docs/asciidoc/images/map_create_graphics.png"))
        map
    }
    
    Map displayMap() {
        // tag::displayMap[]
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
        map.display()
        // end::displayMap[]
        map
    }

    Map getMapProperties() {

        // tag::getMapProperties[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new Fill("#ffffff") + new Stroke("#b2b2b2", 0.5)
        Map map = new Map(
                width: 600,
                height: 600,
                backgroundColor: "#a5bfdd",
                layers: [countries],
                type: "png",
                proj: "EPSG:3857",
                bounds: new Bounds(-180,-85,180,85, "EPSG:4326").reproject("EPSG:3857"),
                fixAspectRatio: false
        )
        File file = new File("map.png")
        map.render(file)
        // end::getMapProperties[]
        moveFile(file, new File("src/docs/asciidoc/images/map_properties.png"))

        // tag::getMapProperties_widthheight[]
        int width = map.width
        int height = map.height
        println "Width and Height = ${width} x ${height}"
        // end::getMapProperties_widthheight[]
        writeFile("map_properties_widthheight", "Width and Height = ${width} x ${height}")

        // tag::getMapProperties_bounds[]
        Bounds bounds = map.bounds
        println "Bounds = ${bounds}"
        // end::getMapProperties_bounds[]
        writeFile("map_properties_bounds", "Bounds = ${bounds}")

        // tag::getMapProperties_proj[]
        Projection projection = map.proj
        println "Projeciton = ${projection}"
        // end::getMapProperties_proj[]
        writeFile("map_properties_proj","Projeciton = ${projection}")

        // tag::getMapProperties_layers[]
        List<Layer> layers = map.layers
        println "Layers:"
        layers.each { Layer layer ->
            println "   ${layer.name}"
        }
        // end::getMapProperties_layers[]
        writeFile("map_properties_layers", "Layers:${NEW_LINE}${layers.collect { '   ' + it.name }.join(NEW_LINE)}")

        // tag::getMapProperties_type[]
        String type = map.type
        println "Type = ${type}"
        // end::getMapProperties_type[]
        writeFile("map_properties_type", "Type = ${type}")

        // tag::getMapProperties_fixAspectRatio[]
        boolean shouldFixAspectRation = map.fixAspectRatio
        println "Fix Aspect Ratio = ${shouldFixAspectRation}"
        // end::getMapProperties_fixAspectRatio[]
        writeFile("map_properties_fixaspectratio", "Fix Aspect Ratio = ${shouldFixAspectRation}")

        // tag::getMapProperties_backgroundcolor[]
        String backgroundColor = map.backgroundColor
        println "Background Color = ${backgroundColor}"
        // end::getMapProperties_backgroundcolor[]
        writeFile("map_properties_backgroundcolor", "Background Color = ${backgroundColor}")

        // tag::getMapProperties_scale[]
        double scale = map.scaleDenominator
        println "Scale = ${scale}"
        // end::getMapProperties_scale[]
        writeFile("map_properties_scale", "Scale = ${scale}")

        map
    }

    Map setScaleComputation() {
        // tag::setScaleComputation[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new Fill("#ffffff") + new Stroke("#b2b2b2", 0.5)
        Layer ocean = workspace.get("ocean")
        ocean.style = new Fill("#a5bfdd")
        Map map = new Map(
                width: 400,
                height: 300,
                layers: [ocean, countries],
                bounds: new Bounds(-162.070313,9.968851,-35.507813,58.995311, "EPSG:4326")
        )
        
        map.setScaleComputation("accurate")
        File accurateFile = new File("map_accurate.png")
        map.render(accurateFile)

        map.setScaleComputation("ogc")
        File ogcFile = new File("map_ogc.png")
        map.render(ogcFile)
        // end::setScaleComputation[]
        moveFile(accurateFile, new File("src/docs/asciidoc/images/map_accurate.png"))
        moveFile(ogcFile, new File("src/docs/asciidoc/images/map_ogc.png"))
        map
    }

    Map setAdvancedProjectionHandling() {
        // tag::setAdvancedProjectionHandling[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new Fill("#ffffff") + new Stroke("#b2b2b2", 0.5)
        Layer ocean = workspace.get("ocean")
        ocean.style = new Fill("#a5bfdd")
        Map map = new Map(
                width: 400,
                height: 300,
                layers: [ocean, countries],
                bounds: new Bounds(-162.070313,9.968851,-35.507813,58.995311, "EPSG:4326")
        )

        map.setAdvancedProjectionHandling(true)
        File trueFile = new File("map_advancedproj_true.png")
        map.render(trueFile)

        map.setAdvancedProjectionHandling(false)
        File falseFile = new File("map_advancedproj_false.png")
        map.render(falseFile)
        // end::setAdvancedProjectionHandling[]
        moveFile(trueFile, new File("src/docs/asciidoc/images/map_advancedproj_true.png"))
        moveFile(falseFile, new File("src/docs/asciidoc/images/map_advancedproj_false.png"))
        map
    }

    Map setContinuousMapWrapping() {
        // tag::setContinuousMapWrapping[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new Fill("#ffffff") + new Stroke("#b2b2b2", 0.5)
        Layer ocean = workspace.get("ocean")
        ocean.style = new Fill("#a5bfdd")
        Map map = new Map(
                width: 800,
                height: 200,
                layers: [ocean, countries]
        )

        map.setContinuousMapWrapping(true)
        File trueFile = new File("map_continuouswrapping_true.png")
        map.render(trueFile)

        map.setContinuousMapWrapping(false)
        File falseFile = new File("map_continuouswrapping_false.png")
        map.render(falseFile)
        // end::setContinuousMapWrapping[]
        moveFile(trueFile, new File("src/docs/asciidoc/images/map_continuouswrapping_true.png"))
        moveFile(falseFile, new File("src/docs/asciidoc/images/map_continuouswrapping_false.png"))
        map
    }

    // Projections

    Map createMercatorMap() {
        // tag::createMercatorMap[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new Fill("#ffffff") + new Stroke("black", 0.5)
        Layer ocean = workspace.get("ocean")
        ocean.style = new Fill("#a5bfdd") + new Stroke("black", 0.5)
        Layer graticules = workspace.get("graticules")
        graticules.style = new Stroke("black", 0.5)
        Projection proj = new Projection("Mercator")
        Bounds bounds = new Bounds(-179.99, -85.0511, 179.99, 85.0511, "EPSG:4326").reproject(proj)
        Map map = new Map(
                width: 400,
                height: 400,
                proj: proj,
                bounds: bounds,
                layers: [ocean, countries, graticules]
        )
        File file = new File("map_mercator.png")
        map.render(file)
        // end::createMercatorMap[]
        moveFile(file, new File("src/docs/asciidoc/images/map_mercator.png"))
        map
    }

    Map createWGS84Map() {
        // tag::createWGS84Map[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new Fill("#ffffff") + new Stroke("black", 0.5)
        Layer ocean = workspace.get("ocean")
        ocean.style = new Fill("#a5bfdd") + new Stroke("black", 0.5)
        Layer graticules = workspace.get("graticules")
        graticules.style = new Stroke("black", 0.5)
        Projection proj = new Projection("WGS84")
        Bounds bounds = new Bounds(-180, -90, 180, 90, "EPSG:4326").reproject(proj)
        Map map = new Map(
                width: 600,
                height: 350,
                proj: proj,
                bounds: bounds,
                layers: [ocean, countries, graticules]
        )
        File file = new File("map_wgs84.png")
        map.render(file)
        // end::createWGS84Map[]
        moveFile(file, new File("src/docs/asciidoc/images/map_wgs84.png"))
        map
    }

    Map createEqualEarthMap() {
        // tag::createEqualEarthMap[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new Fill("#ffffff") + new Stroke("black", 0.5)
        Layer ocean = workspace.get("ocean")
        ocean.style = new Fill("#a5bfdd") + new Stroke("black", 0.5)
        Layer graticules = workspace.get("graticules")
        graticules.style = new Stroke("black", 0.5)
        Projection proj = new Projection("EqualEarth")
        Bounds bounds = new Bounds(-180, -90, 180, 90, "EPSG:4326").reproject(proj)
        Map map = new Map(
                width: 600,
                height: 350,
                proj: proj,
                bounds: bounds,
                layers: [ocean, countries, graticules]
        )
        File file = new File("map_equalearth.png")
        map.render(file)
        // end::createEqualEarthMap[]
        moveFile(file, new File("src/docs/asciidoc/images/map_equalearth.png"))
        map
    }

    Map createMollweideMap() {
        // tag::createMollweideMap[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new Fill("#ffffff") + new Stroke("black", 0.5)
        Layer ocean = workspace.get("ocean")
        ocean.style = new Fill("#a5bfdd") + new Stroke("black", 0.5)
        Layer graticules = workspace.get("graticules")
        graticules.style = new Stroke("black", 0.5)
        Projection proj = new Projection("Mollweide")
        Bounds bounds = new Bounds(-180, -90, 180, 90, "EPSG:4326").reproject(proj)
        Map map = new Map(
                width: 600,
                height: 350,
                proj: proj,
                bounds: bounds,
                layers: [ocean, countries, graticules]
        )
        File file = new File("map_mollweide.png")
        map.render(file)
        // end::createMollweideMap[]
        moveFile(file, new File("src/docs/asciidoc/images/map_mollweide.png"))
        map
    }

    Map createAitoffMap() {
        // tag::createAitoffMap[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new Fill("#ffffff") + new Stroke("black", 0.5)
        Layer ocean = workspace.get("ocean")
        ocean.style = new Fill("#a5bfdd") + new Stroke("black", 0.5)
        Layer graticules = workspace.get("graticules")
        graticules.style = new Stroke("black", 0.5)
        Projection proj = new Projection("Aitoff")
        Bounds bounds = new Bounds(-180, -90, 180, 90, "EPSG:4326").reproject(proj)
        Map map = new Map(
                width: 600,
                height: 350,
                proj: proj,
                bounds: bounds,
                layers: [ocean, countries, graticules]
        )
        File file = new File("map_aitoff.png")
        map.render(file)
        // end::createAitoffMap[]
        moveFile(file, new File("src/docs/asciidoc/images/map_aitoff.png"))
        map
    }

    Map createEckertIVMap() {
        // tag::createEckertIVMap[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new Fill("#ffffff") + new Stroke("black", 0.5)
        Layer ocean = workspace.get("ocean")
        ocean.style = new Fill("#a5bfdd") + new Stroke("black", 0.5)
        Layer graticules = workspace.get("graticules")
        graticules.style = new Stroke("black", 0.5)
        Projection proj = new Projection("EckertIV")
        Bounds bounds = new Bounds(-180, -90, 180, 90, "EPSG:4326").reproject(proj)
        Map map = new Map(
                width: 600,
                height: 350,
                proj: proj,
                bounds: bounds,
                layers: [ocean, countries, graticules]
        )
        File file = new File("map_eckertIV.png")
        map.render(file)
        // end::createEckertIVMap[]
        moveFile(file, new File("src/docs/asciidoc/images/map_eckertIV.png"))
        map
    }

    Map createWagnerIVMap() {
        // tag::createWagnerIVMap[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new Fill("#ffffff") + new Stroke("black", 0.5)
        Layer ocean = workspace.get("ocean")
        ocean.style = new Fill("#a5bfdd") + new Stroke("black", 0.5)
        Layer graticules = workspace.get("graticules")
        graticules.style = new Stroke("black", 0.5)
        Projection proj = new Projection("WagnerIV")
        Bounds bounds = new Bounds(-180, -90, 180, 90, "EPSG:4326").reproject(proj)
        Map map = new Map(
                width: 600,
                height: 350,
                proj: proj,
                bounds: bounds,
                layers: [ocean, countries, graticules]
        )
        File file = new File("map_wagnerIV.png")
        map.render(file)
        // end::createWagnerIVMap[]
        moveFile(file, new File("src/docs/asciidoc/images/map_wagnerIV.png"))
        map
    }

    Map createRobinsonMap() {
        // tag::createRobinsonMap[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new Fill("#ffffff") + new Stroke("black", 0.5)
        Layer ocean = workspace.get("ocean")
        ocean.style = new Fill("#a5bfdd") + new Stroke("black", 0.5)
        Layer graticules = workspace.get("graticules")
        graticules.style = new Stroke("black", 0.5)
        Projection proj = new Projection("Robinson")
        Bounds bounds = new Bounds(-180, -90, 180, 90, "EPSG:4326").reproject(proj)
        Map map = new Map(
                width: 600,
                height: 350,
                proj: proj,
                bounds: bounds,
                layers: [ocean, countries, graticules]
        )
        File file = new File("map_robinson.png")
        map.render(file)
        // end::createRobinsonMap[]
        moveFile(file, new File("src/docs/asciidoc/images/map_robinson.png"))
        map
    }

    Map createWinkelTripelMap() {
        // tag::createWinkelTripelMap[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new Fill("#ffffff") + new Stroke("black", 0.5)
        Layer ocean = workspace.get("ocean")
        ocean.style = new Fill("#a5bfdd") + new Stroke("black", 0.5)
        Layer graticules = workspace.get("graticules")
        graticules.style = new Stroke("black", 0.5)
        Projection proj = new Projection("WinkelTripel")
        Bounds bounds = new Bounds(-180, -90, 180, 90, "EPSG:4326").reproject(proj)
        Map map = new Map(
                width: 600,
                height: 350,
                proj: proj,
                bounds: bounds,
                layers: [ocean, countries, graticules]
        )
        File file = new File("map_winkeltripel.png")
        map.render(file)
        // end::createWinkelTripelMap[]
        moveFile(file, new File("src/docs/asciidoc/images/map_winkeltripel.png"))
        map
    }

    Map createWorldVanderGrintenIMap() {
        // tag::createWorldVanderGrintenIMap[]
        Workspace workspace = new Directory('src/main/resources/shapefiles')
        Layer countries = workspace.get("countries")
        countries.style = new Fill("#ffffff") + new Stroke("black", 0.5)
        Layer ocean = workspace.get("ocean")
        ocean.style = new Fill("#a5bfdd") + new Stroke("black", 0.5)
        Layer graticules = workspace.get("graticules")
        graticules.style = new Stroke("black", 0.5)
        Projection proj = new Projection("WorldVanderGrintenI")
        Bounds bounds = new Bounds(-180, -90, 180, 90, "EPSG:4326").reproject(proj)
        Map map = new Map(
                width: 600,
                height: 350,
                proj: proj,
                bounds: bounds,
                layers: [ocean, countries, graticules]
        )
        File file = new File("map_worldVanderGrintenIMap.png")
        map.render(file)
        // end::createWorldVanderGrintenIMap[]
        moveFile(file, new File("src/docs/asciidoc/images/map_worldVanderGrintenIMap.png"))
        map
    }

    // Map Cube

    File createMapCubeToFile() {
        // tag::createMapCubeToFile[]

        Workspace workspace = new Directory("src/main/resources/shapefiles")
        Layer countries = workspace.get("countries")
        Layer ocean = workspace.get("ocean")
        countries.style = new Fill("#ffffff") + new Stroke("#b2b2b2", 0.5)
        ocean.style = new Fill("#a5bfdd")

        MapCube mapCube = new MapCube(
            drawOutline: true,
            drawTabs: true,
            tabSize: 30,
            title: "World Cube",
            source: "Nartual Earth",
            imageType: "png"
        )
        File file = new File("map_cube_file.png")
        mapCube.render([ocean, countries], file)
        // end::createMapCubeToFile[]
        // @TODO Fix Me
        // moveFile(file, new File("src/docs/asciidoc/images/map_cube_file.png"))
        file
    }

    File createMapCubeToBytes() {
        // tag::createMapCubeToBytes[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new Fill("#ffffff") + new Stroke("#b2b2b2", 0.5)
        Layer ocean = workspace.get("ocean")
        ocean.style = new Fill("#a5bfdd")

        MapCube mapCube = new MapCube()
        byte[] bytes = mapCube.render([ocean, countries])
        // end::createMapCubeToBytes[]
        File file = new File("map_cube_bytes.png")
        file.bytes = bytes
        // @TODO Fix Me
        // moveFile(file, new File("src/docs/asciidoc/images/map_cube_bytes.png"))
        file
    }

    File createMapCubeToOutputStream() {
        // tag::createMapCubeToOutputStream[]
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new Fill("#ffffff") + new Stroke("#b2b2b2", 0.5)
        Layer ocean = workspace.get("ocean")
        ocean.style = new Fill("#a5bfdd")

        MapCube mapCube = new MapCube()
        File file = new File("map_cube_stream.png")
        file.withOutputStream { OutputStream outputStream ->
            mapCube.render([ocean, countries], outputStream)
        }
        // end::createMapCubeToOutputStream[]
        // @TODO Fix Me
        // moveFile(file, new File("src/docs/asciidoc/images/map_cube_stream.png"))
        file
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
        FileOutputStream out = new FileOutputStream(file)
        jpeg.render(map, out)
        out.close()
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
        FileOutputStream out = new FileOutputStream(file)
        png.render(map, out)
        out.close()
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
        FileOutputStream out = new FileOutputStream(file)
        jpeg.render(map, out)
        out.close()
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
            map.bounds = states.getFeatures("name = '${state}'")[0].bounds
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

    File renderAnimatedGifBytes() {
        // tag::renderAnimatedGifBytes[]
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
            map.bounds = states.getFeatures("name = '${state}'")[0].bounds
            def image = gif.render(map)
            image
        }
        File file = new File("states.gif")
        byte[] bytes = gif.renderAnimated(images, 500, true)
        file.bytes = bytes
        // end::renderAnimatedGifBytes[]
        File movedFile = new File("src/docs/asciidoc/images/render_animated_gif_bytes.gif")
        moveFile(file, movedFile)
        images.eachWithIndex { BufferedImage img, int index ->
            saveImage("render_animated_gif_bytes_${index}", img, "png")
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
        FileOutputStream out = new FileOutputStream(file)
        ascii.render(map, out)
        out.close()
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
        FileOutputStream out = new FileOutputStream(file)
        svg.render(map, out)
        out.close()
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
        Feature feature = layer.first(filter: "name='Washington'")
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
        Feature feature = layer.first(filter: "name='Washington'")
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
        Feature feature = layer.first(filter: "name='Washington'")
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
        Feature feature = layer.first(filter: "name='Washington'")
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

    // IO

    List<MapReader> listMapReaders() {
        // tag::listMapReaders[]
        List<MapReader> readers = MapReaders.list()
        readers.each { MapReader reader ->
            println reader.name
        }
        // end::listMapReaders[]
        writeFile("render_map_readers_list", "${readers.collect{it.name}.join(NEW_LINE)}")
        readers
    }

    MapReader findMapReader() {
        // tag::findMapReader[]
        MapReader reader = MapReaders.find("json")
        println reader.name
        // end::findMapReader[]
        writeFile("render_map_readers_find", "${reader.name}")
        reader
    }

    BufferedImage readMapFromJson() {
        // tag::readMapFromJson[]
        String json = """{
    "width": 400,
    "height": 400,
    "type": "png",
    "backgroundColor": "blue",
    "proj": "EPSG:4326",
    "bounds": {
        "minX": -135.911779,
        "minY": 36.993573,
        "maxX": -96.536779,
        "maxY": 51.405899
    },
    "layers": [
        {
            "layertype": "layer", 
            "dbtype": "geopkg", 
            "database": "src/main/resources/data.gpkg", 
            "layername": "ocean", 
            "style": "src/main/resources/ocean.sld"
        },
        {
            "layertype": "layer", 
            "dbtype": "geopkg", 
            "database": "src/main/resources/data.gpkg", 
            "layername": "countries", 
            "style": "src/main/resources/countries.sld"
        },
        {   
            "layertype": "layer", 
            "dbtype": "geopkg", 
            "database": "src/main/resources/data.gpkg", 
            "layername": "states", 
            "style": "src/main/resources/states.sld"
        }
    ]
}
"""
        MapReader mapReader = new JsonMapReader()
        Map map = mapReader.read(json)
        BufferedImage image = map.renderToImage()
        // end::readMapFromJson[]
        saveImage("render_io_json", image)
        image
    }

    BufferedImage readMapFromXml() {
        // tag::readMapFromXml[]
        String xml = """<map>
    <width>400</width>
    <height>400</height>
    <type>png</type>
    <proj>EPSG:4326</proj>
    <backgroundColor>blue</backgroundColor>
    <fixAspectRatio>true</fixAspectRatio>
    <layers>
        <layer>
            <layertype>layer</layertype> 
            <dbtype>geopkg</dbtype> 
            <database>src/main/resources/data.gpkg</database> 
            <layername>ocean</layername>
            <style>src/main/resources/ocean.sld</style>
        </layer>
        <layer>
            <layertype>layer</layertype> 
            <dbtype>geopkg</dbtype> 
            <database>src/main/resources/data.gpkg</database> 
            <layername>countries</layername>
            <style>src/main/resources/countries.sld</style>
        </layer>
        <layer>
            <layertype>layer</layertype> 
            <dbtype>geopkg</dbtype> 
            <database>src/main/resources/data.gpkg</database> 
            <layername>states</layername>
            <style>src/main/resources/states.sld</style>
        </layer>
    </layers>
    <bounds>
        <minX>-135.911779</minX>
        <minY>36.993573</minY>
        <maxX>-96.536779</maxX>
        <maxY>51.405899</maxY>
    </bounds>
</map>
"""
        MapReader mapReader = new XmlMapReader()
        Map map = mapReader.read(xml)
        BufferedImage image = map.renderToImage()
        // end::readMapFromXml[]
        saveImage("render_io_xml", image)
        image
    }


}
