package com.vt.game;

import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by Fck.r.sns on 08.07.2015.
 */
public class Environment {
    private static Environment instance = null;
    public boolean debugDrawings = false;
    public float globalTime = 0.0f;
    public float gameTime = 0.0f;
    public Stage currentStage = null;
    public boolean rewinding = false;

    public static Environment getInstance() {
        if (instance == null)
            instance = new Environment();
        return instance;
    }

    private Environment() {
    }
}
