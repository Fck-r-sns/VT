package com.vt.gameobjects.gui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.vt.gameobjects.GameObject;

/**
 * Created by Fck.r.sns on 19.06.2015.
 */

public class Button extends GameObject {
    protected TextureRegion m_textureUp;
    protected TextureRegion m_textureDown;
    protected ButtonAction m_pushAction;
    protected ButtonAction m_releaseAction;

    public Button() {
        setOrigin(Align.topLeft);
        this.setActive(true);

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                push();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                release();
            }
        });
    }

    public void setPushAction(ButtonAction action) {
        m_pushAction = action;
    }

    public void setReleaseAction(ButtonAction action) {
        m_releaseAction = action;
    }

    public void push() {
        setTexture(m_textureDown);
        if (m_pushAction != null)
            m_pushAction.run();
    }

    public void release() {
        setTexture(m_textureUp);
        if (m_releaseAction != null)
            m_releaseAction.run();
    }
}
