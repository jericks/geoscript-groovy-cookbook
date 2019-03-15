package geoscript.cookbook

import geoscript.geom.Bounds
import geoscript.geom.Geometry
import geoscript.geom.GeometryCollection
import geoscript.geom.LineString
import geoscript.geom.Point
import geoscript.geom.Polygon
import geoscript.proj.Projection

class BoundsRecipes extends Recipes {

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

    Map<String, Boolean> boundsContainsBounds() {
        // tag::boundsContainsBounds1[]
        Bounds bounds1 = new Bounds(-107.226, 34.597, -92.812, 43.068)
        Bounds bounds2 = new Bounds(-104.326, 37.857, -98.349, 40.913)
        println bounds1.contains(bounds2)
        // end::boundsContainsBounds1[]
        drawGeometries("geometry_bounds_contains_bounds1", [bounds1.geometry, bounds2.geometry])
        writeFile("geometry_bounds_contains_bounds1", "${bounds1.contains(bounds2)}")

        // tag::boundsContainsBounds2[]
        Bounds bounds3 = new Bounds(-112.412, 36.809, -99.316, 44.777)
        println bounds1.contains(bounds3)
        // end::boundsContainsBounds2[]
        writeFile("geometry_bounds_contains_bounds2", "${bounds1.contains(bounds3)}")
        drawGeometries("geometry_bounds_contains_bounds2", [bounds1.geometry, bounds3.geometry])

        [
                bounds2: bounds1.contains(bounds2),
                bounds3: bounds1.contains(bounds3)
        ]
    }

    Map<String, Boolean> boundsContainsPoint() {
        // tag::boundsContainsPoint1[]
        Bounds bounds = new Bounds(-107.226, 34.597, -92.812, 43.068)
        Point point1 = new Point(-95.976, 39.639)
        println bounds.contains(point1)
        // end::boundsContainsPoint1[]
        writeFile("geometry_bounds_contains_point1", "${bounds.contains(point1)}")
        drawGeometries("geometry_bounds_contains_point1", [bounds.geometry, point1])

        // tag::boundsContainsPoint2[]
        Point point2 = new Point(-89.384, 38.959)
        println bounds.contains(point2)
        // end::boundsContainsPoint2[]
        writeFile("geometry_bounds_contains_point2", "${bounds.contains(point1)}")
        drawGeometries("geometry_bounds_contains_point2", [bounds.geometry, point2])

        [
                point1: bounds.contains(point1),
                point2: bounds.contains(point2)
        ]
    }

    Map<String, Boolean> boundsIntersectsBounds() {
        // tag::boundsIntersectsBounds1[]
        Bounds bounds1 = new Bounds(-95.885, 46.765, -95.788, 46.811)
        Bounds bounds2 = new Bounds(-95.847, 46.818, -95.810, 46.839)
        println bounds1.intersects(bounds2)
        // end::boundsIntersectsBounds1[]
        writeFile("geometry_bounds_intersects1", "${bounds1.intersects(bounds2)}")
        drawGeometries("geometry_bounds_intersects1", [bounds1.geometry, bounds2.geometry])

        // tag::boundsIntersectsBounds2[]
        Bounds bounds3 = new Bounds(-95.904, 46.747, -95.839, 46.792)
        println bounds1.intersects(bounds3)
        // end::boundsIntersectsBounds2[]
        writeFile("geometry_bounds_intersects2", "${bounds1.intersects(bounds3)}")
        drawGeometries("geometry_bounds_intersects2", [bounds1.geometry, bounds3.geometry])

        [
                bounds2: bounds1.intersects(bounds2),
                bounds3: bounds1.intersects(bounds3)
        ]
    }

    Bounds boundsIntersection() {
        // tag::boundsIntersection[]
        Bounds bounds1 = new Bounds(-95.885, 46.765, -95.788, 46.811)
        Bounds bounds2 = new Bounds(-95.904, 46.747, -95.839, 46.792)
        Bounds bounds3 = bounds1.intersection(bounds2)
        // end::boundsIntersection[]
        drawGeometries("geometry_bounds_intersection", [bounds1.geometry, bounds2.geometry, bounds3.geometry])
        bounds3
    }

    Geometry boundsGetPolygonGridByColumnsAndRows() {
        // tag::boundsGetPolygonGridByColumnsAndRows[]
        Bounds bounds = new Bounds(-180,-90,180,90,"EPSG:4326")
        Geometry geometry = bounds.getGrid(5,4,"polygon")
        // end::boundsGetPolygonGridByColumnsAndRows[]
        drawGeometries("geometry_bounds_grid_polygon_colsrows", [geometry], drawCoords: false)
        geometry
    }

    Geometry boundsGeneratePointGridByColumnsAndRows() {
        // tag::boundsGeneratePointGridByColumnsAndRows[]
        Bounds bounds = new Bounds(-180,-90,180,90,"EPSG:4326")
        List geometries = []
        Geometry geometry = bounds.generateGrid(10,8,"point") { Geometry g, int col, int row ->
            geometries.add(g)
        }
        // end::boundsGeneratePointGridByColumnsAndRows[]
        drawGeometries("geometry_bounds_grid_point_colsrows", geometries)
        new GeometryCollection(geometries)
    }

    Geometry boundsGetCircleGridByCellWidthAndHeight() {
        // tag::boundsGetCircleGridByCellWidthAndHeight[]
        Bounds bounds = new Bounds(-180,-90,180,90,"EPSG:4326")
        Geometry geometry = bounds.getGrid(72.0,72.0,"circle")
        // end::boundsGetCircleGridByCellWidthAndHeight[]
        drawGeometries("geometry_bounds_grid_circle_cellwidthheight", [geometry], drawCoords: false)
        geometry
    }

    Geometry boundsGetHexagonGridByColumnsAndRows() {
        // tag::boundsGetHexagonGridByColumnsAndRows[]
        Bounds bounds = new Bounds(-180,-90,180,90,"EPSG:4326")
        List geometries = []
        Geometry geometry = bounds.generateGrid(72.0,72.0,"hexagon") { Geometry g, int col, int row ->
            geometries.add(g)
        }
        // end::boundsGetHexagonGridByColumnsAndRows[]
        drawGeometries("geometry_bounds_grid_hexagon_cellwidthheight", geometries, drawCoords: false)
        new GeometryCollection(geometries)
    }

    Geometry boundsGetHexagonInvGridByColumnsAndRows() {
        // tag::boundsGetHexagonInvGridByColumnsAndRows[]
        Bounds bounds = new Bounds(-180,-90,180,90,"EPSG:4326")
        Geometry geometry = bounds.getGrid(5,5,"hexagon-inv")
        // end::boundsGetHexagonInvGridByColumnsAndRows[]
        drawGeometries("geometry_bounds_grid_hexagoninv_colsrows", [geometry], drawCoords: false)
        geometry
    }

    Geometry boundsGetTriangleGridByColumnsAndRows() {
        // tag::boundsGetTriangleGridByColumnsAndRows[]
        Bounds bounds = new Bounds(-180,-90,180,90,"EPSG:4326")
        Geometry geometry = bounds.getGrid(5,5,"triangle")
        // end::boundsGetTriangleGridByColumnsAndRows[]
        drawGeometries("geometry_bounds_grid_triangle_colsrows", [geometry], drawCoords: false)
        geometry
    }

    Polygon boundsCreateRectangle() {
        // tag::boundsCreateRectangle[]
        Bounds bounds = new Bounds(0,0,20,20)
        Polygon polygon = bounds.createRectangle(20,Math.toRadians(45))
        // end::boundsCreateRectangle[]
        drawGeometries("geometry_bounds_createrectangle", [bounds.geometry, polygon])
        polygon
    }

    Polygon boundsCreateEllipse() {
        // tag::boundsCreateEllipse[]
        Bounds bounds = new Bounds(0,0,20,20)
        Polygon polygon = bounds.createEllipse()
        // end::boundsCreateEllipse[]
        drawGeometries("geometry_bounds_createreellipse", [bounds.geometry, polygon])
        polygon
    }

    Polygon boundsCreateSquircle() {
        // tag::boundsCreateSquircle[]
        Bounds bounds = new Bounds(0,0,20,20)
        Polygon polygon = bounds.createSquircle()
        // end::boundsCreateSquircle[]
        drawGeometries("geometry_bounds_createresquircle", [bounds.geometry, polygon])
        polygon
    }

    Polygon boundsCreateSuperCircle() {
        // tag::boundsCreateSuperCircle[]
        Bounds bounds = new Bounds(0,0,20,20)
        Polygon polygon = bounds.createSuperCircle(1.75)
        // end::boundsCreateSuperCircle[]
        drawGeometries("geometry_bounds_createresupercircle", [bounds.geometry, polygon])
        polygon
    }

    LineString boundsCreateArc() {
        // tag::boundsCreateArc[]
        Bounds bounds = new Bounds(0,0,20,20)
        LineString lineString = bounds.createArc(Math.toRadians(45), Math.toRadians(90))
        // end::boundsCreateArc[]
        drawGeometries("geometry_bounds_createrearc", [bounds.geometry, lineString])
        lineString
    }

    Polygon boundsCreateArcPolygon() {
        // tag::boundsCreateArcPolygon[]
        Bounds bounds = new Bounds(0,0,20,20)
        Polygon polygon = bounds.createArcPolygon(Math.toRadians(45), Math.toRadians(90))
        // end::boundsCreateArcPolygon[]
        drawGeometries("geometry_bounds_createrearcpolygon", [bounds.geometry, polygon])
        polygon
    }

    Polygon boundsCreateSineStar() {
        // tag::boundsCreateSineStar[]
        Bounds bounds = new Bounds(0,0,20,20)
        Polygon polygon = bounds.createSineStar(5, 2.3)
        // end::boundsCreateSineStar[]
        drawGeometries("geometry_bounds_createsinestar", [bounds.geometry, polygon])
        polygon
    }

    Polygon boundsCreateHexagon() {
        // tag::boundsCreateHexagon[]
        Bounds bounds = new Bounds(0,0,20,20)
        Polygon polygon = bounds.createHexagon(false)
        // end::boundsCreateHexagon[]
        drawGeometries("geometry_bounds_createhexagon", [bounds.geometry, polygon])
        polygon
    }

}
