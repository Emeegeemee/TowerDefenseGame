package org.emeegeemee.td.path;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

/**
 * Username: Justin
 * Date: 12/22/2014
 */
public class Path implements Iterable<Vector2> {
    private Array<Vector2> points;
    private float width;

    public Path() {
        this(new Array<>());
    }

    public Path(Array<Vector2> points) {
        this(points, 5f);
    }

    public Path(Array<Vector2> points, float width) {
        this.points = new Array<>();
        for(Vector2 v : points) {
            this.points.add(v.cpy());
        }

        this.width = width;
    }

    public int getNumberOfWaypoints() {
        return points.size;
    }

    /**
     * adds a copy of the given point to the path
     * @param point the new point to be added to the end of the path
     */
    public void addWaypoint(Vector2 point) {
        points.add(point.cpy());
    }

    /**
     * adds a copy of the given point to the path
     * @param point the new point to be added to the path
     * @param index the index to insert the point to
     */
    public void addWaypoint(Vector2 point, int index) {
        if(index > points.size) {
            addWaypoint(point);
        }
        else {
            points.insert(index, point.cpy());
        }
    }

    /**
     * returns a copy of the closest waypoint to the given point
     * @param point the point to compare to
     * @param copy whether the returned vector is a copy
     * @return the closest waypoint
     */
    public Vector2 getClosestWaypoint(Vector2 point, boolean copy) {
        Vector2 closest = points.get(getClosestWaypointIndex(point));
        return copy ? closest.cpy() : closest;
    }

    public int getClosestWaypointIndex(Vector2 point) {
        if(points.size == 0) {
            throw new IllegalStateException("The path needs at least one point in it");
        }

        double distSq = Double.POSITIVE_INFINITY;
        int index = -1;

        for(int i = 0; i < points.size; i++) {
            Vector2 v = points.get(i);
            double dst = v.dst2(point);

            if(dst < distSq) {
                index = i;
            }
        }

        return index;
    }

    /**
     * Determines if the given circle intersects the path assumes the path
     * @param center center point of the circle
     * @param radius radius of the circle
     * @return whether the circle intersects the path
     */
    public boolean circleIntersect(Vector2 center, float radius) {
        radius += width;
        float radiusSq = radius * radius;

        if(points.size == 0)
            return false;
        else if(points.size == 1) {
            return center.dst2(points.first()) < radiusSq;
        }

        Vector2 point = new Vector2();
        for(int index = 1; index < points.size; index++) {
            Vector2 begin = points.get(index - 1);
            Vector2 end = points.get(index);

            Intersector.nearestSegmentPoint(begin, end, center, point);

            if(point.dst2(center) < radiusSq) {
                return true;
            }
        }

        return false;
    }

    public Vector2 first() {
        return points.first();
    }

    @Override
    public Iterator<Vector2> iterator() {
        return points.iterator();
    }
}
