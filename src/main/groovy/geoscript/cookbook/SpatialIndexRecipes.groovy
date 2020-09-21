package geoscript.cookbook

import geoscript.geom.Bounds
import geoscript.geom.Geometry
import geoscript.geom.Point
import geoscript.index.GeoHash
import geoscript.index.HPRtree
import geoscript.index.Quadtree
import geoscript.index.STRtree

class SpatialIndexRecipes extends Recipes {

    STRtree createSTRtree() {

        // tag::createSTRtree_create[]
        STRtree index = new STRtree()
        // end::createSTRtree_create[]

        // tag::createSTRtree_insert[]
        index.insert(new Bounds(0,0,10,10), new Point(5,5))
        index.insert(new Bounds(2,2,6,6), new Point(4,4))
        index.insert(new Bounds(20,20,60,60), new Point(30,30))
        index.insert(new Bounds(22,22,44,44), new Point(32,32))
        // end::createSTRtree_insert[]

        // tag::createSTRtree_size[]
        int size = index.size
        println size
        // end::createSTRtree_size[]
        writeFile("spatialindex_strtree_size", "${size}")

        // tag::createSTRtree_results[]
        List results = index.query(new Bounds(1,1,5,5))
        results.each { Geometry geometry ->
            println geometry
        }
        // end::createSTRtree_results[]
        writeFile("spatialindex_strtree_results", results.join(NEW_LINE))

        index
    }

    Quadtree createQuadtree() {

        // tag::createQuadtree_create[]
        Quadtree index = new Quadtree()
        // end::createQuadtree_create[]

        // tag::createQuadtree_insert[]
        index.insert(new Bounds(0,0,10,10), new Point(5,5))
        index.insert(new Bounds(2,2,6,6), new Point(4,4))
        index.insert(new Bounds(20,20,60,60), new Point(30,30))
        index.insert(new Bounds(22,22,44,44), new Point(32,32))
        // end::createQuadtree_insert[]

        // tag::createQuadtree_size[]
        int size = index.size
        println size
        // end::createQuadtree_size[]
        writeFile("spatialindex_quadtree_size", "${size}")

        // tag::createQuadtree_results[]
        List results = index.query(new Bounds(1,1,5,5))
        results.each { Geometry geometry ->
            println geometry
        }
        // end::createQuadtree_results[]
        writeFile("spatialindex_quadtree_results", results.join(NEW_LINE))

        // tag::createQuadtree_allresults[]
        List allResults = index.queryAll()
        allResults.each { Geometry geometry ->
            println geometry
        }
        // end::createQuadtree_allresults[]
        writeFile("createQuadtree_allresults", results.join(NEW_LINE))

        // tag::createQuadtree_remove[]
        Geometry itemToRemove = allResults[0]
        boolean removed = index.remove(itemToRemove.bounds, itemToRemove)
        println "Removed? ${removed}"
        println "Size = ${index.size}"
        // end::createQuadtree_remove[]
        writeFile("spatialindex_quadtree_remove", "Removed = ${removed}${NEW_LINE}Size = ${index.size}")

        index
    }

    HPRtree createHPRtree() {

        // tag::createHPRtree_create[]
        HPRtree index = new HPRtree()
        // end::createHPRtree_create[]

        // tag::createHPRtree_insert[]
        index.insert(new Bounds(0,0,10,10), new Point(5,5))
        index.insert(new Bounds(2,2,6,6), new Point(4,4))
        index.insert(new Bounds(20,20,60,60), new Point(30,30))
        index.insert(new Bounds(22,22,44,44), new Point(32,32))
        // end::createHPRtree_insert[]

        // tag::createHPRtree_size[]
        int size = index.size
        println size
        // end::createHPRtree_size[]
        writeFile("spatialindex_hprtree_size", "${size}")

        // tag::createHPRtree_results[]
        List results = index.query(new Bounds(1,1,5,5))
        results.each { Geometry geometry ->
            println geometry
        }
        // end::createHPRtree_results[]
        writeFile("spatialindex_hprtree_results", results.join(NEW_LINE))

        index
    }

    String geohashEncodePoint() {
        // tag::geohashEncodePoint[]
        GeoHash geohash = new GeoHash()
        Point point = new Point(112.5584, 37.8324)
        String hash = geohash.encode(point)
        println hash
        // end::geohashEncodePoint[]
        writeFile("spatialindex_geohash_encodepoints", "${hash}")
        hash
    }

    Point geohashDecodeString() {
        // tag::geohashDecodeString[]
        GeoHash geohash = new GeoHash()
        Point point = geohash.decode("ww8p1r4t8")
        println point
        // end::geohashDecodeString[]
        writeFile("spatialindex_geohash_decodestring", "${point.wkt}")
        point
    }

    long geohashEncodePointAsLong() {
        // tag::geohashEncodePointAsLong[]
        GeoHash geohash = new GeoHash()
        Point point = new Point(112.5584, 37.8324)
        long hash = geohash.encodeLong(point)
        println long
        // end::geohashEncodePointAsLong[]
        writeFile("spatialindex_geohash_encodepoints_long", "${hash}")
        hash
    }

    Point geohashDecodeLong() {
        // tag::geohashDecodeLong[]
        GeoHash geohash = new GeoHash()
        Point point = geohash.decode(4064984913515641)
        println point
        // end::geohashDecodeLong[]
        writeFile("spatialindex_geohash_decodelong", "${point.wkt}")
        point
    }

    Bounds geohashDecodeBoundsFromString() {
        // tag::geohashDecodeBoundsFromString[]
        GeoHash geohash = new GeoHash()
        Bounds bounds = geohash.decodeBounds("ww8p1r4t8")
        println bounds
        // end::geohashDecodeBoundsFromString[]
        writeFile("spatialindex_geohash_decode_bounds_string", "${bounds}")
        bounds
    }

    Bounds geohashDecodeBoundsFromLong() {
        // tag::geohashDecodeBoundsFromLong[]
        GeoHash geohash = new GeoHash()
        Bounds bounds = geohash.decodeBounds(4064984913515641)
        println bounds
        // end::geohashDecodeBoundsFromLong[]
        writeFile("spatialindex_geohash_decode_bounds_string", "${bounds}")
        bounds
    }

    String geohashNeighborFromString() {
        // tag::geohashNeighborFromString[]
        GeoHash geohash = new GeoHash()
        String hash = "dqcjq"
        String north     = geohash.neighbor(hash, GeoHash.Direction.NORTH)
        String northwest = geohash.neighbor(hash, GeoHash.Direction.NORTHWEST)
        String west      = geohash.neighbor(hash, GeoHash.Direction.WEST)
        String southwest = geohash.neighbor(hash, GeoHash.Direction.SOUTHWEST)
        String south     = geohash.neighbor(hash, GeoHash.Direction.SOUTH)
        String southeast = geohash.neighbor(hash, GeoHash.Direction.SOUTHEAST)
        String east      = geohash.neighbor(hash, GeoHash.Direction.EAST)
        String northeast = geohash.neighbor(hash, GeoHash.Direction.NORTHEAST)
        String str = """
                     | ${northwest} ${north} ${northeast}
                     | ${west} ${hash} ${east}
                     | ${southwest} ${south} ${southeast}
                     |""".stripMargin()
        println str
        // end::geohashNeighborFromString[]
        writeFile("spatialindex_geohash_neighbor_from_string", "${str}")
        str
    }

    String geohashNeighborFromLong() {
        // tag::geohashNeighborFromLong[]
        GeoHash geohash = new GeoHash()
        long hash = 1702789509
        long north     = geohash.neighbor(hash, GeoHash.Direction.NORTH)
        long northwest = geohash.neighbor(hash, GeoHash.Direction.NORTHWEST)
        long west      = geohash.neighbor(hash, GeoHash.Direction.WEST)
        long southwest = geohash.neighbor(hash, GeoHash.Direction.SOUTHWEST)
        long south     = geohash.neighbor(hash, GeoHash.Direction.SOUTH)
        long southeast = geohash.neighbor(hash, GeoHash.Direction.SOUTHEAST)
        long east      = geohash.neighbor(hash, GeoHash.Direction.EAST)
        long northeast = geohash.neighbor(hash, GeoHash.Direction.NORTHEAST)
        String str = """
                     | ${northwest} ${north} ${northeast}
                     | ${west} ${hash} ${east}
                     | ${southwest} ${south} ${southeast}
                     |""".stripMargin()
        println str
        // end::geohashNeighborFromLong[]
        writeFile("spatialindex_geohash_neighbor_from_long", "${str}")
        str
    }

    String geohashNeighborsFromString() {
        // tag::geohashNeighborsFromString[]
        GeoHash geohash = new GeoHash()
        String hash = "dqcjq"
        Map neighbors = geohash.neighbors(hash)
        String north     = neighbors[GeoHash.Direction.NORTH]
        String northwest = neighbors[GeoHash.Direction.NORTHWEST]
        String west      = neighbors[GeoHash.Direction.WEST]
        String southwest = neighbors[GeoHash.Direction.SOUTHWEST]
        String south     = neighbors[GeoHash.Direction.SOUTH]
        String southeast = neighbors[GeoHash.Direction.SOUTHEAST]
        String east      = neighbors[GeoHash.Direction.EAST]
        String northeast = neighbors[GeoHash.Direction.NORTHEAST]
        String str = """
                     | ${northwest} ${north} ${northeast}
                     | ${west} ${hash} ${east}
                     | ${southwest} ${south} ${southeast}
                     |""".stripMargin()
        println str
        // end::geohashNeighborsFromString[]
        writeFile("spatialindex_geohash_neighbors_from_string", "${str}")
        str
    }

    String geohashNeighborsFromLong() {
        // tag::geohashNeighborsFromLong[]
        GeoHash geohash = new GeoHash()
        long hash = 1702789509
        Map neighbors = geohash.neighbors(hash)
        long north     = neighbors[GeoHash.Direction.NORTH]
        long northwest = neighbors[GeoHash.Direction.NORTHWEST]
        long west      = neighbors[GeoHash.Direction.WEST]
        long southwest = neighbors[GeoHash.Direction.SOUTHWEST]
        long south     = neighbors[GeoHash.Direction.SOUTH]
        long southeast = neighbors[GeoHash.Direction.SOUTHEAST]
        long east      = neighbors[GeoHash.Direction.EAST]
        long northeast = neighbors[GeoHash.Direction.NORTHEAST]
        String str = """
                     | ${northwest} ${north} ${northeast}
                     | ${west} ${hash} ${east}
                     | ${southwest} ${south} ${southeast}
                     |""".stripMargin()
        println str
        // end::geohashNeighborsFromLong[]
        writeFile("spatialindex_geohash_neighbors_from_long", "${str}")
        str
    }

    List geohashBboxesAsStrings() {
        // tag::geohashBboxesAsStrings[]
        GeoHash geohash = new GeoHash()
        List<String> bboxes = geohash.bboxes(new Bounds(120, 30, 120.0001, 30.0001), 8)
        bboxes.each { String hash ->
            println hash
        }
        // end::geohashBboxesAsStrings[]
        writeFile("spatialindex_geohash_bboxes_as_strings", bboxes.join(NEW_LINE))
        bboxes
    }

    List geohashBboxesAsLongs() {
        // tag::geohashBboxesAsLongs[]
        GeoHash geohash = new GeoHash()
        List<Long> bboxes = geohash.bboxesLong(new Bounds(120, 30, 120.0001, 30.0001), 40)
        bboxes.each { long hash ->
            println hash
        }
        // end::geohashBboxesAsLongs[]
        writeFile("spatialindex_geohash_bboxes_as_longs", bboxes.join(NEW_LINE))
        bboxes
    }
}
