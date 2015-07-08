package com.vt.gameobjects.terrain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.vt.game.Constants;

/**
 * Created by Fck.r.sns on 08.07.2015.
 */
public class LevelFactory {
    public static AbstractLevel createStub(int width, int height) {
        return new StubLevel(width, height);
    }

    public static AbstractLevel createFromTextFile(String fileName) {
        FileHandle file = Gdx.files.internal(fileName);
        if (!file.exists())
            return null;
        String data = file.readString();
        if (data.isEmpty())
            return null;
        String[] rows = data.split("\n");
        int xPos = 0;
        int yPos = 0;
        AbstractLevel level = new Level();
        for (int i = rows.length - 1; i >= 0; --i) {
            String row = rows[i];
            xPos = 0;
            for (char symbol : row.toCharArray()) {
                Tile tile = null;
                switch (symbol) {
                    case Constants.Level.FLOOR_CODE:
                        tile = new Floor();
                        break;
                    case Constants.Level.WALL_CODE:
                        tile = new Wall();
                        break;
                    default:
                        break;
                }
                if (tile != null) {
                    tile.setPosition(Constants.TILE_SIZE * xPos, Constants.TILE_SIZE * yPos);
                    level.setTile(new Vector2(xPos, yPos), tile);
                }
                ++xPos;
            }
            ++yPos;
        }
        return level;
    }
}
