package org.emeegeemee.td.util;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import org.emeegeemee.td.shape.Shape;

/**
 * Username: Justin
 * Date: 12/22/2014
 */
public class QuadTree<T extends Shape> {
    private static final int MAX_OBJECTS = 10;
    private static final int MAX_LEVELS = 5;

    private int level;
    private Rectangle bounds;
    private QuadTree<T>[] nodes;
    private Array<T> objects;
    private final boolean[] indexHelper;

    public QuadTree(Rectangle bounds) {
        this( 0, bounds);
    }

    @SuppressWarnings("unchecked")
    private QuadTree(int level, Rectangle bounds) {
        //System.out.println(level + ": " + bounds);
        this.level = level;
        this.bounds = bounds;
        this.getClass();
        nodes = new QuadTree[4];
        indexHelper = new boolean[4];
        objects = new Array<>();
    }

    public void clear() {
        objects.clear();

        for(int i = 0; i < nodes.length; i++) {
            if(nodes[i] != null) {
                nodes[i].clear();
                nodes[i] = null;
            }
        }
    }

    private void split() {
        float x = bounds.x;
        float y = bounds.y;

        float subWidth = bounds.width / 2f;
        float subHeight = bounds.height / 2f;

        nodes[0] = new QuadTree<>(level + 1, new Rectangle(x, y, subWidth, subHeight));
        nodes[1] = new QuadTree<>(level + 1, new Rectangle(x + subWidth, y, subWidth, subHeight));
        nodes[2] = new QuadTree<>(level + 1, new Rectangle(x, y + subHeight, subWidth, subHeight));
        nodes[3] = new QuadTree<>(level + 1, new Rectangle(x + subWidth, y + subHeight, subWidth, subHeight));
    }

    private boolean[] getIndex(T object) {
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
        if(nodes[0] != null) { //if there are smaller squares
            boolean[] index = getIndex(object);

            for(int i = 0; i < index.length; i++) {
                if(index[i]) {
                    nodes[i].insert(object);
                }
            }
        }
        else { //currently a leaf node
            objects.add(object);

            if(objects.size > MAX_OBJECTS && level < MAX_LEVELS) { //too many elements here and can expand
                split();
                for(T t : objects) {
                    boolean[] index = getIndex(t);
                    for(int i = 0; i < index.length; i++) {
                        if(index[i]) {
                            nodes[i].insert(t);
                        }
                    }
                }

                objects.clear();
            }
        }
    }

    public void retrieve(ObjectSet<T> returnList, T object) {
        if(nodes[0] != null) {
            boolean[] index = getIndex(object);

            for(int i =0; i < index.length; i++) {
                if(index[i]) {
                    nodes[i].retrieve(returnList, object);
                }
            }
        }
        else {
            returnList.addAll(objects);
        }
    }

    public void draw(ShapeRenderer renderer) {
        renderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);

        if(nodes[0] != null) {
            for(QuadTree<T> quadTree : nodes) {
                quadTree.draw(renderer);
            }
        }
        else {
            for(T t : objects) {
                Rectangle rectangle = t.getBoundingBox();
                renderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
                renderer.ellipse(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
            }
        }
    }

    public static void main(String... args) {
        QuadTree<Rect> quadTree = new QuadTree<>(new Rectangle(0, 0, 400, 400));
        ObjectSet<Rect> list = new ObjectSet<>();

        Rect shape = new Rect(new Rectangle(20, 20, 50, 50));

        quadTree.retrieve(list, shape);

        print(list);
        list.clear();

        quadTree.insert(new Rect(new Rectangle(180, 180, 40, 40)));
        quadTree.retrieve(list, shape);

        print(list);
        list.clear();

        quadTree.insert(new Rect(new Rectangle(50, 50, 40, 40)));
        quadTree.retrieve(list, shape);

        print(list);
        list.clear();

        quadTree.retrieve(list, new Rect(new Rectangle(190, 190, 20, 20)));

        print(list);
        list.clear();
    }

    public static void print(ObjectSet<? extends Shape> list) {
        System.out.println("list: ");
        for(Shape s : list) {
            System.out.println(s);
        }
        System.out.println();
    }

    public static class Rect implements Shape {
        private Rectangle rectangle;
        public Rect(Rectangle rectangle) {
            this.rectangle = rectangle;
        }

        @Override
        public Rectangle getBoundingBox() {
            return rectangle;
        }

        public String toString() {
            return "Rect: " + rectangle.toString();
        }
    }
}
