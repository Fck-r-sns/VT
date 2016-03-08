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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.vt.gameobjects.CameraHelper;
import com.vt.game.Constants;
import com.vt.game.Environment;
import com.vt.gameobjects.CameraTarget;
import com.vt.gameobjects.GameObject;
import com.vt.gameobjects.TouchHandler;
import com.vt.gameobjects.actionqueue.ActionQueue;
import com.vt.gameobjects.characters.CharacterObject;
import com.vt.gameobjects.characters.ManualController;
import com.vt.gameobjects.gui.Button;
import com.vt.gameobjects.gui.ButtonAction;
import com.vt.gameobjects.gui.ButtonFactory;
import com.vt.gameobjects.primitives.DrawableLine;
import com.vt.gameobjects.primitives.DrawableRectangle;
import com.vt.gameobjects.terrain.levels.AbstractLevel;
import com.vt.gameobjects.terrain.levels.LevelFactory;
import com.vt.messages.Context;
import com.vt.messages.MessageDispatcher;
import com.vt.messages.MessageHandler;
import com.vt.messages.RewindContext;
import com.vt.physics.CollisionManager;
import com.vt.physics.geometry.LineSegment;
import com.vt.resources.Assets;
import com.vt.utils.DumbProfiler;

import java.util.EnumMap;

/**
 * Created by Fck.r.sns on 04.05.2015.
 */
public class GameScreen implements Screen {
    private OrthographicCamera m_camera;
    private OrthographicCamera m_cameraGui;
    private CameraHelper m_cameraHelper;
    private SpriteBatch m_spriteBatch;
    private ShapeRenderer m_renderer;
    private Stage m_stage;
    private Stage m_stageGui;
    private CharacterObject m_player;
    private ManualController m_playerController;
    private ActionQueue m_actionQueue;
    private ActionQueue.Controller m_actionQueueController;
    private EnumMap<Environment.TimeState, TouchHandler> m_touchHandlers;
    private Array<CharacterObject> m_enemies;
    private CameraTarget m_cameraTarget;
    private Button m_viewButton;
    private Button m_pauseButton;
    private Button m_shootButton;
    private Button m_rewindButton;

    private DumbProfiler m_worldProfiler = new DumbProfiler("GameScreen", 60);
    private DumbProfiler m_renderingProfiler = new DumbProfiler("GameScreen", 60);

    private ObjectSet<DrawableRectangle> m_spatialHashGrid;

    private AbstractLevel m_level;

    public GameScreen() {
        m_worldProfiler.start("Initialisation");
        Constants.SCREEN_RATIO = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
        m_renderer = new ShapeRenderer();
        m_spriteBatch = new SpriteBatch();
        m_camera = new OrthographicCamera();
        m_cameraHelper = new CameraHelper(m_camera);
        m_stage = new Stage(new ScreenViewport(m_camera), m_spriteBatch); // stage overwrites camera's viewport
        m_cameraHelper.setZoom(1.5f);

        Environment env = Environment.getInstance();
        env.currentStage = m_stage;
        env.globalTime = -1.0f;
        env.gameTime = -1.0f;
        env.rewindableTime = -1.0f;

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
//        m_level = LevelFactory.createFromTextFile(Constants.Level.RAYCASTING_TEST_FILE);

        m_player = new CharacterObject();
        m_player.setInitialPosition(m_level.getPlayerPosition().x, m_level.getPlayerPosition().y);
        m_playerController = new ManualController(m_player);

        m_actionQueue = new ActionQueue(m_player);
        m_actionQueueController = m_actionQueue.new Controller();
        MessageDispatcher.getInstance().subscribeToBroadcast(MessageDispatcher.BroadcastMessageType.TouchDownWithRealTime, new MessageHandler() {
            @Override
            public void onMessageReceived(Context ctx) {
                m_actionQueue.clear();
            }
        });
        MessageDispatcher.getInstance().subscribeToBroadcast(MessageDispatcher.BroadcastMessageType.Rewind, new MessageHandler() {
            @Override
            public void onMessageReceived(Context ctx) {
                m_actionQueue.clear();
            }
        });

        m_touchHandlers = new EnumMap<Environment.TimeState, TouchHandler>(Environment.TimeState.class);
        m_touchHandlers.put(Environment.TimeState.RealTime, m_playerController);
        m_touchHandlers.put(Environment.TimeState.ActivePause, m_actionQueueController);

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
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return m_touchHandlers.get(Environment.getInstance().getTimeState())
                        .handleTouchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                m_touchHandlers.get(Environment.getInstance().getTimeState())
                        .handleTouchUp(event, x, y, pointer, button);
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                m_touchHandlers.get(Environment.getInstance().getTimeState())
                        .handleTouchDragged(event, x, y, pointer);
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
                m_actionQueueController.setCurrentPointerToView();
            }
        });
        m_viewButton.setReleaseAction(new ButtonAction() {
            @Override
            public void run() {
                m_playerController.setCurrentPointerToMovement();
                m_actionQueueController.setCurrentPointerToMovement();
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
                Environment.getInstance().togglePaused();
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
                startRewinding(Constants.REWIND_TIME);
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

        m_spatialHashGrid = new ObjectSet<DrawableRectangle>(64);
        for (int x = 0; x < 10; ++x) {
            for (int y = 0; y < 10; ++y) {
                m_spatialHashGrid.add(new DrawableRectangle(
                        x * Constants.SPATIAL_HASH_TABLE_BUCKET_WIDTH,
                        y * Constants.SPATIAL_HASH_TABLE_BUCKET_HEIGHT,
                        Constants.SPATIAL_HASH_TABLE_BUCKET_WIDTH,
                        Constants.SPATIAL_HASH_TABLE_BUCKET_HEIGHT,
                        Assets.getInstance().gui.blueVector,
                        0.03f
                ));
            }
        }

        m_worldProfiler.process();

        // here is the game starts
        env.globalTime = 0.0f;
        env.gameTime = 0.0f;
        env.rewindableTime = 0.0f;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Environment env = Environment.getInstance();
        ++env.frameCounter;
        boolean paused = env.isPaused();
        boolean rewinding = env.isRewinding();
        delta = 1 / 60.0f;
        env.globalTime += delta;

        m_worldProfiler.start("Update state");
        if (rewinding) {
            if (env.gameTime <= env.getRewindTargetTime() || env.rewindableTime <= 0.0f) {
                stopRewinding();
                rewinding = false;
            } else {
                float rewindTime = delta * (Constants.REWIND_SPEED_MULTIPLIER + 1); // +1 to compensate real time going forward
                MessageDispatcher.getInstance().sendBroadcast(MessageDispatcher.BroadcastMessageType.Rewind, new RewindContext(rewindTime));
                env.gameTime -= Math.min(rewindTime, env.gameTime);
                env.rewindableTime -= rewindTime;
            }
        }
        if (rewinding || !paused) {
            if (!rewinding) {
                env.gameTime += delta;
                env.rewindableTime = Math.min(env.rewindableTime + delta, Constants.MAX_HISTORY_TIME);
                CollisionManager.getInstance().checkCollisions(delta);
            }
            m_level.update(delta);
            m_cameraHelper.update(delta);
            m_actionQueue.act(delta);
            m_stage.act(delta);
        }

        m_stageGui.act(delta); // gui updates even on pause (gui animation and others)

        m_worldProfiler.process();

        m_renderingProfiler.start("Rendering");
        m_camera.update(false);
        m_spriteBatch.setProjectionMatrix(m_camera.combined);
        m_renderer.setProjectionMatrix(m_camera.combined);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        m_spriteBatch.begin();
        m_level.draw(m_spriteBatch);
        m_actionQueue.draw(m_spriteBatch);
        for (DrawableRectangle r : m_spatialHashGrid) {
            r.draw(m_spriteBatch);
        }
        {
//            ObjectMap<SpatialHash, ObjectSet<LineSegment>> lines = CollisionManager.getInstance().getStaticLineSegments().getData();
//            for (ObjectSet<LineSegment> b : lines.values()) {
//                for (LineSegment s : b) {
//                    new DrawableLine(s.p1.x, s.p1.y, s.p2.x, s.p2.y, Assets.getInstance().gui.redVector, 0.05f).draw(m_spriteBatch);
//                }
//            }

            ObjectSet<LineSegment> b = CollisionManager.getInstance().getStaticLineSegments().getBucket(m_player.spatialHash());
            for (LineSegment s : b) {
                new DrawableLine(s.p1.x, s.p1.y, s.p2.x, s.p2.y, Assets.getInstance().gui.redVector, 0.08f).draw(m_spriteBatch);
            }

        }
        m_spriteBatch.end();
        m_stage.draw();

        m_cameraGui.update(false);
        m_spriteBatch.setProjectionMatrix(m_cameraGui.combined);
        m_stageGui.draw();
        m_spriteBatch.begin();
        Assets.getInstance().gui.font.draw(
                m_spriteBatch,
                String.format("%.2f", Environment.getInstance().gameTime),
                m_cameraGui.viewportWidth - Constants.TIME_LABEL_MARGIN_X,
                m_cameraGui.viewportHeight - Constants.TIME_LABEL_MARGIN_Y
        );
        Assets.getInstance().gui.font.draw(
                m_spriteBatch,
                "FPS: " + Gdx.graphics.getFramesPerSecond(),
                m_cameraGui.viewportWidth - Constants.FPS_LABEL_MARGIN_X,
                m_cameraGui.viewportHeight - Constants.FPS_LABEL_MARGIN_Y
        );
        m_spriteBatch.end();

        if (env.debugDrawings) {
            Circle c = m_player.getBoundingShape();
            m_renderer.begin(ShapeRenderer.ShapeType.Line);
            m_renderer.setColor(1, 1, 1, 1);
            m_renderer.rect(m_player.getX(), m_player.getY(), m_player.getOriginX(), m_player.getOriginY(),
                    m_player.getWidth(), m_player.getHeight(), 1, 1, m_player.getRotation());
            m_renderer.circle(c.x, c.y, c.radius, 20);
            m_renderer.end();
        }

        m_renderingProfiler.process();
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

    private void startRewinding(float rewindingTime) {
        Environment env = Environment.getInstance();
        env.setRewinding(true);
        env.setRewindTargetTime(Environment.getInstance().gameTime - rewindingTime);
    }

    private void stopRewinding() {
        Environment env = Environment.getInstance();
        env.setRewinding(false);
        env.setPaused(true);
        env.rewindableTime = Math.max(env.rewindableTime, 0.0f);
    }
}
