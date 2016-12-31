package geoscript.cookbook

import geoscript.geom.Bounds
import geoscript.proj.Projection
import geoscript.geom.Point
import geoscript.geom.Geometry

class ProjectionRecipes extends Recipes {

    Projection createFromEpsg() {
        // tag::createFromEpsg[]
        Projection proj = new Projection("EPSG:4326")
        println proj.wkt
        // end::createFromEpsg[]
        writeFile("projection_createprojectionfromepsg", proj.wkt)
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
        writeFile("projection_createprojectionfromwkt", proj.wkt)
        proj
    }

    Projection createFromName() {
        // tag::createFromName[]
        Projection proj = new Projection("Mollweide")
        println proj.wkt
        // end::createFromName[]
        writeFile("projection_createprojectionfromname", proj.wkt)
        proj
    }

    Map<String, Object> getProperties() {
        // tag::getProperties_id[]
        Projection proj = new Projection("EPSG:4326")
        String id = proj.id
        // end::getProperties_id[]
        writeFile("projection_id", "${id}")
        // tag::getProperties_srs[]
        String srs = proj.srs
        // end::getProperties_srs[]
        writeFile("projection_srs", "${srs}")
        // tag::getProperties_epsg[]
        int epsg = proj.epsg
        // end::getProperties_epsg[]
        writeFile("projection_epsg", "${epsg}")
        // tag::getProperties_wkt[]
        String wkt = proj.wkt
        // end::getProperties_wkt[]
        writeFile("projection_wkt", "${wkt}")
        // tag::getProperties_bounds[]
        Bounds bounds = proj.bounds
        // end::getProperties_bounds[]
        writeFile("projection_bounds", "${bounds}")
        // tag::getProperties_geobounds[]
        Bounds geoBounds = proj.geoBounds
        // end::getProperties_geobounds[]
        writeFile("projection_geobounds", "${geoBounds}")
        [
                id: id,
                srs: srs,
                epsg: epsg,
                wkt: wkt,
                bounds: bounds,
                geoBounds: geoBounds
        ]
    }

    Geometry transformProj() {
        // tag::transformProj[]
        Projection fromProj = new Projection("EPSG:4326")
        Projection toProj = new Projection("EPSG:2927")
        Geometry geom = new Point(-122.440, 47.245)
        Geometry projectedGeom = fromProj.transform(geom, toProj)
        println projectedGeom
        // end::transformProj[]
        writeFile("projection_transformproj", projectedGeom.wkt)
        projectedGeom
    }

    Geometry transformStr() {
        // tag::transformStr[]
        Projection fromProj = new Projection("EPSG:4326")
        Geometry geom = new Point(-122.440, 47.245)
        Geometry projectedGeom = fromProj.transform(geom, "EPSG:2927")
        println projectedGeom
        // end::transformStr[]
        writeFile("projection_transformstr", projectedGeom.wkt)
        projectedGeom
    }

    Geometry transformStaticStr() {
        // tag::transformStaticStr[]
        Geometry epsg4326Geom = new Point(-122.440, 47.245)
        Geometry epsg2927Geom = Projection.transform(epsg4326Geom, "EPSG:4326", "EPSG:2927")
        println epsg2927Geom
        // end::transformStaticStr[]
        writeFile("projection_transformstaticstr", epsg2927Geom.wkt)
        epsg2927Geom
    }

    Geometry transformStaticProj() {
        // tag::transformStaticProj[]
        Projection epsg4326 = new Projection("EPSG:4326")
        Projection epsg2927 = new Projection("EPSG:2927")
        Geometry epsg4326Geom = new Point(-122.440, 47.245)
        Geometry epsg2927Geom = Projection.transform(epsg4326Geom, epsg4326, epsg2927)
        println epsg2927Geom
        // end::transformStaticProj[]
        writeFile("projection_transformstaticproj", epsg2927Geom.wkt)
        epsg2927Geom
    }
}
