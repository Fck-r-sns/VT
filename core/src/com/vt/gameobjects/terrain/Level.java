package com.vt.gameobjects.terrain;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Fck.r.sns on 26.06.2015.
 */
public abstract class Level {
    protected Array<Array<Tile>> m_tiles;

    protected Level() {
    }

    public void update(float delta) {
        for (Array<Tile> column : m_tiles)
            for (Tile tile : column)
                tile.update(delta);
    }

    public void draw(SpriteBatch spriteBatch) {
        for (Array<Tile> column : m_tiles)
            for (Tile tile : column)
                tile.draw(spriteBatch);
    }
}
