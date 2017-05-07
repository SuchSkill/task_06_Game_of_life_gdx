package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.GameOfLife;

import java.net.URISyntaxException;

public class DesktopLauncher {
	public static void main (String[] arg) throws URISyntaxException {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		new LwjglApplication(new GameOfLife(), config);
	}
}
