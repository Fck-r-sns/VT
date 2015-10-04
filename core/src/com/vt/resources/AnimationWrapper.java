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
    private Animation.PlayMode m_normalPlayMode = Animation.PlayMode.LOOP;
    private Animation.PlayMode m_reversedPlayMode = Animation.PlayMode.LOOP_REVERSED;

    public AnimationWrapper(Animation animation) {
        m_animation = animation;
        m_startingTime = Environment.getInstance().gameTime;
    }

    public void setNormalPlayMode(Animation.PlayMode mode) {
        m_normalPlayMode = mode;
    }

    public void setReversedPlayMode(Animation.PlayMode mode) {
        m_reversedPlayMode = mode;
    }

    public void restart() {
        m_startingTime = Environment.getInstance().gameTime;
        m_animation.setPlayMode(m_normalPlayMode);
    }

    public void restartReversed() {
        m_startingTime = Environment.getInstance().gameTime;
        m_animation.setPlayMode(m_reversedPlayMode);
    }

    Animation getAnimation() {
        return m_animation;
    }

    public TextureRegion getCurrentFrame() {
        if (m_animation != null) {
            float animationTime = Math.abs(Environment.getInstance().gameTime - m_startingTime);
            return m_animation.getKeyFrame(animationTime);
        } else
            return null;
    }
}
