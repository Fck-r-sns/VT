package com.vt.gameobjects.terrain;

import com.badlogic.gdx.math.Vector2;
import com.vt.game.Constants;

/**
 * Created by Fck.r.sns on 26.06.2015.
 */
public class StubLevel extends AbstractLevel {
    protected StubLevel(int width, int height) {
        for (int column = 0; column < width; ++column) {
            for (int row = 0; row < height; ++row) {
                Tile tile = new Floor();
                tile.setPosition(Constants.TILE_SIZE * column, Constants.TILE_SIZE * row);
                m_tiles.put(new Vector2(column, row), tile);
            }
        }
        setPlayerPosition(width * Constants.TILE_SIZE / 2.0f, height * Constants.TILE_SIZE / 2.0f);
    }
}
