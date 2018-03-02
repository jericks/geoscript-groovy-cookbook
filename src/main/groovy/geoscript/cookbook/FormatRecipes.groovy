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

    Raster write() {
        // tag::write[]
        File file = new File("src/main/resources/earth.tif")
        Format format = Format.getFormat(file)
        Raster raster = format.read("earth")

        File outFile = new File("target/earth.png")
        Format outFormat = Format.getFormat(outFile)
        outFormat.write(raster)
        Raster outRaster = outFormat.read("earth")

        // end::write[]
        draw("format_write", [outRaster])
        outRaster
    }

}
