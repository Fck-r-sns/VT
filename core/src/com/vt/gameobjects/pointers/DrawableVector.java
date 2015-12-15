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

    public DrawableVector(float originX, float originY, float x, float y, Color color, float width) {
        m_origin = new Vector2(originX, originY);
        m_vector = new Vector2(x, y);
        m_color = color;
        m_width = width;
    }

    public void draw(ShapeRenderer renderer) {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        Color prevColor = renderer.getColor();
        renderer.setColor(m_color);
        renderer.rectLine(m_origin, m_vector, m_width);
        renderer.setColor(prevColor);
        renderer.end();
    }

    public void draw() {
        draw(new ShapeRenderer());
    }
}
