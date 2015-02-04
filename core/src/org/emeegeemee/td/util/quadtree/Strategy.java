package org.emeegeemee.td.util.quadtree;

import com.badlogic.gdx.math.Rectangle;
import org.emeegeemee.td.shape.Shape;

import java.util.Collection;

/**
 * Username: Justin
 * Date: 12/27/2014
 */
public abstract class Strategy<T extends Shape> {
    private final Strategy<T> parent;
    private final int level;
    private final Rectangle bounds;

    public Strategy(Strategy<T> parent, int level, Rectangle bounds) {
        this.parent = parent;
        this.level = level;
        this.bounds = bounds;
    }

    public abstract Strategy<T>[] children();
    public abstract Collection<T> getObjects();

    public Strategy<T> parent() {
        return parent;
    }

    public int getLevel() {
        return level;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}