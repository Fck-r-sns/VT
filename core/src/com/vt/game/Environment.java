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
    private boolean m_rewinding = false;
    private float m_rewindTargetTime = 0;

    public static Environment getInstance() {
        if (instance == null)
            instance = new Environment();
        return instance;
    }

    public void setRewinding(boolean rewinding) {
        this.m_rewinding = rewinding;
    }

    public boolean isRewinding() {
        return m_rewinding;
    }

    public void setRewindTargetTime(float time) {
        m_rewindTargetTime = Math.max(time, 0.0f);
    }

    public float getRewindTargetTime() {
        return m_rewindTargetTime;
    }

    private Environment() {
    }
}
