package com.vt.gameobjects.terrain.tiles;

/**
 * Created by Fck.r.sns on 07.07.2015.
 */
public class TileFactory {
    public static Tile create(Tile.Type type, float x, float y) {
        switch (type) {
            case Floor:
                return new Floor(x, y);
            case Wall:
                return new Wall(x, y);
            default:
                return null;
        }
    }
}
