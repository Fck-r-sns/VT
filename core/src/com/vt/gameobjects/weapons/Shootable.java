package com.vt.gameobjects.weapons;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Fck.r.sns on 10.07.2015.
 */
public interface Shootable {
    public void shoot();
    public void reload();
    public Vector2 getDirection();
    public Vector2 getShootingPoint();
}
