package com.vt.gameobjects.weapons;

import com.badlogic.gdx.math.Vector2;
import com.vt.gameobjects.GameObject;

/**
 * Created by Fck.r.sns on 25.07.2015.
 */
public abstract class AbstractWeapon extends GameObject implements Shootable {
    @Override
    public void setRotation(float rotation) {
        if (getRotation() != rotation) {
            super.setRotation(rotation);
            Vector2 rotatedPosition = new Vector2(getX(), getY());
            rotatedPosition.sub(getOriginX(), getOriginY());
            rotatedPosition.rotate(rotation);
            rotatedPosition.add(getOriginX(), getOriginY());
            setPosition(rotatedPosition.x, rotatedPosition.y);
        }
    }
}
