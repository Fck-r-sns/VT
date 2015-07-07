package com.vt.gameobjects.terrain;

import com.vt.resources.Assets;

/**
 * Created by Fck.r.sns on 22.06.2015.
 */
public class Floor extends Tile {
    public Floor() {
        setType(Type.Floor);
        setTexture(Assets.getInstance().terrain.plainFloorDark);
    }
}
