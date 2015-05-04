package com.vt.game;

import com.badlogic.gdx.Game;
import com.vt.screens.GameScreen;

public class VTGame extends Game {
    @Override
	public void create () {
        setScreen(new GameScreen());
	}
}
