package com.vt.gameobjects;

import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.vt.game.Constants;
import com.vt.resources.Assets;

/**
 * Created by Fck.r.sns on 16.05.2015.
 */
public class PlayerObject extends ActingGameObject {
    private MovementPointer m_movementPointer;
    private ViewPointer m_viewPointer;

    public PlayerObject(MovementPointer mp, ViewPointer vp) {
        m_movementPointer = mp;
        m_viewPointer = vp;

        setSize(Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
        setOrigin(Align.center);
//        setOrigin(Constants.PLAYER_ORIGIN_X * Constants.PLAYER_WIDTH,
//                Constants.PLAYER_ORIGIN_Y * Constants.PLAYER_HEIGHT);
        setTexture(Assets.getInstance().gameEntities.player);
        this.setName(Constants.PLAYER_ACTOR_NAME);

        setBehavior(
                new Arrive<Vector2>(this, m_movementPointer)
                        .setDecelerationRadius(Constants.PLAYER_ARRIVAL_DECELERATION_RADIUS)
        );
    }

    @Override
    protected void update(float delta) {
        super.update(delta);
        if (m_movementPointer.getPosition().dst(getPosition()) < Constants.PLAYER_ARRIVAL_TOLERANCE) {
            m_movementPointer.setActive(false);
            m_linearVelocity.set(0, 0);
        }

        if (m_viewPointer.isActive())
            setRotation(m_viewPointer.getPosition().sub(getPosition()).angle());
    }
}
