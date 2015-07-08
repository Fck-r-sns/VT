package com.vt.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.vt.game.CameraHelper;
import com.vt.game.Constants;
import com.vt.game.Environment;
import com.vt.game.InputIntegrator;
import com.vt.gameobjects.CameraTarget;
import com.vt.gameobjects.GameObject;
import com.vt.gameobjects.MovementPointer;
import com.vt.gameobjects.PlayerObject;
import com.vt.gameobjects.ViewPointer;
import com.vt.gameobjects.gui.PauseButton;
import com.vt.gameobjects.gui.ViewButton;
import com.vt.gameobjects.terrain.AbstractLevel;
import com.vt.gameobjects.terrain.LevelFactory;
import com.vt.physics.CollisionManager;
import com.vt.resources.Assets;

/**
 * Created by Fck.r.sns on 04.05.2015.
 */
public class GameScreen implements Screen {
    private OrthographicCamera m_camera;
    private OrthographicCamera m_cameraGui;
    private CameraHelper m_cameraHelper;
    private SpriteBatch m_spriteBatch;
    private Stage m_stage;
    private Stage m_stageGui;
    private PlayerObject m_player;
    private MovementPointer m_movementPointer;
    private ViewPointer m_viewPointer;
    private CameraTarget m_cameraTarget;
    private ViewButton m_viewButton;
    private PauseButton m_pauseButton;
    boolean m_pause = false;

    private ShapeRenderer renderer = new ShapeRenderer();

    private AbstractLevel m_level;

    public GameScreen() {
        Constants.SCREEN_RATIO = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
        m_spriteBatch = new SpriteBatch();
        m_camera = new OrthographicCamera();
        m_cameraHelper = new CameraHelper(m_camera);
        m_stage = new Stage(new ScreenViewport(m_camera), m_spriteBatch); // stage overwrites camera's viewport
        m_camera.setToOrtho(false,
                Constants.VIEWPORT_WIDTH,
                Constants.VIEWPORT_WIDTH * Constants.SCREEN_RATIO);
        m_camera.update();

        m_cameraGui = new OrthographicCamera();
        m_stageGui = new Stage(new ScreenViewport(m_cameraGui), m_spriteBatch); // stage overwrites camera's viewport
        m_cameraGui.setToOrtho(false,
                Constants.GUI_VIEWPORT_WIDTH,
                Constants.GUI_VIEWPORT_WIDTH * Constants.SCREEN_RATIO);
        m_cameraGui.update();

        Assets.getInstance().init();

//        m_level = LevelFactory.createStub(10, 10);
        m_level = LevelFactory.createFromTextFile(Constants.Level.LEVEL_TEST_FILE);

        m_movementPointer = new MovementPointer();
        m_movementPointer.setPosition((m_camera.viewportWidth - m_movementPointer.getWidth()) / 2,
                (m_camera.viewportHeight - m_movementPointer.getHeight()) / 2);
        m_stage.addActor(this.m_movementPointer);

        m_viewPointer = new ViewPointer();
        m_viewPointer.setPosition((m_camera.viewportWidth - m_viewPointer.getWidth()) / 2,
                (m_camera.viewportHeight - m_viewPointer.getHeight()) / 2);
        m_stage.addActor(this.m_viewPointer);

        m_player = new PlayerObject(m_movementPointer, m_viewPointer);
        m_player.setInitialPosition((m_camera.viewportWidth - m_player.getWidth()) / 2,
                (m_camera.viewportHeight - m_player.getHeight()) / 2);
        m_stage.addActor(this.m_player);

        m_cameraTarget = new CameraTarget(new GameObject[]{m_player, m_viewPointer});
        m_cameraTarget.setPosition(m_player.getX(Align.center), m_player.getY(Align.center));
        m_cameraHelper.setTarget(m_cameraTarget);
        m_stage.addActor(m_cameraTarget);

        m_stage.getRoot().addListener(m_movementPointer.getController().setActive(true));
        m_stage.getRoot().addListener(m_viewPointer.getController().setActive(false));

        Group gui = new Group();
        m_stageGui.addActor(gui);
        m_viewButton = new ViewButton();
        gui.addActor(m_viewButton);
        m_viewButton.setPosition(
                Constants.VIEW_BUTTON_MARGIN_X + 0,
                Constants.VIEW_BUTTON_MARGIN_Y + m_cameraGui.viewportHeight,
                Align.topLeft
        );
        m_viewButton.addCaptureListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                event.stop();
                m_viewPointer.getController().setActive(true);
                m_movementPointer.getController().setActive(false);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                event.stop();
                m_viewPointer.getController().setActive(false);
                m_movementPointer.getController().setActive(true);
            }
        });

        m_pauseButton = new PauseButton();
        gui.addActor(m_pauseButton);
        m_pauseButton.setPosition(
                Constants.PAUSE_BUTTON_MARGIN_X + 0,
                Constants.PAUSE_BUTTON_MARGIN_Y + m_cameraGui.viewportHeight,
                Align.topLeft
        );
        m_pauseButton.addCaptureListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                togglePause();
                return true;
            }
        });

        Gdx.input.setInputProcessor(
                new InputIntegrator()
                        .addInputProcessor(m_stageGui)
                        .addInputProcessor(m_stage)
        );
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Environment.getInstance().globalTime += delta;

        if (!isPaused()) {
            CollisionManager.getInstance().update(delta);
            m_cameraHelper.update(delta);
            m_stage.act(delta);
            m_stageGui.act(delta);
        }

        m_camera.update(false);
        m_spriteBatch.setProjectionMatrix(m_camera.combined);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        m_spriteBatch.begin();
        m_level.draw(m_spriteBatch);
        m_spriteBatch.end();
        m_stage.draw();

        m_cameraGui.update(false);
        m_spriteBatch.setProjectionMatrix(m_cameraGui.combined);
        m_stageGui.draw();

        if (Environment.getInstance().debugMode) {
            renderer.setProjectionMatrix(m_camera.combined);
            Circle c = m_player.getBoundingShape();
            renderer.begin(ShapeRenderer.ShapeType.Line);
            renderer.setColor(1, 1, 1, 1);
            renderer.rect(m_player.getX(), m_player.getY(), m_player.getOriginX(), m_player.getOriginY(),
                    m_player.getWidth(), m_player.getHeight(), 1, 1, m_player.getRotation());
            renderer.circle(c.x, c.y, c.radius, 20);
            renderer.end();
        }
    }

    @Override
    public void resize(int width, int height) {
        Constants.SCREEN_RATIO = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();

        m_camera.setToOrtho(false,
                Constants.VIEWPORT_WIDTH,
                Constants.VIEWPORT_WIDTH * Constants.SCREEN_RATIO);
        m_camera.update();

        m_cameraGui.setToOrtho(false,
                Constants.GUI_VIEWPORT_WIDTH,
                Constants.GUI_VIEWPORT_WIDTH * Constants.SCREEN_RATIO);
        m_cameraGui.update();
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

    public boolean isPaused() {
        return m_pause;
    }

    public void setPause(boolean pause) {
        m_pause = pause;
    }

    public void togglePause() {
        m_pause = !m_pause;
    }
}
