package org.emeegeemee.td.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.emeegeemee.td.Settings;

import java.util.Arrays;

/**
 * Username: Justin
 * Date: 1/4/2015
 */
public class SettingsScreen extends ScreenAdapter {
    private Stage stage;
    private Skin skin;

    private Settings settings;

    public SettingsScreen(Settings settings) {
        this.settings = settings;

        stage = new Stage(new FitViewport(960, 540));
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas("uiskin.atlas"));

        VerticalGroup verticalGroup = new VerticalGroup().space(10f);
        verticalGroup.setFillParent(true);
        stage.addActor(verticalGroup);

        Label label = new Label("Settings", skin);
        label.setFontScale(1.5f);
        verticalGroup.addActor(label);

        Table table = new Table(skin);
        verticalGroup.addActor(table);

        List<Graphics.DisplayMode> list = new List<>(skin);
        list.setItems(Arrays.stream(Gdx.graphics.getDisplayModes()).filter(this::refreshFilter).sorted(SettingsScreen::compare).toArray(Graphics.DisplayMode[]::new));
        list.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Graphics.DisplayMode selection = list.getSelection().first();
                settings.setResolutionWidth(selection.width);
                settings.setResolutionHeight(selection.height);
            }
        });
        int i = 0;
        for(Graphics.DisplayMode mode : list.getItems()) {
            if(mode.height == settings.getResolutionHeight() && mode.width == settings.getResolutionWidth()) {
                break;
            }
            i++;
        }
        list.setSelectedIndex(i);

        ScrollPane pane = new ScrollPane(list);
        table.add(pane).width(200f).height(300f).colspan(2);
        table.row();

        CheckBox checkBox = new CheckBox("Fullscreen", skin);
        checkBox.setChecked(settings.getFullscreen());
        checkBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                settings.setFullscreen(checkBox.isChecked());
            }
        });
        table.add(checkBox);
        table.row();

        Button button = new Button(skin);
        button.add("Apply");
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                settings.flush();
                Gdx.graphics.setDisplayMode(settings.getResolutionWidth(), settings.getResolutionHeight(), settings.getFullscreen());
            }
        });
        table.add(button);

        button = new Button(skin);
        button.add("Quit");
        //TODO setup a listener to take you back to the main menu
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        table.add(button);
        table.row();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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

    private boolean refreshFilter(Graphics.DisplayMode mode) {
        return mode.refreshRate == Gdx.graphics.getDesktopDisplayMode().refreshRate;
    }

    private static int compare(Graphics.DisplayMode o1, Graphics.DisplayMode o2) {
        if(o1.width < o2.width)
            return -1;
        else if(o1.width > o2.width)
            return 1;
        else if(o1.height < o2.height)
            return -1;
        else if(o1.height > o2.height)
            return 1;
        else if(o1.refreshRate < o2.refreshRate)
            return -1;
        else if(o1.refreshRate > o2.refreshRate)
            return 1;
        return 0;
    }
}
