package org.emeegeemee.td;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Username: Justin
 * Date: 1/4/2015
 */
public class Settings {
    private static final String RESOLUTION_WIDTH = "screenWidth";
    private static final String RESOLUTION_HEIGHT = "screenHeight";
    private static final String FULLSCREEN = "fullscreen";

    private Preferences preferences;

    public Settings(Preferences preferences) {
        this.preferences = preferences;
    }

    public void setResolutionWidth(int width) {
        preferences.putInteger(RESOLUTION_WIDTH, width);
    }

    public int getResolutionWidth() {
        return preferences.getInteger(RESOLUTION_WIDTH, Gdx.graphics.getDesktopDisplayMode().width);
    }

    public void setResolutionHeight(int height) {
        preferences.putInteger(RESOLUTION_HEIGHT, height);
    }

    public int getResolutionHeight() {
        return preferences.getInteger(RESOLUTION_HEIGHT, Gdx.graphics.getDesktopDisplayMode().height);
    }

    public void setFullscreen(boolean fullscreen) {
        preferences.putBoolean(FULLSCREEN, fullscreen);
    }

    public boolean getFullscreen() {
        return preferences.getBoolean(FULLSCREEN, true);
    }

    public void flush() {
        preferences.flush();
    }
}
