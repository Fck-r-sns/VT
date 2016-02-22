package com.vt.gameobjects.terrain.levels;

import com.vt.game.Constants;
import com.vt.gameobjects.terrain.tiles.Floor;
import com.vt.gameobjects.terrain.tiles.Tile;

/**
 * Created by Fck.r.sns on 26.06.2015.
 */
public class StubLevel extends AbstractLevel {
    protected StubLevel(int width, int height) {
        for (int column = 0; column < width; ++column) {
            for (int row = 0; row < height; ++row) {
                Tile tile = new Floor(Constants.TILE_SIZE * column, Constants.TILE_SIZE * row);
                m_tiles.put(new Tile.Index(column, row), tile);
            }
        }
        setPlayerPosition(width * Constants.TILE_SIZE / 2.0f, height * Constants.TILE_SIZE / 2.0f);
    }
}
