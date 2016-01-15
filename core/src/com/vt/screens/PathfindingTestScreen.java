package com.vt.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.vt.game.CameraHelper;
import com.vt.game.Constants;
import com.vt.game.Environment;
import com.vt.gameobjects.pointers.DrawableVector;
import com.vt.gameobjects.terrain.levels.AbstractLevel;
import com.vt.gameobjects.terrain.levels.LevelFactory;
import com.vt.gameobjects.terrain.tiles.Tile;
import com.vt.logic.pathfinding.AStarAlgorithm;
import com.vt.logic.pathfinding.DijkstraAlgorithm;
import com.vt.logic.pathfinding.Graph;
import com.vt.logic.pathfinding.Pathfinder;
import com.vt.resources.Assets;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by fckrsns on 05.01.2016.
 */
public class PathfindingTestScreen implements Screen {
    private OrthographicCamera m_camera;
    private CameraHelper m_cameraHelper;
    private SpriteBatch m_spriteBatch;
    private ShapeRenderer m_renderer;
    private Stage m_stage;
    private Graph m_graph;
    private List<Graph.Vertex> m_path;
    Pathfinder m_pathfinder;

    private final boolean m_useDijkstra = false;
    private final boolean m_drawGraph = false;
    private final boolean m_drawPath = false;
    private final boolean m_drawVariations = false;

    private AbstractLevel m_level;

    public PathfindingTestScreen() {
        Constants.SCREEN_RATIO = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
        m_renderer = new ShapeRenderer();
        m_spriteBatch = new SpriteBatch();
        m_camera = new OrthographicCamera();
        m_cameraHelper = new CameraHelper(m_camera);
        m_stage = new Stage(new ScreenViewport(m_camera), m_spriteBatch); // stage overwrites camera's viewport

        m_camera.setToOrtho(false,
                Constants.VIEWPORT_WIDTH,
                Constants.VIEWPORT_WIDTH * Constants.SCREEN_RATIO);
        m_camera.update();

        Environment env = Environment.getInstance();
        env.currentStage = m_stage;
        env.globalTime = -1.0f;
        env.gameTime = -1.0f;

        Assets.getInstance().init();

////        m_level = LevelFactory.createFromTextFile(Constants.Level.PATHFINDING_TEST_FILE);
//        m_level = LevelFactory.createFromTextFile(Constants.Level.LEVEL_TEST_FILE);
//        int levelSize = 9;
////        m_level = LevelFactory.createPathfindingTest(levelSize, levelSize);
////        m_level = LevelFactory.createStub(levelSize, levelSize);
//        m_graph = m_level.createGraph();
//        if (m_useDijkstra) {
//            m_pathfinder = new DijkstraAlgorithm(m_graph);
//        } else {
//            m_pathfinder = new AStarAlgorithm(m_graph, new AStarAlgorithm.Heuristic() {
//                @Override
//                public float calculate(Graph.Vertex vertex, Graph.Vertex targetVertex) {
////                float dX = targetVertex.x - vertex.x;
////                float dY = targetVertex.y - vertex.y;
////                return (float) Math.sqrt(dX * dX + dY * dY);
//                    float dx = Math.abs(targetVertex.x - vertex.x);
//                    float dy = Math.abs(targetVertex.y - vertex.y);
//                    float D = 1 * 1.0f;
//                    float D2 = 1 * 1.41f;
//                    return D * (dx + dy) + (D2 - 2 * D) * Math.min(dx, dy);
////                    return 0;
//                }
//            });
//        }
//        long time = System.nanoTime();
//        m_path =  m_pathfinder.findPath(m_graph.getVertex(new Tile.Index(1, 6)), m_graph.getVertex(new Tile.Index(29, 5)));
////        m_path = m_pathfinder.findPath(m_graph.getVertex(new Tile.Index(0, 0)), m_graph.getVertex(new Tile.Index(levelSize - 1, levelSize - 1)));
////        m_path = m_pathfinder.findPath(m_graph.getVertex(new Tile.Index(4, 2)), m_graph.getVertex(new Tile.Index(7, 7)));
//        time = System.nanoTime() - time;
//        Gdx.app.debug("Pathfinding", "Execution time = " + time);

        PrintWriter file;
        try {
            file = new PrintWriter("output.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
        long dijkstraAverage = 0;
        long astarAverage = 0;
        int testsCount = 80;
        int repetitionCount = 100;
        for (int i = 0; i < testsCount; ++i) {
            int levelSize = 5 + 4 * i;
            long time = System.nanoTime();
            m_level = LevelFactory.createPathfindingTest(levelSize, levelSize);
            time = System.nanoTime() - time;
            Gdx.app.debug("Pathfinding", levelSize + "x" + levelSize + ": " + "level creation = " + time);
            time = System.nanoTime();
            m_graph = m_level.createGraph();
            time = System.nanoTime() - time;
            Gdx.app.debug("Pathfinding", levelSize + "x" + levelSize + ": " + "graph creation = " + time);
            Pathfinder dijkstra = new DijkstraAlgorithm(m_graph);
            Pathfinder astar = new AStarAlgorithm(m_graph, new AStarAlgorithm.Heuristic() {
                @Override
                public float calculate(Graph.Vertex vertex, Graph.Vertex targetVertex) {
                    float dx = Math.abs(targetVertex.x - vertex.x);
                    float dy = Math.abs(targetVertex.y - vertex.y);
                    float D = 1.0f;
                    float D2 = 1.41f;
                    return D * (dx + dy) + (D2 - 2 * D) * Math.min(dx, dy);
                }
            });
            Array<Long> dijkstraTime = new Array<Long>(repetitionCount);
            Array<Long> astarTime = new Array<Long>(repetitionCount);
            dijkstraAverage = 0;
            astarAverage = 0;
            Graph.Vertex from = m_graph.getVertex(new Tile.Index(0, 0));
            Graph.Vertex to = m_graph.getVertex(new Tile.Index(levelSize - 1, levelSize - 1));
            for (int j = 0; j < repetitionCount; ++j) {
                time = System.nanoTime();
                m_path = dijkstra.findPath(from, to);
                time = System.nanoTime() - time;
                if (m_path == null) {
                    throw new NullPointerException();
                }
                dijkstraTime.add(time);
                dijkstraAverage += time;

                time = System.nanoTime();
                m_path = astar.findPath(from, to);
                time = System.nanoTime() - time;
                if (m_path == null) {
                    throw new NullPointerException();
                }
                astarTime.add(time);
                astarAverage += time;
            }
            dijkstraAverage /= dijkstraTime.size;
            astarAverage /= astarTime.size;
            file.println(levelSize + "," + dijkstraAverage + "," + astarAverage);
            Gdx.app.debug("Pathfinding", levelSize + "x" + levelSize + ": " + "Dijkstra time = " + dijkstraAverage);
            Gdx.app.debug("Pathfinding", levelSize + "x" + levelSize + ": " + "A*       time = " + astarAverage);
        }
        file.close();

        m_stage.getRoot().addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                final float MOVE_RANGE = 1.0f;
                final float ZOOM_VALUE = 0.1f;
                switch (keycode) {
                    case Input.Keys.LEFT:
                        m_cameraHelper.moveLeft(MOVE_RANGE);
                        break;
                    case Input.Keys.RIGHT:
                        m_cameraHelper.moveRight(MOVE_RANGE);
                        break;
                    case Input.Keys.UP:
                        m_cameraHelper.moveUp(MOVE_RANGE);
                        break;
                    case Input.Keys.DOWN:
                        m_cameraHelper.moveDown(MOVE_RANGE);
                        break;
                    case Input.Keys.PLUS:
                        m_cameraHelper.zoomUp(ZOOM_VALUE);
                        break;
                    case Input.Keys.MINUS:
                        m_cameraHelper.zoomDown(ZOOM_VALUE);
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
        Gdx.input.setInputProcessor(m_stage);

        m_cameraHelper.setZoom(3.8f);
        m_cameraHelper.moveDown(3);
        m_cameraHelper.moveLeft(1);

        env.globalTime = 0.0f;
        env.gameTime = 0.0f;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Environment env = Environment.getInstance();
        delta = 1 / 60.0f;
        env.globalTime += delta;

        m_level.update(delta);
        m_stage.act(delta);

        m_camera.update(false);
        m_spriteBatch.setProjectionMatrix(m_camera.combined);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        m_spriteBatch.begin();
        m_level.draw(m_spriteBatch);
        m_spriteBatch.end();
        m_stage.draw();

        m_renderer.setProjectionMatrix(m_camera.combined);
        if (m_drawGraph)
            m_graph.draw(m_renderer);

//        if (m_drawVariations)
//            for (DrawableVector v : m_pathfinder.variations)
//                v.draw(m_renderer);

        if (m_drawPath)
            if (m_path != null)
                Graph.drawPath(m_renderer, m_path);
    }

    @Override
    public void resize(int width, int height) {
        Constants.SCREEN_RATIO = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
        m_camera.setToOrtho(false,
                Constants.VIEWPORT_WIDTH,
                Constants.VIEWPORT_WIDTH * Constants.SCREEN_RATIO);
        m_camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        Assets.getInstance().init();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        m_stage.dispose();
        m_spriteBatch.dispose();
        Assets.getInstance().dispose();
    }
}
