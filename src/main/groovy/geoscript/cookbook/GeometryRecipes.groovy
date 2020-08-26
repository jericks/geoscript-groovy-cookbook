package geoscript.cookbook

import geoscript.geom.io.GeoPackageReader
import geoscript.geom.io.GeoPackageWriter
import geoscript.geom.io.TWkbReader
import org.h2.command.Prepared
import org.locationtech.jts.geom.IntersectionMatrix
import geoscript.geom.Bounds
import geoscript.geom.CircularRing
import geoscript.geom.CircularString
import geoscript.geom.CompoundCurve
import geoscript.geom.CompoundRing
import geoscript.geom.Geometry
import geoscript.geom.GeometryCollection
import geoscript.geom.LineString
import geoscript.geom.LinearRing
import geoscript.geom.MultiLineString
import geoscript.geom.MultiPoint
import geoscript.geom.MultiPolygon
import geoscript.geom.Point
import geoscript.geom.Polygon
import geoscript.geom.PreparedGeometry
import geoscript.geom.io.GeoJSONReader
import geoscript.geom.io.GeoJSONWriter
import geoscript.geom.io.GeoRSSReader
import geoscript.geom.io.GeoRSSWriter
import geoscript.geom.io.GeobufReader
import geoscript.geom.io.GeobufWriter
import geoscript.geom.io.Gml2Reader
import geoscript.geom.io.Gml2Writer
import geoscript.geom.io.Gml3Reader
import geoscript.geom.io.Gml3Writer
import geoscript.geom.io.GooglePolylineEncoder
import geoscript.geom.io.GpxReader
import geoscript.geom.io.GpxWriter
import geoscript.geom.io.KmlReader
import geoscript.geom.io.KmlWriter
import geoscript.geom.io.Reader
import geoscript.geom.io.Readers
import geoscript.geom.io.WkbReader
import geoscript.geom.io.WkbWriter
import geoscript.geom.io.WktReader
import geoscript.geom.io.WktWriter
import geoscript.geom.io.Writer
import geoscript.geom.io.Writers
import geoscript.viewer.Viewer

class GeometryRecipes extends Recipes {
  
    Point createPoint() {
        // tag::createPoint[]
        Point point = new Point(-123,46)
        // end::createPoint[]
        drawGeometry("geometry_create_point", point)
        point
    }

    LineString createLineStringFromCoordinates() {
        // tag::createLineStringFromCoordinates[]
        LineString lineString = new LineString(
                [3.1982421875, 43.1640625],
                [6.7138671875, 49.755859375],
                [9.7021484375, 42.5927734375],
                [15.3271484375, 53.798828125]
        )
        // end::createLineStringFromCoordinates[]
        drawGeometry("geometry_create_linestring_coordinates", lineString)
        lineString
    }

    Polygon createPolygon() {
        // tag::createPolygon[]
        Polygon polygon = new Polygon([[
               [-101.35986328125, 47.754097979680026],
               [-101.5576171875, 46.93526088057719],
               [-100.12939453125, 46.51351558059737],
               [-99.77783203125, 47.44294999517949],
               [-100.45898437499999, 47.88688085106901],
               [-101.35986328125, 47.754097979680026]
        ]])
        // end::createPolygon[]
        drawGeometry("geometry_create_polygon", polygon)
        polygon
    }

    Polygon createPolygonWithHoles() {
        // tag::createPolygonWithHoles[]
        Polygon polygonWithHoles = new Polygon(
            // Exterior Ring
            new LinearRing(
                [-122.39138603210449, 47.58659965790016],
                [-122.41250038146973, 47.57681522195182],
                [-122.40305900573729, 47.56523364515569],
                [-122.38117218017578, 47.56621817878201],
                [-122.3712158203125, 47.57235661809739],
                [-122.37602233886717, 47.584747123985615],
                [-122.39138603210449, 47.58659965790016]
            ),
            // Holes
            [
                new LinearRing(
                    [-122.39859580993652, 47.578957532923376],
                    [-122.40468978881836, 47.57548347095205],
                    [-122.39593505859376, 47.570271945800094],
                    [-122.3920726776123, 47.57606249728773],
                    [-122.39859580993652, 47.578957532923376]
                ),
                new LinearRing(
                    [-122.3836612701416, 47.58156292813543],
                    [-122.38829612731934, 47.57114056934196],
                    [-122.37456321716309, 47.57420959047542],
                    [-122.37868309020995, 47.58023129789275],
                    [-122.3836612701416, 47.58156292813543]
                )
            ]
        )
        // end::createPolygonWithHoles[]
        drawGeometry("geometry_create_polygon_withholes", polygonWithHoles)
        polygonWithHoles
    }

    MultiPoint createMultiPoint() {
        // tag::createMultiPoint[]
        MultiPoint multiPoint = new MultiPoint([
                new Point(-122.3876953125, 47.5820839916191),
                new Point(-122.464599609375, 47.25686404408872),
                new Point(-122.48382568359374, 47.431803338643334)
        ])
        // end::createMultiPoint[]
        drawGeometry("geometry_create_multipoint", multiPoint)
        multiPoint
    }

    MultiLineString createMultiLineString() {
        // tag::createMultiLineString[]
        MultiLineString multiLineString = new MultiLineString([
                new LineString (
                        [-122.3822021484375, 47.57837853860192],
                        [-122.32452392578125, 47.48380086737799]
                ),
                new LineString (
                        [-122.32452392578125, 47.48380086737799],
                        [-122.29705810546874, 47.303447043862626]
                ),
                new LineString (
                        [-122.29705810546874, 47.303447043862626],
                        [-122.42889404296875, 47.23262467463881]
                )
        ])
        // end::createMultiLineString[]
        drawGeometry("geometry_create_multilinestring", multiLineString)
        multiLineString
    }

    MultiPolygon createMultiPolygon() {
        // tag::createMultiPolygon[]
        MultiPolygon multiPolygon = new MultiPolygon(
            new Polygon ([[
                    [-122.2723388671875,  47.818687628247105],
                    [-122.37945556640624, 47.66168780332917],
                    [-121.95373535156249, 47.67093619422418],
                    [-122.2723388671875,  47.818687628247105]
            ]]),
            new Polygon ([[
                    [-122.76672363281249, 47.42437092240516],
                    [-122.76672363281249, 47.59505101193038],
                    [-122.52227783203125, 47.59505101193038],
                    [-122.52227783203125, 47.42437092240516],
                    [-122.76672363281249, 47.42437092240516]
            ]]),
            new Polygon ([[
                    [-122.20367431640624, 47.543163654317304],
                    [-122.3712158203125,  47.489368981370724],
                    [-122.33276367187499, 47.35371061951363],
                    [-122.11029052734374, 47.3704545156932],
                    [-122.08831787109375, 47.286681888764214],
                    [-122.28332519531249, 47.2270293988673],
                    [-122.2174072265625,  47.154237057576594],
                    [-121.904296875,      47.32579231609051],
                    [-122.06085205078125, 47.47823216312885],
                    [-122.20367431640624, 47.543163654317304]
            ]])
        )
        // end::createMultiPolygon[]
        drawGeometry("geometry_create_multipolygon", multiPolygon)
        multiPolygon
    }

    GeometryCollection createGeometryCollection() {
        // tag::createGeometryCollection[]
        GeometryCollection geometryCollection = new GeometryCollection(
            new LineString ([-157.044, 58.722], [-156.461, 58.676]),
            new Point(-156.648, 58.739),
            new Polygon([[
                 [-156.395, 58.7083],
                 [-156.412, 58.6026],
                 [-155.874, 58.5825],
                 [-155.313, 58.4822],
                 [-155.385, 58.6655],
                 [-156.203, 58.7368],
                 [-156.395, 58.7083]
            ]]),
            new Point(-156.741, 58.582)
        )
        // end::createGeometryCollection[]
        drawGeometry("geometry_create_geometrycollection", geometryCollection)
        geometryCollection
    }

    CircularString createCircularString() {
        // tag::createCircularString[]
        CircularString circularString = new CircularString([
                [-122.464599609375, 47.247542522268006],
                [-122.03613281249999, 47.37789454155521],
                [-122.37670898437499, 47.58393661978134]
        ])
        // end::createCircularString[]
        drawGeometry("geometry_create_circularstring", circularString)
        circularString
    }

    CircularRing createCircularRing() {
        // tag::createCircularRing[]
        CircularRing circularRing = new CircularRing([
                [-118.47656249999999, 41.508577297439324],
                [-109.6875, 57.51582286553883],
                [-93.8671875, 42.032974332441405],
                [-62.57812500000001, 30.14512718337613],
                [-92.10937499999999, 7.36246686553575],
                [ -127.265625, 14.604847155053898],
                [-118.47656249999999, 41.508577297439324]
        ])
        // end::createCircularRing[]
        drawGeometries("geometry_create_circularring", [circularRing.linear])
        circularRing
    }

    CompoundCurve createCompoundCurve() {
        // tag::createCompoundCurve[]
        CompoundCurve compoundCurve = new CompoundCurve([
            new CircularString([
                    [27.0703125, 23.885837699862005],
                    [5.9765625, 40.17887331434696],
                    [22.5, 47.98992166741417],
            ]),
            new LineString([
                    [22.5, 47.98992166741417],
                    [71.71875, 49.15296965617039],
            ]),
            new CircularString([
                    [71.71875, 49.15296965617039],
                    [81.5625, 39.36827914916011],
                    [69.9609375, 24.5271348225978]

            ])
        ])
        // end::createCompoundCurve[]
        drawGeometries("geometry_create_compoundcurve", [compoundCurve.linear])
        compoundCurve
    }

    CompoundRing createCompoundRing() {
        // tag::createCompoundRing[]
        CompoundRing compoundRing = new CompoundRing([
                new CircularString([
                        [27.0703125, 23.885837699862005],
                        [5.9765625, 40.17887331434696],
                        [22.5, 47.98992166741417],
                ]),
                new LineString([
                        [22.5, 47.98992166741417],
                        [71.71875, 49.15296965617039],
                ]),
                new CircularString([
                        [71.71875, 49.15296965617039],
                        [81.5625, 39.36827914916011],
                        [69.9609375, 24.5271348225978]

                ]),
                new LineString([
                        [69.9609375, 24.5271348225978],
                        [27.0703125, 23.885837699862005],
                ])
        ])
        // end::createCompoundRing[]
        drawGeometries("geometry_create_compoundring", [compoundRing.linear])
        compoundRing
    }

    // CircularRing

    CircularRing circularRingCurvedWkt() {
        // tag::circularRingCurvedWkt[]
        CircularRing circularRing = new CircularRing([
                [-118.47656249999999, 41.508577297439324],
                [-109.6875, 57.51582286553883],
                [-93.8671875, 42.032974332441405],
                [-62.57812500000001, 30.14512718337613],
                [-92.10937499999999, 7.36246686553575],
                [ -127.265625, 14.604847155053898],
                [-118.47656249999999, 41.508577297439324]
        ])
        println "WKT = ${circularRing.wkt}"
        println "Curnved WKT = ${circularRing.curvedWkt}"
        // end::circularRingCurvedWkt[]
        drawGeometries("geometry_circularring_curvedwkt", [circularRing.linear])
        writeFile("geometry_circularring_curvedwkt", "WKT = ${circularRing.wkt}${NEW_LINE}Curnved WKT = ${circularRing.curvedWkt}")
        circularRing
    }

    List<Point> circularRingControlPoints() {
        // tag::circularRingControlPoints[]
        CircularRing circularRing = new CircularRing([
                [-118.47656249999999, 41.508577297439324],
                [-109.6875, 57.51582286553883],
                [-93.8671875, 42.032974332441405],
                [-62.57812500000001, 30.14512718337613],
                [-92.10937499999999, 7.36246686553575],
                [ -127.265625, 14.604847155053898],
                [-118.47656249999999, 41.508577297439324]
        ])
        List<Point> points = circularRing.controlPoints
        points.each { Point point ->
            println point
        }
        // end::circularRingControlPoints[]
        drawGeometries("geometry_circularring_controlpoints", [circularRing.linear] + points, drawCoords: false)
        writeFile("geometry_circularring_controlpoints", "${points.collect { it.wkt }.join(NEW_LINE)}")
        points
    }

    Geometry circularRingToLinear() {
        // tag::circularRingToLinear[]
        CircularRing circularRing = new CircularRing([
                [-118.47656249999999, 41.508577297439324],
                [-109.6875, 57.51582286553883],
                [-93.8671875, 42.032974332441405],
                [-62.57812500000001, 30.14512718337613],
                [-92.10937499999999, 7.36246686553575],
                [ -127.265625, 14.604847155053898],
                [-118.47656249999999, 41.508577297439324]
        ])
        Geometry linear = circularRing.linear
        println linear.wkt
        // end::circularRingToLinear[]
        drawGeometries("geometry_circularring_linear", [circularRing.linear])
        writeFile("geometry_circularring_linear", "${linear.wkt}")
        linear
    }

    // CircularString

    CircularString circularStringCurvedWkt() {
        // tag::circularStringCurvedWkt[]
        CircularString circularString = new CircularString([
                [-122.464599609375, 47.247542522268006],
                [-122.03613281249999, 47.37789454155521],
                [-122.37670898437499, 47.58393661978134]
        ])
        println "WKT = ${circularString.wkt}"
        println "Curnved WKT = ${circularString.curvedWkt}"
        // end::circularStringCurvedWkt[]
        drawGeometries("geometry_circularstring_curvedwkt", [circularString.linear])
        writeFile("geometry_circularstring_curvedwkt", "WKT = ${circularString.wkt}${NEW_LINE}Curnved WKT = ${circularString.curvedWkt}")
        circularString
    }

    List<Point> circularStringControlPoints() {
        // tag::circularStringControlPoints[]
        CircularString circularString = new CircularString([
                [-122.464599609375, 47.247542522268006],
                [-122.03613281249999, 47.37789454155521],
                [-122.37670898437499, 47.58393661978134]
        ])
        List<Point> points = circularString.controlPoints
        points.each { Point point ->
            println point
        }
        // end::circularStringControlPoints[]
        drawGeometries("geometry_circularstring_controlpoints", [circularString.linear] + points, drawCoords: false)
        writeFile("geometry_circularstring_controlpoints", "${points.collect { it.wkt }.join(NEW_LINE)}")
        points
    }

    Geometry circularStringToLinear() {
        // tag::circularStringToLinear[]
        CircularString circularString = new CircularString([
                [-122.464599609375, 47.247542522268006],
                [-122.03613281249999, 47.37789454155521],
                [-122.37670898437499, 47.58393661978134]
        ])
        Geometry linear = circularString.linear
        println linear.wkt
        // end::circularStringToLinear[]
        drawGeometries("geometry_circularstring_linear", [linear])
        writeFile("geometry_circularstring_linear", "${linear.wkt}")
        linear
    }

    // CompoundCurve

    CompoundCurve compoundCurveCurvedWkt() {
        // tag::compoundCurveCurvedWkt[]
        CompoundCurve compoundCurve = new CompoundCurve([
                new CircularString([
                        [27.0703125, 23.885837699862005],
                        [5.9765625, 40.17887331434696],
                        [22.5, 47.98992166741417],
                ]),
                new LineString([
                        [22.5, 47.98992166741417],
                        [71.71875, 49.15296965617039],
                ]),
                new CircularString([
                        [71.71875, 49.15296965617039],
                        [81.5625, 39.36827914916011],
                        [69.9609375, 24.5271348225978]

                ])
        ])
        println "WKT = ${compoundCurve.wkt}"
        println "Curnved WKT = ${compoundCurve.curvedWkt}"
        // end::compoundCurveCurvedWkt[]
        drawGeometries("geometry_compoundcurve_curvedwkt", [compoundCurve.linear])
        writeFile("geometry_compoundcurve_curvedwkt", "WKT = ${compoundCurve.wkt}${NEW_LINE}Curnved WKT = ${compoundCurve.curvedWkt}")
        compoundCurve
    }

    List<LineString> compoundCurveComponents() {
        // tag::compoundCurveComponents[]
        CompoundCurve compoundCurve = new CompoundCurve([
                new CircularString([
                        [27.0703125, 23.885837699862005],
                        [5.9765625, 40.17887331434696],
                        [22.5, 47.98992166741417],
                ]),
                new LineString([
                        [22.5, 47.98992166741417],
                        [71.71875, 49.15296965617039],
                ]),
                new CircularString([
                        [71.71875, 49.15296965617039],
                        [81.5625, 39.36827914916011],
                        [69.9609375, 24.5271348225978]

                ])
        ])
        List<LineString> lineStrings = compoundCurve.components
        lineStrings.each { LineString lineString ->
            println lineString
        }
        // end::compoundCurveComponents[]
        drawGeometries("geometry_compoundcurve_components", lineStrings, drawCoords: true)
        writeFile("geometry_compoundcurve_components", "${lineStrings.collect { it.wkt }.join(NEW_LINE)}")
        lineStrings
    }

    Geometry compoundCurveToLinear() {
        // tag::compoundCurveToLinear[]
        CompoundCurve compoundCurve = new CompoundCurve([
                new CircularString([
                        [27.0703125, 23.885837699862005],
                        [5.9765625, 40.17887331434696],
                        [22.5, 47.98992166741417],
                ]),
                new LineString([
                        [22.5, 47.98992166741417],
                        [71.71875, 49.15296965617039],
                ]),
                new CircularString([
                        [71.71875, 49.15296965617039],
                        [81.5625, 39.36827914916011],
                        [69.9609375, 24.5271348225978]

                ])
        ])
        Geometry linear = compoundCurve.linear
        println linear.wkt
        // end::compoundCurveToLinear[]
        drawGeometries("geometry_compoundcurve_linear", [linear])
        writeFile("geometry_compoundcurve_linear", "${linear.wkt}")
        linear
    }
    
    // CompoundRing

    CompoundRing compoundRingCurvedWkt() {
        // tag::compoundRingCurvedWkt[]
        CompoundRing compoundRing = new CompoundRing([
                new CircularString([
                        [27.0703125, 23.885837699862005],
                        [5.9765625, 40.17887331434696],
                        [22.5, 47.98992166741417],
                ]),
                new LineString([
                        [22.5, 47.98992166741417],
                        [71.71875, 49.15296965617039],
                ]),
                new CircularString([
                        [71.71875, 49.15296965617039],
                        [81.5625, 39.36827914916011],
                        [69.9609375, 24.5271348225978]

                ]),
                new LineString([
                        [69.9609375, 24.5271348225978],
                        [27.0703125, 23.885837699862005],
                ])
        ])
        println "WKT = ${compoundRing.wkt}"
        println "Curnved WKT = ${compoundRing.curvedWkt}"
        // end::compoundRingCurvedWkt[]
        drawGeometries("geometry_compoundring_curvedwkt", [compoundRing.linear])
        writeFile("geometry_compoundring_curvedwkt", "WKT = ${compoundRing.wkt}${NEW_LINE}Curnved WKT = ${compoundRing.curvedWkt}")
        compoundRing
    }

    List<LineString> compoundRingComponents() {
        // tag::compoundRingComponents[]
        CompoundRing compoundRing = new CompoundRing([
                new CircularString([
                        [27.0703125, 23.885837699862005],
                        [5.9765625, 40.17887331434696],
                        [22.5, 47.98992166741417],
                ]),
                new LineString([
                        [22.5, 47.98992166741417],
                        [71.71875, 49.15296965617039],
                ]),
                new CircularString([
                        [71.71875, 49.15296965617039],
                        [81.5625, 39.36827914916011],
                        [69.9609375, 24.5271348225978]

                ]),
                new LineString([
                        [69.9609375, 24.5271348225978],
                        [27.0703125, 23.885837699862005],
                ])
        ])
        List<LineString> lineStrings = compoundRing.components
        lineStrings.each { LineString lineString ->
            println lineString
        }
        // end::compoundRingComponents[]
        drawGeometries("geometry_compoundring_components", lineStrings, drawCoords: true)
        writeFile("geometry_compoundring_components", "${lineStrings.collect { it.wkt }.join(NEW_LINE)}")
        lineStrings
    }

    Geometry compoundRingToLinear() {
        // tag::compoundRingToLinear[]
        CompoundRing compoundRing = new CompoundRing([
                new CircularString([
                        [27.0703125, 23.885837699862005],
                        [5.9765625, 40.17887331434696],
                        [22.5, 47.98992166741417],
                ]),
                new LineString([
                        [22.5, 47.98992166741417],
                        [71.71875, 49.15296965617039],
                ]),
                new CircularString([
                        [71.71875, 49.15296965617039],
                        [81.5625, 39.36827914916011],
                        [69.9609375, 24.5271348225978]

                ]),
                new LineString([
                        [69.9609375, 24.5271348225978],
                        [27.0703125, 23.885837699862005],
                ])
        ])
        Geometry linear = compoundRing.linear
        println linear.wkt
        // end::compoundRingToLinear[]
        drawGeometries("geometry_compoundring_linear", [linear])
        writeFile("geometry_compoundring_linear", "${linear.wkt}")
        linear
    }
    
    // LineStrings

    Map<String,Geometry> getStartPointFromLineString() {
        // tag::getStartPointFromLineString[]
        LineString lineString = new LineString(
                [3.1982421875, 43.1640625],
                [6.7138671875, 49.755859375],
                [9.7021484375, 42.5927734375],
                [15.3271484375, 53.798828125]
        )
        Point startPoint = lineString.startPoint
        // end::getStartPointFromLineString[]
        drawGeometries("geometry_get_startpoint_from_linestring", [startPoint, lineString], drawCoords: false)
        [lineString: lineString, point: startPoint]
    }

    Map<String,Geometry> getEndPointFromLineString() {
        // tag::getEndPointFromLineString[]
        LineString lineString = new LineString(
                [3.1982421875, 43.1640625],
                [6.7138671875, 49.755859375],
                [9.7021484375, 42.5927734375],
                [15.3271484375, 53.798828125]
        )
        Point endPoint = lineString.endPoint
        // end::getEndPointFromLineString[]
        drawGeometries("geometry_get_endpoint_from_linestring", [endPoint, lineString], drawCoords: false)
        [lineString: lineString, point: endPoint]
    }

    Map<String,Geometry> reverseLineString() {
        // tag::reverseLineString[]
        LineString lineString = new LineString(
                [3.1982421875, 43.1640625],
                [6.7138671875, 49.755859375],
                [9.7021484375, 42.5927734375],
                [15.3271484375, 53.798828125]
        )
        Point startPoint = lineString.startPoint

        LineString reversedLineString = lineString.reverse()
        Point reversedStartPoint = reversedLineString.startPoint
        // end::reverseLineString[]
        drawGeometries("geometry_reverse_linestring1", [startPoint, lineString], drawCoords: false)
        drawGeometries("geometry_reverse_linestring2", [reversedStartPoint, reversedLineString], drawCoords: false)
        [lineString: lineString, startPoint: startPoint, reversedLineString: reversedLineString, reversedStartPoint: reversedStartPoint]
    }

    Map<String,Object> isLineStringClosed() {
        // tag::isLineStringClosed[]
        LineString lineString1 = new LineString(
                [3.1982421875, 43.1640625],
                [6.7138671875, 49.755859375],
                [9.7021484375, 42.5927734375],
                [15.3271484375, 53.798828125]
        )
        boolean isClosed1 = lineString1.closed
        println "Is ${lineString1.wkt} closed? ${isClosed1}"

        LineString lineString2 = new LineString(
                [3.1982421875, 43.1640625],
                [6.7138671875, 49.755859375],
                [9.7021484375, 42.5927734375],
                [15.3271484375, 53.798828125],
                [3.1982421875, 43.1640625]
        )
        boolean isClosed2 = lineString2.closed
        println "Is ${lineString2.wkt} closed? ${isClosed2}"
        // end::isLineStringClosed[]
        drawGeometry("geometry_is_linestring_closed1", lineString1)
        writeFile("geometry_is_linestring_closed1", "Is ${lineString1.wkt} closed? ${isClosed1}")
        drawGeometry("geometry_is_linestring_closed2", lineString2)
        writeFile("geometry_is_linestring_closed2", "Is ${lineString2.wkt} closed? ${isClosed2}")
        [lineString1: lineString1, isClosed1: isClosed1, lineString2: lineString2, isClosed2: isClosed2]
    }

    Map<String,Object> isLineStringRing() {
        // tag::isLineStringRing[]
        LineString lineString1 = new LineString(
            [-122.391428, 47.563300],
            [-122.391836, 47.562793],
            [-122.391010, 47.562417],
            [-122.390516, 47.563126]
        )
        boolean isRing1 = lineString1.ring
        println "Is ${lineString1.wkt} a ring? ${isRing1}"

        LineString lineString2 = new LineString(
            [-122.391428, 47.563300],
            [-122.391836, 47.562793],
            [-122.391010, 47.562417],
            [-122.390516, 47.563126],
            [-122.391428, 47.563300]
        )
        boolean isRing2 = lineString2.ring
        println "Is ${lineString2.wkt} a ring? ${isRing2}"
        // end::isLineStringRing[]
        drawGeometry("geometry_is_linestring_ring1", lineString1)
        writeFile("geometry_is_linestring_ring1", "Is ${lineString1.wkt} a ring? ${isRing1}")
        drawGeometry("geometry_is_linestring_ring2", lineString2)
        writeFile("geometry_is_linestring_ring2", "Is ${lineString2.wkt} a ring? ${isRing2}")
        [lineString1: lineString1, isRing1: isRing1, lineString2: lineString2, isRing2: isRing2]
    }

    Map<String,Geometry> closeLineString() {
        // tag::closeLineString[]
        LineString lineString = new LineString(
                [-122.391428, 47.563300],
                [-122.391836, 47.562793],
                [-122.391010, 47.562417],
                [-122.390516, 47.563126]
        )

        LinearRing linearRing = lineString.close()
        // end::closeLineString[]
        drawGeometry("geometry_close_linestring", lineString)
        drawGeometry("geometry_close_linearring", linearRing)
        [lineString: lineString, linearRing: linearRing]
    }

    MultiLineString lineStringPlus() {
        // tag::lineStringPlus[]
        LineString lineString1 = new LineString([
            [-122.39142894744873, 47.5812734461813],
            [-122.38237380981445, 47.58121554959838]
        ])

        LineString lineString2 = new LineString([
            [-122.38640785217285, 47.58552866972616],
            [-122.38670825958253, 47.57837853860192]
        ])

        MultiLineString multiLineString = lineString1 + lineString2
        // end::lineStringPlus[]
        drawGeometry("geometry_linestring_plus", multiLineString)
        multiLineString
    }

    LineString lineStringPlusPoint() {
        // tag::lineStringPlusPoint[]
        LineString lineString = new LineString([
                [-122.39142894744873, 47.5812734461813],
                [-122.38237380981445, 47.58121554959838]
        ])

        Point point = new Point(-122.38640785217285, 47.58552866972616)

        LineString lineStringWithPoint = lineString + point
        // end::lineStringPlusPoint[]
        drawGeometries("geometry_linestring_pluspoint1", [lineString, point])
        drawGeometry("geometry_linestring_pluspoint2", lineStringWithPoint)
        lineStringWithPoint
    }

    LineString lineStringAddPoint() {
        // tag::lineStringAddPoint[]
        LineString lineString = new LineString([
                [-122.39142894744873, 47.5812734461813],
                [-122.38237380981445, 47.58121554959838]
        ])

        Point point = new Point(-122.38640785217285, 47.58552866972616)

        LineString lineStringWithPoint = lineString.addPoint(1, point)
        // end::lineStringAddPoint[]
        drawGeometries("geometry_linestring_addpoint1", [lineString, point])
        drawGeometry("geometry_linestring_addpoint2", lineStringWithPoint)
        lineStringWithPoint
    }

    LineString lineStringSetPoint() {
        // tag::lineStringSetPoint[]
        LineString lineString = new LineString([
                [-122.39142894744873, 47.5812734461813],
                [-122.38237380981445, 47.58121554959838]
        ])

        Point point = new Point(-122.38640785217285, 47.58552866972616)

        LineString newLineString = lineString.setPoint(1, point)
        // end::lineStringSetPoint[]
        drawGeometries("geometry_linestring_setpoint1", [lineString, point])
        drawGeometry("geometry_linestring_setpoint2", newLineString)
        newLineString
    }

    LineString lineStringRemovePoint() {
        // tag::lineStringRemovePoint[]
        LineString lineString = new LineString([
                [-122.39142894744873, 47.5812734461813],
                [-122.38237380981445, 47.58121554959838],
                [-122.38640785217285, 47.58552866972616]
        ])

        LineString newLineString = lineString.removePoint(2)
        // end::lineStringRemovePoint[]
        drawGeometries("geometry_linestring_removepoint1", [lineString])
        drawGeometry("geometry_linestring_removepoint2", newLineString)
        newLineString
    }

    List<LineString> lineStringNegative() {
        // tag::lineStringNegative1[]
        LineString lineString1 = new LineString([
            [-122.39423990249632, 47.57926150237904],
            [-122.3918581008911, 47.58121554959838],
            [-122.38657951354979, 47.58121554959838],
            [-122.38638639450075, 47.58535499390333],
            [-122.38374710083008, 47.58535499390333]
        ])
        // end::lineStringNegative1[]
        drawGeometry("geometry_linestring_negative1", lineString1)

        // tag::lineStringNegative2[]
        LineString lineString2 = -lineString1
        // end::lineStringNegative2[]
        drawGeometry("geometry_linestring_negative2", lineString2)

        // tag::lineStringNegative3[]
        LineString lineString3 = -lineString2
        // end::lineStringNegative3[]
        drawGeometry("geometry_linestring_negative3", lineString3)

        [lineString1, lineString2, lineString3]
    }

    List<Point> lineStringInterpolatePoint() {
        // tag::lineStringInterpolatePoint[]
        LineString lineString = new LineString([
            [-122.39423990249632, 47.57926150237904],
            [-122.3918581008911, 47.58121554959838],
            [-122.38657951354979, 47.58121554959838],
            [-122.38638639450075, 47.58535499390333],
            [-122.38374710083008, 47.58535499390333]
        ])
        Point startPoint = lineString.interpolatePoint(0.0)
        Point midPoint = lineString.interpolatePoint(0.5)
        Point endPoint = lineString.interpolatePoint(1.0)
        // end::lineStringInterpolatePoint[]
        drawGeometries("geometry_linestring_interpolatepoint", [lineString, startPoint, midPoint, endPoint], drawCoords: false)

        [startPoint, midPoint, endPoint]
    }

    List<Double> lineStringLocatePoint() {
        // tag::lineStringLocatePoint[]
        LineString lineString = new LineString([
            [-122.39423990249632, 47.57926150237904],
            [-122.3918581008911, 47.58121554959838],
            [-122.38657951354979, 47.58121554959838],
            [-122.38638639450075, 47.58535499390333],
            [-122.38374710083008, 47.58535499390333]
        ])

        Point point1 = new Point(-122.39423990249632, 47.57926150237904)
        Double position1 = lineString.locatePoint(point1)
        println "Position of ${point1} is ${position1}"

        Point point2 = new Point(-122.38736758304911, 47.58121554959838)
        Double position2 = lineString.locatePoint(point2)
        println "Position of ${point2} is ${position2}"
        
        Point point3 = new Point(-122.38374710083008, 47.58535499390333)
        Double position3 = lineString.locatePoint(point3)
        println "Position of ${point3} is ${position3}"
        // end::lineStringLocatePoint[]
        drawGeometries("geometry_linestring_locatepoint", [lineString, point1, point2, point3], drawCoords: false)
        writeFile("geometry_linestring_locatepoint", """Position of ${point1} is ${position1}
Position of ${point2} is ${position2}
Position of ${point3} is ${position3}""")

        [position1, position2, position3]
    }

    List<Point> lineStringPlacePoint() {
        // tag::lineStringPlacePoint[]
        LineString lineString = new LineString ([
            [-122.37155914306639, 47.57166173655188],
            [-122.32160568237306, 47.5714301073211]
        ])

        Point point1 = new Point(-122.358341217041, 47.57432539907205)
        Point pointOnLine1 = lineString.placePoint(point1)

        Point point2 = new Point(-122.33860015869139, 47.56830301243495)
        Point pointOnLine2 = lineString.placePoint(point2)
        // end::lineStringPlacePoint[]
        drawGeometries("geometry_linestring_placepoint1", [lineString, point1, point2])
        drawGeometries("geometry_linestring_placepoint2", [lineString, point1, point2, pointOnLine1, pointOnLine2])
        [pointOnLine1, pointOnLine2]
    }

    LineString lineStringSubLine() {
        // tag::lineStringSubLine[]
        LineString lineString = new LineString([
            [-122.39423990249632, 47.57926150237904],
            [-122.3918581008911, 47.58121554959838],
            [-122.38657951354979, 47.58121554959838],
            [-122.38638639450075, 47.58535499390333],
            [-122.38374710083008, 47.58535499390333]
        ])
        LineString subLine = lineString.subLine(0.33, 0.66)
        // end::lineStringSubLine[]
        drawGeometry("geometry_linestring_subline1", lineString)
        drawGeometry("geometry_linestring_subline2", subLine)
        subLine
    }

    MultiPoint lineStringCreatePointsAlong() {
        // tag::lineStringCreatePointsAlong[]
        LineString lineString = new LineString([
                [-122.39423990249632, 47.57926150237904],
                [-122.3918581008911, 47.58121554959838],
                [-122.38657951354979, 47.58121554959838],
                [-122.38638639450075, 47.58535499390333],
                [-122.38374710083008, 47.58535499390333]
        ])
        MultiPoint multiPoint = lineString.createPointsAlong(0.001)
        // end::lineStringCreatePointsAlong[]
        drawGeometries("geometry_linestring_createpointsalong", [lineString, multiPoint])
        multiPoint
    }

    // MultiLineString

    MultiLineString multiLineStringPlus() {
        // tag::multiLineStringPlus[]
        MultiLineString multiLineString = new MultiLineString([
            new LineString (
                    [-122.3822021484375, 47.57837853860192],
                    [-122.32452392578125, 47.48380086737799]
            ),
            new LineString (
                    [-122.32452392578125, 47.48380086737799],
                    [-122.29705810546874, 47.303447043862626]
            )
        ])

        LineString lineString = new LineString (
            [-122.29705810546874, 47.303447043862626],
            [-122.42889404296875, 47.23262467463881]
        )

        MultiLineString newMultiLineString = multiLineString + lineString
        // end::multiLineStringPlus[]
        drawGeometry("geometry_multilinestring_plus", newMultiLineString)
        newMultiLineString
    }

    MultiLineString multiLineStringMerge() {
        // tag::multiLineStringMerge[]
        MultiLineString multiLineString = new MultiLineString([
            new LineString (
                [-122.3822021484375, 47.57837853860192],
                [-122.32452392578125, 47.48380086737799]
            ),
            new LineString (
                [-122.32452392578125, 47.48380086737799],
                [-122.29705810546874, 47.303447043862626]
            ),
            new LineString (
                [-122.29705810546874, 47.303447043862626],
                [-122.42889404296875, 47.23262467463881]
            )
        ])
        MultiLineString mergedMultiLineString = multiLineString.merge()

        println "Original MultiLineString = ${multiLineString}"
        println "Merged MultiLineString   = ${mergedMultiLineString}"
        // end::multiLineStringMerge[]
        drawGeometry("geometry_multilinestring_merge", mergedMultiLineString)
        writeFile("geometry_multilinestring_merge", "Original MultiLineString = ${multiLineString}${NEW_LINE}${NEW_LINE}Merged MultiLineString   = ${mergedMultiLineString}")
        mergedMultiLineString
    }

    MultiPoint multiLineStringCreatePointsAlong() {
        // tag::multiLineStringCreatePointsAlong[]
        MultiLineString lines = new MultiLineString(
                new LineString ([-5.70068359375, 45.1416015625], [2.47314453125, 53.9306640625]),
                new LineString ([-1.21826171875, 53.9306640625], [8.88916015625, 46.1962890625]),
                new LineString ([0.71533203125, 42.63671875], [7.13134765625, 50.37109375]),
                new LineString ([-5.83251953125, 46.943359375], [4.45068359375, 42.98828125])
        )
        MultiPoint points = lines.createPointsAlong(1)
        // end::multiLineStringCreatePointsAlong[]
        drawGeometries("geometry_multilinestring_createpointsalong", [lines, points])
        points
    }


    // Point

    Point getPointXYZ() {
        // tag::getPointXYZ[]
        Point point = new Point(-122.38632, 47.58208, 101.45)
        println "X = ${point.x}"
        println "Y = ${point.y}"
        println "Z = ${point.z}"
        // end::getPointXYZ[]
        drawGeometry("geometry_point_xyz", point)
        writeFile("geometry_point_xyz", "X = ${point.x}${NEW_LINE}Y = ${point.y}${NEW_LINE}Z = ${point.z}")
        point
    }

    MultiPoint plusPoint() {
        // tag::plusPoint[]
        Point point1 = new Point(-122.38632, 47.58208)
        Point point2 = new Point(-122.37001, 47.55868)
        MultiPoint points = point1 + point2
        // end::plusPoint[]
        drawGeometry("geometry_plus_point", points)
        points
    }

    List<Double> getAngleBetweenPoints() {
        // tag::getAngleBetweenPoints[]
        Point point1 = new Point(-122.29980, 47.65058)
        Point point2 = new Point(-120.54199, 46.64943)
        double angleInDegrees = point1.getAngle(point2, "degrees")
        println "Angle in degrees = ${angleInDegrees}"

        double angleInRadians = point1.getAngle(point2, "radians")
        println "Angle in radians = ${angleInRadians}"
        // end::getAngleBetweenPoints[]
        drawGeometries("geometry_point_getangle", [point1, point2])
        writeFile("geometry_point_getangle", "Angle in degrees = ${angleInDegrees}${NEW_LINE}Angle in radians = ${angleInRadians}")
        [angleInDegrees, angleInRadians]
    }

    double getAzimuthBetweenPoints() {
        // tag::getAzimuthBetweenPoints[]
        Point point1 = new Point(-122.29980, 47.65058)
        Point point2 = new Point(-120.54199, 46.64943)
        double azimuth = point1.getAzimuth(point2)
        println "Azimuth = ${azimuth}"
        // end::getAzimuthBetweenPoints[]
        drawGeometries("geometry_point_getazimuth", [point1, point2])
        writeFile("geometry_point_getazimuth", "Azimuth = ${azimuth}")
        azimuth
    }

    // MultiPoint

    MultiPoint plusMultiPoint() {
        // tag::plusMultiPoint[]
        MultiPoint multiPoint = new MultiPoint(
                new Point(-122.83813,47.05141),
                new Point(-122.38220,47.58023)
        )
        println multiPoint.wkt
        MultiPoint newMultiPoint = multiPoint + new Point(-122.48657, 47.271775)
        println newMultiPoint.wkt
        // end::plusMultiPoint[]
        drawGeometry("geometry_plus_multipoint1", multiPoint)
        drawGeometry("geometry_plus_multipoint2", newMultiPoint)
        writeFile("geometry_plus_multipoint", "${multiPoint.wkt}${NEW_LINE}${newMultiPoint}")
        newMultiPoint
    }

    // Polygon

    LinearRing polygonGetExteriorRing() {
        // tag::polygonGetExteriorRing[]
        Polygon polygon = new Polygon(
            // Exterior Ring
            new LinearRing(
                [-122.39138603210449, 47.58659965790016],
                [-122.41250038146973, 47.57681522195182],
                [-122.40305900573729, 47.56523364515569],
                [-122.38117218017578, 47.56621817878201],
                [-122.3712158203125, 47.57235661809739],
                [-122.37602233886717, 47.584747123985615],
                [-122.39138603210449, 47.58659965790016]
            ),
            // Holes
            [
                new LinearRing(
                    [-122.39859580993652, 47.578957532923376],
                    [-122.40468978881836, 47.57548347095205],
                    [-122.39593505859376, 47.570271945800094],
                    [-122.3920726776123, 47.57606249728773],
                    [-122.39859580993652, 47.578957532923376]
                ),
                new LinearRing(
                    [-122.3836612701416, 47.58156292813543],
                    [-122.38829612731934, 47.57114056934196],
                    [-122.37456321716309, 47.57420959047542],
                    [-122.37868309020995, 47.58023129789275],
                    [-122.3836612701416, 47.58156292813543]
                )
            ]
        )
        LinearRing exteriorRing = polygon.getExteriorRing()
        // end::polygonGetExteriorRing[]
        drawGeometry("geometry_polygon_exteriorring1", polygon)
        drawGeometry("geometry_polygon_exteriorring2", exteriorRing)
        exteriorRing
    }

    List<LinearRing> polygonInteriorRing() {
        // tag::polygonInteriorRing[]
        Polygon polygon = new Polygon(
                // Exterior Ring
                new LinearRing(
                        [-122.39138603210449, 47.58659965790016],
                        [-122.41250038146973, 47.57681522195182],
                        [-122.40305900573729, 47.56523364515569],
                        [-122.38117218017578, 47.56621817878201],
                        [-122.3712158203125, 47.57235661809739],
                        [-122.37602233886717, 47.584747123985615],
                        [-122.39138603210449, 47.58659965790016]
                ),
                // Holes
                [
                        new LinearRing(
                                [-122.39859580993652, 47.578957532923376],
                                [-122.40468978881836, 47.57548347095205],
                                [-122.39593505859376, 47.570271945800094],
                                [-122.3920726776123, 47.57606249728773],
                                [-122.39859580993652, 47.578957532923376]
                        ),
                        new LinearRing(
                                [-122.3836612701416, 47.58156292813543],
                                [-122.38829612731934, 47.57114056934196],
                                [-122.37456321716309, 47.57420959047542],
                                [-122.37868309020995, 47.58023129789275],
                                [-122.3836612701416, 47.58156292813543]
                        )
                ]
        )

        println "# Interior Rings = ${polygon.numInteriorRing}"
        (0..<polygon.numInteriorRing).each { int i ->
            println "  ${polygon.getInteriorRingN(i)}"
        }

        println "Interior Rings"
        polygon.interiorRings.each { LinearRing ring ->
            println "  ${ring}"
        }
        // end::polygonInteriorRing[]
        writeFile("geometry_polygon_interiorring", """  
# Interior Rings = ${polygon.numInteriorRing}
${(0..<polygon.numInteriorRing).collect { '  ' + polygon.getInteriorRingN(it).wkt }.join(NEW_LINE)} 

Interior Rings
${polygon.interiorRings.collect { '  ' + it.wkt }.join(NEW_LINE)}
""")
        drawGeometry("geometry_polygon_interiorring1", polygon)
        drawGeometry("geometry_polygon_interiorring2", polygon.getInteriorRingN(0))
        drawGeometry("geometry_polygon_interiorring3", polygon.getInteriorRingN(1))
        polygon.interiorRings
    }

    MultiPolygon plusPolygons() {
        // tag::plusPolygons[]
        Polygon polygon1 = new Polygon ([[
          [-122.2723388671875,  47.818687628247105],
          [-122.37945556640624, 47.66168780332917],
          [-121.95373535156249, 47.67093619422418],
          [-122.2723388671875,  47.818687628247105]
        ]])
        Polygon polygon2 = new Polygon ([[
          [-122.76672363281249, 47.42437092240516],
          [-122.76672363281249, 47.59505101193038],
          [-122.52227783203125, 47.59505101193038],
          [-122.52227783203125, 47.42437092240516],
          [-122.76672363281249, 47.42437092240516]
        ]])
        MultiPolygon multiPolygon = polygon1 + polygon2
        // end::plusPolygons[]
        drawGeometry("geometry_plus_polygons", multiPolygon)
        multiPolygon
    }

    MultiPolygon splitPolygon() {

        // tag::splitPolygon[]
        Polygon polygon = new Polygon(
            new LinearRing(
                [-122.39138603210449, 47.58659965790016],
                [-122.41250038146973, 47.57681522195182],
                [-122.40305900573729, 47.56523364515569],
                [-122.38117218017578, 47.56621817878201],
                [-122.3712158203125, 47.57235661809739],
                [-122.37602233886717, 47.584747123985615],
                [-122.39138603210449, 47.58659965790016]
            )
        )

        LineString lineString = new LineString([
            [-122.3924160003662, 47.56395951534652],
            [-122.38649368286131, 47.58729434121508]
        ])

        MultiPolygon multiPolygon = polygon.split(lineString)
        // end::splitPolygon[]
        drawGeometries("geometry_split_polygon1", [polygon, lineString])
        drawGeometry("geometry_split_polygon2", multiPolygon)
        multiPolygon
    }

    // MultiPolygon
    MultiPolygon multiPolygonPlus() {

        // tag::multiPolygonPlus[]
        MultiPolygon multiPolygon = new MultiPolygon (
            new Polygon([[
                 [-122.2723388671875,  47.818687628247105],
                 [-122.37945556640624, 47.66168780332917],
                 [-121.95373535156249, 47.67093619422418],
                 [-122.2723388671875,  47.818687628247105]
            ]]),
            new Polygon ([[
                 [-122.76672363281249, 47.42437092240516],
                 [-122.76672363281249, 47.59505101193038],
                 [-122.52227783203125, 47.59505101193038],
                 [-122.52227783203125, 47.42437092240516],
                 [-122.76672363281249, 47.42437092240516]
            ]])
        )
        Polygon polygon = new Polygon ([[
            [-122.32177734375, 47.54501765940571],
            [-122.27645874023438, 47.36673410912714],
            [-122.03887939453125, 47.44480754169437],
            [-122.15972900390624, 47.55150616084034],
            [-122.32177734375, 47.54501765940571]
        ]])

        MultiPolygon newMultiPolygon = multiPolygon + polygon
        // end::multiPolygonPlus[]

        drawGeometry("geometry_multipolygon_plus", newMultiPolygon)
        newMultiPolygon
    }

    public Geometry multiPolygonSplit() {

        // tag::multiPolygonSplit[]
        MultiPolygon multiPolygon = new MultiPolygon (
            new Polygon([[
                     [-122.2723388671875,  47.818687628247105],
                     [-122.37945556640624, 47.66168780332917],
                     [-121.95373535156249, 47.67093619422418],
                     [-122.2723388671875,  47.818687628247105]
            ]]),
            new Polygon ([[
                  [-122.76672363281249, 47.42437092240516],
                  [-122.76672363281249, 47.59505101193038],
                  [-122.52227783203125, 47.59505101193038],
                  [-122.52227783203125, 47.42437092240516],
                  [-122.76672363281249, 47.42437092240516]
            ]]),
            new Polygon ([[
                  [-122.32177734375, 47.54501765940571],
                  [-122.27645874023438, 47.36673410912714],
                  [-122.03887939453125, 47.44480754169437],
                  [-122.15972900390624, 47.55150616084034],
                  [-122.32177734375, 47.54501765940571]
            ]])
        )

        LineString lineString = new LineString([
            [-122.84362792968749, 47.484728927366504],
            [-122.05810546875, 47.50421439972969],
            [-122.35748291015625, 47.85832433461554]
        ])

        Geometry splitGeometry = multiPolygon.split(lineString)
        // end::multiPolygonSplit[]

        drawGeometries("geometry_multipolygon_split1", [multiPolygon, lineString])
        drawGeometry("geometry_multipolygon_split2", splitGeometry)

        splitGeometry
    }

    // Geometry Collection

    GeometryCollection geometryCollectionPlus() {
        // tag::geometryCollectionPlus[]
        GeometryCollection geometryCollection = new GeometryCollection([
            new Point(-122.38654196262358, 47.581211931059826),
            new LineString([
                [-122.3865446448326, 47.58118841055313],
                [-122.38657146692276, 47.58067638459562]
            ]),
            new Polygon(new LinearRing([
                [-122.38693356513977, 47.58088445228483],
                [-122.38672703504562, 47.58088445228483],
                [-122.38672703504562, 47.58096225129535],
                [-122.38693356513977, 47.58096225129535],
                [-122.38693356513977, 47.58088445228483]
            ]))
        ])
        GeometryCollection newGeometryCollection =  geometryCollection + new Point(-122.38718032836913, 47.58121374032914)
        // end::geometryCollectionPlus[]
        drawGeometry("geometry_geometrycollection_plus1", geometryCollection)
        drawGeometry("geometry_geometrycollection_plus2", newGeometryCollection)
        newGeometryCollection
    }

    List<Geometry> geometryCollectionSlice() {
        // tag::geometryCollectionSlice1[]
        GeometryCollection geometryCollection = new GeometryCollection([
                new Point(-122.38654196262358, 47.581211931059826),
                new LineString([
                        [-122.3865446448326, 47.58118841055313],
                        [-122.38657146692276, 47.58067638459562]
                ]),
                new Polygon(new LinearRing([
                        [-122.38693356513977, 47.58088445228483],
                        [-122.38672703504562, 47.58088445228483],
                        [-122.38672703504562, 47.58096225129535],
                        [-122.38693356513977, 47.58096225129535],
                        [-122.38693356513977, 47.58088445228483]
                ]))
        ])
        // end::geometryCollectionSlice1[]
        // tag::geometryCollectionSlice2[]
        Polygon slicedGeometryCollection1 =  geometryCollection.slice(2)
        // end::geometryCollectionSlice2[]
        // tag::geometryCollectionSlice3[]
        GeometryCollection slicedGeometryCollection2 =  geometryCollection.slice(0,2)
        // end::geometryCollectionSlice3[]
        drawGeometry("geometry_geometrycollection_slice1", geometryCollection)
        drawGeometry("geometry_geometrycollection_slice2", slicedGeometryCollection1)
        drawGeometry("geometry_geometrycollection_slice3", slicedGeometryCollection2)
        [slicedGeometryCollection1, slicedGeometryCollection2]
    }

    List<Geometry> geometryCollectionNarrow() {
        // tag::geometryCollectionNarrow1[]
        GeometryCollection geometryCollection1 = new GeometryCollection([
            new Point(-122.38654196262358, 47.581211931059826),
            new Point(-122.38718032836913, 47.58121374032914),
        ])
        Geometry narrowedGeometry1 = geometryCollection1.narrow()
        println "Narrow Geometry #1 = ${narrowedGeometry1.wkt}"
        // end::geometryCollectionNarrow1[]
        writeFile("geometry_geometrycollection_narrow1", "Narrow Geometry #1 = ${narrowedGeometry1.wkt}")
        drawGeometry("geometry_geometrycollection_narrow1", narrowedGeometry1)

        // tag::geometryCollectionNarrow2[]
        GeometryCollection geometryCollection2 = new GeometryCollection([
                new Point(-122.38654196262358, 47.581211931059826),
                new Polygon(new LinearRing([
                        [-122.38693356513977, 47.58088445228483],
                        [-122.38672703504562, 47.58088445228483],
                        [-122.38672703504562, 47.58096225129535],
                        [-122.38693356513977, 47.58096225129535],
                        [-122.38693356513977, 47.58088445228483]
                ]))
        ])
        Geometry narrowedGeometry2 = geometryCollection2.narrow()
        println "Narrow Geometry #2 = ${narrowedGeometry2.wkt}"
        // end::geometryCollectionNarrow2[]
        writeFile("geometry_geometrycollection_narrow2", "Narrow Geometry #2 = ${narrowedGeometry2.wkt}")
        drawGeometry("geometry_geometrycollection_narrow2", narrowedGeometry2)
        
        [narrowedGeometry1, narrowedGeometry2]
    }

    // Processing Geometries

    String getGeometryType() {
        // tag::getGeometryType[]
        Geometry geom = Geometry.fromString("POINT (-124.80 48.92)")
        String type = geom.geometryType
        println type
        // end::getGeometryType[]
        writeFile("geometry_geometrytype","${type}")
        type
    }

    double getArea() {
        // tag::getArea[]
        Polygon polygon = new Polygon([[
            [-124.80, 48.92],
            [-126.21, 45.33],
            [-114.60, 45.08],
            [-115.31, 51.17],
            [-121.99, 52.05],
            [-124.80, 48.92]
        ]])
        double area = polygon.area
        println area
        // end::getArea[]
        writeFile("geometry_getarea", "${area}")
        return area
    }

    double getLength() {
        // tag::getLength[]
        LineString lineString = new LineString([-122.69, 49.61], [-99.84, 45.33])
        double length = lineString.length
        println length
        // end::getLength[]
        writeFile("geometry_getlength", "${length}")
        return length
    }

    Geometry bufferPoint() {
        // tag::bufferPoint[]
        Point point = new Point(-123,46)
        Geometry bufferedPoint = point.buffer(2)
        // end::bufferPoint[]
        drawGeometries("geometry_buffer_point", [bufferedPoint, point])
        bufferedPoint
    }

    List<Geometry> bufferLineString() {
        List<Geometry> geometries = []
        // tag::bufferLineString1[]
        LineString line = new LineString([
            [-122.563, 47.576],
            [-112.0166, 46.589],
            [-101.337, 47.606]
        ])
        Geometry bufferedLine1 = line.buffer(2.1, 10, Geometry.CAP_BUTT)
        // end::bufferLineString1[]
        drawGeometries("geometry_buffer_line1", [bufferedLine1, line])
        geometries.add(bufferedLine1)

        // tag::bufferLineString2[]
        Geometry bufferedLine2 = line.buffer(2.1, 10, Geometry.CAP_ROUND)
        // end::bufferLineString2[]
        drawGeometries("geometry_buffer_line2", [bufferedLine2, line])
        geometries.add(bufferedLine2)

        // tag::bufferLineString3[]
        Geometry bufferedLine3 = line.buffer(2.1, 10, Geometry.CAP_SQUARE)
        // end::bufferLineString3[]
        drawGeometries("geometry_buffer_line3", [bufferedLine3, line])
        geometries.add(bufferedLine3)

        geometries
    }

    List<Geometry> bufferLineStringSingleSided() {
        List<Geometry> geometries = []

        // tag::bufferLineStringSingleSided1[]
        LineString line = new LineString([
                [-122.563, 47.576],
                [-112.0166, 46.589],
                [-101.337, 47.606]
        ])
        Geometry rightBufferedLine = line.singleSidedBuffer(1.5)
        // end::bufferLineStringSingleSided1[]
        drawGeometries("geometry_buffer_line_singlesided1", [rightBufferedLine, line])
        geometries.add(rightBufferedLine)

        // tag::bufferLineStringSingleSided2[]
        Geometry leftBufferedLine = line.singleSidedBuffer(-1.5)
        // end::bufferLineStringSingleSided2[]
        drawGeometries("geometry_buffer_line_singlesided2", [leftBufferedLine, line])
        geometries.add(leftBufferedLine)

        geometries
    }

    Geometry variableBufferTwo() {

        // tag::variableBufferTwo[]
        LineString line = new LineString([
                [-122.38494873046875, 47.57281986733871],
                [-122.27508544921875, 47.342545069660225],
                [-122.15972900390624, 47.14302937421008],
                [-121.96197509765625, 47.03082254778662],
                [-121.73950195312499, 46.89586230605985],
                [-121.56372070312499, 46.81509864599243]
        ])
        Geometry buffer = line.variableBuffer([0.03, 0.07])
        // end::variableBufferTwo[]

        drawGeometries("geometry_variable_buffer_two", [buffer, line])

        buffer
    }

    Geometry variableBufferThree() {

        // tag::variableBufferThree[]
        LineString line = new LineString([
                [-122.38494873046875, 47.57281986733871],
                [-122.27508544921875, 47.342545069660225],
                [-122.15972900390624, 47.14302937421008],
                [-121.96197509765625, 47.03082254778662],
                [-121.73950195312499, 46.89586230605985],
                [-121.56372070312499, 46.81509864599243]
        ])
        Geometry buffer = line.variableBuffer([0.03, 0.07, 0.1])
        // end::variableBufferThree[]

        drawGeometries("geometry_variable_buffer_three", [buffer, line])

        buffer
    }

    Geometry variableBufferSameNumber() {

        // tag::variableBufferSameNumber[]
        LineString line = new LineString([
                [-122.38494873046875, 47.57281986733871],
                [-122.27508544921875, 47.342545069660225],
                [-122.15972900390624, 47.14302937421008],
                [-121.96197509765625, 47.03082254778662],
                [-121.73950195312499, 46.89586230605985],
                [-121.56372070312499, 46.81509864599243]
        ])
        Geometry buffer = line.variableBuffer([0.03, 0.05, 0.07, 0.09, 0.1, 0.2])
        // end::variableBufferSameNumber[]

        drawGeometries("geometry_variable_buffer_samenumber", [buffer, line])

        buffer
    }

    Geometry getLargestEmptyCircle() {

        // tag::getLargestEmptyCircle[]
        Geometry g = Geometry.fromWKT("POLYGON ((-122.38855361938475 47.5805786829606, -122.38636493682861 47.5783206388176, " +
                "-122.38700866699219 47.5750491969984, -122.38177299499512 47.57502024527343, " +
                "-122.38481998443604 47.5780600889959, -122.38151550292969 47.5805786829606, " +
                "-122.38855361938475 47.5805786829606))")
        Geometry circle = g.getLargestEmptyCircle(1.0)
        // end::getLargestEmptyCircle[]

        drawGeometries("geometry_largest_empty_circle", [g, circle])

        circle
    }

    Geometry getMaximumInscribedCircle() {

        // tag::getMaximumInscribedCircle[]
        Geometry g = Geometry.fromWKT("POLYGON ((-122.38855361938475 47.5805786829606, -122.38636493682861 47.5783206388176, " +
                "-122.38700866699219 47.5750491969984, -122.38177299499512 47.57502024527343, " +
                "-122.38481998443604 47.5780600889959, -122.38151550292969 47.5805786829606, " +
                "-122.38855361938475 47.5805786829606))")
        Geometry circle = g.getMaximumInscribedCircle(1.0)
        // end::getMaximumInscribedCircle[]

        drawGeometries("geometry_maximum_inscribed_circle", [g, circle])

        circle
    }

    Map<String, Boolean> contains() {

        Map<String, Boolean> results = [:]

        // tag::contains1[]
        Polygon polygon1 = new Polygon([[
            [-120.739, 48.151],
            [-121.003, 47.070],
            [-119.465, 47.137],
            [-119.553, 46.581],
            [-121.267, 46.513],
            [-121.168, 45.706],
            [-118.476, 45.951],
            [-118.762, 48.195],
            [-120.739, 48.151]
        ]])

        Polygon polygon2 = new Polygon([[
            [-120.212, 47.591],
            [-119.663, 47.591],
            [-119.663, 47.872],
            [-120.212, 47.872],
            [-120.212, 47.591]
        ]])

        boolean contains = polygon1.contains(polygon2)
        println contains
        // end::contains1[]
        drawGeometries("geometry_contains_1", [polygon1, polygon2])
        writeFile("geometry_contains_1", "${contains}")
        results["1contains2"] = contains

        // tag::contains2[]
        Polygon polygon3 = new Polygon([[
            [-120.563, 46.739],
            [-119.948, 46.739],
            [-119.948, 46.965],
            [-120.563, 46.965],
            [-120.563, 46.739]
        ]])

        contains = polygon1.contains(polygon3)
        println contains
        // end::contains2[]
        drawGeometries("geometry_contains_2", [polygon1, polygon3])
        writeFile("geometry_contains_2", "${contains}")
        results["1contains3"] = contains

        results
    }

    Map<String, Boolean> within() {

        Map<String, Boolean> results = [:]

        // tag::within1[]

        Polygon polygon1 = new Polygon([[
                [-120.212, 47.591],
                [-119.663, 47.591],
                [-119.663, 47.872],
                [-120.212, 47.872],
                [-120.212, 47.591]
        ]])

        Polygon polygon2 = new Polygon([[
                [-120.739, 48.151],
                [-121.003, 47.070],
                [-119.465, 47.137],
                [-119.553, 46.581],
                [-121.267, 46.513],
                [-121.168, 45.706],
                [-118.476, 45.951],
                [-118.762, 48.195],
                [-120.739, 48.151]
        ]])

        boolean within = polygon1.within(polygon2)
        println within
        // end::within1[]
        drawGeometries("geometry_within_1", [polygon1, polygon2])
        writeFile("geometry_within_1", "${within}")
        results["1within2"] = within

        // tag::within2[]
        Polygon polygon3 = new Polygon([[
                [-120.563, 46.739],
                [-119.948, 46.739],
                [-119.948, 46.965],
                [-120.563, 46.965],
                [-120.563, 46.739]
        ]])

        within = polygon1.within(polygon3)
        println within
        // end::within2[]
        drawGeometries("geometry_within_2", [polygon1, polygon3])
        writeFile("geometry_within_2", "${within}")
        results["1within3"] = within

        results
    }

    Map<String, Boolean> touches() {

        Map<String, Boolean> results = [:]

        // tag::touches1[]
        LineString line1 = new LineString([
            [-122.38651514053345, 47.58219978280006],
            [-122.38651514053345, 47.58020234903306]
        ])

        LineString line2 = new LineString([
            [-122.38651514053345, 47.58124449789785],
            [-122.38333940505981, 47.58124449789785]
        ])

        boolean touches = line1.touches(line2)
        // end::touches1[]
        drawGeometries("geometry_touches_12", [line1, line2])
        writeFile("geometry_touches_12", "${touches}")
        results["touches_12"] = touches

        // tag::touches2[]
        LineString line3 = new LineString([
            [-122.386257648468, 47.58183793450921],
            [-122.38348960876465, 47.5818668824645]
        ])

        touches = line1.touches(line3)
        // end::touches2[]
        drawGeometries("geometry_touches_13", [line1, line3])
        writeFile("geometry_touches_13", "${touches}")
        results["touches_13"] = touches

        results
    }

    Geometry convexHull() {
        // tag::convexHull[]
        Geometry geometry = new MultiPoint(
            new Point(-119.882, 47.279),
            new Point(-100.195, 46.316),
            new Point(-111.796, 42.553),
            new Point(-90.7031, 34.016)
        )
        Geometry convexHull = geometry.convexHull
        // end::convexHull[]
        drawGeometries("geometry_convexhull", [convexHull, geometry])
        convexHull
    }

    Map<String, Boolean> covers() {

        Map<String, Boolean> results = [:]

        // tag::covers1[]
        Polygon polygon1 = new Polygon([[
                [-120.739, 48.151],
                [-121.003, 47.070],
                [-119.465, 47.137],
                [-119.553, 46.581],
                [-121.267, 46.513],
                [-121.168, 45.706],
                [-118.476, 45.951],
                [-118.762, 48.195],
                [-120.739, 48.151]
        ]])

        Polygon polygon2 = new Polygon([[
                [-120.212, 47.591],
                [-119.663, 47.591],
                [-119.663, 47.872],
                [-120.212, 47.872],
                [-120.212, 47.591]
        ]])

        boolean isCovered = polygon1.covers(polygon2)
        println isCovered
        // end::covers1[]
        drawGeometries("geometry_covers_1", [polygon1, polygon2])
        writeFile("geometry_covers_1", "${isCovered}")
        results["1covers2"] = isCovered

        // tag::covers2[]
        Polygon polygon3 = new Polygon([[
                [-120.563, 46.739],
                [-119.948, 46.739],
                [-119.948, 46.965],
                [-120.563, 46.965],
                [-120.563, 46.739]
        ]])

        isCovered = polygon1.covers(polygon3)
        println isCovered
        // end::covers2[]
        drawGeometries("geometry_covers_2", [polygon1, polygon3])
        writeFile("geometry_covers_2", "${isCovered}")
        results["1covers3"] = isCovered

        results
    }

    Map<String, Boolean> coveredBy() {

        Map<String, Boolean> results = [:]

        // tag::coveredBy1[]
        Polygon polygon1 = new Polygon([[
                [-120.739, 48.151],
                [-121.003, 47.070],
                [-119.465, 47.137],
                [-119.553, 46.581],
                [-121.267, 46.513],
                [-121.168, 45.706],
                [-118.476, 45.951],
                [-118.762, 48.195],
                [-120.739, 48.151]
        ]])

        Polygon polygon2 = new Polygon([[
                [-120.212, 47.591],
                [-119.663, 47.591],
                [-119.663, 47.872],
                [-120.212, 47.872],
                [-120.212, 47.591]
        ]])

        boolean isCoveredBy = polygon2.coveredBy(polygon1)
        println isCoveredBy
        // end::coveredBy1[]
        drawGeometries("geometry_coveredby_1", [polygon1, polygon2])
        writeFile("geometry_coveredby_1", "${isCoveredBy}")
        results["2coveredBy1"] = isCoveredBy

        // tag::coveredBy2[]
        Polygon polygon3 = new Polygon([[
                [-120.563, 46.739],
                [-119.948, 46.739],
                [-119.948, 46.965],
                [-120.563, 46.965],
                [-120.563, 46.739]
        ]])

        isCoveredBy = polygon3.coveredBy(polygon1)
        println isCoveredBy
        // end::coveredBy2[]
        drawGeometries("geometry_coveredby_2", [polygon1, polygon3])
        writeFile("geometry_coveredby_2", "${isCoveredBy}")
        results["3coveredBy1"] = isCoveredBy

        results
    }
    
    Bounds bounds() {
        // tag::bounds[]
        Point point = new Point(-123,46)
        Polygon polygon = point.buffer(2)
        Bounds bounds = polygon.bounds
        // end::bounds[]
        drawGeometries("geometry_bounds", [bounds.geometry, polygon, point])
        bounds
    }

    Geometry createRandomPoints() {
      // tag::createRandomPoints[]
      Geometry geometry = new Bounds(-180, -90, 180, 90).geometry
      MultiPoint randomPoints = Geometry.createRandomPoints(geometry, 100)
      // end::createRandomPoints[] 
      drawOnBasemap("geometry_create_random_points", [
        createLayerFromGeometry("points", randomPoints, "shape=#0066FF shape-size=6")
      ])
      randomPoints
    }

    Geometry createRandomPointsInGrid() {
        // tag::createRandomPointsInGrid[]
        Bounds bounds = new Bounds(-180, -90, 180, 90)
        MultiPoint randomPoints = Geometry.createRandomPointsInGrid(bounds, 100, true, 0.5)
        // end::createRandomPointsInGrid[]
        drawOnBasemap("geometry_create_random_points_ingrid", [
                createLayerFromGeometry("points", randomPoints, "shape=#0066FF shape-size=6")
        ])
        randomPoints
    }

    Geometry createFromText() {
        // tag::createFromText[]
        Geometry geometry = Geometry.createFromText("Geo")
        // end::createFromText[]
        Viewer.drawToFile(geometry, new File("src/main/resources/docs/images/geometry_createfromtext.png"), size:[300,300])
    }

    Geometry createSierpinskiCarpet() {
        // tag::createSierpinskiCarpet[]
        Bounds bounds = new Bounds(21.645,36.957,21.676,36.970, "EPSG:4326")
        Geometry geometry = Geometry.createSierpinskiCarpet(bounds, 50)
        // end::createSierpinskiCarpet[]
        drawGeometry("geometry_sierpinskicarpet", geometry, drawCoords: false)
        geometry
    }

    Geometry createKochSnowflake() {
        // tag::createKochSnowflake[]
        Bounds bounds = new Bounds(21.645,36.957,21.676,36.970, "EPSG:4326")
        Geometry geometry = Geometry.createKochSnowflake(bounds, 50)
        // end::createKochSnowflake[]
        drawGeometry("geometry_kochsnowflake", geometry, drawCoords: false)
        geometry
    }

    Geometry cascadeUnion() {
        // tag::cascadeUnion[]
        Geometry randomPoints = Geometry.createRandomPoints(new Bounds(-180,-90,180,90, "EPSG:4326").geometry, 100)
        List<Geometry> bufferedPoints = randomPoints.collect{Geometry geom -> geom.buffer(4.5)}
        Geometry unionedGeometry = Geometry.cascadedUnion(bufferedPoints)
        // end::cascadeUnion[]
        drawOnBasemap("geometry_cascadeunion", [
            createLayerFromGeometry("points", randomPoints, "shape=#0066FF shape-size=6"),
            createLayerFromGeometry("polygons", unionedGeometry, "fill=#B0C4DE stroke=#4682B4")
        ])
        unionedGeometry
    }

    Map<String, Boolean> crosses() {
        Map<String, Boolean> values = [:]

        // tag::crosses[]
        LineString line1 = new LineString([[-122.486, 47.256], [-121.695, 46.822]])
        LineString line2 = new LineString([[-122.387, 47.613], [-121.750, 47.353]])
        LineString line3 = new LineString([[-122.255, 47.368], [-121.882, 47.746]])

        boolean doesCross12 = line1.crosses(line2)
        println doesCross12

        boolean doesCross13 = line1.crosses(line3)
        println doesCross13

        boolean doesCross23 = line2.crosses(line3)
        println doesCross23
        // end::crosses[]
        drawGeometries("geometry_crosses", [line1, line2, line3])
        writeFile("geometry_crosses", "${doesCross12}${NEW_LINE}${doesCross13}${NEW_LINE}${doesCross23}")
        values["12"] = doesCross12
        values["13"] = doesCross13
        values["23"] = doesCross23

        values
    }

    Geometry difference() {

        // tag::difference[]
        Polygon polygon1 = new Polygon([[
                [-121.915, 47.390],
                [-122.640, 46.995],
                [-121.739, 46.308],
                [-121.168, 46.777],
                [-120.981, 47.316],
                [-121.409, 47.413],
                [-121.915, 47.390]
        ]])

        Polygon polygon2 = new Polygon([[
                [-120.794, 46.664],
                [-121.541, 46.995],
                [-122.200, 46.536],
                [-121.937, 45.890],
                [-120.959, 46.096],
                [-120.794, 46.664]
        ]])

        Geometry difference = polygon1.difference(polygon2)
        // end::difference[]
        drawGeometries("geometry_difference", [difference, polygon1, polygon2])

        difference
    }

    Geometry symDifference() {

        // tag::symDifference[]
        Polygon polygon1 = new Polygon([[
                [-121.915, 47.390],
                [-122.640, 46.995],
                [-121.739, 46.308],
                [-121.168, 46.777],
                [-120.981, 47.316],
                [-121.409, 47.413],
                [-121.915, 47.390]
        ]])

        Polygon polygon2 = new Polygon([[
                [-120.794, 46.664],
                [-121.541, 46.995],
                [-122.200, 46.536],
                [-121.937, 45.890],
                [-120.959, 46.096],
                [-120.794, 46.664]
        ]])

        Geometry symDifference = polygon1.symDifference(polygon2)
        // end::symDifference[]
        drawGeometries("geometry_symdifference", [symDifference, polygon1, polygon2])

        symDifference
    }

    Map<String, Boolean> disjoint() {

        Map<String, Boolean> values = [:]

        // tag::disjoint[]
        Polygon polygon1 = new Polygon([[
                [-121.915, 47.390],
                [-122.640, 46.995],
                [-121.739, 46.308],
                [-121.168, 46.777],
                [-120.981, 47.316],
                [-121.409, 47.413],
                [-121.915, 47.390]
        ]])

        Polygon polygon2 = new Polygon([[
                [-120.794, 46.664],
                [-121.541, 46.995],
                [-122.200, 46.536],
                [-121.937, 45.890],
                [-120.959, 46.096],
                [-120.794, 46.664]
        ]])

        Polygon polygon3 = new Polygon([[
                [-120.541, 47.376],
                [-120.695, 47.047],
                [-119.794, 46.830],
                [-119.586, 47.331],
                [-120.102, 47.509],
                [-120.541, 47.376]
        ]])

        boolean isDisjoint12 = polygon1.disjoint(polygon2)
        println isDisjoint12

        boolean isDisjoint13 = polygon1.disjoint(polygon3)
        println isDisjoint13

        boolean isDisjoint23 = polygon2.disjoint(polygon3)
        println isDisjoint23
        // end::disjoint[]
        drawGeometries("geometry_distjoint", [polygon1, polygon2, polygon3])
        writeFile("geometry_disjoint", "${isDisjoint12}${NEW_LINE}${isDisjoint13}${NEW_LINE}${isDisjoint23}")
        values["12"] = isDisjoint12
        values["13"] = isDisjoint13
        values["23"] = isDisjoint23

        values
    }

    double distance() {
        // tag::distance[]
        Point point1 = new Point(-122.442, 47.256)
        Point point2 = new Point(-122.321, 47.613)
        double distance = point1.distance(point2)
        println distance
        // end::distance[]
        drawGeometries("geometry_distance", [point1, point2])
        writeFile("geometry_distance", "${distance}")
        distance
    }

    Geometry boundary() {
        // tag::boundary[]
        Polygon polygon = new Polygon([
            [
                    [-121.915, 47.390],
                    [-122.640, 46.995],
                    [-121.739, 46.308],
                    [-121.168, 46.777],
                    [-120.981, 47.316],
                    [-121.409, 47.413],
                    [-121.915, 47.390]
            ],
            [
                    [-122.255, 46.935],
                    [-121.992, 46.935],
                    [-121.992, 47.100],
                    [-122.255, 47.100],
                    [-122.255, 46.935]
            ],
            [
                    [-121.717, 46.777],
                    [-121.398, 46.777],
                    [-121.398, 47.002],
                    [-121.717, 47.002],
                    [-121.717, 46.777]
            ]
        ])
        Geometry boundary = polygon.boundary
        // end::boundary[]
        drawGeometries("geometry_boundary", [polygon, boundary])
        boundary
    }

    Geometry centroid() {
        // tag::centroid[]
        Polygon polygon = new Polygon([[
               [-118.937, 48.327],
               [-121.157, 48.356],
               [-121.684, 46.331],
               [-119.355, 46.498],
               [-119.355, 47.219],
               [-120.629, 47.219],
               [-120.607, 47.783],
               [-119.201, 47.739],
               [-118.937, 48.327]
        ]])
        Geometry centroid = polygon.centroid
        // end::centroid[]
        drawGeometries("geometry_centroid", [polygon, centroid])
        centroid
    }

    Geometry interiorPoint() {
        // tag::interiorPoint[]
        Polygon polygon = new Polygon([[
               [-118.937, 48.327],
               [-121.157, 48.356],
               [-121.684, 46.331],
               [-119.355, 46.498],
               [-119.355, 47.219],
               [-120.629, 47.219],
               [-120.607, 47.783],
               [-119.201, 47.739],
               [-118.937, 48.327]
        ]])
        Geometry interiorPoint = polygon.interiorPoint
        // end::interiorPoint[]
        drawGeometries("geometry_interiorPoint", [polygon, interiorPoint])
        interiorPoint
    }

    int getNumGeometries() {
        // tag::getNumGeometries[]
        MultiPoint multiPoint = new MultiPoint([
                new Point(-122.3876953125, 47.5820839916191),
                new Point(-122.464599609375, 47.25686404408872),
                new Point(-122.48382568359374, 47.431803338643334)
        ])
        int number = multiPoint.numGeometries
        println number
        // end::getNumGeometries[]
        drawGeometry("geometry_getnumgeometries", multiPoint)
        writeFile("geometry_getnumgeometries", "${number}")
        number
    }

    List<Geometry> getGeometryN() {
        // tag::getGeometryN[]
        MultiPoint multiPoint = new MultiPoint([
                new Point(-122.3876953125, 47.5820839916191),
                new Point(-122.464599609375, 47.25686404408872),
                new Point(-122.48382568359374, 47.431803338643334)
        ])
        (0..<multiPoint.getNumGeometries()).each { int i ->
            Geometry geometry = multiPoint.getGeometryN(i)
            println geometry.wkt
        }
        // end::getGeometryN[]
        drawGeometry("geometry_getgeometryn", multiPoint)
        writeFile("geometry_getgeometryn",  multiPoint.geometries.collect { it.wkt }.join(NEW_LINE))
        (0..<multiPoint.getNumGeometries()).collect { int i ->
            multiPoint.getGeometryN(i)
        }
    }

    List<Geometry> getGeometries() {
        // tag::getGeometries[]
        MultiPoint multiPoint = new MultiPoint([
                new Point(-122.3876953125, 47.5820839916191),
                new Point(-122.464599609375, 47.25686404408872),
                new Point(-122.48382568359374, 47.431803338643334)
        ])
        List<Geometry> geometries = multiPoint.geometries
        geometries.each { Geometry geometry ->
            println geometry.wkt
        }
        // end::getGeometries[]
        drawGeometry("geometry_getgeometries", multiPoint)
        writeFile("geometry_getgeometries",  "${geometries.collect{ it.wkt }.join(NEW_LINE)}")
        geometries
    }

    int getNumPoints() {
        // tag::getNumPoints[]
        Polygon polygon = new Polygon([[
            [-120.563, 46.739],
            [-119.948, 46.739],
            [-119.948, 46.965],
            [-120.563, 46.965],
            [-120.563, 46.739]
        ]])
        int number = polygon.numPoints
        println number
        // end::getNumPoints[]
        drawGeometry("geometry_numpoints", polygon)
        writeFile("geometry_numpoints",  "${number}")
        number
    }

    Geometry intersection() {

        // tag::intersection[]
        Polygon polygon1 = new Polygon([[
                [-121.915, 47.390],
                [-122.640, 46.995],
                [-121.739, 46.308],
                [-121.168, 46.777],
                [-120.981, 47.316],
                [-121.409, 47.413],
                [-121.915, 47.390]
        ]])

        Polygon polygon2 = new Polygon([[
                [-120.794, 46.664],
                [-121.541, 46.995],
                [-122.200, 46.536],
                [-121.937, 45.890],
                [-120.959, 46.096],
                [-120.794, 46.664]
        ]])

        Geometry intersection = polygon1.intersection(polygon2)
        // end::intersection[]
        drawGeometries("geometry_intersection", [polygon1, polygon2, intersection])

        intersection
    }

    Map<String, Boolean> intersects() {

        Map<String, Boolean> values = [:]

        // tag::intersects[]
        Polygon polygon1 = new Polygon([[
                [-121.915, 47.390],
                [-122.640, 46.995],
                [-121.739, 46.308],
                [-121.168, 46.777],
                [-120.981, 47.316],
                [-121.409, 47.413],
                [-121.915, 47.390]
        ]])

        Polygon polygon2 = new Polygon([[
                [-120.794, 46.664],
                [-121.541, 46.995],
                [-122.200, 46.536],
                [-121.937, 45.890],
                [-120.959, 46.096],
                [-120.794, 46.664]
        ]])

        Polygon polygon3 = new Polygon([[
                [-120.541, 47.376],
                [-120.695, 47.047],
                [-119.794, 46.830],
                [-119.586, 47.331],
                [-120.102, 47.509],
                [-120.541, 47.376]
        ]])

        boolean does1intersect2 = polygon1.intersects(polygon2)
        println does1intersect2

        boolean does1intersect3 = polygon1.intersects(polygon3)
        println does1intersect3

        boolean does2intersect3 = polygon2.intersects(polygon3)
        println does2intersect3
        // end::intersects[]
        drawGeometries("geometry_intersects", [polygon1, polygon2, polygon3])
        writeFile("geometry_intersects", "${does1intersect2}${NEW_LINE}${does1intersect3}${NEW_LINE}${does2intersect3}")
        values["12"] = does1intersect2
        values["13"] = does1intersect3
        values["23"] = does2intersect3

        values
    }

    Map<String, Boolean> overlaps() {

        Map<String, Boolean> values = [:]

        // tag::overlaps[]
        Polygon polygon1 = new Polygon([[
                [-121.915, 47.390],
                [-122.640, 46.995],
                [-121.739, 46.308],
                [-121.168, 46.777],
                [-120.981, 47.316],
                [-121.409, 47.413],
                [-121.915, 47.390]
        ]])

        Polygon polygon2 = new Polygon([[
                [-120.794, 46.664],
                [-121.541, 46.995],
                [-122.200, 46.536],
                [-121.937, 45.890],
                [-120.959, 46.096],
                [-120.794, 46.664]
        ]])

        Polygon polygon3 = new Polygon([[
                [-120.541, 47.376],
                [-120.695, 47.047],
                [-119.794, 46.830],
                [-119.586, 47.331],
                [-120.102, 47.509],
                [-120.541, 47.376]
        ]])

        boolean does1overlap2 = polygon1.overlaps(polygon2)
        println does1overlap2

        boolean does1overlap3 = polygon1.overlaps(polygon3)
        println does1overlap3

        boolean does2overlap3 = polygon2.overlaps(polygon3)
        println does2overlap3
        // end::overlaps[]
        drawGeometries("geometry_overlaps", [polygon1, polygon2, polygon3])
        writeFile("geometry_overlaps", "${does1overlap2}${NEW_LINE}${does1overlap3}${NEW_LINE}${does2overlap3}")
        values["12"] = does1overlap2
        values["13"] = does1overlap3
        values["23"] = does2overlap3

        values
    }

    Geometry union() {

        // tag::union[]
        Polygon polygon1 = new Polygon([[
                [-121.915, 47.390],
                [-122.640, 46.995],
                [-121.739, 46.308],
                [-121.168, 46.777],
                [-120.981, 47.316],
                [-121.409, 47.413],
                [-121.915, 47.390]
        ]])

        Polygon polygon2 = new Polygon([[
                [-120.794, 46.664],
                [-121.541, 46.995],
                [-122.200, 46.536],
                [-121.937, 45.890],
                [-120.959, 46.096],
                [-120.794, 46.664]
        ]])

        Geometry union = polygon1.union(polygon2)
        // end::union[]
        drawGeometries("geometry_union", [union])

        union
    }

    Geometry getOctagonalEnvelope() {
        // tag::getOctagonalEnvelope[]
        Geometry geometry = new Polygon ([[
            [-122.20367431640624, 47.543163654317304],
            [-122.3712158203125,  47.489368981370724],
            [-122.33276367187499, 47.35371061951363],
            [-122.11029052734374, 47.3704545156932],
            [-122.08831787109375, 47.286681888764214],
            [-122.28332519531249, 47.2270293988673],
            [-122.2174072265625,  47.154237057576594],
            [-121.904296875,      47.32579231609051],
            [-122.06085205078125, 47.47823216312885],
            [-122.20367431640624, 47.543163654317304]
        ]])
        Geometry octagonalEnvelope = geometry.octagonalEnvelope
        // end::getOctagonalEnvelope[]
        drawGeometries("geometry_octagonalEnvelope", [geometry, octagonalEnvelope])
        octagonalEnvelope
    }

    Geometry getMinimumRectangle() {
        // tag::getMinimumRectangle[]
        Geometry geometry = new Polygon ([[
          [-122.20367431640624, 47.543163654317304],
          [-122.3712158203125,  47.489368981370724],
          [-122.33276367187499, 47.35371061951363],
          [-122.11029052734374, 47.3704545156932],
          [-122.08831787109375, 47.286681888764214],
          [-122.28332519531249, 47.2270293988673],
          [-122.2174072265625,  47.154237057576594],
          [-121.904296875,      47.32579231609051],
          [-122.06085205078125, 47.47823216312885],
          [-122.20367431640624, 47.543163654317304]
        ]])
        Geometry minimumRectangle = geometry.minimumRectangle
        // end::getMinimumRectangle[]
        drawGeometries("geometry_minimumrectangle", [geometry, minimumRectangle])
        minimumRectangle
    }

    Geometry getMinimumBoundingCircle() {
        // tag::getMinimumBoundingCircle[]
        Geometry geometry = new Polygon ([[
          [-122.20367431640624, 47.543163654317304],
          [-122.3712158203125,  47.489368981370724],
          [-122.33276367187499, 47.35371061951363],
          [-122.11029052734374, 47.3704545156932],
          [-122.08831787109375, 47.286681888764214],
          [-122.28332519531249, 47.2270293988673],
          [-122.2174072265625,  47.154237057576594],
          [-121.904296875,      47.32579231609051],
          [-122.06085205078125, 47.47823216312885],
          [-122.20367431640624, 47.543163654317304]
        ]])
        Geometry minimumBoundingCircle = geometry.minimumBoundingCircle
        // end::getMinimumBoundingCircle[]
        drawGeometries("geometry_minimumBoundingCircle", [geometry, minimumBoundingCircle])
        minimumBoundingCircle
    }

    Geometry getMinimumDiameter() {
        // tag::getMinimumDiameter[]
        Geometry geometry = new Polygon ([[
            [-122.20367431640624, 47.543163654317304],
            [-122.3712158203125,  47.489368981370724],
            [-122.33276367187499, 47.35371061951363],
            [-122.11029052734374, 47.3704545156932],
            [-122.08831787109375, 47.286681888764214],
            [-122.28332519531249, 47.2270293988673],
            [-122.2174072265625,  47.154237057576594],
            [-121.904296875,      47.32579231609051],
            [-122.06085205078125, 47.47823216312885],
            [-122.20367431640624, 47.543163654317304]
        ]])
        Geometry minimumDiameter = geometry.minimumDiameter
        // end::getMinimumDiameter[]
        drawGeometries("geometry_minimumDiameter", [geometry, minimumDiameter])
        minimumDiameter
    }

    Geometry getMinimumClearance() {
        // tag::getMinimumClearance[]
        Geometry geometry = new Polygon ([[
            [-122.20367431640624, 47.543163654317304],
            [-122.3712158203125,  47.489368981370724],
            [-122.33276367187499, 47.35371061951363],
            [-122.11029052734374, 47.3704545156932],
            [-122.08831787109375, 47.286681888764214],
            [-122.28332519531249, 47.2270293988673],
            [-122.2174072265625,  47.154237057576594],
            [-121.904296875,      47.32579231609051],
            [-122.06085205078125, 47.47823216312885],
            [-122.20367431640624, 47.543163654317304]
        ]])
        Geometry minimumClearance = geometry.minimumClearance
        // end::getMinimumClearance[]
        drawGeometries("geometry_minimumClearance", [geometry, minimumClearance])
        minimumClearance
    }

    List<LineString> offset() {
        // tag::offset[]
        LineString line = new LineString(
                [3.198, 43.164],
                [6.713, 49.755],
                [9.702, 42.592],
                [15.32, 53.798]
        )
        LineString positive = line.offset(1.2)
        LineString negative = line.offset(-2.4)
        // end::offset[]
        drawGeometries("geometry_offset", [line, positive, negative])
        [positive, negative]
    }

    Map<String, Integer> getDimension() {
        // tag::getDimension[]
        Point point = Geometry.fromWKT("POINT (-122.3437 47.7540)")
        println "Point Dimension = ${point.dimension}"

        LineString lineString = Geometry.fromWKT("LINESTRING (-122.525 47.256, -122.376 47.595)")
        println "LineString Dimension = ${lineString.dimension}"

        Polygon polygon = Geometry.fromWKT("POLYGON ((-122.590 47.204, -122.365 47.204, -122.365 47.312, -122.590 47.312, -122.590 47.204))")
        println "Polygon Dimension = ${polygon.dimension}"
        // end::getDimension[]
        writeFile("geometry_dimension", """           
Point Dimension = ${point.dimension}
LineString Dimension = ${lineString.dimension}
Polygon Dimension = ${polygon.dimension}
""")
        [point: point.dimension, lineString: lineString.dimension, polygon: polygon.dimension]
    }

    List<Boolean> isEmpty() {
        // tag::isEmpty[]
        Geometry geom1 = Geometry.fromWKT("POINT EMPTY")
        boolean isGeom1Empty = geom1.empty
        println "Is ${geom1.wkt} empty? ${isGeom1Empty ? 'Yes' : 'No'}"

        Geometry geom2 = Geometry.fromWKT("POINT (-122.3437 47.7540)")
        boolean isGeom2Empty = geom2.empty
        println "Is ${geom2.wkt} empty? ${isGeom2Empty ? 'Yes' : 'No'}"
        // end::isEmpty[]
        writeFile("geometry_isempty", "Is ${geom1.wkt} empty? ${isGeom1Empty ? 'Yes' : 'No'}${NEW_LINE}Is ${geom2.wkt} empty? ${isGeom2Empty ? 'Yes' : 'No'}")
        [isGeom1Empty, isGeom2Empty]
    }

    List<Boolean> isRectangle() {
        // tag::isRectangle1[]
        Geometry geom1 = Geometry.fromWKT("POLYGON ((-122.590 47.204, -122.365 47.204, -122.365 47.312, -122.590 47.312, -122.590 47.204))")
        boolean isGeom1Rect = geom1.isRectangle()
        println "Is the geometry a rectangle? ${isGeom1Rect ? 'Yes' : 'No'}"
        // end::isRectangle1[]
        writeFile("geometry_isrectangle1", "Is the geometry a rectangle? ${isGeom1Rect ? 'Yes' : 'No'}")
        drawGeometry("geometry_isrectangle1", geom1)

        // tag::isRectangle2[]
        Geometry geom2 = Geometry.fromWKT("POLYGON ((-122.360 47.215, -122.656 46.912, -121.838 46.931, -122.360 47.215))")
        boolean isGeom2Rect = geom2.isRectangle()
        println "Is the geometry a rectangle? ${isGeom2Rect ? 'Yes' : 'No'}"
        // end::isRectangle2[]
        writeFile("geometry_isrectangle2", "Is the geometry a rectangle? ${isGeom2Rect ? 'Yes' : 'No'}")
        drawGeometry("geometry_isrectangle2", geom2)

        [isGeom1Rect, isGeom2Rect]
    }

    List<Boolean> isSimple() {
        // tag::isSimple1[]
        Geometry geom1 = new LineString(
                [-122.323, 47.599],
                [-122.385, 47.581]
        )
        boolean isGeom1Simple = geom1.simple
        println "Is the Geometry simple? ${isGeom1Simple}"
        // end::isSimple1[]
        writeFile("geometry_issimple1", "Is the Geometry simple? ${isGeom1Simple}")
        drawGeometry("geometry_issimple1", geom1)

        // tag::isSimple2[]
        Geometry geom2 = new LineString(
                [-122.356, 47.537],
                [-122.295, 47.580],
                [-122.284, 47.532],
                [-122.353, 47.574]
        )
        boolean isGeom2Simple = geom2.simple
        println "Is the Geometry simple? ${isGeom2Simple}"
        // end::isSimple2[]
        writeFile("geometry_issimple2", "Is the Geometry simple? ${isGeom2Simple}")
        drawGeometry("geometry_issimple2", geom2)

        [isGeom1Simple, isGeom2Simple]
    }

    List<Boolean> isValid() {
        // tag::isValid1[]
        Geometry geom1 = new LineString(
                [-122.323, 47.599],
                [-122.385, 47.581]
        )
        boolean isGeom1Valid = geom1.valid
        println "Is the Geometry valid? ${isGeom1Valid}"
        // end::isValid1[]
        writeFile("geometry_isvalid1", "Is the Geometry valid? ${isGeom1Valid}")
        drawGeometry("geometry_isvalid1", geom1)

        // tag::isValid2[]
        Geometry geom2 = new Polygon(new LinearRing([
            [48.16406, 42.29356],
            [35.15625, 25.79989],
            [64.33593, 24.52713],
            [26.71875, 39.09596],
            [48.16406, 42.29356],
        ]))
        boolean isGeom2Valid = geom2.valid
        println "Is the Geometry valid? ${isGeom2Valid}"
        println geom2.validReason
        // end::isValid2[]
        writeFile("geometry_isvalid2", "Is the Geometry valid? ${isGeom2Valid}${NEW_LINE}${geom2.validReason}")
        drawGeometry("geometry_isvalid2", geom2)

        [isGeom1Valid, isGeom2Valid]
    }

    List<Boolean> isCurved() {
        // tag::isCurved1[]
        Geometry geom1 = new CircularString([
                [-122.464599609375, 47.247542522268006],
                [-122.03613281249999, 47.37789454155521],
                [-122.37670898437499, 47.58393661978134]
        ])

        boolean isGeom1Curved = geom1.curved
        println "Is the Geometry valid? ${isGeom1Curved}"
        // end::isCurved1[]
        writeFile("geometry_iscurved1", "Is the Geometry curved? ${isGeom1Curved}")
        drawGeometry("geometry_iscurved1", geom1)

        // tag::isCurved2[]
        Geometry geom2 = new LineString(
                [-122.323, 47.599],
                [-122.385, 47.581]
        )
        boolean isGeom2Curved = geom2.curved
        println "Is the Geometry valid? ${isGeom2Curved}"
        // end::isCurved2[]
        writeFile("geometry_iscurved2", "Is the Geometry curved? ${isGeom2Curved}")
        drawGeometry("geometry_iscurved2", geom2)

        [isGeom1Curved, isGeom2Curved]
    }

    List<Boolean> isWithinDistance() {
        // tag::isWithinDistance1[]
        Geometry geom1 = new Point(-88.945, 41.771)
        Geometry geom2 = new Point(-113.906, 37.160)

        double distance1 = 26.0
        boolean isWithin1 = geom1.isWithinDistance(geom2, distance1)
        println "Is ${geom1} within ${distance1} of ${geom2}? ${isWithin1 ? 'Yes' : 'No'}"
        // end::isWithinDistance1[]
        writeFile("geometry_iswithindistance1", "Is ${geom1} within ${distance1} of ${geom2}? ${isWithin1 ? 'Yes' : 'No'}")
        
        // tag::isWithinDistance2[]
        double distance2 = 15.5
        boolean isWithin2 = geom1.isWithinDistance(geom2, distance2)
        println "Is ${geom1} within ${distance2} of ${geom2}? ${isWithin2 ? 'Yes' : 'No'}"
        // end::isWithinDistance2[]
        writeFile("geometry_iswithindistance2", "Is ${geom1} within ${distance2} of ${geom2}? ${isWithin2 ? 'Yes' : 'No'}")

        [isWithin1, isWithin2]
    }

    Geometry getDelaunayTriangleDiagram() {
        // tag::getDelaunayTriangleDiagram[]
        Geometry points = Geometry.createRandomPoints(new Bounds(-180, -90, 180, 90).geometry, 100)
        Geometry delaunayTriangle = points.delaunayTriangleDiagram
        // end::getDelaunayTriangleDiagram[]
        drawOnBasemap("geometry_delaunaytrianglediagram", [
                createLayerFromGeometry("delaunay", delaunayTriangle, "fill=#0066FF stroke=#navy fill-opacity=0.5"),
                createLayerFromGeometry("points", points, "shape=#0066FF shape-size=8")
        ])
        delaunayTriangle
    }

    Geometry getVoronoiDiagram() {
        // tag::getVoronoiDiagram[]
        Geometry points = Geometry.createRandomPoints(new Bounds(-180, -90, 180, 90).geometry, 100)
        Geometry voronoiDiagram = points.voronoiDiagram
        // end::getVoronoiDiagram[]
        drawOnBasemap("geometry_voronoidiagram", [
                createLayerFromGeometry("voronoi", voronoiDiagram, "fill=#0066FF stroke=#navy fill-opacity=0.5"),
                createLayerFromGeometry("points", points, "shape=#0066FF shape-size=8")
        ], new Bounds(-180, -90, 180, 90, "EPSG:4326"))
        voronoiDiagram
    }

    Geometry normalize() {
        // tag::normalize[]
        Geometry geometry = Geometry.fromWKT("POLYGON((2 4, 1 3, 2 1, 6 1, 6 3, 4 4, 2 4))")
        geometry.normalize()
        println "Normalized Geometry = ${geometry}"
        // end::normalize[]
        writeFile("geometry_normalize", "Normalized Geometry = ${geometry}")
        drawGeometry("geometry_normalize", geometry)
        geometry
    }

    Map<String,Geometry> norm() {
        // tag::norm[]
        Geometry geometry = Geometry.fromWKT("POLYGON((2 4, 1 3, 2 1, 6 1, 6 3, 4 4, 2 4))")
        Geometry normalizedGeometry = geometry.norm
        println "Un-normalized Geometry = ${geometry}"
        println "Normalized Geometry    = ${normalizedGeometry}"
        // end::norm[]
        writeFile("geometry_norm", "Un-normalized Geometry = ${geometry}${NEW_LINE}Normalized Geometry = ${normalizedGeometry}")
        drawGeometry("geometry_norm", normalizedGeometry)
        [geometry: geometry, normalizedGeometry: normalizedGeometry]
    }

    Geometry smooth() {
        // tag::smooth[]
        Geometry geometry = Geometry.fromWKT("POLYGON((10 0, 10 20, 0 20, 0 30, 30 30, 30 20, 20 20, 20 0, 10 0))")
        Geometry smoothed = geometry.smooth(0.75)
        // end::smooth[]
        drawGeometries("geometry_smooth", [geometry, smoothed])
        smoothed
    }

    IntersectionMatrix relateIntersectionMatrix() {
        // tag::relateIntersectionMatrix[]
        Polygon polygon1 = new Polygon([[
                [-121.915, 47.390],
                [-122.640, 46.995],
                [-121.739, 46.308],
                [-121.168, 46.777],
                [-120.981, 47.316],
                [-121.409, 47.413],
                [-121.915, 47.390]
        ]])

        Polygon polygon2 = new Polygon([[
                [-120.794, 46.664],
                [-121.541, 46.995],
                [-122.200, 46.536],
                [-121.937, 45.890],
                [-120.959, 46.096],
                [-120.794, 46.664]
        ]])

        IntersectionMatrix matrix = polygon1.relate(polygon2)
        println "Intersection Matrix = ${matrix}"
        println "Contains = ${matrix.contains}"
        println "Covered By = ${matrix.coveredBy}"
        println "Covers = ${matrix.covers}"
        println "Disjoint = ${matrix.disjoint}"
        println "Intersects = ${matrix.intersects}"
        println "Within = ${matrix.within}"
        // end::relateIntersectionMatrix[]
        drawGeometries("geometry_relateIntersectionMatrix", [polygon1, polygon2])
        writeFile("geometry_relateIntersectionMatrix", """
Intersection Matrix = ${matrix}
Contains = ${matrix.contains}
Covered By = ${matrix.coveredBy}
Covers = ${matrix.covers}
Disjoint = ${matrix.disjoint}
Intersects = ${matrix.intersects}
Within = ${matrix.within}
""")
        matrix
    }

    List<Boolean> relate() {
        // tag::relate[]
        Polygon polygon1 = new Polygon([[
                [-121.915, 47.390],
                [-122.640, 46.995],
                [-121.739, 46.308],
                [-121.168, 46.777],
                [-120.981, 47.316],
                [-121.409, 47.413],
                [-121.915, 47.390]
        ]])

        Polygon polygon2 = new Polygon([[
                [-120.794, 46.664],
                [-121.541, 46.995],
                [-122.200, 46.536],
                [-121.937, 45.890],
                [-120.959, 46.096],
                [-120.794, 46.664]
        ]])

        println polygon1.relate(polygon2, "212101212")
        println polygon1.relate(polygon2, "111111111")
        println polygon1.relate(polygon2, "222222222")

        // end::relate[]
        drawGeometries("geometry_relate", [polygon1, polygon2])
        writeFile("geometry_relate", """
${polygon1.relate(polygon2, "212101212")}
${polygon1.relate(polygon2, "111111111")}
${polygon1.relate(polygon2, "222222222")}
""")
        [
            polygon1.relate(polygon2, "212101212"),
            polygon1.relate(polygon2, "111111111"),
            polygon1.relate(polygon2, "222222222")
        ]
    }

    Geometry densify() {
        // tag::densify[]
        Geometry geometry = new LineString([
            [-122.28062152862547, 47.12986316579223],
            [-122.2809863090515, 47.12935221617075],
            [-122.2809863090515, 47.12786313499169],
            [-122.28111505508421, 47.127731743474406],
            [-122.28137254714966, 47.127673347140345],
            [-122.28178024291992, 47.12768794622986],
            [-122.28227376937865, 47.128067521151195],
            [-122.28227376937865, 47.12906024275466]
        ])
        Geometry densified = geometry.densify(0.0001)
        println "# of points in original geometry = ${geometry.numPoints}"
        println "# of points in densified geometry = ${densified.numPoints}"
        // end::densify[]
        drawGeometry("geometry_densify_orig", geometry)
        drawGeometry("geometry_densify", densified)
        writeFile("geometry_densify", "# of points in original geometry = ${geometry.numPoints}${NEW_LINE}# of points in densified geometry = ${densified.numPoints}")
        densified
    }

    Geometry simplify() {
        // tag::simplify[]
        Geometry geometry = new LineString([
            [-123.59619140625001, 47.338822694822],
            [-123.04687499999999, 47.010225655683485],
            [-122.2119140625, 46.965259400349275],
            [-121.201171875, 47.17477833929903],
            [-120.87158203125, 47.487513008956554],
            [-120.62988281249999, 48.31242790407178],
            [-120.84960937499999, 48.647427805533546],
            [-121.59667968749999, 48.850258199721495],
            [-122.36572265625, 48.980216985374994],
            [-123.134765625, 48.83579746243093],
            [-123.3984375, 48.44377831058802],
            [-123.59619140625001, 48.10743118848039],
            [-123.85986328124999, 47.62097541515849]
        ])
        Geometry simplified = geometry.simplify(0.5)
        println "# of points in original geometry = ${geometry.numPoints}"
        println "# of points in simplified geometry = ${simplified.numPoints}"
        // end::simplify[]
        drawGeometry("geometry_simplify_orig", geometry)
        drawGeometry("geometry_simplify", simplified)
        writeFile("geometry_simplify", "# of points in original geometry = ${geometry.numPoints}${NEW_LINE}# of points in simplified geometry = ${simplified.numPoints}")
        simplified
    }

    Geometry simplifyPreservingTopology() {
        // tag::simplifyPreservingTopology[]
        Geometry geometry = new LineString([
                [-123.59619140625001, 47.338822694822],
                [-123.04687499999999, 47.010225655683485],
                [-122.2119140625, 46.965259400349275],
                [-121.201171875, 47.17477833929903],
                [-120.87158203125, 47.487513008956554],
                [-120.62988281249999, 48.31242790407178],
                [-120.84960937499999, 48.647427805533546],
                [-121.59667968749999, 48.850258199721495],
                [-122.36572265625, 48.980216985374994],
                [-123.134765625, 48.83579746243093],
                [-123.3984375, 48.44377831058802],
                [-123.59619140625001, 48.10743118848039],
                [-123.85986328124999, 47.62097541515849]
        ])
        Geometry simplified = geometry.simplifyPreservingTopology(0.1)
        println "# of points in original geometry = ${geometry.numPoints}"
        println "# of points in simplified geometry = ${simplified.numPoints}"
        // end::simplifyPreservingTopology[]
        drawGeometry("geometry_simplifyPreservingTopology_orig", geometry)
        drawGeometry("geometry_simplifyPreservingTopology", simplified)
        writeFile("geometry_simplifyPreservingTopology", "# of points in original geometry = ${geometry.numPoints}${NEW_LINE}# of points in simplified geometry = ${simplified.numPoints}")
        simplified
    }

    List<Geometry> translate() {
        // tag::translate[]
        Geometry geometry = new Polygon(new LinearRing([
            [-121.83837890625, 47.5913464767971],
            [-122.76123046875, 46.9802523552188],
            [-122.67333984374, 46.3014061543733],
            [-121.00341796874, 46.3772542051002],
            [-121.22314453124, 47.1448974855539],
            [-121.83837890625, 47.5913464767971]
        ]))
        Geometry translatedGeometry = geometry.translate(2.1, 3.2)
        // end::translate[]
        drawGeometries("geometry_translate", [geometry, translatedGeometry])
        [geometry,translatedGeometry]
    }

    List<Geometry> scaleXY() {
        // tag::scaleXY[]
        Geometry geometry = new Polygon(new LinearRing([
                [-121.83837890625, 47.5913464767971],
                [-122.76123046875, 46.9802523552188],
                [-122.67333984374, 46.3014061543733],
                [-121.00341796874, 46.3772542051002],
                [-121.22314453124, 47.1448974855539],
                [-121.83837890625, 47.5913464767971]
        ]))
        Geometry scaledGeometry = geometry.scale(1.1,1.2)
        println scaledGeometry
        // end::scaleXY[]
        drawGeometries("geometry_scalexy", [geometry, scaledGeometry])
        [geometry,scaledGeometry]
    }

    List<Geometry> scaleXYAroundPoint() {
        // tag::scaleXYAroundPoint[]
        Geometry geometry = new Polygon(new LinearRing([
                [-121.83837890625, 47.5913464767971],
                [-122.76123046875, 46.9802523552188],
                [-122.67333984374, 46.3014061543733],
                [-121.00341796874, 46.3772542051002],
                [-121.22314453124, 47.1448974855539],
                [-121.83837890625, 47.5913464767971]
        ]))
        Point centroid = geometry.centroid
        Geometry scaledGeometry = geometry.scale(1.1, 1.1, centroid.x, centroid.y)
        // end::scaleXYAroundPoint[]
        drawGeometries("geometry_scalexy_around_point", [geometry, scaledGeometry])
        [geometry,scaledGeometry]
    }

    List<Geometry> rotate() {
        // tag::rotate_theta[]
        Geometry geometry = new Polygon(new LinearRing([
                [-121.83837890625, 47.5913464767971],
                [-122.76123046875, 46.9802523552188],
                [-122.67333984374, 46.3014061543733],
                [-121.00341796874, 46.3772542051002],
                [-121.22314453124, 47.1448974855539],
                [-121.83837890625, 47.5913464767971]
        ]))
        Geometry theta = geometry.rotate(Math.toRadians(45))
        // end::rotate_theta[]
        drawGeometry("geometry_rotate_theta1", geometry)
        drawGeometry("geometry_rotate_theta2", theta)

        // tag::rotate_sincos[]
        Geometry sinCos = geometry.rotate(Math.toRadians(15), Math.toRadians(35))
        // end::rotate_sincos[]
        drawGeometry("geometry_rotate_sincos1", geometry)
        drawGeometry("geometry_rotate_sincos2", sinCos)

        // tag::rotate_thetaxy[]
        Geometry thetaXY = geometry.rotate(Math.toRadians(90), geometry.centroid.x, geometry.centroid.y)
        // end::rotate_thetaxy[]
        drawGeometries("geometry_rotate_thetaxy", [geometry, thetaXY])

        // tag::rotate_sincosxy[]
        Geometry sinCosXY = geometry.rotate(Math.toRadians(15), Math.toRadians(35), geometry.centroid.x, geometry.centroid.y)
        // end::rotate_sincosxy[]
        drawGeometries("geometry_rotate_sincosxy", [geometry, sinCosXY])

        [geometry, theta, sinCos, thetaXY, sinCosXY]
    }

    List<Geometry> shear() {
        // tag::shear[]
        Geometry geometry = new Polygon(new LinearRing([
                [-121.83837890625, 47.5913464767971],
                [-122.76123046875, 46.9802523552188],
                [-122.67333984374, 46.3014061543733],
                [-121.00341796874, 46.3772542051002],
                [-121.22314453124, 47.1448974855539],
                [-121.83837890625, 47.5913464767971]
        ]))
        Geometry shearedGeometry = geometry.shear(0.1,0.4)
        // end::shear[]
        drawGeometry("geometry_shear1", geometry)
        drawGeometry("geometry_shear2", shearedGeometry)
        [geometry,shearedGeometry]
    }

    List<Geometry> reflect() {
        // tag::reflect1[]
        Geometry geometry = new Polygon(new LinearRing([
                [-121.83837890625, 47.5913464767971],
                [-122.76123046875, 46.9802523552188],
                [-122.67333984374, 46.3014061543733],
                [-121.00341796874, 46.3772542051002],
                [-121.22314453124, 47.1448974855539],
                [-121.83837890625, 47.5913464767971]
        ]))
        Point centroid = geometry.centroid
        Geometry reflectedGeometry = geometry.reflect(0.1,0.4, centroid.x, centroid.y)
        // end::reflect1[]
        drawGeometries("geometry_reflect", [geometry, reflectedGeometry])

        // tag::reflect2[]
        Geometry reflectedAroundOrigin = geometry.reflect(0.5, 0.34)
        // end::reflect2[]
        drawGeometry("geometry_reflect2", geometry)
        drawGeometry("geometry_reflected2", reflectedAroundOrigin)

        [geometry,reflectedGeometry,reflectedAroundOrigin]
    }

    List<Geometry> reducePrecision() {
        // tag::reducePrecision[]
        Geometry g1 = new Point(5.19775390625, 51.07421875)
        println "Original Geometry: ${g1.wkt}"

        Geometry g2 = g1.reducePrecision()
        println "Floating Point Geometry: ${g2.wkt}"

        Geometry g3 = g1.reducePrecision("fixed", scale: 100)
        println "Fixed Point Geometry: ${g3.wkt}"

        Geometry g4 = g1.reducePrecision("floating_single", pointwise: true, removecollapsed: true)
        println "Floating Point Single Geometry: ${g4.wkt}"
        // end::reducePrecision[]
        writeFile("geometry_reducePrecision", """Original Geometry: ${g1.wkt}
Floating Point Geometry: ${g2.wkt}              
Fixed Point Geometry: ${g3.wkt} 
Floating Point Single Geometry: ${g4.wkt}
""")
        [g1,g2,g3,g4]
    }

    List<Geometry> getNearestPoints() {
        // tag::getNearestPoints[]
        Geometry point = new Point( -122.3845276236534, 47.58285653105873)
        Geometry polygon = Geometry.fromWKT("POLYGON ((-122.3848307132721 47.58285110342092, " +
                "-122.38484144210814 47.58255620092149, -122.38469392061235 47.582558010144346, " +
                "-122.3846912384033 47.5825797208137, -122.38460808992384 47.58258695770149, " +
                "-122.38460808992384 47.582628569786834, -122.38458126783371 47.58263037900717, " +
                "-122.38458126783371 47.58277330721735, -122.38460540771483 47.58277149800195, " +
                "-122.38460540771483 47.582805873084084, -122.38467246294022 47.5828131099406, " +
                "-122.38467246294022 47.58285110342092, -122.3848307132721 47.58285110342092))")
        List<Point> nearestPoints = polygon.getNearestPoints(point)
        // end::getNearestPoints[]
        drawGeometries("geometry_nearestpoints", [point, polygon, new MultiPoint(nearestPoints)], drawCoords: false)
        nearestPoints
    }

    List<Point> getAt() {
        // tag::getAt[]
        MultiPoint multiPoint = new MultiPoint([
                new Point(-122.3876953125, 47.5820839916191),
                new Point(-122.464599609375, 47.25686404408872),
                new Point(-122.48382568359374, 47.431803338643334)
        ])
        Point p1 = multiPoint[0]
        println p1

        Point p2 = multiPoint[1]
        println p2

        Point p3 = multiPoint[2]
        println p3
        // end::getAt[]
        writeFile("geometry_getat", """${p1.wkt}
${p2.wkt}
${p3.wkt}
""")
        [p1,p2,p3]
    }

    List<Object> asType() {
        // tag::asType[]
        Geometry geometry = new Polygon([[
            [-121.915, 47.390],
            [-122.640, 46.995],
            [-121.739, 46.308],
            [-121.168, 46.777],
            [-120.981, 47.316],
            [-121.409, 47.413],
            [-121.915, 47.390]
        ]])

        Bounds bounds = geometry as Bounds
        println bounds

        Point point = geometry as Point
        println point
        // end::asType[]
        writeFile("geometry_astype","${bounds}${NEW_LINE}${point}")
        drawGeometries("geometry_astype", [geometry, bounds.geometry, point])
        [bounds, point]
    }

    List<Boolean> equals() {
        //tag::equals[]
        Point point1 = new Point(-121.915, 47.390)
        Point point2 = new Point(-121.915, 47.390)
        Point point3 = new Point(-121.409, 47.413)

        boolean does1equal2 = point1.equals(point2)
        println "Does ${point1} equal ${point2}? ${does1equal2 ? 'Yes' : 'No'}"

        boolean does1equal3 = point1.equals(point3)
        println "Does ${point1} equal ${point3}? ${does1equal3 ? 'Yes' : 'No'}"

        boolean does2equal3 = point2.equals(point3)
        println "Does ${point2} equal ${point3}? ${does2equal3 ? 'Yes' : 'No'}"
        //end::equals[]
        writeFile("geometry_equals", """Does ${point1} equal ${point2}? ${does1equal2 ? 'Yes' : 'No'}
Does ${point1} equal ${point3}? ${does1equal3 ? 'Yes' : 'No'}
Does ${point2} equal ${point3}? ${does2equal3 ? 'Yes' : 'No'}
""")
        [does1equal2, does1equal3, does2equal3]
    }

    List<Boolean> equalsTopo() {
        //tag::equalsTopo[]
        Point point1 = new Point(-121.915, 47.390)
        Point point2 = new Point(-121.915, 47.390)
        Point point3 = new Point(-121.409, 47.413)

        boolean does1equal2 = point1.equalsTopo(point2)
        println "Does ${point1} equal ${point2}? ${does1equal2 ? 'Yes' : 'No'}"

        boolean does1equal3 = point1.equalsTopo(point3)
        println "Does ${point1} equal ${point3}? ${does1equal3 ? 'Yes' : 'No'}"

        boolean does2equal3 = point2.equalsTopo(point3)
        println "Does ${point2} equal ${point3}? ${does2equal3 ? 'Yes' : 'No'}"
        //end::equalsTopo[]
        writeFile("geometry_equalstopo", """Does ${point1} equal ${point2}? ${does1equal2 ? 'Yes' : 'No'}
Does ${point1} equal ${point3}? ${does1equal3 ? 'Yes' : 'No'}
Does ${point2} equal ${point3}? ${does2equal3 ? 'Yes' : 'No'}
""")
        [does1equal2, does1equal3, does2equal3]
    }

    List<Boolean> equalsNorm() {
        //tag::equalsNorm[]
        Geometry geom1 = Geometry.fromWKT("POLYGON ((2 4, 1 3, 2 1, 6 1, 6 3, 4 4, 2 4))")
        Geometry geom2 = Geometry.fromWKT("POLYGON ((1 3, 2 4, 4 4, 6 3, 6 1, 2 1, 1 3))")
        Geometry geom3 = Geometry.fromWKT("POLYGON ((1 1, 1 4, 4 4, 4 1, 1 1))")

        boolean does1equal2 = geom1.equalsNorm(geom2)
        println "Does ${geom1} equal ${geom2}? ${does1equal2 ? 'Yes' : 'No'}"

        boolean does1equal3 = geom1.equalsNorm(geom3)
        println "Does ${geom1} equal ${geom3}? ${does1equal3 ? 'Yes' : 'No'}"

        boolean does2equal3 = geom2.equalsNorm(geom3)
        println "Does ${geom2} equal ${geom3}? ${does2equal3 ? 'Yes' : 'No'}"
        //end::equalsNorm[]
        writeFile("geometry_equalsnorm", """Does ${geom1} equal ${geom2}? ${does1equal2 ? 'Yes' : 'No'}
Does ${geom1} equal ${geom3}? ${does1equal3 ? 'Yes' : 'No'}
Does ${geom2} equal ${geom3}? ${does2equal3 ? 'Yes' : 'No'}
""")
        [does1equal2, does1equal3, does2equal3]
    }

    List<Long> prepare() {

        //tag::prepare[]
        Geometry geometry = new Polygon([[
             [-121.915, 47.390],
             [-122.640, 46.995],
             [-121.739, 46.308],
             [-121.168, 46.777],
             [-120.981, 47.316],
             [-121.409, 47.413],
             [-121.915, 47.390]
        ]])
        PreparedGeometry preparedGeometry = geometry.prepare()

        Closure timer = { Closure action ->
            long start = System.nanoTime()
            action.call()
            long end = System.nanoTime()
            end - start
        }

        MultiPoint points = Geometry.createRandomPoints(new Bounds(-180, -90, 180, 90).geometry, 100000)


        long timeWithGeometry = timer({ ->
            points.geometries.each { Point point ->
                geometry.contains(point)
            }
        })
        println "Time with Geometry         = ${timeWithGeometry} nanoseconds"

        long timeWithPreparedGeometry = timer({ ->
            points.geometries.each { Point point ->
                preparedGeometry.contains(point)
            }
        })

        println "Time with PreparedGeometry = ${timeWithPreparedGeometry} nanoseconds"
        //end::prepare[]
        writeFile("geometry_prepare", """Time with Geometry         = ${timeWithGeometry} nanoseconds      
Time with PreparedGeometry = ${timeWithPreparedGeometry} nanoseconds
""")
        [timeWithGeometry, timeWithPreparedGeometry]
    }

    List<Long> prepareStatic() {

        //tag::prepareStatic[]
        Geometry geometry = new Polygon([[
             [-121.915, 47.390],
             [-122.640, 46.995],
             [-121.739, 46.308],
             [-121.168, 46.777],
             [-120.981, 47.316],
             [-121.409, 47.413],
             [-121.915, 47.390]
        ]])
        PreparedGeometry preparedGeometry = Geometry.prepare(geometry)

        Closure timer = { Closure action ->
            long start = System.nanoTime()
            action.call()
            long end = System.nanoTime()
            end - start
        }

        MultiPoint points = Geometry.createRandomPoints(new Bounds(-180, -90, 180, 90).geometry, 100000)


        long timeWithGeometry = timer({ ->
            points.geometries.each { Point point ->
                geometry.contains(point)
            }
        })
        println "Time with Geometry         = ${timeWithGeometry} nanoseconds"

        long timeWithPreparedGeometry = timer({ ->
            points.geometries.each { Point point ->
                preparedGeometry.contains(point)
            }
        })

        println "Time with PreparedGeometry = ${timeWithPreparedGeometry} nanoseconds"
        //end::prepareStatic[]
        writeFile("geometry_preparestatic", """Time with Geometry         = ${timeWithGeometry} nanoseconds      
Time with PreparedGeometry = ${timeWithPreparedGeometry} nanoseconds
""")
        [timeWithGeometry, timeWithPreparedGeometry]
    }

    // Prepared Geometry

    PreparedGeometry preparedGeometryCreate() {
        // tag::preparedGeometryCreate[]
        Polygon polygon = new Polygon([[
           [-120.739, 48.151],
           [-121.003, 47.070],
           [-119.465, 47.137],
           [-119.553, 46.581],
           [-121.267, 46.513],
           [-121.168, 45.706],
           [-118.476, 45.951],
           [-118.762, 48.195],
           [-120.739, 48.151]
        ]])

        PreparedGeometry preparedGeometry = new PreparedGeometry(polygon)

        Geometry geometry = preparedGeometry.geometry
        // end::preparedGeometryCreate[]
        drawGeometry("geometry_preparedgeometry_create", geometry)
        preparedGeometry
    }

    Map<String, Boolean> preparedGeometryContains() {

        Map<String, Boolean> results = [:]

        // tag::preparedGeometryContains1[]
        PreparedGeometry polygon1 = new PreparedGeometry(new Polygon([[
                [-120.739, 48.151],
                [-121.003, 47.070],
                [-119.465, 47.137],
                [-119.553, 46.581],
                [-121.267, 46.513],
                [-121.168, 45.706],
                [-118.476, 45.951],
                [-118.762, 48.195],
                [-120.739, 48.151]
        ]]))

        Polygon polygon2 = new Polygon([[
                [-120.212, 47.591],
                [-119.663, 47.591],
                [-119.663, 47.872],
                [-120.212, 47.872],
                [-120.212, 47.591]
        ]])

        boolean contains = polygon1.contains(polygon2)
        println contains
        // end::preparedGeometryContains1[]
        drawGeometries("geometry_preparedgeometry_contains_1", [polygon1.geometry, polygon2])
        writeFile("geometry_preparedgeometry_contains_1", "${contains}")
        results["1contains2"] = contains

        // tag::preparedGeometryContains2[]
        Polygon polygon3 = new Polygon([[
                [-120.563, 46.739],
                [-119.948, 46.739],
                [-119.948, 46.965],
                [-120.563, 46.965],
                [-120.563, 46.739]
        ]])

        contains = polygon1.contains(polygon3)
        println contains
        // end::preparedGeometryContains2[]
        drawGeometries("geometry_preparedgeometry_contains_2", [polygon1.geometry, polygon3])
        writeFile("geometry_preparedgeometry_contains_2", "${contains}")
        results["1contains3"] = contains

        results
    }

    Map<String, Boolean> preparedGeometryContainsProperly() {

        Map<String, Boolean> results = [:]

        // tag::preparedGeometryContainsProperly1[]
        PreparedGeometry polygon1 = new PreparedGeometry(new Polygon([[
            [-122.50064849853516, 47.22096718353454],
            [-122.41928100585938, 47.22096718353454],
            [-122.41928100585938, 47.277365616965646],
            [-122.50064849853516, 47.277365616965646],
            [-122.50064849853516, 47.22096718353454]
        ]]))

        Polygon polygon2 = new Polygon([[
            [-122.44571685791014, 47.24031721435104],
            [-122.42958068847656, 47.24031721435104],
            [-122.42958068847656, 47.253135632244216],
            [-122.44571685791014, 47.253135632244216],
            [-122.44571685791014, 47.24031721435104]
        ]])

        boolean contains = polygon1.containsProperly(polygon2)
        println contains
        // end::preparedGeometryContainsProperly1[]
        drawGeometries("geometry_preparedgeometry_containsproperly_1", [polygon1.geometry, polygon2])
        writeFile("geometry_preparedgeometry_containsproperly_1", "${contains}")
        results["1contains2"] = contains

        // tag::preparedGeometryContainsProperly2[]
        Polygon polygon3 = new Polygon([[
            [-122.50064849853516, 47.22096718353454],
            [-122.46631622314455, 47.22096718353454],
            [-122.46631622314455, 47.277365616965646],
            [-122.50133514404297, 47.277365616965646],
            [-122.50064849853516, 47.22096718353454],
        ]])

        contains = polygon1.containsProperly(polygon3)
        println contains
        // end::preparedGeometryContainsProperly2[]
        drawGeometries("geometry_preparedgeometry_containsproperly_2", [polygon1.geometry, polygon3])
        writeFile("geometry_preparedgeometry_containsproperly_2", "${contains}")
        results["1contains3"] = contains

        results
    }

    Map<String, Boolean> preparedGeometryCoveredBy() {

        Map<String, Boolean> results = [:]

        // tag::preparedGeometryCoveredBy1[]
        Polygon polygon1 = new Polygon([[
                [-120.739, 48.151],
                [-121.003, 47.070],
                [-119.465, 47.137],
                [-119.553, 46.581],
                [-121.267, 46.513],
                [-121.168, 45.706],
                [-118.476, 45.951],
                [-118.762, 48.195],
                [-120.739, 48.151]
        ]])

        PreparedGeometry polygon2 = new PreparedGeometry(new Polygon([[
                [-120.212, 47.591],
                [-119.663, 47.591],
                [-119.663, 47.872],
                [-120.212, 47.872],
                [-120.212, 47.591]
        ]]))

        boolean isCoveredBy = polygon2.coveredBy(polygon1)
        println isCoveredBy
        // end::preparedGeometryCoveredBy1[]
        drawGeometries("geometry_preparedgeometry_coveredby_1", [polygon1, polygon2.geometry])
        writeFile("geometry_preparedgeometry_coveredby_1", "${isCoveredBy}")
        results["2coveredBy1"] = isCoveredBy

        // tag::preparedGeometryCoveredBy2[]
        PreparedGeometry polygon3 = new PreparedGeometry(new Polygon([[
                [-120.563, 46.739],
                [-119.948, 46.739],
                [-119.948, 46.965],
                [-120.563, 46.965],
                [-120.563, 46.739]
        ]]))

        isCoveredBy = polygon3.coveredBy(polygon1)
        println isCoveredBy
        // end::preparedGeometryCoveredBy2[]
        drawGeometries("geometry_preparedgeometry_coveredby_2", [polygon1, polygon3.geometry])
        writeFile("geometry_preparedgeometry_coveredby_2", "${isCoveredBy}")
        results["3coveredBy1"] = isCoveredBy

        results
    }


    Map<String, Boolean> preparedGeometryCovers() {

        Map<String, Boolean> results = [:]

        // tag::preparedGeometryCovers1[]
        PreparedGeometry polygon1 = new PreparedGeometry(new Polygon([[
                [-120.739, 48.151],
                [-121.003, 47.070],
                [-119.465, 47.137],
                [-119.553, 46.581],
                [-121.267, 46.513],
                [-121.168, 45.706],
                [-118.476, 45.951],
                [-118.762, 48.195],
                [-120.739, 48.151]
        ]]))

        Polygon polygon2 = new Polygon([[
                [-120.212, 47.591],
                [-119.663, 47.591],
                [-119.663, 47.872],
                [-120.212, 47.872],
                [-120.212, 47.591]
        ]])

        boolean isCovered = polygon1.covers(polygon2)
        println isCovered
        // end::preparedGeometryCovers1[]
        drawGeometries("geometry_preparedgeometry_covers_1", [polygon1.geometry, polygon2])
        writeFile("geometry_preparedgeometry_covers_1", "${isCovered}")
        results["1covers2"] = isCovered

        // tag::preparedGeometryCovers2[]
        Polygon polygon3 = new Polygon([[
                [-120.563, 46.739],
                [-119.948, 46.739],
                [-119.948, 46.965],
                [-120.563, 46.965],
                [-120.563, 46.739]
        ]])

        isCovered = polygon1.covers(polygon3)
        println isCovered
        // end::preparedGeometryCovers2[]
        drawGeometries("geometry_preparedgeometry_covers_2", [polygon1.geometry, polygon3])
        writeFile("geometry_preparedgeometry_covers_2", "${isCovered}")
        results["1covers3"] = isCovered

        results
    }

    Map<String, Boolean> preparedGeometryCrosses() {
        Map<String, Boolean> values = [:]

        // tag::preparedGeometryCrosses[]
        PreparedGeometry line1 = new PreparedGeometry(new LineString([[-122.387, 47.613], [-121.750, 47.353]]))
        LineString line2 = new LineString([[-122.255, 47.368], [-121.882, 47.746]])
        LineString line3 = new LineString([[-122.486, 47.256], [-121.695, 46.822]])

        boolean doesCross12 = line1.crosses(line2)
        println doesCross12

        boolean doesCross13 = line1.crosses(line3)
        println doesCross13
        // end::preparedGeometryCrosses[]
        drawGeometries("geometry_preparedgeometry_crosses", [line1.geometry, line2, line3])
        writeFile("geometry_preparedgeometry_crosses", "${doesCross12}${NEW_LINE}${doesCross13}")
        values["12"] = doesCross12
        values["13"] = doesCross13

        values
    }

    Map<String, Boolean> preparedGeometryDisjoint() {

        Map<String, Boolean> values = [:]

        // tag::preparedGeometryDisjoint[]
        PreparedGeometry polygon1 = new PreparedGeometry(new Polygon([[
            [-121.915, 47.390],
            [-122.640, 46.995],
            [-121.739, 46.308],
            [-121.168, 46.777],
            [-120.981, 47.316],
            [-121.409, 47.413],
            [-121.915, 47.390]
        ]]))

        Polygon polygon2 = new Polygon([[
            [-120.794, 46.664],
            [-121.541, 46.995],
            [-122.200, 46.536],
            [-121.937, 45.890],
            [-120.959, 46.096],
            [-120.794, 46.664]
        ]])

        Polygon polygon3 = new Polygon([[
            [-120.541, 47.376],
            [-120.695, 47.047],
            [-119.794, 46.830],
            [-119.586, 47.331],
            [-120.102, 47.509],
            [-120.541, 47.376]
        ]])

        boolean isDisjoint12 = polygon1.disjoint(polygon2)
        println isDisjoint12

        boolean isDisjoint13 = polygon1.disjoint(polygon3)
        println isDisjoint13
        // end::preparedGeometryDisjoint[]
        drawGeometries("geometry_preparedgeometry_disjoint", [polygon1.geometry, polygon2, polygon3])
        writeFile("geometry_preparedgeometry_disjoint", "${isDisjoint12}${NEW_LINE}${isDisjoint13}")
        values["12"] = isDisjoint12
        values["13"] = isDisjoint13

        values
    }

    Map<String, Boolean> preparedGeometryIntersects() {

        Map<String, Boolean> values = [:]

        // tag::preparedGeometryIntersects[]
        PreparedGeometry polygon1 = new PreparedGeometry(new Polygon([[
            [-121.915, 47.390],
            [-122.640, 46.995],
            [-121.739, 46.308],
            [-121.168, 46.777],
            [-120.981, 47.316],
            [-121.409, 47.413],
            [-121.915, 47.390]
        ]]))

        Polygon polygon2 = new Polygon([[
            [-120.794, 46.664],
            [-121.541, 46.995],
            [-122.200, 46.536],
            [-121.937, 45.890],
            [-120.959, 46.096],
            [-120.794, 46.664]
        ]])

        Polygon polygon3 = new Polygon([[
            [-120.541, 47.376],
            [-120.695, 47.047],
            [-119.794, 46.830],
            [-119.586, 47.331],
            [-120.102, 47.509],
            [-120.541, 47.376]
        ]])

        boolean does1intersect2 = polygon1.intersects(polygon2)
        println does1intersect2

        boolean does1intersect3 = polygon1.intersects(polygon3)
        println does1intersect3
        // end::preparedGeometryIntersects[]
        drawGeometries("geometry_preparedgeometry_intersects", [polygon1.geometry, polygon2, polygon3])
        writeFile("geometry_preparedgeometry_intersects", "${does1intersect2}${NEW_LINE}${does1intersect3}")
        values["12"] = does1intersect2
        values["13"] = does1intersect3

        values
    }

    Map<String, Boolean> preparedGeometryOverlaps() {

        Map<String, Boolean> values = [:]

        // tag::preparedGeometryOverlaps[]
        PreparedGeometry polygon1 = new PreparedGeometry(new Polygon([[
            [-121.915, 47.390],
            [-122.640, 46.995],
            [-121.739, 46.308],
            [-121.168, 46.777],
            [-120.981, 47.316],
            [-121.409, 47.413],
            [-121.915, 47.390]
        ]]))

        Polygon polygon2 = new Polygon([[
            [-120.794, 46.664],
            [-121.541, 46.995],
            [-122.200, 46.536],
            [-121.937, 45.890],
            [-120.959, 46.096],
            [-120.794, 46.664]
        ]])

        Polygon polygon3 = new Polygon([[
            [-120.541, 47.376],
            [-120.695, 47.047],
            [-119.794, 46.830],
            [-119.586, 47.331],
            [-120.102, 47.509],
            [-120.541, 47.376]
        ]])

        boolean does1overlap2 = polygon1.overlaps(polygon2)
        println does1overlap2

        boolean does1overlap3 = polygon1.overlaps(polygon3)
        println does1overlap3
        // end::preparedGeometryOverlaps[]
        drawGeometries("geometry_preparedgeometry_overlaps", [polygon1.geometry, polygon2, polygon3])
        writeFile("geometry_preparedgeometry_overlaps", "${does1overlap2}${NEW_LINE}${does1overlap3}")
        values["12"] = does1overlap2
        values["13"] = does1overlap3

        values
    }

    Map<String, Boolean> preparedGeometryTouches() {

        Map<String, Boolean> results = [:]

        // tag::preparedGeometryTouches1[]
        PreparedGeometry line1 = new PreparedGeometry(new LineString([
            [-122.38651514053345, 47.58219978280006],
            [-122.38651514053345, 47.58020234903306]
        ]))

        LineString line2 = new LineString([
            [-122.38651514053345, 47.58124449789785],
            [-122.38333940505981, 47.58124449789785]
        ])

        boolean touches = line1.touches(line2)
        // end::preparedGeometryTouches1[]
        drawGeometries("geometry_preparedgeometry_touches_12", [line1.geometry, line2])
        writeFile("geometry_preparedgeometry_touches_12", "${touches}")
        results["touches_12"] = touches

        // tag::preparedGeometryTouches2[]
        LineString line3 = new LineString([
            [-122.386257648468, 47.58183793450921],
            [-122.38348960876465, 47.5818668824645]
        ])

        touches = line1.touches(line3)
        // end::preparedGeometryTouches2[]
        drawGeometries("geometry_preparedgeometry_touches_13", [line1.geometry, line3])
        writeFile("geometry_preparedgeometry_touches_13", "${touches}")
        results["touches_13"] = touches

        results
    }

    Map<String, Boolean> preparedGeometryWithin() {

        Map<String, Boolean> results = [:]

        // tag::preparedGeometryWithin1[]

        PreparedGeometry polygon1 = new PreparedGeometry(new Polygon([[
            [-120.212, 47.591],
            [-119.663, 47.591],
            [-119.663, 47.872],
            [-120.212, 47.872],
            [-120.212, 47.591]
        ]]))

        Polygon polygon2 = new Polygon([[
            [-120.739, 48.151],
            [-121.003, 47.070],
            [-119.465, 47.137],
            [-119.553, 46.581],
            [-121.267, 46.513],
            [-121.168, 45.706],
            [-118.476, 45.951],
            [-118.762, 48.195],
            [-120.739, 48.151]
        ]])

        boolean within = polygon1.within(polygon2)
        println within
        // end::preparedGeometryWithin1[]
        drawGeometries("geometry_preparedgeometry_within_1", [polygon1.geometry, polygon2])
        writeFile("geometry_preparedgeometry_within_1", "${within}")
        results["1within2"] = within

        // tag::preparedGeometryWithin2[]
        Polygon polygon3 = new Polygon([[
            [-120.563, 46.739],
            [-119.948, 46.739],
            [-119.948, 46.965],
            [-120.563, 46.965],
            [-120.563, 46.739]
        ]])

        within = polygon1.within(polygon3)
        println within
        // end::preparedGeometryWithin2[]
        drawGeometries("geometry_preparedgeometry_within_2", [polygon1.geometry, polygon3])
        writeFile("geometry_preparedgeometry_within_2", "${within}")
        results["1within3"] = within

        results
    }

    // Geometry Readers and Writers

    List<Reader> getGeometryReaders() {
        // tag::getGeometryReaders[]
        List<Reader> readers = Readers.list()
        readers.each { Reader reader ->
            println reader.class.simpleName
        }
        // end::getGeometryReaders[]
        writeFile("geometry_list_readers", "${readers.collect{it.class.simpleName}.join(NEW_LINE)}")
        readers
    }

    Geometry findGeometryReader() {
        // tag::findGeometryReader[]
        String wkt = "POINT (-123.15 46.237)"
        Reader reader = Readers.find("wkt")
        Geometry geometry = reader.read(wkt)
        // end::findGeometryReader[]
        drawGeometry("geometry_find_reader", geometry)
        geometry
    }

    List<Writer> getGeometryWriters() {
        // tag::getGeometryWriters[]
        List<Writer> writers = Writers.list()
        writers.each { Writer writer ->
            println writer.class.simpleName
        }
        // end::getGeometryWriters[]
        writeFile("geometry_list_writers", "${writers.collect{it.class.simpleName}.join(NEW_LINE)}")
        writers
    }

    String findGeometryWriter() {
        // tag::findGeometryWriter[]
        Geometry geometry = new Point(-122.45, 43.21)
        Writer writer = Writers.find("geojson")
        String geojson = writer.write(geometry)
        println geojson
        // end::findGeometryWriter[]
        writeFile("geometry_find_writer", "${geojson}")
        geojson
    }

    List<Geometry> fromString() {
        //tag::fromString[]
        Geometry geom1 = Geometry.fromString('POINT (-123.15 46.237)')
        println geom1

        Geometry geom2 = Geometry.fromString('{"type":"LineString","coordinates":[[3.198,43.164],[6.713,49.755],[9.702,42.592],[15.32,53.798]]}')
        println geom2


        Geometry geom3 = Geometry.fromString('<Point><coordinates>-123.15,46.237</coordinates></Point>')
        println geom3
        //end::fromString[]
        writeFile("geometry_fromstring", """${geom1}${NEW_LINE}${geom2}${NEW_LINE}${geom3}""")
        [geom1, geom2, geom3]
    }

    // WKB

    Geometry readGeometryFromWKBReader() {
        // tag::readGeometryFromWKBReader[]
        String wkb = "0000000001C05EC9999999999A40471E5604189375"
        WkbReader reader = new WkbReader()
        Geometry geometry = reader.read(wkb)
        // end::readGeometryFromWKBReader[]
        drawGeometry("geometry_read_wkbreader", geometry)
        geometry
    }

    Geometry readGeometryFromWKB() {
        // tag::readGeometryFromWKB[]
        String wkb = "000000000200000004400995810624DD2F404594FDF3B645A2401ADA1CAC0831274048E0A3D70A3D714023676C8B43958140454BC6A7EF9DB2402EA3D70A3D70A4404AE624DD2F1AA0"
        Geometry geometry = Geometry.fromWKB(wkb)
        // end::readGeometryFromWKB[]
        drawGeometry("geometry_read_geometryfromwkb", geometry)
        geometry
    }

    String writeGeometryToWKB() {
        // tag::writeGeometryToWKB[]
        Geometry geometry = new Point(-123.15, 46.237)
        String wkb = geometry.wkb
        println wkb
        // end::writeGeometryToWKB[]
        writeFile("geometry_to_wkb", wkb)
        wkb
    }

    String writeGeometryToWKBUsingWriter() {
        // tag::writeGeometryToWKBUsingWriter[]
        Geometry geometry = new LineString(
                [3.198, 43.164],
                [6.713, 49.755],
                [9.702, 42.592],
                [15.32, 53.798]
        )
        WkbWriter writer = new WkbWriter()
        String wkb = writer.write(geometry)
        println wkb
        // end::writeGeometryToWKBUsingWriter[]
        writeFile("geometry_to_wkb_using_writer", wkb)
        wkb
    }

    // TWKB

    Geometry readGeometryFromTWkb() {
        // tag::readGeometryFromTWkb[]
        TWkbReader reader = new TWkbReader()
        Geometry geometry = reader.read("01000204")
        println geometry.wkt
        // end::readGeometryFromTWkb[]
        writeFile("geometry_read_from_twkb", "${geometry.wkt}")
        drawGeometry("geometry_read_from_twkb", geometry)
        geometry
    }

    // WKT

    Geometry readGeometryFromWKTReader() {
        // tag::readGeometryFromWKTReader[]
        String wkt = "POINT (-123.15 46.237)"
        WktReader reader = new WktReader()
        Geometry geometry = reader.read(wkt)
        // end::readGeometryFromWKTReader[]
        drawGeometry("geometry_read_wktreader", geometry)
        geometry
    }

    Geometry readGeometryFromWKT() {
        // tag::readGeometryFromWKT[]
        String wkt = "LINESTRING (3.198 43.164, 6.7138 49.755, 9.702 42.592, 15.327 53.798)"
        Geometry geometry = Geometry.fromWKT(wkt)
        // end::readGeometryFromWKT[]
        drawGeometry("geometry_read_geometryfromwkt", geometry)
        geometry
    }

    String writeGeometryToWKT() {
        // tag::writeGeometryToWKT[]
        Geometry geometry = new Point(-123.15, 46.237)
        String wkt = geometry.wkt
        println wkt
        // end::writeGeometryToWKT[]
        writeFile("geometry_to_wkt", wkt)
        wkt
    }

    String writeGeometryToWKTUsingWriter() {
        // tag::writeGeometryToWKTUsingWriter[]
        Geometry geometry = new LineString(
                [3.198, 43.164],
                [6.713, 49.755],
                [9.702, 42.592],
                [15.32, 53.798]
        )
        WktWriter writer = new WktWriter()
        String wkt = writer.write(geometry)
        println wkt
        // end::writeGeometryToWKTUsingWriter[]
        writeFile("geometry_to_wkt_using_writer", wkt)
        wkt
    }

    // GeoJSON

    Geometry readGeometryFromGeoJSONReader() {
        // tag::readGeometryFromGeoJSONReader[]
        String json = '{"type":"Point","coordinates":[-123.15,46.237]}'
        GeoJSONReader reader = new GeoJSONReader()
        Geometry geometry = reader.read(json)
        // end::readGeometryFromGeoJSONReader[]
        drawGeometry("geometry_read_geojsonreader", geometry)
        geometry
    }

    Geometry readGeometryFromGeoJSON() {
        // tag::readGeometryFromGeoJSON[]
        String json = '{"type":"LineString","coordinates":[[3.198,43.164],[6.713,49.755],[9.702,42.592],[15.32,53.798]]}'
        Geometry geometry = Geometry.fromGeoJSON(json)
        // end::readGeometryFromGeoJSON[]
        drawGeometry("geometry_read_geometryfromgeojson", geometry)
        geometry
    }

    String writeGeometryToGeoJSON() {
        // tag::writeGeometryToGeoJSON[]
        Geometry geometry = new Point(-123.15, 46.237)
        String json = geometry.geoJSON
        println json
        // end::writeGeometryToGeoJSON[]
        writeFile("geometry_to_geojson", json)
        json
    }

    String writeGeometryToGeoJSONUsingWriter() {
        // tag::writeGeometryToGeoJSONUsingWriter[]
        Geometry geometry = new LineString(
                [3.198, 43.164],
                [6.713, 49.755],
                [9.702, 42.592],
                [15.32, 53.798]
        )
        GeoJSONWriter writer = new GeoJSONWriter()
        String json = writer.write(geometry)
        println json
        // end::writeGeometryToGeoJSONUsingWriter[]
        writeFile("geometry_to_geojson_using_writer", json)
        json
    }

    // GeoPackage

    String writeGeometryToGeoPackageString() {
        // tag::writeGeometryToGeoPackageString[]
        Geometry geometry = new Point(-123.15, 46.237)
        GeoPackageWriter writer = new GeoPackageWriter()
        String str = writer.write(geometry)
        println str
        // end::writeGeometryToGeoPackageString[]
        writeFile("geometry_to_geopackage_string", str)
        str
    }

    byte[] writeGeometryToGeoPackageBytes() {
        // tag::writeGeometryToGeoPackageBytes[]
        Geometry geometry = new Point(-123.15, 46.237)
        GeoPackageWriter writer = new GeoPackageWriter()
        byte[] bytes = writer.writeBytes(geometry)
        println bytes.encodeHex()
        // end::writeGeometryToGeoPackageBytes[]
        writeFile("geometry_to_geopackage_bytes", bytes.encodeHex().toString())
        bytes
    }

    Geometry readGeometryFromGeoPackageString() {
        // tag::readGeometryFromGeoPackageString[]
        String str = "4750000200000000c05ec9999999999ac05ec9999999999a40471e560418937540471e56041893750000000001c05ec9999999999a40471e5604189375"
        GeoPackageReader reader = new GeoPackageReader()
        Geometry geometry = reader.read(str)
        // end::readGeometryFromGeoPackageString[]
        drawGeometry("geometry_from_geopackage_string", geometry)
        geometry
    }

    Geometry readGeometryFromGeoPackageBytes() {
        // tag::readGeometryFromGeoPackageBytes[]
        byte[] bytes = "4750000200000000c05ec9999999999ac05ec9999999999a40471e560418937540471e56041893750000000001c05ec9999999999a40471e5604189375".decodeHex()
        GeoPackageReader reader = new GeoPackageReader()
        Geometry geometry = reader.read(bytes)
        // end::readGeometryFromGeoPackageBytes[]
        drawGeometry("geometry_from_geopackage_bytes", geometry)
        geometry
    }

    // KML

    Geometry readGeometryFromKMLReader() {
        // tag::readGeometryFromKMLReader[]
        String kml = "<Point><coordinates>-123.15,46.237</coordinates></Point>"
        KmlReader reader = new KmlReader()
        Geometry geometry = reader.read(kml)
        // end::readGeometryFromKMLReader[]
        drawGeometry("geometry_read_kmlreader", geometry)
        geometry
    }

    Geometry readGeometryFromKML() {
        // tag::readGeometryFromKML[]
        String kml = "<LineString><coordinates>3.198,43.164 6.713,49.755 9.702,42.592 15.32,53.798</coordinates></LineString>"
        Geometry geometry = Geometry.fromKml(kml)
        // end::readGeometryFromKML[]
        drawGeometry("geometry_read_geometryfromkml", geometry)
        geometry
    }

    String writeGeometryToKML() {
        // tag::writeGeometryToKML[]
        Geometry geometry = new Point(-123.15, 46.237)
        String kml = geometry.kml
        println kml
        // end::writeGeometryToKML[]
        writeFile("geometry_to_kml", kml)
        kml
    }

    String writeGeometryToKMLUsingWriter() {
        // tag::writeGeometryToKMLUsingWriter[]
        Geometry geometry = new LineString(
                [3.198, 43.164],
                [6.713, 49.755],
                [9.702, 42.592],
                [15.32, 53.798]
        )
        KmlWriter writer = new KmlWriter()
        String kml = writer.write(geometry)
        println kml
        // end::writeGeometryToKMLUsingWriter[]
        writeFile("geometry_to_kml_using_writer", kml)
        kml
    }
    
    // Geobuf

    Geometry readGeometryFromGeobufReader() {
        // tag::readGeometryFromGeobufReader[]
        String geobuf = "10021806320c08001a08dffab87590958c2c"
        GeobufReader reader = new GeobufReader()
        Geometry geometry = reader.read(geobuf)
        // end::readGeometryFromGeobufReader[]
        drawGeometry("geometry_read_geobufreader", geometry)
        geometry
    }

    Geometry readGeometryFromGeobuf() {
        // tag::readGeometryFromGeobuf[]
        String geobuf = "10021806322408021a20e0b08603c0859529f089ad03b0c8a40690efec02efb1ea06a0e5ad05e0f5d70a"
        Geometry geometry = Geometry.fromGeobuf(geobuf)
        // end::readGeometryFromGeobuf[]
        drawGeometry("geometry_read_geometryfromgeobuf", geometry)
        geometry
    }

    String writeGeometryToGeobuf() {
        // tag::writeGeometryToGeobuf[]
        Geometry geometry = new Point(-123.15, 46.237)
        String geobuf = geometry.geobuf
        println geobuf
        // end::writeGeometryToGeobuf[]
        writeFile("geometry_to_geobuf", geobuf)
        geobuf
    }

    String writeGeometryToGeobufUsingWriter() {
        // tag::writeGeometryToGeobufUsingWriter[]
        Geometry geometry = new LineString(
                [3.198, 43.164],
                [6.713, 49.755],
                [9.702, 42.592],
                [15.32, 53.798]
        )
        GeobufWriter writer = new GeobufWriter()
        String geobuf = writer.write(geometry)
        println geobuf
        // end::writeGeometryToGeobufUsingWriter[]
        writeFile("geometry_to_geobuf_using_writer", geobuf)
        geobuf
    }

    // GML 2

    Geometry readGeometryFromGml2Reader() {
        // tag::readGeometryFromGml2Reader[]
        String gml2 = "<gml:Point><gml:coordinates>-123.15,46.237</gml:coordinates></gml:Point>"
        Gml2Reader reader = new Gml2Reader()
        Geometry geometry = reader.read(gml2)
        // end::readGeometryFromGml2Reader[]
        drawGeometry("geometry_read_gml2reader", geometry)
        geometry
    }

    Geometry readGeometryFromGml2() {
        // tag::readGeometryFromGml2[]
        String gml2 = "<gml:LineString><gml:coordinates>3.198,43.164 6.713,49.755 9.702,42.592 15.32,53.798</gml:coordinates></gml:LineString>"
        Geometry geometry = Geometry.fromGML2(gml2)
        // end::readGeometryFromGml2[]
        drawGeometry("geometry_read_geometryfromgml2", geometry)
        geometry
    }

    String writeGeometryToGml2() {
        // tag::writeGeometryToGml2[]
        Geometry geometry = new Point(-123.15, 46.237)
        String gml2 = geometry.gml2
        println gml2
        // end::writeGeometryToGml2[]
        writeFile("geometry_to_gml2", gml2)
        gml2
    }

    String writeGeometryToGml2UsingWriter() {
        // tag::writeGeometryToGml2UsingWriter[]
        Geometry geometry = new LineString(
                [3.198, 43.164],
                [6.713, 49.755],
                [9.702, 42.592],
                [15.32, 53.798]
        )
        Gml2Writer writer = new Gml2Writer()
        String gml2 = writer.write(geometry)
        println gml2
        // end::writeGeometryToGml2UsingWriter[]
        writeFile("geometry_to_gml2_using_writer", gml2)
        gml2
    }

    // GML 3

    Geometry readGeometryFromGml3Reader() {
        // tag::readGeometryFromGml3Reader[]
        String gml3 = "<gml:Point><gml:pos>-123.15 46.237</gml:pos></gml:Point>"
        Gml3Reader reader = new Gml3Reader()
        Geometry geometry = reader.read(gml3)
        // end::readGeometryFromGml3Reader[]
        drawGeometry("geometry_read_gml3reader", geometry)
        geometry
    }

    Geometry readGeometryFromGml3() {
        // tag::readGeometryFromGml3[]
        String gml3 = "<gml:LineString><gml:posList>3.198 43.164 6.713 49.755 9.702 42.592 15.32 53.798</gml:posList></gml:LineString>"
        Geometry geometry = Geometry.fromGML3(gml3)
        // end::readGeometryFromGml3[]
        drawGeometry("geometry_read_geometryfromgml3", geometry)
        geometry
    }

    String writeGeometryToGml3() {
        // tag::writeGeometryToGml3[]
        Geometry geometry = new Point(-123.15, 46.237)
        String gml3 = geometry.gml3
        println gml3
        // end::writeGeometryToGml3[]
        writeFile("geometry_to_gml3", gml3)
        gml3
    }

    String writeGeometryToGml3UsingWriter() {
        // tag::writeGeometryToGml3UsingWriter[]
        Geometry geometry = new LineString(
                [3.198, 43.164],
                [6.713, 49.755],
                [9.702, 42.592],
                [15.32, 53.798]
        )
        Gml3Writer writer = new Gml3Writer()
        String gml3 = writer.write(geometry)
        println gml3
        // end::writeGeometryToGml3UsingWriter[]
        writeFile("geometry_to_gml3_using_writer", gml3)
        gml3
    }

    // GPX

    Geometry readGeometryFromGpxReader() {
        // tag::readGeometryFromGpxReader[]
        String gpx = "<wpt lat='46.237' lon='-123.15'/>"
        GpxReader reader = new GpxReader()
        Geometry geometry = reader.read(gpx)
        // end::readGeometryFromGpxReader[]
        drawGeometry("geometry_read_gpxreader", geometry)
        geometry
    }

    Geometry readGeometryFromGpx() {
        // tag::readGeometryFromGpx[]
        String gpx = "<rte><rtept lat='43.164' lon='3.198' /><rtept lat='49.755' lon='6.713' /><rtept lat='42.592' lon='9.702' /><rtept lat='53.798' lon='15.32' /></rte>"
        Geometry geometry = Geometry.fromGpx(gpx)
        // end::readGeometryFromGpx[]
        drawGeometry("geometry_read_geometryfromgpx", geometry)
        geometry
    }

    String writeGeometryToGpx() {
        // tag::writeGeometryToGpx[]
        Geometry geometry = new Point(-123.15, 46.237)
        String gpx = geometry.gpx
        println gpx
        // end::writeGeometryToGpx[]
        writeFile("geometry_to_gpx", gpx)
        gpx
    }

    String writeGeometryToGpxUsingWriter() {
        // tag::writeGeometryToGpxUsingWriter[]
        Geometry geometry = new LineString(
                [3.198, 43.164],
                [6.713, 49.755],
                [9.702, 42.592],
                [15.32, 53.798]
        )
        GpxWriter writer = new GpxWriter()
        String gpx = writer.write(geometry)
        println gpx
        // end::writeGeometryToGpxUsingWriter[]
        writeFile("geometry_to_gpx_using_writer", gpx)
        gpx
    }
    
    // GeoRSS

    Geometry readGeometryFromGeoRSSReader() {
        // tag::readGeometryFromGeoRSSReader[]
        String georss = "<georss:point>46.237 -123.15</georss:point>"
        GeoRSSReader reader = new GeoRSSReader()
        Geometry geometry = reader.read(georss)
        // end::readGeometryFromGeoRSSReader[]
        drawGeometry("geometry_read_georssreader", geometry)
        geometry
    }

    String writeGeometryToGeoRSSUsingWriter() {
        // tag::writeGeometryToGeoRSSUsingWriter[]
        Geometry geometry = new LineString(
                [3.198, 43.164],
                [6.713, 49.755],
                [9.702, 42.592],
                [15.32, 53.798]
        )
        GeoRSSWriter writer = new GeoRSSWriter()
        String georss = writer.write(geometry)
        println georss
        // end::writeGeometryToGeoRSSUsingWriter[]
        writeFile("geometry_to_georss_using_writer", georss)
        georss
    }

    // Google Polyline

    Geometry readGeometryFromGooglePolyline() {
        // tag::readGeometryFromGooglePolyline[]
        String str = "_p~iF~ps|U_ulLnnqC_mqNvxq`@"
        GooglePolylineEncoder encoder = new GooglePolylineEncoder()
        Geometry geometry = encoder.read(str)
        // end::readGeometryFromGooglePolyline[]
        drawGeometry("geometry_read_googlepolyline", geometry)
        geometry
    }

    String writeGeometryToGooglePolyline() {
        // tag::writeGeometryToGooglePolyline[]
        Geometry geometry = new LineString(
                [3.198, 43.164],
                [6.713, 49.755],
                [9.702, 42.592],
                [15.32, 53.798]
        )
        GooglePolylineEncoder encoder = new GooglePolylineEncoder()
        String str = encoder.write(geometry)
        println str
        // end::writeGeometryToGooglePolyline[]
        writeFile("geometry_to_googlepolyline", str)
        str
    }
}
