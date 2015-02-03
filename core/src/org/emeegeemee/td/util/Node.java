package org.emeegeemee.td.util;

import com.badlogic.gdx.math.Rectangle;
import org.emeegeemee.td.shape.Shape;

import java.util.Collection;
import java.util.Collections;

/**
 * Username: Justin
 * Date: 12/27/2014
 */
public class Node<T extends Shape> extends Strategy<T> {
    private final Strategy<T>[] children;

    public Node(Strategy<T> parent, int level, Rectangle bounds, Strategy<T>[] children) {
        super(parent, level, bounds);
        this.children = children;
    }

    @Override
    public Strategy<T>[] children() {
        return children;
    }

    @Override
    public Collection<T> getObjects() {
        return Collections.emptySet();
    }

    @Override
    public String toString() {
        return String.format("Node: %s", super.toString());
    }
}