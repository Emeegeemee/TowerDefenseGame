package org.emeegeemee.td.util.quadtree;

import com.badlogic.gdx.math.Rectangle;
import org.emeegeemee.td.shape.Shape;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Username: Justin
 * Date: 12/27/2014
 */
public class Leaf<T extends Shape> extends Strategy<T> {
    private static final Strategy[] EMPTY = new Strategy[0];
    private final Set<T> objects;

    public Leaf(Strategy<T> parent, int level, Rectangle bounds) {
        super(parent, level, bounds);
        objects = new HashSet<>();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Strategy<T>[] children() {
        return EMPTY;
    }

    @Override
    public Collection<T> getObjects() {
        return objects;
    }
}