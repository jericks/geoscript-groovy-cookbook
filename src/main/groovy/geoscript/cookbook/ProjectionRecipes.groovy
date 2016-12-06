package geoscript.cookbook

import geoscript.proj.Projection

class ProjectionRecipes {

    Projection createFromEpsg() {
        // tag::createFromEpsg[]
        Projection proj = new Projection("EPSG:4326")
        println proj.wkt
        // end::createFromEpsg[]
        proj
    }

    Projection createFromWkt() {
        // tag::createFromWkt[]
        Projection proj = new Projection("""GEOGCS["WGS 84",
  DATUM["World Geodetic System 1984",
    SPHEROID["WGS 84", 6378137.0, 298.257223563, AUTHORITY["EPSG","7030"]],
    AUTHORITY["EPSG","6326"]],
  PRIMEM["Greenwich", 0.0, AUTHORITY["EPSG","8901"]],
  UNIT["degree", 0.017453292519943295],
  AXIS["Geodetic longitude", EAST],
  AXIS["Geodetic latitude", NORTH],
  AUTHORITY["EPSG","4326"]]""")
        // end::createFromWkt[]
        proj
    }

    Projection createFromName() {
        // tag::createFromName[]
        Projection proj = new Projection("Mollweide")
        println proj.wkt
        // end::createFromName[]
        proj
    }

}
