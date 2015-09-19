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
        m_startingTime = Environment.getInstance().gameTime;
    }

    public void restart() {
        m_startingTime = Environment.getInstance().gameTime;
    }

    Animation getAnimation() {
        return m_animation;
    }

    public TextureRegion getCurrentFrame() {
        if (m_animation != null) {
            float animationTime = Math.max(Environment.getInstance().gameTime - m_startingTime, 0.0f);
            return m_animation.getKeyFrame(animationTime);
        } else
            return null;
    }
}
