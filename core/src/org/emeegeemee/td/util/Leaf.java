package org.emeegeemee.td.util;

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
    private Set<T> objects;

    public Leaf(Strategy<T> parent, int level, Rectangle bounds) {
        super(parent, level, bounds);
        objects = new HashSet<>();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Strategy<T>[] children() {
        return new Strategy[0];
    }

    @Override
    public Collection<T> getObjects() {
        return objects;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(String.format("Leaf: %s", super.toString()));
        for(T object : objects) {
            builder.append(String.format("\t%s%n", object.getBoundingBox()));
        }
        return builder.toString();
    }
}