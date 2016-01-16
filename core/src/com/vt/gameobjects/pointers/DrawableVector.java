package com.vt.gameobjects.pointers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Fck.r.sns on 15.12.2015.
 */
public class DrawableVector {
    private Vector2 m_origin;
    private Vector2 m_vector;
    private Color m_color;
    private float m_width;
    private boolean m_drawArrow;

    public DrawableVector(float originX, float originY, float x, float y, Color color, float width) {
        m_origin = new Vector2(originX, originY);
        m_vector = new Vector2(x, y);
        m_color = color;
        m_width = width;
        m_drawArrow = true;
    }

    public DrawableVector(float originX, float originY, float x, float y, Color color, float width, boolean drawArrow) {
        this(originX, originY, x, y, color, width);
        m_drawArrow = drawArrow;
    }

    public void setOrigin(float x, float y) {
        m_origin.set(x, y);
    }

    public void setOrigin(final Vector2 pos) {
        m_origin.set(pos.x, pos.y);
    }

    public void setVector(float x, float y) {
        m_vector.set(x, y);
    }

    public void setVector(final Vector2 pos) {
        m_vector.set(pos.x, pos.y);
    }

    public void draw(ShapeRenderer renderer) {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        Color prevColor = renderer.getColor();
        renderer.setColor(m_color);
        renderer.rectLine(m_origin, m_vector, m_width);
        renderer.end();
        if (m_drawArrow) {
            final float ARROW_X = 5 * m_width;
            final float ARROW_Y = m_width;
            renderer.begin(ShapeRenderer.ShapeType.Filled);
            float angle = m_vector.cpy().sub(m_origin).angle();
            Vector2 arrow1 = new Vector2(-ARROW_X, ARROW_Y).rotate(angle).add(m_vector);
            Vector2 arrow2 = new Vector2(-ARROW_X, -ARROW_Y).rotate(angle).add(m_vector);
            renderer.rectLine(m_vector, arrow1, m_width);
            renderer.rectLine(m_vector, arrow2, m_width);
            renderer.end();
        }
        renderer.setColor(prevColor);
    }

    public void draw() {
        draw(new ShapeRenderer());
    }
}
