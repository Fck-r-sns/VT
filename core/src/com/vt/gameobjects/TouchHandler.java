package com.vt.gameobjects;

import com.badlogic.gdx.scenes.scene2d.InputEvent;

/**
 * Created by fckrsns on 16.01.2016.
 */
public interface TouchHandler {
    boolean handleTouchDown(InputEvent event, float x, float y, int pointer, int button);
    void handleTouchUp(InputEvent event, float x, float y, int pointer, int button);
    void handleTouchDragged(InputEvent event, float x, float y, int pointer);
}
