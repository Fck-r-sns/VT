package com.vt.gameobjects.gui;

import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.vt.game.Constants;
import com.vt.gameobjects.GameObject;
import com.vt.resources.Assets;

/**
 * Created by Fck.r.sns on 19.06.2015.
 */

public class Button extends GameObject {
    public Button() {
        setOrigin(Align.topLeft);
        this.setActive(true);
    }
}
