package com.vt.utils;

import com.badlogic.gdx.Gdx;

/**
 * Created by fckrsns on 05.03.2016.
 */
public class DumbProfiler {
    private long m_timeAccumulator = 0;
    private long m_startTime = 0;
    private int m_frameCounter = 0;
    private int m_averageBy_frames = 1;
    private String m_domainTag = "";
    private String m_comment = "";

    public DumbProfiler(String domainTag, int averageBy_frames) {
        m_averageBy_frames = averageBy_frames;
        m_domainTag = domainTag;
    }

    public void start() {
        m_startTime = System.nanoTime();
    }

    public void start(String comment) {
        m_comment = comment;
        m_startTime = System.nanoTime();
    }

    public void process() {
        m_timeAccumulator += System.nanoTime() - m_startTime;
        ++m_frameCounter;
        if (m_frameCounter >= m_averageBy_frames) {
            Gdx.app.log(m_domainTag, m_comment + ": " + m_timeAccumulator / m_frameCounter / 1000000.0f);
            m_frameCounter = 0;
            m_timeAccumulator = 0;
        }
    }
}
