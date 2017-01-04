package geoscript.cookbook

import geoscript.geom.Bounds
import geoscript.geom.Geometry
import geoscript.geom.Point
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


}
