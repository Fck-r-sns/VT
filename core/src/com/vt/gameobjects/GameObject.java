package com.vt.gameobjects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.vt.physics.Spatial;

/**
 * Created by Fck.r.sns on 04.05.2015.
 */
public abstract class GameObject extends Actor implements Spatial {
    boolean m_active;
    private TextureRegion m_texture;
    private boolean m_usePositionCorrection = false;

    public GameObject() {
        setActive(true);
    }

    public void setUsePositionCorrection(boolean value) {
        m_usePositionCorrection = value;
    }

    public void setTexture(TextureRegion texture) {
        m_texture = texture;
    }

    public boolean isActive() {
        return m_active;
    }

    public void setActive(boolean active) {
        m_active = active;
        setVisible(m_active);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (m_texture == null)
            return;
        float xOffset = m_usePositionCorrection ? getWidth() / 2 - getOriginX() : 0.0f;
        float yOffset = m_usePositionCorrection ? getHeight() / 2 - getOriginY() : 0.0f;
        batch.draw(
                m_texture,
                getX() + xOffset,
                getY() + yOffset,
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(),
                getRotation()
        );
    }

    @Override
    public void act(float delta) {
        update(delta);
        super.act(delta);
    }

    @Override
    public void setRotation(float degrees) {
        if (degrees > 360) {
            super.setRotation(degrees - 360);
        } else if (degrees < 0) {
            super.setRotation(degrees + 360);
        } else {
            super.setRotation(degrees);
        }
    }

    protected void update(float delta) {
    }

    @Override
    public Vector2 getPosition() {
        return new Vector2(getX(), getY());
    }
}
