package com.vt.gameobjects.terrain;

import com.vt.resources.Assets;

/**
 * Created by Fck.r.sns on 07.07.2015.
 */
public class Wall extends Tile {
    public Wall() {
        setType(Type.Wall);
        setTexture(Assets.getInstance().terrain.wallRocky);
    }
}
