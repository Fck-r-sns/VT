package com.vt.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.vt.screens.GameScreen;

public class VTGame extends Game {
    @Override
	public void create () {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        setScreen(new GameScreen());
	}

    @Override
    public void render () {
        super.render();
//        Gdx.app.debug("VTGame", "JavaHeapUsage = " + Gdx.app.getJavaHeap());
//        Gdx.app.debug("VTGame", "NativeHeapUsage = " + Gdx.app.getNativeHeap());
    }
}
