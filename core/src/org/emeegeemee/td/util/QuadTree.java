package org.emeegeemee.td.util;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.emeegeemee.td.shape.Shape;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Set;

/**
 * Username: Justin
 * Date: 12/22/2014
 */
public class QuadTree<T extends Shape> {
    private static final int QUADRANTS = 4;
    private final boolean[] indexHelper = new boolean[QUADRANTS]; //create the boolean array once to save on memory allocation/garbage collection

    private int maxObjects, maxLevels;
    private Strategy<T> strategy;

    public QuadTree(Rectangle bounds) {
        this(bounds, 10, 5);
    }

    public QuadTree(Rectangle bounds, int maxObjects, int maxLevels) {
        strategy = new Leaf<>(null, 0, bounds);
        this.maxObjects = maxObjects;
        this.maxLevels = maxLevels;
    }

    public void clear() {
        strategy = new Leaf<>(null, 0, strategy.getBounds());
    }

    @SuppressWarnings("unchecked")
    private void split(Strategy<T> leaf, int index) {
        Strategy<T>[] children = new Strategy[QUADRANTS];
        Strategy<T> node = new Node<>(leaf.parent(), leaf.getLevel(), leaf.getBounds(), children);

        Rectangle bounds = node.getBounds();
        int level = node.getLevel();

        float x = bounds.x;
        float y = bounds.y;

        float subWidth = bounds.width / 2f;
        float subHeight = bounds.height / 2f;

        children[0] = new Leaf<>(node, level + 1, new Rectangle(x, y, subWidth, subHeight));
        children[1] = new Leaf<>(node, level + 1, new Rectangle(x + subWidth, y, subWidth, subHeight));
        children[2] = new Leaf<>(node, level + 1, new Rectangle(x, y + subHeight, subWidth, subHeight));
        children[3] = new Leaf<>(node, level + 1, new Rectangle(x + subWidth, y + subHeight, subWidth, subHeight));

        if(node.parent() == null) {
            strategy = node;
        }
        else {
            leaf.parent().children()[index] = node;
        }

        leaf.getObjects().forEach((T object) -> insert(node, object));
    }

    private boolean[] getIndex(Strategy<T> strategy, T object) {
        Rectangle bounds = strategy.getBounds();
        boolean[] quads = indexHelper;

        Rectangle pRect = object.getBoundingBox();

        float xMid = bounds.x + bounds.width / 2f;
        float yMid = bounds.y + bounds.height / 2f;

        float top = pRect.y + pRect.height;
        float right = pRect.x + pRect.width;

        boolean belowMid = pRect.y <= yMid; //bot is below mid
        boolean aboveMid = top > yMid; //top is above mid

        boolean leftMid = pRect.x <= xMid; //left is left of mid
        boolean rightMid = right > xMid; //right is right of mid

        quads[0] = (belowMid && leftMid); //bot left
        quads[1] = (belowMid && rightMid); //bot right
        quads[2] = (aboveMid && leftMid); //top left
        quads[3] = (aboveMid && rightMid); //top right

        return quads;
    }

    public void insert(T object) {
        insert(strategy, object);
    }

    private void insert(Strategy<T> strategy, T object) {
        Deque<Pair<Integer, Strategy<T>>> stack = new ArrayDeque<>();
        stack.addFirst(new ImmutablePair<>(-1, strategy));

        while(!stack.isEmpty()) {
            Pair<Integer, Strategy<T>> pair = stack.removeFirst();
            Strategy<T> cur = pair.getRight();
            Strategy<T>[] children = cur.children();

            if(children.length > 0) {//node
                boolean[] index = getIndex(cur, object);
                for(int i = 0; i < index.length; i++) {
                    if(index[i]) {
                        stack.addFirst(new ImmutablePair<>(i, children[i]));
                    }
                }
            }
            else {//leaf
                Collection<T> objects = cur.getObjects();
                objects.add(object);
                if(objects.size() > maxObjects && cur.getLevel() < maxLevels) {
                    split(cur, pair.getLeft());
                }
            }
        }
    }

    public void retrieve(Set<T> returnList, T object) {
        Deque<Strategy<T>> stack = new ArrayDeque<>();
        stack.addFirst(strategy);

        while(!stack.isEmpty()) {
            Strategy<T> cur = stack.removeFirst();
            Strategy<T>[] children = cur.children();

            if(children.length > 0) {//node
                boolean[] index = getIndex(cur, object);
                for(int i = 0; i < children.length; i++) {
                    if(index[i]) {
                        stack.addFirst(children[i]);
                    }
                }
            }
            else {//leaf
                returnList.addAll(cur.getObjects());
            }
        }
    }

    public void draw(ShapeRenderer renderer) {
        Deque<Strategy<T>> stack = new ArrayDeque<>();
        stack.addFirst(strategy);

        while(!stack.isEmpty()) {
            Strategy<T> cur = stack.removeFirst();
            Strategy<T>[] children = cur.children();

            Rectangle bounds = cur.getBounds();
            renderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);

            for(Strategy<T> child : children) {
                stack.addFirst(child);
            }

            for(T object : cur.getObjects()) {
                Rectangle rect = object.getBoundingBox();
                renderer.ellipse(rect.x, rect.y, rect.width, rect.height);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Quadtree:" + System.lineSeparator());
        Deque<Strategy<T>> stack = new ArrayDeque<>();
        stack.addFirst(strategy);
        while(!stack.isEmpty()) {
            Strategy<T> cur = stack.removeFirst();
            builder.append(cur.toString());
            for(Strategy<T> child : cur.children()) {
                stack.addFirst(child);
            }
        }
        return builder.toString();
    }
}
