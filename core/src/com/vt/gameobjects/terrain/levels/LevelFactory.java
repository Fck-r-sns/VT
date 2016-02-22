package com.vt.gameobjects.terrain.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.vt.game.Constants;
import com.vt.gameobjects.terrain.tiles.Tile;
import com.vt.gameobjects.terrain.tiles.TileFactory;

/**
 * Created by Fck.r.sns on 08.07.2015.
 */
public class LevelFactory {
    public static AbstractLevel createStub(int width, int height) {
        return new StubLevel(width, height);
    }

    public static AbstractLevel createPathfindingTest(int width, int height) {
        AbstractLevel level = new StubLevel(width, height);
        int x = height / 2;
        for (int y = 2; y <= width - 3; ++y) {
            Tile tile = TileFactory.create(Tile.Type.Wall, Constants.TILE_SIZE * x, Constants.TILE_SIZE * y);
            level.setTile(new Tile.Index(x, y), tile);
        }
        return level;
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
                    case Constants.Level.PLAYER_START_POS_CODE:
                        tile = TileFactory.create(Tile.Type.Floor, Constants.TILE_SIZE * xPos, Constants.TILE_SIZE * yPos);
                        level.setPlayerPosition((xPos + 0.5f ) * Constants.TILE_SIZE, (yPos + 0.5f ) * Constants.TILE_SIZE);
                        break;
                    case Constants.Level.ENEMY_START_POS_CODE:
                        tile = TileFactory.create(Tile.Type.Floor, Constants.TILE_SIZE * xPos, Constants.TILE_SIZE * yPos);
                        level.addEnemyPosition((xPos + 0.5f ) * Constants.TILE_SIZE, (yPos + 0.5f ) * Constants.TILE_SIZE);
                        break;
                    case Constants.Level.FLOOR_CODE:
                        tile = TileFactory.create(Tile.Type.Floor, Constants.TILE_SIZE * xPos, Constants.TILE_SIZE * yPos);
                        break;
                    case Constants.Level.WALL_CODE:
                        tile = TileFactory.create(Tile.Type.Wall, Constants.TILE_SIZE * xPos, Constants.TILE_SIZE * yPos);
                        break;
                    default:
                        break;
                }
                if (tile != null) {
                    level.setTile(new Tile.Index(xPos, yPos), tile);
                }
                ++xPos;
            }
            ++yPos;
        }
        return level;
    }
}
