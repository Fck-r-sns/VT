package com.vt.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Fck.r.sns on 04.05.2015.
 */
public class GameObject extends Actor {
    private void log(String text) {
        Gdx.app.log("GameObject", text);
    }

    private TextureRegion m_texture;

    public GameObject() {
    }

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
    public void setRotation (float degrees) {
        if (degrees > 360) {
            super.setRotation(degrees - 360);
        } else if (degrees < 0) {
            super.setRotation(degrees + 360);
        } else {
            super.setRotation(degrees);
        }
    }
}
