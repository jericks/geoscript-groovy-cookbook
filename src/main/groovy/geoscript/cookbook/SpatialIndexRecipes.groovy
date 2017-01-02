package geoscript.cookbook

import geoscript.geom.Bounds
import geoscript.geom.Geometry
import geoscript.geom.Point
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



}
