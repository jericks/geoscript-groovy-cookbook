package geoscript.cookbook

import geoscript.geom.Polygon
import geoscript.viewer.Viewer

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

}
