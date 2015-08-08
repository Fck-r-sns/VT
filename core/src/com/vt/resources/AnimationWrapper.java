package com.vt.resources;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vt.game.Environment;

/**
 * Created by Fck.r.sns on 08.08.2015.
 */
public class AnimationWrapper {
    private float m_startingTime;
    private Animation m_animation;

    public AnimationWrapper(Animation animation) {
        m_animation = animation;
        m_startingTime = Environment.getInstance().globalTime;
    }

    public void restart() {
        m_startingTime = Environment.getInstance().globalTime;
    }

    Animation getAnimation() {
        return m_animation;
    }

    public TextureRegion getCurrentFrame() {
        if (m_animation != null)
            return m_animation.getKeyFrame(Environment.getInstance().globalTime - m_startingTime);
        else
            return null;
    }
}
