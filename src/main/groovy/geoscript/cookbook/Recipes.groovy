package geoscript.cookbook

import geoscript.geom.Geometry
import geoscript.plot.Chart
import geoscript.viewer.Viewer
import org.apache.commons.io.FileUtils

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

class Recipes {

    protected static final String NEW_LINE = System.getProperty("line.separator")

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

    protected void saveImage(String name, BufferedImage image) {
        File file = new File("src/docs/asciidoc/images/${name}.png")
        if(!file.parentFile.exists()) {
            file.parentFile.mkdir()
        }
        ImageIO.write(image, "png", file)
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
}
