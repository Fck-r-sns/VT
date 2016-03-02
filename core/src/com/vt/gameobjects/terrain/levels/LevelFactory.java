package com.vt.gameobjects.terrain.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.vt.game.Constants;
import com.vt.gameobjects.terrain.tiles.Tile;
import com.vt.gameobjects.terrain.tiles.TileFactory;
import com.vt.gameobjects.terrain.tiles.Wall;
import com.vt.physics.CollisionManager;
import com.vt.physics.SpatialHash;
import com.vt.physics.SpatialHashTable;
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
                    case '\r':
                    case '\n':
                        continue;
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
        SpatialHashTable<LineSegment> segmentsTable = new SpatialHashTable<LineSegment>(64, 16);
        Tile.Index idx = new Tile.Index(0, 0);
        for (int y = 0; y < maxY; ++y) {
            for (int x = 0; x < maxX[y]; ++x) {
                idx.setValue(x, y);
                int beginX = x;
                int beginY = y;
                int height = Integer.MAX_VALUE;
                while (walls.containsKey(idx)) {
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
                    for (int i = beginX; i < beginX + width; ++i) {
                        for (int j = beginY; j < beginY + height; ++j) {
                            idx.setValue(i, j);
                            walls.remove(idx);
                        }
                    }
                    float x1 = beginX * Constants.TILE_SIZE;
                    float y1 = beginY * Constants.TILE_SIZE;
                    float x2 = (beginX + width) * Constants.TILE_SIZE;
                    float y2 = (beginY + height) * Constants.TILE_SIZE;

                    LineSegment[] ss = {
                            new LineSegment(new Point(x1, y1), new Point(x2, y1)),
                            new LineSegment(new Point(x2, y1), new Point(x2, y2)),
                            new LineSegment(new Point(x2, y2), new Point(x1, y2)),
                            new LineSegment(new Point(x1, y2), new Point(x1, y1))
                    };
                    for (LineSegment s : ss) {
                        SpatialHash h1 = s.spatialHash1();
                        SpatialHash h2 = s.spatialHash2();
                        if (h1.x == h2.x) {
                            // go vertical
                            int xHash = h1.x;
                            int yStart = Math.min(h1.y, h2.y);
                            int yStop = Math.max(h1.y, h2.y);
                            for (int yHash = yStart; yHash <= yStop; ++yHash) {
                                segmentsTable.add(new SpatialHash(xHash, yHash), s);
                            }
                        } else {
                            // go horizontal
                            int yHash = h1.y;
                            int xStart = Math.min(h1.x, h2.x);
                            int xStop = Math.max(h1.x, h2.x);
                            for (int xHash = xStart; xHash <= xStop; ++xHash) {
                                segmentsTable.add(new SpatialHash(xHash, yHash), s);
                            }
                        }
                    }
                }
            }
        }
        CollisionManager.getInstance().setStaticLineSegments(segmentsTable);
        return level;
    }
}
