package geoscript.cookbook

import geoscript.layer.Format
import geoscript.layer.Raster

class FormatRecipes extends Recipes {

    Raster read() {
        // tag::read[]
        File file = new File("src/main/resources/earth.tif")
        Format format = Format.getFormat(file)
        Raster raster = format.read("earth")
        // end::read[]
        draw("format_read", [raster])
        raster
    }

}
