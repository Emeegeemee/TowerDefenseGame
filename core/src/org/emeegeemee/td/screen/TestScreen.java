package org.emeegeemee.td.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Username: Justin
 * Date: 12/27/2014
 */
public class TestScreen extends ScreenAdapter {
    private Stage stage;
    private Skin skin;

    private Texture image = new Texture("badlogic.jpg");

    private SpriteBatch batch;

    public TestScreen() {
        batch = new SpriteBatch();

        stage = new Stage(new FitViewport(960, 540), batch);
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas("uiskin.atlas"));

        Table table = new Table(skin);
        //table.setFillParent(true);
        table.setSize(200, 150);
        table.setPosition(50, 50);
        stage.addActor(table);

        final TextButton button = new TextButton("Click me!", skin);
        table.add(button);
        table.row();

        CheckBox box = new CheckBox("checkbox", skin);
        table.add(box);
        table.row();

        table.add("Hello 1");
        table.row();
        table.debug();

        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Clicked! Is checked: " + button.isChecked());
                button.setText("Good job!");
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Camera camera = stage.getViewport().getCamera();
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        //batch.setTransformMatrix(camera.combined);

        batch.begin();
        batch.draw(image, 0f, 0f);
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();
    }
}
