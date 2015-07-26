package com.vt.gameobjects.weapons;

import com.badlogic.gdx.math.Vector2;
import com.vt.gameobjects.GameObject;

/**
 * Created by Fck.r.sns on 25.07.2015.
 */
public abstract class AbstractWeapon extends GameObject implements Shootable {
    public void rotate(float rotation) {
        super.setRotation(rotation);
        Vector2 rotated = new Vector2(-getOriginX(), -getOriginY());
        rotated.rotate(rotation);
        rotated.add(getPosition());
        setPosition(rotated.x, rotated.y);
    }
}
