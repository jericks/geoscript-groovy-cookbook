package geoscript.cookbook

import geoscript.layer.Format
import geoscript.layer.Raster

class FormatRecipes extends Recipes {

    Format getFormat() {
        // tag::getFormat[]
        File file = new File("src/main/resources/earth.tif")
        Format format = Format.getFormat(file)
        println format.name
        // end::getFormat[]
        writeFile("format_get", "${format.name}")
        format
    }

    Format hasRaster() {
        // tag::hasRaster[]
        File file = new File("src/main/resources/earth.tif")
        Format format = Format.getFormat(file)

        boolean hasEarth = format.has("earth")
        println "Has raster named earth? ${hasEarth}"

        boolean hasWorld = format.has("world")
        println "Has raster named world? ${hasWorld}"
        // end::hasRaster[]
        writeFile("format_has", "Has raster named earth? ${hasEarth}${NEW_LINE}Has raster named world? ${hasWorld}")
        format
    }

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
