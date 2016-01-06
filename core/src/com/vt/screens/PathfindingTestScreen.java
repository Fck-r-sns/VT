package com.vt.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.vt.game.CameraHelper;
import com.vt.game.Constants;
import com.vt.game.Environment;
import com.vt.gameobjects.pointers.DrawableVector;
import com.vt.gameobjects.terrain.levels.AbstractLevel;
import com.vt.gameobjects.terrain.levels.LevelFactory;
import com.vt.gameobjects.terrain.tiles.Tile;
import com.vt.logic.pathfinding.DijkstraAlgorithm;
import com.vt.logic.pathfinding.Graph;
import com.vt.logic.pathfinding.Pathfinder;
import com.vt.resources.Assets;

import java.util.List;
import java.util.Map;

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
    private List<Graph.Node> m_path;
    Pathfinder m_pathfinder;

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

        m_level = LevelFactory.createFromTextFile(Constants.Level.PATHFINDING_TEST_FILE);
//        m_level = LevelFactory.createFromTextFile(Constants.Level.LEVEL_TEST_FILE);
        m_graph = m_level.createGraph();
        m_pathfinder = new DijkstraAlgorithm(m_graph);
//        m_pathfinder = new AStarAlgorithm(m_graph, new AStarAlgorithm.Heuristic() {
//            @Override
//            public float calculate(Graph.Node node, Graph.Node targetNode) {
////                float dX = targetNode.x - node.x;
////                float dY = targetNode.y - node.y;
////                return (float) Math.sqrt(dX * dX + dY * dY);
//                float dx = Math.abs(targetNode.x - node.x);
//                float dy = Math.abs(targetNode.y - node.y);
//                float D = 1.0f;
//                float D2 = 1.41f;
//                return D * (dx + dy) + (D2 - 2 * D) * Math.min(dx, dy);
//            }
//        });
//        m_path =  m_pathfinder.findPath(m_graph.getNode(new Tile.Index(1, 6)), m_graph.getNode(new Tile.Index(15, 4)));
        m_path = m_pathfinder.findPath(m_graph.getNode(new Tile.Index(1, 1)), m_graph.getNode(new Tile.Index(7, 7)));

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

        m_cameraHelper.setZoom(2);
        m_cameraHelper.setPosition(5, 10);

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
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        m_spriteBatch.begin();
        m_level.draw(m_spriteBatch);
        m_spriteBatch.end();
        m_stage.draw();

        m_renderer.setProjectionMatrix(m_camera.combined);
//        m_graph.draw(m_renderer);
        for (DrawableVector v : m_pathfinder.variations)
            v.draw(m_renderer);
        if (m_path != null)
            Graph.drawPath(m_renderer, m_path);

        m_spriteBatch.begin();
        Assets.getInstance().gui.font.setScale(0.05f);
        Assets.getInstance().gui.font.setColor(Color.BLACK);
        for (Map.Entry entry : m_pathfinder.d.entrySet()) {
            Tile.Index index = (Tile.Index) entry.getKey();
            Float value = (Float) entry.getValue();
            Assets.getInstance().gui.font.draw(
                    m_spriteBatch,
                    String.format("%.1f", value),
                    (index.x + 0.5f) * Constants.TILE_SIZE,
                    (index.y + 0.5f) * Constants.TILE_SIZE
            );
        }
        m_spriteBatch.end();
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
