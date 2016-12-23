package geoscript.cookbook

import geoscript.geom.Bounds
import geoscript.geom.CircularRing
import geoscript.geom.CircularString
import geoscript.geom.CompoundCurve
import geoscript.geom.CompoundRing
import geoscript.geom.Geometry
import geoscript.geom.GeometryCollection
import geoscript.geom.LineString
import geoscript.geom.MultiLineString
import geoscript.geom.MultiPoint
import geoscript.geom.MultiPolygon
import geoscript.geom.Point
import geoscript.geom.Polygon
import geoscript.geom.io.GeoJSONReader
import geoscript.geom.io.GeoJSONWriter
import geoscript.geom.io.WktReader
import geoscript.geom.io.WktWriter
import geoscript.proj.Projection
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

    Bounds createBounds() {
        // tag::createBounds[]
        Bounds bounds = new Bounds(-127.265, 43.068, -113.554, 50.289, "EPSG:4326")
        // end::createBounds[]
        drawGeometries("geometry_create_bounds", [bounds.geometry])
        bounds
    }

    Bounds createBoundsNoProjection() {
        // tag::createBoundsNoProjection[]
        Bounds bounds = new Bounds(-127.265, 43.068, -113.554, 50.289)
        bounds.proj = new Projection("EPSG:4326")
        // end::createBoundsNoProjection[]
        drawGeometries("geometry_create_bounds_no_proj", [bounds.geometry])
        bounds
    }

    Bounds createBoundsFromStringWithCommas() {
        // tag::createBoundsFromStringWithCommas[]
        Bounds bounds = Bounds.fromString("-127.265,43.068,-113.554,50.289,EPSG:4326")
        // end::createBoundsFromStringWithCommas[]
        drawGeometries("geometry_create_bounds_fromstring_withcommas", [bounds.geometry])
        bounds
    }

    Bounds createBoundsFromStringWithSpaces() {
        // tag::createBoundsFromStringWithSpaces[]
        Bounds bounds = Bounds.fromString("12.919921874999998 40.84706035607122 15.99609375 41.77131167976407 EPSG:4326")
        // end::createBoundsFromStringWithSpaces[]
        drawGeometries("geometry_create_bounds_fromstring_withspaces", [bounds.geometry])
        bounds
    }

    Map getBoundsProperties() {

        // tag::getBoundsProperties[]
        Bounds bounds = new Bounds(-127.265, 43.068, -113.554, 50.289, "EPSG:4326")
        String boundsStr = bounds.toString()
        println boundsStr
        // end::getBoundsProperties[]
        writeFile("geometry_bounds_properties", boundsStr)

        // tag::getBoundsProperties_minX[]
        double minX = bounds.minX
        println minX
        // end::getBoundsProperties_minX[]
        writeFile("geometry_bounds_properties_minx", "${minX}")

        // tag::getBoundsProperties_minY[]
        double minY = bounds.minY
        println minY
        // end::getBoundsProperties_minY[]
        writeFile("geometry_bounds_properties_miny", "${minY}")

        // tag::getBoundsProperties_maxX[]
        double maxX = bounds.maxX
        println maxX
        // end::getBoundsProperties_maxX[]
        writeFile("geometry_bounds_properties_maxx", "${maxX}")

        // tag::getBoundsProperties_maxY[]
        double maxY = bounds.maxY
        println maxY
        // end::getBoundsProperties_maxY[]
        writeFile("geometry_bounds_properties_maxy", "${maxY}")

        // tag::getBoundsProperties_proj[]
        Projection proj = bounds.proj
        println proj.id
        // end::getBoundsProperties_proj[]
        writeFile("geometry_bounds_properties_proj", "${proj.id}")

        // tag::getBoundsProperties_area[]
        double area = bounds.area
        println area
        // end::getBoundsProperties_area[]
        writeFile("geometry_bounds_properties_area", "${area}")

        // tag::getBoundsProperties_width[]
        double width = bounds.width
        println width
        // end::getBoundsProperties_width[]
        writeFile("geometry_bounds_properties_width", "${width}")

        // tag::getBoundsProperties_height[]
        double height = bounds.height
        println height
        // end::getBoundsProperties_height[]
        writeFile("geometry_bounds_properties_height", "${height}")

        // tag::getBoundsProperties_aspect[]
        double aspect = bounds.aspect
        println aspect
        // end::getBoundsProperties_aspect[]
        writeFile("geometry_bounds_properties_aspect", "${aspect}")


        [
                bounds: boundsStr,
                minX: minX,
                minY: minY,
                maxX: maxX,
                maxY: maxY,
                proj: proj,
                area: area,
                width: width,
                height: height,
                aspect: aspect
        ]
    }

    Bounds expandByBounds() {
        // tag::expandByBounds[]
        Bounds bounds1 = new Bounds(-127.265, 43.068, -113.554, 50.289, "EPSG:4326")
        Bounds bounds2 = new Bounds(-127.265, 43.068, -113.554, 50.289, "EPSG:4326")
        bounds2.expandBy(10.1)
        // end::expandByBounds[]
        drawGeometries("geometry_bounds_expandby", [bounds2.polygon, bounds1.polygon])
        bounds2
    }

    Bounds expandBounds() {
        // tag::expandBounds[]
        Bounds bounds1 = new Bounds(8.4375, 37.996162679728116, 19.6875, 46.07323062540835, "EPSG:4326")
        Bounds bounds2 = new Bounds(22.5, 31.952162238024975, 30.937499999999996, 37.43997405227057, "EPSG:4326")
        bounds1.expand(bounds2)
        // end::expandBounds[]
        Bounds bounds3 = new Bounds(8.4375, 37.996162679728116, 19.6875, 46.07323062540835, "EPSG:4326")
        drawGeometries("geometry_bounds_expand", [bounds1.polygon, bounds2.polygon, bounds3.polygon])
        bounds1
    }

    Bounds scaleBounds() {
        // tag::scaleBounds[]
        Bounds bounds1 = new Bounds(-127.265, 43.068, -113.554, 50.289, "EPSG:4326")
        Bounds bounds2 = bounds1.scale(2)
        // end::scaleBounds[]
        drawGeometries("geometry_bounds_scale", [bounds2.polygon, bounds1.polygon])
        bounds2
    }

    Bounds reprojectBounds() {
        // tag::reprojectBounds1[]
        Bounds bounds = new Bounds(-122.485, 47.246, -122.452, 47.267, "EPSG:4326")
        println bounds
        // end::reprojectBounds1[]
        writeFile("geometry_bounds_reproject1", "${bounds}")
        // tag::reprojectBounds2[]
        Bounds reprojectedBounds = bounds.reproject("EPSG:2927")
        println reprojectedBounds
        // end::reprojectBounds2[]
        writeFile("geometry_bounds_reproject2", "${reprojectedBounds}")
        reprojectedBounds
    }

    Geometry boundsGetGeometry() {
        // tag::boundsGetGeometry[]
        Bounds bounds = new Bounds(-122.485, 47.246, -122.452, 47.267, "EPSG:4326")
        Geometry geometry = bounds.geometry
        // end::boundsGetGeometry[]
        drawGeometries("geometry_bounds_geometry", [geometry])
        geometry
    }

    Polygon boundsGetPolygon() {
        // tag::boundsGetPolygon[]
        Bounds bounds = new Bounds(-122.485, 47.246, -122.452, 47.267, "EPSG:4326")
        Polygon polygon = bounds.polygon
        // end::boundsGetPolygon[]
        drawGeometries("geometry_bounds_polygon", [polygon])
        polygon
    }

    List<Point> boundsGetCorners() {
        // tag::boundsGetCorners[]
        Bounds bounds = new Bounds(-122.485, 47.246, -122.452, 47.267, "EPSG:4326")
        List<Point> points = bounds.corners
        // end::boundsGetCorners[]
        drawGeometries("geometry_bounds_corners", points)
        points
    }

    List<Bounds> boundsTile() {
        // tag::boundsTile[]
        Bounds bounds = new Bounds(-122.485, 47.246, -122.452, 47.267, "EPSG:4326")
        List<Bounds> subBounds = bounds.tile(0.25)
        // end::boundsTile[]
        drawGeometries("geometry_bounds_tile", [bounds.geometry, new GeometryCollection(subBounds.collect { Bounds b -> b.geometry})])
        subBounds
    }

    List<Bounds> boundsQuadTree() {
        // tag::boundsQuadTree[]
        Bounds bounds = new Bounds(-180, -90, 180, 90, "EPSG:4326")
        bounds.quadTree(0,2) { Bounds b ->
            println b
        }
        // end::boundsQuadTree[]
        StringBuilder str = new StringBuilder()
        List<Bounds> subBounds = []
        bounds.quadTree(0,2) { Bounds b ->
            str.append(b.toString()).append(NEW_LINE)
            subBounds.add(b)
        }
        writeFile("geometry_bounds_quadtree", str.toString())
        subBounds
    }

    Map<String, Boolean> boundsIsEmpty() {
        // tag::boundsIsEmpty_false[]
        Bounds bounds = new Bounds(0,10,10,20)
        println bounds.isEmpty()
        // end::boundsIsEmpty_false[]
        writeFile("geometry_bounds_isempty_false", "${bounds.isEmpty()}")

        // tag::boundsIsEmpty_true[]
        Bounds emptyBounds = new Bounds(0,10,10,10)
        println emptyBounds.isEmpty()
        // end::boundsIsEmpty_true[]
        writeFile("geometry_bounds_isempty_true", "${emptyBounds.isEmpty()}")

        [
                bounds: bounds.isEmpty(),
                emptyBounds: emptyBounds.isEmpty()
        ]
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

    Bounds bounds() {
        // tag::bounds[]
        Point point = new Point(-123,46)
        Polygon polygon = point.buffer(2)
        Bounds bounds = polygon.bounds
        // end::bounds[]
        drawGeometries("geometry_bounds", [bounds.geometry, polygon, point])
        bounds
    }

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

}