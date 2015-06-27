package com.vt.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.vt.game.CameraHelper;
import com.vt.game.Constants;
import com.vt.gameobjects.CameraTarget;
import com.vt.gameobjects.GameObject;
import com.vt.gameobjects.MovementPointer;
import com.vt.gameobjects.PlayerObject;
import com.vt.gameobjects.ViewPointer;
import com.vt.gameobjects.gui.Button;
import com.vt.gameobjects.terrain.Floor;
import com.vt.gameobjects.terrain.Level;
import com.vt.gameobjects.terrain.StubLevel;
import com.vt.resources.Assets;

/**
 * Created by Fck.r.sns on 04.05.2015.
 */
public class GameScreen implements Screen {
    private OrthographicCamera m_camera;
    private CameraHelper m_cameraHelper;
    private SpriteBatch m_spriteBatch;
    private Stage m_stage;
    private PlayerObject m_player;
    private MovementPointer m_movementPointer;
    private ViewPointer m_viewPointer;
    private CameraTarget m_cameraTarget;
    private Button m_button;

    private Level m_level;

    public GameScreen() {
        m_spriteBatch = new SpriteBatch();
        m_camera = new OrthographicCamera();
        m_cameraHelper = new CameraHelper(m_camera);
        m_stage = new Stage(new ScreenViewport(m_camera), m_spriteBatch); // stage overwrites camera's viewport
        m_camera.setToOrtho(false,
                Constants.VIEWPORT_WIDTH,
                Constants.VIEWPORT_WIDTH
                        * Gdx.graphics.getHeight()
                        / Gdx.graphics.getWidth());
        m_camera.update();
        Assets.getInstance().init();

        m_level = new StubLevel(10, 10);

        m_movementPointer = new MovementPointer();
        m_movementPointer.setPosition((m_camera.viewportWidth - m_movementPointer.getWidth()) / 2,
                (m_camera.viewportHeight - m_movementPointer.getHeight()) / 2);
        m_stage.addActor(this.m_movementPointer);

        m_viewPointer = new ViewPointer();
        m_viewPointer.setPosition((m_camera.viewportWidth - m_viewPointer.getWidth()) / 2,
                (m_camera.viewportHeight - m_viewPointer.getHeight()) / 2);
        m_stage.addActor(this.m_viewPointer);

        m_player = new PlayerObject(m_movementPointer, m_viewPointer);
        m_player.setPosition((m_camera.viewportWidth - m_player.getWidth()) / 2,
                (m_camera.viewportHeight - m_player.getHeight()) / 2);
        m_stage.addActor(this.m_player);

        m_cameraTarget = new CameraTarget(new GameObject[]{m_player, m_viewPointer});
        m_cameraTarget.setPosition(m_player.getX(Align.center), m_player.getY(Align.center));
        m_cameraHelper.setTarget(m_cameraTarget);
        m_stage.addActor(m_cameraTarget);

        m_stage.getRoot().addListener(m_movementPointer.getController().setActive(true));
        m_stage.getRoot().addListener(m_viewPointer.getController().setActive(false));
        Gdx.input.setInputProcessor(m_stage);

        Group gui = new Group();
        m_button = new Button();
        gui.addActor(m_button);
        m_button.setPosition(0, m_camera.viewportHeight, Align.topLeft);
        m_button.addCaptureListener(new InputListener() {
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
        m_stage.addActor(gui);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        m_cameraHelper.update(delta);
        m_camera.update(false);
        m_spriteBatch.setProjectionMatrix(m_camera.combined);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        m_spriteBatch.begin();
        m_level.draw(m_spriteBatch);
        m_spriteBatch.end();
        m_stage.act(delta);
        m_stage.draw();
    }

    @Override
    public void resize(int width, int height) {
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
