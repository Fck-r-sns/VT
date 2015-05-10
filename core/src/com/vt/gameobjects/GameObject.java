package com.vt.gameobjects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Fck.r.sns on 04.05.2015.
 */
public abstract class GameObject extends Actor {
    private TextureRegion m_texture;

    public void setTexture(TextureRegion texture) {
        m_texture = texture;
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        batch.draw(m_texture, getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(),
                getRotation());
    }

    @Override
    public void act(float delta) {
        update(delta);
        super.act(delta);
    }

    @Override
    public void setRotation (float degrees) {
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
}
