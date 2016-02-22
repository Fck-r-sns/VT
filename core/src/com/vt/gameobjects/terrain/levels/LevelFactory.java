package com.vt.gameobjects.terrain.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.vt.game.Constants;
import com.vt.gameobjects.terrain.tiles.Tile;
import com.vt.gameobjects.terrain.tiles.TileFactory;
import com.vt.gameobjects.terrain.tiles.Wall;
import com.vt.physics.CollisionManager;
import com.vt.physics.geometry.LineSegment;
import com.vt.physics.geometry.Point;

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
        int maxY = rows.length;
        int[] maxX = new int[maxY];
        AbstractLevel level = new Level();
        ObjectMap<Tile.Index, Wall> walls = new ObjectMap<Tile.Index, Wall>(64);
        for (int i = maxY - 1; i >= 0; --i) {
            String row = rows[i];
            int yPos = maxY - 1 - i;
            int xPos = 0;
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
                        walls.put(new Tile.Index(xPos, yPos), (Wall)tile);
                        break;
                    default:
                        break;
                }
                if (tile != null) {
                    level.setTile(new Tile.Index(xPos, yPos), tile);
                }
                ++xPos;
            }
            maxX[yPos] = xPos;
        }
        ObjectSet<LineSegment> segments = new ObjectSet<LineSegment>(64);
        Tile.Index idx = new Tile.Index(0, 0);
        for (int y = 0; y < maxY; ++y) {
            for (int x = 0; x < maxX[y]; ++x) {
                idx.setValue(x, y);
                int beginX = x;
                int beginY = y;
                int height = Integer.MAX_VALUE;
                while (walls.remove(idx) != null) {
                    int currentHeight = 0;
                    do {
                        ++currentHeight;
                        idx.setValue(x, y + currentHeight);
                    } while (walls.containsKey(idx));
                    height = Math.min(height, currentHeight);
                    ++x;
                    idx.setValue(x, y);
                }
                int width = x - beginX;
                if (width != 0) {
                    float x1 = beginX * Constants.TILE_SIZE;
                    float y1 = beginY * Constants.TILE_SIZE;
                    float x2 = (beginX + width) * Constants.TILE_SIZE;
                    float y2 = (beginY + height) * Constants.TILE_SIZE;
                    segments.addAll(
                            new LineSegment(new Point(x1, y1), new Point(x2, y1)),
                            new LineSegment(new Point(x2, y1), new Point(x2, y2)),
                            new LineSegment(new Point(x2, y2), new Point(x1, y2)),
                            new LineSegment(new Point(x1, y2), new Point(x1, y1))
                    );
                }
            }
        }
        CollisionManager.getInstance().setStaticLineSegments(segments);
        return level;
    }
}
