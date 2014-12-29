package org.emeegeemee.td;

import com.badlogic.gdx.Game;
import org.emeegeemee.td.screen.TestScreen;

public class TowerDefense extends Game {
	@Override
	public void create () {
		setScreen(new TestScreen());
	}
}
