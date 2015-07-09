package com.vt.gameobjects.gui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.vt.gameobjects.GameObject;

/**
 * Created by Fck.r.sns on 19.06.2015.
 */

public class Button extends GameObject {
    enum State {
        Up,
        Down
    }
    protected State m_state = State.Up;
    protected TextureRegion m_textureUp;
    protected TextureRegion m_textureDown;

    public Button() {
        setOrigin(Align.topLeft);
        this.setActive(true);
    }

    public void push() {
        m_state = State.Down;
        setTexture(m_textureDown);
    }

    public void release() {
        m_state = State.Up;
        setTexture(m_textureUp);
    }
}
