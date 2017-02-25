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
import geoscript.geom.io.GeobufReader
import geoscript.geom.io.GeobufWriter
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
}