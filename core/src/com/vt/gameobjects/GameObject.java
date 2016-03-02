package com.vt.gameobjects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.vt.game.Constants;
import com.vt.messages.Context;
import com.vt.messages.MessageDispatcher;
import com.vt.messages.MessageHandler;
import com.vt.messages.RewindContext;
import com.vt.physics.Spatial;
import com.vt.physics.SpatialHash;
import com.vt.physics.SpatiallyHashable;
import com.vt.serialization.RestorableValue;
import com.vt.serialization.ValuesChangeHistory;

/**
 * Created by Fck.r.sns on 04.05.2015.
 */
public abstract class GameObject extends Group implements Spatial, SpatiallyHashable {
    static private int m_idGenerator = 0;
    private ValuesChangeHistory m_valuesHistory;
    private final Integer m_id;
    private boolean m_active;
    private TextureRegion m_texture;

    public GameObject() {
        m_id = ++m_idGenerator;
        m_valuesHistory = new ValuesChangeHistory();
        MessageDispatcher.getInstance().subscribeToBroadcast(
                MessageDispatcher.BroadcastMessageType.Rewind,
                new MessageHandler() {
                    @Override
                    public void onMessageReceived(Context ctx) {
                        if (ctx != null && ctx instanceof RewindContext) {
                            m_valuesHistory.rewindBack(((RewindContext) ctx).getRewindTime());
                        }
                    }
                });
        setActive(true);
    }

    @Override
    public SpatialHash spatialHash() {
        return new SpatialHash((int)getX(Constants.ALIGN_ORIGIN), (int)getY(Constants.ALIGN_ORIGIN));
    }

    protected ValuesChangeHistory getValuesHistory() {
        return m_valuesHistory;
    }

    public final Integer getId() {
        return m_id;
    }

    public void setTexture(TextureRegion texture) {
        m_texture = texture;
    }

    public boolean isActive() {
        return m_active;
    }

    public void setActive(boolean active) {
        if (isActive() != active)
            m_valuesHistory.addValue(new RestorableValue() {
                private boolean m_previousActiveValue = isActive();

                @Override
                public void restore() {
                    GameObject.this.m_active = m_previousActiveValue;
                    GameObject.this.setVisible(m_previousActiveValue);
                }
            });

        m_active = active;
        setVisible(m_active);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (m_texture != null) {
            float height = getHeight();
            float width = m_texture.getRegionWidth() * height / m_texture.getRegionHeight();
            batch.draw(
                    m_texture,
                    getX(), getY(),
                    getOriginX(), getOriginY(),
                    width, height,
                    getScaleX(), getScaleY(),
                    getRotation()
            );
        }
        drawChildren(batch, parentAlpha);
    }

    @Override
    public final void act(float delta) {
        getValuesHistory().clearOldValues();
        update(delta);
        super.act(delta);
    }

    private void setRotationWithoutStoring(float degrees) {
        super.setRotation(degrees);
    }

    @Override
    public void setRotation(float degrees) {
        if (degrees > 360) {
            degrees -= 360;
        } else if (degrees < 0) {
            degrees += 360;
        }

        if (getRotation() != degrees) {
            m_valuesHistory.addValue(new RestorableValue() {
                private float m_previousRotation = getRotation();

                @Override
                public void restore() {
                    GameObject.this.setRotationWithoutStoring(m_previousRotation);
                }
            });

            super.setRotation(degrees);
            rotationChanged();
        }
    }

    public void update(float delta) {
    }

    @Override
    public Vector2 getPosition() {
        return new Vector2(getX(Constants.ALIGN_ORIGIN), getY(Constants.ALIGN_ORIGIN));
    }

    @Override
    public float getX(int alignment) {
        if (alignment == Constants.ALIGN_ORIGIN)
            return getX() + getOriginX();
        else
            return super.getX(alignment);
    }

    @Override
    public float getY(int alignment) {
        if (alignment == Constants.ALIGN_ORIGIN)
            return getY() + getOriginY();
        else
            return super.getY(alignment);
    }

    @Override
    public void setPosition(float x, float y) {
        if (getX() != x || getY() != y)
            m_valuesHistory.addValue(new RestorableValue() {
                private float x = getX();
                private float y = getY();

                @Override
                public void restore() {
                    GameObject.this.setX(x);
                    GameObject.this.setY(y);
                }
            });

        super.setPosition(x, y);
    }

    @Override
    public void setPosition(float x, float y, int alignment) {
        if (getX(alignment) != x || getY(alignment) != y)
            m_valuesHistory.addValue(new RestorableValue() {
                private float m_previousX = getX();
                private float m_previousY = getY();

                @Override
                public void restore() {
                    GameObject.this.setX(m_previousX);
                    GameObject.this.setY(m_previousY);
                }
            });

        if (alignment == Constants.ALIGN_ORIGIN) {
            x -= getOriginX();
            y -= getOriginY();
            super.setPosition(x, y);
        } else {
            super.setPosition(x, y, alignment);
        }
    }

    protected void rotationChanged() {

    }
}
