package com.vt.gameobjects.pointers;

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
                    0, 0,
                    m_width, m_length,
                    1.0f, 1.0f,
                    m_rotation
            );
        }

//        renderer.begin(ShapeRenderer.ShapeType.Filled);
//        Color prevColor = renderer.getColor();
//        renderer.setColor(m_color);
//        renderer.rectLine(m_origin, m_vector, m_width);
//        renderer.end();
//        if (m_drawArrow) {
//            final float ARROW_X = 5 * m_width;
//            final float ARROW_Y = m_width;
//            renderer.begin(ShapeRenderer.ShapeType.Filled);
//            float angle = m_vector.cpy().sub(m_origin).angle();
//            Vector2 arrow1 = new Vector2(-ARROW_X, ARROW_Y).rotate(angle).add(m_vector);
//            Vector2 arrow2 = new Vector2(-ARROW_X, -ARROW_Y).rotate(angle).add(m_vector);
//            renderer.rectLine(m_vector, arrow1, m_width);
//            renderer.rectLine(m_vector, arrow2, m_width);
//            renderer.end();
//        }
//        renderer.setColor(prevColor);
    }

    private void updateLengthAndRotation() {
        Vector2 v = m_vector.cpy().sub(m_origin);
        m_length = v.len();
        m_rotation = v.angle() - 90;
    }
}
