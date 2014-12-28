package org.emeegeemee.td;

import com.badlogic.gdx.Game;
import org.emeegeemee.td.screen.GameScreen;

public class TowerDefense extends Game {
	@Override
	public void create () {
		this.setScreen(new GameScreen());
	}
}
