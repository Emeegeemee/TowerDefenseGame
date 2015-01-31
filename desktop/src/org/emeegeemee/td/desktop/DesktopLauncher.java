package org.emeegeemee.td.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.emeegeemee.td.TowerDefense;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = 960;
		config.height = 540;
		config.resizable = false;


		//System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");

		new LwjglApplication(new TowerDefense(), config);
	}
}
