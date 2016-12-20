package geoscript.cookbook

import geoscript.geom.Geometry
import geoscript.plot.Chart
import geoscript.viewer.Viewer

class Recipes {

    protected void drawGeometry(String name, Geometry g) {
        File file = new File("src/docs/asciidoc/images/${name}.png")
        if(!file.parentFile.exists()) {
            file.parentFile.mkdir()
        }
        Viewer.drawToFile(g, file, size: [200, 200], drawCoords: true)
    }

    protected void drawGeometries(String name, List<Geometry> geometries) {
        File file = new File("src/docs/asciidoc/images/${name}.png")
        if(!file.parentFile.exists()) {
            file.parentFile.mkdir()
        }
        Viewer.drawToFile(geometries, file, size: [200,200], drawCoords: true)
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