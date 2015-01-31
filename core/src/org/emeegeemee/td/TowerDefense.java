package org.emeegeemee.td;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import org.emeegeemee.td.screen.GameScreen;

public class TowerDefense extends Game {
	private Settings settings;

	@Override
	public void create () {
		settings = new Settings(Gdx.app.getPreferences("settings"));
		Gdx.graphics.setDisplayMode(settings.getResolutionWidth(), settings.getResolutionHeight(), settings.getFullscreen());
		setScreen(new GameScreen());//(settings));
	}
}
