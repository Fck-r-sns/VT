package com.vt.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.vt.messages.MessageDispatcher;

/**
 * Created by Fck.r.sns on 08.07.2015.
 */
public class Environment {
    public enum TimeState {
        RealTime,
        ActivePause
    }

    private static Environment instance = null;
    public boolean debugDrawings = false;
    public long frameCounter = 0;
    public float globalTime = 0.0f;
    public float gameTime = 0.0f;
    public float rewindableTime = 0.0f;
    public Stage currentStage = null;
    private TimeState m_timeState = TimeState.RealTime;
    private boolean m_rewinding = false;
    private float m_rewindTargetTime = 0;

    public static Environment getInstance() {
        if (instance == null)
            instance = new Environment();
        return instance;
    }

    public void setPaused(boolean paused) {
        m_timeState = paused ? TimeState.ActivePause : TimeState.RealTime;
        if (isPaused()) {
            m_timeState = TimeState.ActivePause;
            MessageDispatcher.getInstance().sendBroadcast(MessageDispatcher.BroadcastMessageType.StateChangedToActivePause, null);
        } else {
            m_timeState = TimeState.RealTime;
            MessageDispatcher.getInstance().sendBroadcast(MessageDispatcher.BroadcastMessageType.StateChangedToRealTime, null);
        }
    }

    public void togglePaused() {
        setPaused(!isPaused());
    }

    public boolean isPaused() {
        return m_timeState == TimeState.ActivePause;
    }

    public TimeState getTimeState() {
        return m_timeState;
    }

    public void setRewindTargetTime(float time) {
        m_rewindTargetTime = Math.max(time, 0.0f);
    }

    public void setRewinding(boolean rewinding) {
        this.m_rewinding = rewinding;
    }

    public boolean isRewinding() {
        return m_rewinding;
    }

    public float getRewindTargetTime() {
        return m_rewindTargetTime;
    }

    private Environment() {
    }
}
