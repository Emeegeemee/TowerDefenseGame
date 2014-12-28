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
public class Leaf<T extends Shape> extends Strategy<T> {
    private Array<T> objects;

    public Leaf(int level, Rectangle bounds) {
        super(level, bounds);

        objects = new Array<>();
    }

    @Override
    public void insert(T object) {
        objects.add(object);
    }

    @Override
    public void retrieve(ObjectSet<T> list, T object) {
        list.addAll(objects);
    }

    @Override
    public int size() {
        return objects.size;
    }

    @Override
    public Array<T> getObjects() {
        return objects;
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        super.draw(renderer);

        for(T object : objects) {
            Rectangle rect = object.getBoundingBox();
            renderer.ellipse(rect.x, rect.y, rect.width, rect.height);
        }
    }
}