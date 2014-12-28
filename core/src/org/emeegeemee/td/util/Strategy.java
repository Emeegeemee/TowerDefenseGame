package org.emeegeemee.td.util;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import org.emeegeemee.td.shape.Shape;

/**
 * Username: Justin
 * Date: 12/27/2014
 */
public abstract class Strategy<T extends Shape> {
    private final int level;
    private final Rectangle bounds;

    public Strategy(int level, Rectangle bounds) {
        this.level = level;
        this.bounds = bounds;
    }

    public abstract void insert(T object);
    public abstract void retrieve(ObjectSet<T> list, T object);
    public abstract int size();

    public int getLevel() {
        return level;
    }

    public abstract Array<T> getObjects();

    public Rectangle getBounds() {
        return bounds;
    }

    public void draw(ShapeRenderer renderer) {
        renderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
    }
}
