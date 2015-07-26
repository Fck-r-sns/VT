package com.vt.gameobjects.weapons;

import com.vt.game.Constants;
import com.vt.game.Environment;

/**
 * Created by Fck.r.sns on 25.07.2015.
 */
public class Rifle extends AbstractWeapon {
    public Rifle() {
        setOrigin(-Constants.RIFLE_SHOOTING_POINT_X, -Constants.RIFLE_SHOOTING_POINT_Y);
    }

    @Override
    public void shoot() {
        Projectile p = new Projectile();
        p.setPosition(getX(), getY(), Constants.ALIGN_ORIGIN);
        p.setStartingAngle(getRotation());
        p.setStartingVelocity(15);
        Environment.getInstance().currentStage.addActor(p);
    }

    @Override
    public void reload() {

    }
}
