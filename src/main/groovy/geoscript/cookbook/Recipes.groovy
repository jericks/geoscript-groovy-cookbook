package geoscript.cookbook

import geoscript.geom.Bounds
import geoscript.geom.Geometry
import geoscript.layer.Cursor
import geoscript.layer.Layer
import geoscript.layer.Renderable
import geoscript.plot.Chart
import geoscript.proj.Projection
import geoscript.render.Map as GMap
import geoscript.style.io.SLDReader
import geoscript.style.io.SimpleStyleReader
import geoscript.viewer.Viewer
import geoscript.workspace.GeoPackage
import geoscript.workspace.Workspace
import org.apache.commons.io.FileUtils

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

class Recipes {

    protected static final String NEW_LINE = System.getProperty("line.separator")

    static {
        System.setProperty("org.geotools.referencing.forceXY", "true")
    }

    protected void drawGeometry(Map options = [:], String name, Geometry g) {
        File file = new File("src/docs/asciidoc/images/${name}.png")
        if(!file.parentFile.exists()) {
            file.parentFile.mkdir()
        }
        Viewer.drawToFile(g, file, size: options.get("size", [200,200]), drawCoords: options.get("drawCoords", true))
    }

    protected void drawGeometries(Map options = [:], String name, List<Geometry> geometries) {
        File file = new File("src/docs/asciidoc/images/${name}.png")
        if(!file.parentFile.exists()) {
            file.parentFile.mkdir()
        }
        Viewer.drawToFile(geometries, file, size: options.get("size", [200,200]), drawCoords: options.get("drawCoords", true))
    }

    protected void saveImage(String name, BufferedImage image, String type) {
        File file = new File("src/docs/asciidoc/images/${name}.${type}")
        if(!file.parentFile.exists()) {
            file.parentFile.mkdir()
        }
        ImageIO.write(image, type, file)
    }

    protected void saveImage(String name, BufferedImage image) {
        saveImage(name, image, "png")
    }

    protected File getImageFile(String name) {
        new File("src/docs/asciidoc/images/${name}.png")
    }

    protected File moveFile(File fromFile, File toFile) {
        if (toFile.exists()) {
            toFile.delete()
        }
        FileUtils.moveFile(fromFile, toFile)
        toFile
    }

    protected void writeFile(String name, String text) {
        File dir = new File("src/docs/asciidoc/output")
        if (!dir.exists()) {
            dir.mkdir()
        }
        File file = new File(dir, "${name}.txt")
        file.text = text
    }

    protected void drawChart(String name, Chart chart) {
        File file = new File("src/docs/asciidoc/images/${name}.png")
        if(!file.parentFile.exists()) {
            file.parentFile.mkdir()
        }
        chart.save(file, size: [300,300])
    }

    protected Layer createLayerFromGeometry(String name, Geometry geometry, String styleStr) {
        Layer layer = new Layer(name)
        layer.proj = new Projection("EPSG:4326")
        layer.add([geom: geometry])
        layer.style = new SimpleStyleReader().read(styleStr)
        layer
    }

    protected Layer createLayerFromCursor(Cursor cursor, String styleStr) {
        Layer layer = new Layer(cursor.col)
        layer.proj = new Projection("EPSG:4326")
        layer.style = new SimpleStyleReader().read(styleStr)
        layer
    }

    protected void drawOnBasemap(String name, List<Renderable> layers, Bounds bounds) {
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new SLDReader().read(new File('src/main/resources/countries.sld'))
        Layer ocean = workspace.get("ocean")
        ocean.style = new SLDReader().read(new File('src/main/resources/ocean.sld'))
        GMap map = new GMap(
                width: 500,
                height: 300,
                layers: [ocean, countries],
                bounds: bounds
        )
        map.setAdvancedProjectionHandling(false)
        map.setContinuousMapWrapping(false)
        layers.each { Renderable layer ->
            map.addLayer(layer)
        }
        File file = new File("src/docs/asciidoc/images/${name}.png")
        if(!file.parentFile.exists()) {
            file.parentFile.mkdir()
        }
        map.render(file)
    }

    protected void drawOnBasemap(String name, List<Renderable> layers) {
        Workspace workspace = new GeoPackage('src/main/resources/data.gpkg')
        Layer countries = workspace.get("countries")
        countries.style = new SLDReader().read(new File('src/main/resources/countries.sld'))
        Layer ocean = workspace.get("ocean")
        ocean.style = new SLDReader().read(new File('src/main/resources/ocean.sld'))
        GMap map = new GMap(
                width: 500,
                height: 300,
                layers: [ocean, countries]
        )
        map.setAdvancedProjectionHandling(false)
        map.setContinuousMapWrapping(false)
        layers.each { Renderable layer ->
            map.addLayer(layer)
        }
        File file = new File("src/docs/asciidoc/images/${name}.png")
        if(!file.parentFile.exists()) {
            file.parentFile.mkdir()
        }
        map.render(file)
    }

    protected void draw(String name, List<Renderable> layers) {
        GMap map = new GMap(
                width: 500,
                height: 300,
                layers: layers
        )
        map.setAdvancedProjectionHandling(false)
        map.setContinuousMapWrapping(false)
        File file = new File("src/docs/asciidoc/images/${name}.png")
        if(!file.parentFile.exists()) {
            file.parentFile.mkdir()
        }
        map.render(file)
    }

    protected void draw(String name, List<Renderable> layers, Bounds bounds) {
        GMap map = new GMap(
                width: 500,
                height: 300,
                layers: layers,
                bounds: bounds
        )
        map.setAdvancedProjectionHandling(false)
        map.setContinuousMapWrapping(false)
        File file = new File("src/docs/asciidoc/images/${name}.png")
        if(!file.parentFile.exists()) {
            file.parentFile.mkdir()
        }
        map.render(file)
    }

}
