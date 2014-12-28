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
public class Node<T extends Shape> extends Strategy<T> {
    private static final int MAX_OBJECTS = 10;
    private static final int MAX_LEVEL = 5;

    private static final int QUADRANTS = 4;
    private final boolean[] indexHelper = new boolean[QUADRANTS]; //create the boolean array once to save on memory allocation/garbage collection

    private final Strategy<T>[] children;

    @SuppressWarnings("unchecked")
    public Node(int level, Rectangle bounds) {
        super(level, bounds);

        children = new Strategy[QUADRANTS];
        split();
    }

    public Node(int level, Rectangle bounds, Array<T> objects) {
        this(level, bounds);

        for(T t : objects) {
            insert(t);
        }
    }

    private void split() {
        Rectangle bounds = getBounds();
        int level = getLevel();

        float x = bounds.x;
        float y = bounds.y;

        float subWidth = bounds.width / 2f;
        float subHeight = bounds.height / 2f;

        children[0] = new Leaf<>(level + 1, new Rectangle(x, y, subWidth, subHeight));
        children[1] = new Leaf<>(level + 1, new Rectangle(x + subWidth, y, subWidth, subHeight));
        children[2] = new Leaf<>(level + 1, new Rectangle(x, y + subHeight, subWidth, subHeight));
        children[3] = new Leaf<>(level + 1, new Rectangle(x + subWidth, y + subHeight, subWidth, subHeight));
    }

    private boolean[] getIndex(T object) {
        Rectangle bounds = getBounds();
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

    @Override
    public void insert(T object) {
        boolean[] index = getIndex(object);
        for(int i = 0; i < QUADRANTS; i++) {
            if(index[i]) {
                Strategy<T> child = children[i];
                child.insert(object);

                if(child.size() > MAX_OBJECTS && child.getLevel() < MAX_LEVEL) {
                    children[i] = new Node<>(child.getLevel(), child.getBounds(), child.getObjects());
                }
            }
        }
    }

    @Override
    public void retrieve(ObjectSet<T> list, T object) {
        boolean[] index = getIndex(object);
        for(int i = 0; i < QUADRANTS; i++) {
            if(index[i]) {
                children[i].retrieve(list, object);
            }
        }
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Array<T> getObjects() {
        return new Array<>(0);
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        super.draw(renderer);

        for(Strategy<T> strategy : children) {
            strategy.draw(renderer);
        }
    }
}