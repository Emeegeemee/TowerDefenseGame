package org.emeegeemee.td.shape;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Username: Justin
 * Date: 12/27/2014
 */
public class CircleShape implements Shape {
    private Circle circle;

    public CircleShape(CircleShape cs) {
        circle = new Circle(cs.circle);
    }

    public CircleShape(Vector2 position, float radius) {
        this(position.x, position.y, radius);
    }

    public CircleShape(float x, float y, float radius) {
        circle = new Circle(x, y, radius);
    }

    public CircleShape(Circle c) {
        circle = c;
    }

    public void set(Vector2 position, float radius) {
        circle.x = position.x;
        circle.y = position.y;
        circle.radius = radius;
    }

    public boolean intersect(CircleShape c) {
        return circle.overlaps(c.circle);
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(circle.x - circle.radius, circle.y - circle.radius, 2f * circle.radius, 2f * circle.radius);
    }
}
