package com.vt.gameobjects.pointers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.vt.game.Constants;
import com.vt.gameobjects.GameObject;
import com.vt.resources.Assets;

/**
 * Created by Fck.r.sns on 10.05.2015.
 */
public class ViewPointer extends GameObject {
    public ViewPointer() {
        setSize(Constants.VIEW_POINTER_WIDTH, Constants.VIEW_POINTER_HEIGHT);
        setOrigin(Align.center);
        setTexture(Assets.getInstance().gameEntities.viewPointer);
        this.setName(Constants.VIEW_POINTER_ACTOR_NAME);
        this.setActive(true);
    }

    public Vector2 getPosition() {
        return new Vector2(getX(Align.center), getY(Align.center));
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }
}
