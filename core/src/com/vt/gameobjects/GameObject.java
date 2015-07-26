package com.vt.gameobjects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.vt.game.Constants;
import com.vt.physics.Spatial;

/**
 * Created by Fck.r.sns on 04.05.2015.
 */
public abstract class GameObject extends Group implements Spatial {
    static private int m_idGenerator = 0;
    private Integer m_id;
    private boolean m_active;
    private TextureRegion m_texture;

    public GameObject() {
        m_id = ++m_idGenerator;
        setActive(true);
    }

    public final Integer getId() {
        return m_id;
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
        if (m_texture != null)
            batch.draw(
                    m_texture,
                    getX(), getY(),
                    getOriginX(), getOriginY(),
                    getWidth(), getHeight(),
                    getScaleX(), getScaleY(),
                    getRotation()
            );
        drawChildren(batch, parentAlpha);
    }

    @Override
    public final void act(float delta) {
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
        rotationChanged();
    }

    public void update(float delta) {
    }

    @Override
    public Vector2 getPosition() {
        return new Vector2(getX() + getOriginX(), getY() + getOriginY());
    }

    @Override
    public float getX(int alignment) {
        if (alignment == Constants.ALIGN_ORIGIN)
            return getX() + getOriginX();
        else
            return super.getX(alignment);
    }

    @Override
    public float getY(int alignment) {
        if (alignment == Constants.ALIGN_ORIGIN)
            return getY() + getOriginY();
        else
            return super.getY(alignment);
    }

    @Override
    public void setPosition(float x, float y, int alignment) {
        if (alignment == Constants.ALIGN_ORIGIN) {
            x -= getOriginX();
            y -= getOriginY();
            super.setPosition(x, y);
        } else {
            super.setPosition(x, y, alignment);
        }
    }

    protected void rotationChanged() {

    }
}
