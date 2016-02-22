package com.vt.gameobjects.terrain.tiles;

import com.vt.resources.Assets;

/**
 * Created by Fck.r.sns on 22.06.2015.
 */
public class Floor extends Tile {
    public Floor(float x, float y) {
        super(x, y);
        m_passable = true;
        setTexture(Assets.getInstance().terrain.simpleFloor);
    }
}
