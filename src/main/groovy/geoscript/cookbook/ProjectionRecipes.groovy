package geoscript.cookbook

import geoscript.geom.Bounds
import geoscript.proj.DecimalDegrees
import geoscript.proj.Geodetic
import geoscript.proj.Projection
import geoscript.geom.Point
import geoscript.geom.Geometry
import org.geotools.referencing.CRS

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

    List<Projection> getAllProjections() {
        CRS.metaClass.static.getSupportedCodes = { String type ->
            [4326,4269,26918,2263,2927]
        }
        // tag::getAllProjections[]
        List<Projection> projections = Projection.projections()
        // end::getAllProjections[]
        CRS.metaClass = null
        writeFile("projection_projections", projections.join(NEW_LINE) + NEW_LINE + "...")
        projections
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

    Geodetic createGeodetic() {
        // tag::createGeodetic[]
        Geodetic geodetic = new Geodetic("wgs84")
        println geodetic
        // end::createGeodetic[]
        writeFile("projection_geodetic", "${geodetic}")
        geodetic
    }

    Map inverseGeodetic() {
        // tag::inverseGeodetic1[]
        Geodetic geodetic = new Geodetic("clrk66")
        Point bostonPoint = new Point(-71.117, 42.25)
        Point portlandPoint = new Point(-123.683, 45.52)
        Map results = geodetic.inverse(bostonPoint, portlandPoint)
        double forwardAzimuth =  results.forwardAzimuth
        println forwardAzimuth
        // end::inverseGeodetic1[]
        writeFile("projection_geodetic_inverse_1", "${forwardAzimuth}")
        // tag::inverseGeodetic2[]
        double backAzimuth = results.backAzimuth
        println backAzimuth
        // end::inverseGeodetic2[]
        writeFile("projection_geodetic_inverse_2", "${backAzimuth}")
        // tag::inverseGeodetic3[]
        double distance = results.distance
        println distance
        // end::inverseGeodetic3[]
        writeFile("projection_geodetic_inverse_3", "${distance}")
        results
    }

    Map forwardGeodetic() {
        // tag::forwardGeodetic1[]
        Geodetic geodetic = new Geodetic("clrk66")
        Point bostonPoint = new Point(-71.117, 42.25)
        Map results = geodetic.forward(bostonPoint, -66.531, 4164192.708)
        Point point = results.point
        println point
        // end::forwardGeodetic1[]
        writeFile("projection_geodetic_forward_1", results.point.wkt)
        // tag::forwardGeodetic2[]
        double azimuth = results.backAzimuth
        println azimuth
        // end::forwardGeodetic2[]
        writeFile("projection_geodetic_forward_2", "${azimuth}")
        results
    }

    List<Point> placePointsGeodetic() {
        // tag::placePointsGeodetic[]
        Geodetic geodetic = new Geodetic("clrk66")
        Point bostonPoint = new Point(-71.117, 42.25)
        Point portlandPoint = new Point(-123.683, 45.52)
        List<Point> points = geodetic.placePoints(bostonPoint, portlandPoint, 10)
        points.each { Point point ->
            println point.wkt
        }
        // end::placePointsGeodetic[]
        writeFile("projection_geodetic_points", points.join(NEW_LINE))
        points
    }

    DecimalDegrees createDecimalDegreesFromLongitudeAndLatitude() {
        // tag::createDecimalDegreesFromLongitudeAndLatitude[]
        DecimalDegrees decimalDegrees = new DecimalDegrees(-122.525619, 47.212023)
        println decimalDegrees
        // end::createDecimalDegreesFromLongitudeAndLatitude[]
        writeFile("projection_decimaldegrees_longitude_latitude", "${decimalDegrees}")
        decimalDegrees
    }

    DecimalDegrees createDecimalDegreesFromPoint() {
        // tag::createDecimalDegreesFromPoint[]
        DecimalDegrees decimalDegrees = new DecimalDegrees(new Point(-122.525619,47.212023))
        println decimalDegrees
        // end::createDecimalDegreesFromPoint[]
        writeFile("projection_decimaldegrees_point", "${decimalDegrees}")
        decimalDegrees
    }

    DecimalDegrees createDecimalDegreesFromLongitudeLatitudeString() {
        // tag::createDecimalDegreesFromLongitudeLatitudeString[]
        DecimalDegrees decimalDegrees = new DecimalDegrees("-122.525619, 47.212023")
        println decimalDegrees
        // end::createDecimalDegreesFromLongitudeLatitudeString[]
        writeFile("projection_decimaldegrees_longitudelatitudestring", "${decimalDegrees}")
        decimalDegrees
    }

    DecimalDegrees createDecimalDegreesFromTwoStringsWithGlyphs() {
        // tag::createDecimalDegreesFromTwoStringsWithGlyphs[]
        DecimalDegrees decimalDegrees = new DecimalDegrees("122\u00B0 31' 32.23\" W", "47\u00B0 12' 43.28\" N")
        println decimalDegrees
        // end::createDecimalDegreesFromTwoStringsWithGlyphs[]
        writeFile("projection_decimaldegrees_twostrings_glyphs", "${decimalDegrees}")
        decimalDegrees
    }

    DecimalDegrees createDecimalDegreesFromTwoStringsWithCharacters() {
        // tag::createDecimalDegreesFromTwoStringsWithCharacters[]
        DecimalDegrees decimalDegrees = new DecimalDegrees("122d 31m 32.23s W", "47d 12m 43.28s N")
        println decimalDegrees
        // end::createDecimalDegreesFromTwoStringsWithCharacters[]
        writeFile("projection_decimaldegrees_twostrings_characters", "${decimalDegrees}")
        decimalDegrees
    }

    DecimalDegrees createDecimalDegreesFromOneDMSString() {
        // tag::createDecimalDegreesFromOneDMSString[]
        DecimalDegrees decimalDegrees = new DecimalDegrees("122d 31m 32.23s W, 47d 12m 43.28s N")
        println decimalDegrees
        // end::createDecimalDegreesFromOneDMSString[]
        writeFile("projection_decimaldegrees_dmsstring", "${decimalDegrees}")
        decimalDegrees
    }

    DecimalDegrees createDecimalDegreesFromOneDDMGlyphsString() {
        // tag::createDecimalDegreesFromOneDDMGlyphsString[]
        DecimalDegrees decimalDegrees = new DecimalDegrees("122\u00B0 31.5372' W, 47\u00B0 12.7213' N")
        println decimalDegrees
        // end::createDecimalDegreesFromOneDDMGlyphsString[]
        writeFile("projection_decimaldegrees_ddmglyphstring", "${decimalDegrees}")
        decimalDegrees
    }

    DecimalDegrees createDecimalDegreesFromOneDDMString() {
        // tag::createDecimalDegreesFromOneDDMString[]
        DecimalDegrees decimalDegrees = new DecimalDegrees("122d 31.5372m W, 47d 12.7213m N")
        println decimalDegrees
        // end::createDecimalDegreesFromOneDDMString[]
        writeFile("projection_decimaldegrees_ddmstring", "${decimalDegrees}")
        decimalDegrees
    }

    Map decimalDegreesGetDms() {
        // tag::decimalDegreesGetDms1[]
        DecimalDegrees decimalDegrees = new DecimalDegrees("122d 31m 32.23s W", "47d 12m 43.28s N")
        Map dms = decimalDegrees.dms
        println "Degrees: ${dms.longitude.degrees}"
        println "Minutes: ${dms.longitude.minutes}"
        println "Seconds: ${dms.longitude.seconds}"
        // end::decimalDegreesGetDms1[]
        writeFile("projection_decimaldegrees_dms_1", "Degrees: ${dms.longitude.degrees}" + NEW_LINE +
                "Minutes: ${dms.longitude.minutes}" + NEW_LINE +
                "Seconds: ${dms.longitude.seconds}"
        )
        // tag::decimalDegreesGetDms2[]
        println "Degrees: ${dms.latitude.degrees}"
        println "Minutes: ${dms.latitude.minutes}"
        println "Seconds: ${dms.latitude.seconds}"
        // end::decimalDegreesGetDms2[]
        writeFile("projection_decimaldegrees_dms_2", """Degrees: ${dms.latitude.degrees}
Minutes: ${dms.latitude.minutes}
Seconds: ${dms.latitude.seconds}""")
        dms
    }

    Map decimalDegreesToDms() {
        // tag::decimalDegreesToDms1[]
        DecimalDegrees decimalDegrees = new DecimalDegrees("122d 31m 32.23s W", "47d 12m 43.28s N")
        println decimalDegrees.toDms(true)
        // end::decimalDegreesToDms1[]
        writeFile("projection_decimaldegrees_todms1", decimalDegrees.toDms(true))
        // tag::decimalDegreesToDms2[]
        println decimalDegrees.toDms(false)
        // end::decimalDegreesToDms2[]
        writeFile("projection_decimaldegrees_todms2", decimalDegrees.toDms(false))
        return [
                true:  decimalDegrees.toDms(true),
                false: decimalDegrees.toDms(false)
        ]
    }

    Map decimalDegreesGetDdm() {
        // tag::decimalDegreesGetDdm1[]
        DecimalDegrees decimalDegrees = new DecimalDegrees("122d 31m 32.23s W", "47d 12m 43.28s N")
        Map dms = decimalDegrees.ddm
        println "Degrees: ${dms.longitude.degrees}"
        println "Minutes: ${dms.longitude.minutes}"
        // end::decimalDegreesGetDdm1[]
        writeFile("projection_decimaldegrees_ddm_1", "Degrees: ${dms.longitude.degrees}" + NEW_LINE +
                "Minutes: ${dms.longitude.minutes}"
        )
        // tag::decimalDegreesGetDdm2[]
        println "Degrees: ${dms.latitude.degrees}"
        println "Minutes: ${dms.latitude.minutes}"
        // end::decimalDegreesGetDdm2[]
        writeFile("projection_decimaldegrees_ddm_2", """Degrees: ${dms.latitude.degrees}
Minutes: ${dms.latitude.minutes}""")
        dms
    }

    Map decimalDegreesToDdm() {
        // tag::decimalDegreesToDdm1[]
        DecimalDegrees decimalDegrees = new DecimalDegrees("122d 31m 32.23s W", "47d 12m 43.28s N")
        println decimalDegrees.toDdm(true)
        // end::decimalDegreesToDdm1[]
        writeFile("projection_decimaldegrees_toddm1", decimalDegrees.toDdm(true))
        // tag::decimalDegreesToDdm2[]
        println decimalDegrees.toDdm(false)
        // end::decimalDegreesToDdm2[]
        writeFile("projection_decimaldegrees_toddm2", decimalDegrees.toDdm(false))
        return [
                true:  decimalDegrees.toDdm(true),
                false: decimalDegrees.toDdm(false)
        ]
    }

    Point decimalDegreesGetPoint() {
        // tag::decimalDegreesGetPoint[]
        DecimalDegrees decimalDegrees = new DecimalDegrees("122d 31m 32.23s W", "47d 12m 43.28s N")
        Point point = decimalDegrees.point
        // end::decimalDegreesGetPoint[]
        writeFile("projection_decimaldegrees_point", "${point.wkt}")
        point
    }
}
