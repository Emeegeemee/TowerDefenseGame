package org.emeegeemee.td.util;

import com.badlogic.gdx.math.Rectangle;
import org.emeegeemee.td.shape.Shape;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * Username: Justin
 * Date: 12/27/2014
 */
public class Node<T extends Shape> extends Strategy<T> {
    private static final Set EMPTY_SET = Collections.emptySet();
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
    @SuppressWarnings("unchecked")
    public Collection<T> getObjects() {
        return EMPTY_SET;
    }
}