package org.emeegeemee.td.shape;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Username: Justin
 * Date: 2/3/2015
 */
public class RectangleShape implements Shape {
    private final Rectangle rectangle;

    public RectangleShape(RectangleShape shape) {
        this(new Rectangle(shape.rectangle));
    }

    public RectangleShape(Vector2 position, Vector2 dimensions) {
        this(position, dimensions.x, dimensions.y);
    }

    public RectangleShape(Vector2 position, float width, float height) {
        this(position.x, position.y, width, height);
    }

    public RectangleShape(float x, float y, float width, float height) {
        this(new Rectangle(x, y, width, height));
    }

    public RectangleShape(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public void set(Vector2 position, float width, float height) {
        rectangle.set(position.x, position.y, width, height);
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(rectangle);
    }
}
