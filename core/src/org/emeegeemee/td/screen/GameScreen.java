package org.emeegeemee.td.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.emeegeemee.td.path.Path;
import org.emeegeemee.td.shape.CircleShape;
import org.emeegeemee.td.util.QuadTree;

import java.util.HashSet;
import java.util.Set;

/**
 * Username: Justin
 * Date: 12/22/2014
 */
public class GameScreen implements Screen {
    ShapeRenderer renderer;
    SpriteBatch batch;
    Path path;
    QuadTree<CircleShape> circles;

    public GameScreen() {

        batch = new SpriteBatch();
        renderer = new ShapeRenderer();

        path = new Path();
        circles = new QuadTree<>(new Rectangle(0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        for(int i = 0; i < MathUtils.random(5, 10); i++) {
            path.addWaypoint(new Vector2(MathUtils.random(50f, 910f), MathUtils.random(50f, 490f)));
        }
    }

    Vector2 mousePos = new Vector2();
    float radius = 25f;
    CircleShape newCircle = new CircleShape(mousePos, 20f);
    Set<CircleShape> set = new HashSet<>();
    int numCircles = 0;

    @Override
    public void render(float delta) {
        mousePos.set(Gdx.input.getX(), -Gdx.input.getY() + Gdx.graphics.getHeight());
        newCircle.set(mousePos, 20f);

        boolean intersect = path.circleIntersect(mousePos, radius);

        if(!intersect) {
            circles.retrieve(set, newCircle);

            for(CircleShape cs : set) {
                intersect = newCircle.intersect(cs);
                if(intersect) {
                    break;
                }
            }

            set.clear();
        }

        if(Gdx.input.isTouched() && !intersect) {
            numCircles++;
            circles.insert(new CircleShape(newCircle));
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Vector2 prev = path.first();

        if(intersect)
            renderer.setColor(Color.RED);
        else
            renderer.setColor(Color.GREEN);
        renderer.begin(ShapeRenderer.ShapeType.Line);

        renderer.circle(mousePos.x, mousePos.y, 20f);

        renderer.setColor(Color.WHITE);
        for(Vector2 point : path) {
            renderer.line(prev, point);
            prev = point;
        }

        circles.draw(renderer);

        renderer.end();
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        for(Vector2 point : path) {
            renderer.circle(point.x, point.y, 5f);
        }

        renderer.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        renderer.dispose();
    }
}
