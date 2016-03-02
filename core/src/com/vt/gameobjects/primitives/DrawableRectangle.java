package com.vt.gameobjects.primitives;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by fckrsns on 02.03.2016.
 */
public class DrawableRectangle {
    private Vector2 m_position;
    private Vector2 m_dimension;
    private DrawableLine m_leftLine;
    private DrawableLine m_rightLine;
    private DrawableLine m_topLine;
    private DrawableLine m_bottomLine;

    public DrawableRectangle(float x, float y, float width, float height, TextureRegion texture, float lineWidth) {
        m_position = new Vector2(x, y);
        m_dimension = new Vector2(width, height);
        m_leftLine = new DrawableLine(x, y, x, y + height, texture, lineWidth);
        m_topLine = new DrawableLine(x, y + height, x + width, y + height, texture, lineWidth);
        m_rightLine = new DrawableLine(x + width, y, x + width, y + height, texture, lineWidth);
        m_bottomLine = new DrawableLine(x, y, x + width, y, texture, lineWidth);
    }

    public void draw(Batch batch) {
        m_leftLine.draw(batch);
        m_topLine.draw(batch);
        m_rightLine.draw(batch);
        m_bottomLine.draw(batch);
    }
}
