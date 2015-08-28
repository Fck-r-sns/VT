package com.vt.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.vt.game.CameraHelper;
import com.vt.game.Constants;
import com.vt.game.Environment;
import com.vt.gameobjects.CameraTarget;
import com.vt.gameobjects.GameObject;
import com.vt.gameobjects.characters.ManualController;
import com.vt.gameobjects.gui.Button;
import com.vt.gameobjects.characters.CharacterObject;
import com.vt.gameobjects.gui.ButtonAction;
import com.vt.gameobjects.gui.ButtonFactory;
import com.vt.gameobjects.terrain.levels.AbstractLevel;
import com.vt.gameobjects.terrain.levels.LevelFactory;
import com.vt.messages.MessageDispatcher;
import com.vt.messages.RewindContext;
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
    private CharacterObject m_player;
    private ManualController m_playerController;
    private Array<CharacterObject> m_enemies;
    private CameraTarget m_cameraTarget;
    private Button m_viewButton;
    private Button m_pauseButton;
    private Button m_shootButton;
    private Button m_rewindButton;
    boolean m_pause = false;

    private ShapeRenderer renderer = new ShapeRenderer();

    private AbstractLevel m_level;

    public GameScreen() {
        Constants.SCREEN_RATIO = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
        m_spriteBatch = new SpriteBatch();
        m_camera = new OrthographicCamera();
        m_cameraHelper = new CameraHelper(m_camera);
        m_stage = new Stage(new ScreenViewport(m_camera), m_spriteBatch); // stage overwrites camera's viewport
        Environment.getInstance().currentStage = m_stage;
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

        m_level = LevelFactory.createFromTextFile(Constants.Level.LEVEL_TEST_FILE);
//        m_level = LevelFactory.createStub(1, 1);

        m_player = new CharacterObject();
        m_player.setInitialPosition(m_level.getPlayerPosition().x, m_level.getPlayerPosition().y);
        m_playerController = new ManualController(m_player);

        m_enemies = new Array<CharacterObject>(16);
        for (int i = 0; i < m_level.getEnemiesCount(); ++i) {
            CharacterObject enemy = new CharacterObject();
            Vector2 pos = m_level.getEnemyPosition(i);
            enemy.setInitialPosition(pos.x, pos.y);
            m_stage.addActor(enemy);
            m_enemies.add(enemy);
        }

        m_cameraTarget = new CameraTarget(new GameObject[]{m_player, m_player.getViewPointer()});
        m_cameraTarget.setPosition(m_player.getX(Align.center), m_player.getY(Align.center));
        m_cameraHelper.setTarget(m_cameraTarget);
        m_stage.addActor(m_cameraTarget);

        m_stage.getRoot().addListener(new InputListener() {
            private int m_firstTouchPointer = -1;

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // in case of touchUp skipping m_firstTouchPointer can be locked on not actual value
                // for that case added check for pointer == 0
                if (m_firstTouchPointer == -1 || pointer == 0) // 0 is always first touch
                    m_firstTouchPointer = pointer;
                if (pointer == m_firstTouchPointer)
                    m_playerController.setPointerPosition(x, y);
                else
                    m_playerController.shoot();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (pointer == m_firstTouchPointer)
                    m_firstTouchPointer = -1;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                if (pointer == m_firstTouchPointer)
                    m_playerController.setPointerPosition(x, y);
            }
        });

        Group gui = new Group();
        m_stageGui.addActor(gui);
        m_viewButton = ButtonFactory.create(ButtonFactory.ButtonType.View);
        gui.addActor(m_viewButton);
        m_viewButton.setPosition(
                Constants.VIEW_BUTTON_MARGIN_X + 0,
                Constants.VIEW_BUTTON_MARGIN_Y + m_cameraGui.viewportHeight,
                Align.topLeft
        );
        m_viewButton.setPushAction(new ButtonAction() {
            @Override
            public void run() {
                m_playerController.setCurrentPointerToView();
            }
        });
        m_viewButton.setReleaseAction(new ButtonAction() {
            @Override
            public void run() {
                m_playerController.setCurrentPointerToMovement();
            }
        });

        m_pauseButton = ButtonFactory.create(ButtonFactory.ButtonType.Pause);
        gui.addActor(m_pauseButton);
        m_pauseButton.setPosition(
                Constants.PAUSE_BUTTON_MARGIN_X + 0,
                Constants.PAUSE_BUTTON_MARGIN_Y + m_cameraGui.viewportHeight,
                Align.topLeft
        );
        m_pauseButton.setPushAction(new ButtonAction() {
            @Override
            public void run() {
                togglePause();
            }
        });

        m_rewindButton = ButtonFactory.create(ButtonFactory.ButtonType.Rewind);
        gui.addActor(m_rewindButton);
        m_rewindButton.setPosition(
                Constants.REWIND_BUTTON_MARGIN_X + 0,
                Constants.REWIND_BUTTON_MARGIN_Y + m_cameraGui.viewportHeight,
                Align.topLeft
        );
        m_rewindButton.setPushAction(new ButtonAction() {
            @Override
            public void run() {
                float rewindTime = 1.0f;
                MessageDispatcher.getInstance().sendBroadcast(MessageDispatcher.BroadcastMessageType.Rewind, new RewindContext(rewindTime));
                Environment.getInstance().globalTime -= Math.min(rewindTime, Environment.getInstance().globalTime);
            }
        });

        m_shootButton = ButtonFactory.create(ButtonFactory.ButtonType.Shoot);
        gui.addActor(m_shootButton);
        m_shootButton.setPosition(
                Constants.SHOOT_BUTTON_MARGIN_X + 0,
                Constants.SHOOT_BUTTON_MARGIN_Y + m_cameraGui.viewportHeight,
                Align.topLeft
        );
        m_shootButton.setPushAction(new ButtonAction() {
            @Override
            public void run() {
                m_playerController.shoot();
            }
        });

        Gdx.input.setInputProcessor(new InputMultiplexer(m_stageGui, m_stage));
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        delta = 1 / 60.0f;
        Environment.getInstance().globalTime += delta;

        if (!isPaused()) {
            CollisionManager.getInstance().update(delta);
            m_level.update(delta);
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

        if (Environment.getInstance().debugDrawings) {
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
