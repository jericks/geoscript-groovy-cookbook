package geoscript.cookbook

import geoscript.geom.Geometry
import geoscript.geom.Point
import geoscript.geom.Polygon
import geoscript.viewer.Viewer
import org.apache.commons.io.FileUtils

import java.awt.image.BufferedImage

class ViewerRecipes extends Recipes {

    BufferedImage drawToImage() {
        // tag::drawToImage[]
        Polygon polygon = new Polygon([[
               [-101.35986328125, 47.754097979680026],
               [-101.5576171875, 46.93526088057719],
               [-100.12939453125, 46.51351558059737],
               [-99.77783203125, 47.44294999517949],
               [-100.45898437499999, 47.88688085106901],
               [-101.35986328125, 47.754097979680026]
        ]])
        BufferedImage image = Viewer.drawToImage(polygon)
        // end::drawToImage[]
        saveImage("viewer_draw2image", image)
        image
    }

    BufferedImage drawToImageWithOptions() {
        // tag::drawToImageWithOptions[]
        Polygon polygon = new Polygon([[
               [-101.35986328125, 47.754097979680026],
               [-101.5576171875, 46.93526088057719],
               [-100.12939453125, 46.51351558059737],
               [-99.77783203125, 47.44294999517949],
               [-100.45898437499999, 47.88688085106901],
               [-101.35986328125, 47.754097979680026]
        ]])
        BufferedImage image = Viewer.drawToImage(
                polygon,
                size: [200,200],
                drawCoords: true,
                fillCoords: true,
                fillPolys: true
        )
        // end::drawToImageWithOptions[]
        saveImage("viewer_draw2imagewithoptions", image)
        image
    }

    BufferedImage drawToImageGeometries() {
        // tag::drawToImageGeometries[]
        Point point = new Point(-123.11, 47.23)
        Geometry buffer = point.buffer(4)
        Geometry bounds = buffer.bounds.geometry
        BufferedImage image = Viewer.drawToImage(
                [bounds, buffer, point],
                size: [200,200],
                drawCoords: true,
                fillCoords: true,
                fillPolys: true
        )
        // end::drawToImageGeometries[]
        saveImage("viewer_draw2imagegeometries", image)
        image
    }

    File drawToFile() {
        // tag::drawToFile[]
        Point point = new Point(-123.11, 47.23)
        Geometry buffer = point.buffer(4)
        File file = new File("geometry.png")
        Viewer.drawToFile([buffer, point], file, size: [200,200])
        // end::drawToFile[]
        moveFile(file, getImageFile("viewer_drawtofile"))
    }

    BufferedImage plotToImage() {
        // tag::plotToImage[]
        Polygon polygon = new Polygon([[
               [-101.35986328125, 47.754097979680026],
               [-101.5576171875, 46.93526088057719],
               [-100.12939453125, 46.51351558059737],
               [-99.77783203125, 47.44294999517949],
               [-100.45898437499999, 47.88688085106901],
               [-101.35986328125, 47.754097979680026]
        ]])
        BufferedImage image = Viewer.plotToImage(polygon)
        // end::plotToImage[]
        saveImage("viewer_plot2image", image)
        image
    }

    BufferedImage plotToImageGeometries() {
        // tag::plotToImageGeometries[]
        Point point = new Point(-123.11, 47.23)
        Geometry buffer = point.buffer(4)
        Geometry bounds = buffer.bounds.geometry
        BufferedImage image = Viewer.plotToImage(
                [bounds, buffer, point],
                size: [300,300],
                drawCoords: true,
                fillCoords: true,
                fillPolys: true
        )
        // end::plotToImageGeometries[]
        saveImage("viewer_plot2imagegeometries", image)
        image
    }

    File plotToFile() {
        // tag::plotToFile[]
        Point point = new Point(-123.11, 47.23)
        Geometry buffer = point.buffer(4)
        File file = new File("geometry.png")
        Viewer.plotToFile([buffer, point], file, size: [300,300])
        // end::plotToFile[]
        moveFile(file, getImageFile("viewer_plottofile"))
    }
}
