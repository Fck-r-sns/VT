package com.vt.gameobjects.weapons;

import com.vt.game.Constants;
import com.vt.game.Environment;

/**
 * Created by Fck.r.sns on 06.08.2015.
 */
public class Pistol extends AbstractWeapon {
    public Pistol() {
        setOrigin(-Constants.PISTOL_SHOOTING_POINT_X, -Constants.PISTOL_SHOOTING_POINT_Y);
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
