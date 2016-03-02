package com.vt.gameobjects.primitives;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by fckrsns on 02.03.2016.
 */
public class DrawableLine {
    private Vector2 m_begin;
    private Vector2 m_end;
    private float m_width;
    private TextureRegion m_texture;
    private float m_length;
    private float m_rotation;

    public DrawableLine(float beginX, float beginY, float endX, float endY, TextureRegion texture, float width) {
        m_begin = new Vector2(beginX, beginY);
        m_end = new Vector2(endX, endY);
        m_texture = texture;
        m_width = width;
        updateLengthAndRotation();
    }

    public void draw(Batch batch) {
        if (m_texture != null) {
            batch.draw(
                    m_texture,
                    m_begin.x, m_begin.y,
                    m_width / 2, 0,
                    m_width, m_length,
                    1.0f, 1.0f,
                    m_rotation
            );
        }
    }

    private void updateLengthAndRotation() {
        Vector2 v = m_end.cpy().sub(m_begin);
        m_length = v.len();
        m_rotation = v.angle() - 90;
    }
}
