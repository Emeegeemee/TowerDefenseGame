package org.emeegeemee.td.util;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ObjectSet;
import org.emeegeemee.td.shape.Shape;

/**
 * Username: Justin
 * Date: 12/22/2014
 */
public class QuadTree<T extends Shape> {
    private Strategy<T> strategy;

    public QuadTree(Rectangle bounds) {
        strategy = new Node<>(0, bounds);
    }

    public void clear() {
        strategy = new Node<>(0, strategy.getBounds());
    }

    public void insert(T object) {
        strategy.insert(object);
    }

    public void retrieve(ObjectSet<T> returnList, T object) {
        strategy.retrieve(returnList, object);
    }

    public void draw(ShapeRenderer renderer) {
        strategy.draw(renderer);
    }
}
