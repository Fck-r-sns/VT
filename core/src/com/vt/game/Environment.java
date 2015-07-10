package com.vt.game;

/**
 * Created by Fck.r.sns on 08.07.2015.
 */
public class Environment {
    private static Environment instance = null;
    public boolean debugDrawings = true;
    public float globalTime = 0.0f;

    public static Environment getInstance() {
        if (instance == null)
            instance = new Environment();
        return instance;
    }

    private Environment() {
    }
}
