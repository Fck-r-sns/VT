package com.vt.gameobjects.terrain;

import com.badlogic.gdx.utils.Array;
import com.vt.game.Constants;

/**
 * Created by Fck.r.sns on 26.06.2015.
 */
public class StubLevel extends Level {
    public StubLevel(int width, int height) {
        m_tiles = new Array<Array<Tile>>(width);
        for (int column = 0; column < width; ++column) {
            Array<Tile> columnArray = new Array<Tile>(height);
            for (int row = 0; row < height; ++row) {
                Tile tile = new Floor();
                tile.setPosition(Constants.TILE_SIZE * column, Constants.TILE_SIZE * row);
                columnArray.add(tile);
            }
            m_tiles.add(columnArray);
        }
    }
}
