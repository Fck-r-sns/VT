package com.vt.gameobjects.primitives;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Fck.r.sns on 15.12.2015.
 */
public class DrawableVector {
    private Vector2 m_origin;
    private Vector2 m_vector;
    private float m_width;
    private boolean m_drawArrow;
    private TextureRegion m_texture;
    private float m_length;
    private float m_rotation;

    public DrawableVector(float originX, float originY, float x, float y, TextureRegion texture, float width) {
        this(originX, originY, x, y, texture, width, true);
    }

    public DrawableVector(float originX, float originY, float x, float y, TextureRegion texture, float width, boolean drawArrow) {
        m_origin = new Vector2(originX, originY);
        m_vector = new Vector2(x, y);
        m_texture = texture;
        m_width = width;
        m_drawArrow = drawArrow;
        updateLengthAndRotation();
    }

    public void setOrigin(float x, float y) {
        m_origin.set(x, y);
        updateLengthAndRotation();
    }

    public void setOrigin(final Vector2 pos) {
        m_origin.set(pos.x, pos.y);
        updateLengthAndRotation();
    }

    public void setVector(float x, float y) {
        m_vector.set(x, y);
        updateLengthAndRotation();
    }

    public void setVector(final Vector2 pos) {
        m_vector.set(pos.x, pos.y);
        updateLengthAndRotation();
    }

    public void draw(Batch batch) {
        if (m_texture != null) {
            batch.draw(
                    m_texture,
                    m_origin.x, m_origin.y,
                    m_width / 2, 0,
                    m_width, m_length,
                    1.0f, 1.0f,
                    m_rotation
            );
            if (m_drawArrow) {
                float angle = m_vector.cpy().sub(m_origin).angle();
                float arrowLength = 5.1f * m_width; // sqrt(1^2 + 5^2) * m_width
                batch.draw(
                        m_texture,
                        m_vector.x, m_vector.y,
                        m_width / 2, 0,
                        m_width, arrowLength,
                        1.0f, 1.0f,
                        angle + 90 - 15
                );
                batch.draw(
                        m_texture,
                        m_vector.x, m_vector.y,
                        m_width / 2, 0,
                        m_width, arrowLength,
                        1.0f, 1.0f,
                        angle + 90 + 15
                );
            }
        }
    }

    private void updateLengthAndRotation() {
        Vector2 v = m_vector.cpy().sub(m_origin);
        m_length = v.len();
        m_rotation = v.angle() - 90;
    }
}
