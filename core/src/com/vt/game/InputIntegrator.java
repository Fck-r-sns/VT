package com.vt.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Fck.r.sns on 28.06.2015.
 */
public class InputIntegrator implements InputProcessor{
    private Array<InputProcessor> m_processors;

    public InputIntegrator() {
        m_processors = new Array<InputProcessor>();
    }

    public InputIntegrator addInputProcessor(InputProcessor ip) {
        m_processors.add(ip);
        return this;
    }

    @Override
    public boolean keyDown(int keycode) {
        for (InputProcessor ip : m_processors) {
            if (ip.keyDown(keycode))
                return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        for (InputProcessor ip : m_processors) {
            if (ip.keyUp(keycode))
                return true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        for (InputProcessor ip : m_processors) {
            if (ip.keyTyped(character))
                return true;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        for (InputProcessor ip : m_processors) {
            if (ip.touchDown(screenX, screenY, pointer, button))
                return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        for (InputProcessor ip : m_processors) {
            if (ip.touchUp(screenX, screenY, pointer, button))
                return true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        for (InputProcessor ip : m_processors) {
            if (ip.touchDragged(screenX, screenY, pointer))
                return true;
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        for (InputProcessor ip : m_processors) {
            if (ip.mouseMoved(screenX, screenY))
                return true;
        }
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        for (InputProcessor ip : m_processors) {
            if (ip.scrolled(amount))
                return true;
        }
        return false;
    }
}
