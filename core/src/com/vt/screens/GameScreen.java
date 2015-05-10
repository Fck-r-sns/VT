package com.vt.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.vt.game.Constants;
import com.vt.gameobjects.ActingGameObject;
import com.vt.gameobjects.MovementPointer;
import com.vt.resources.Assets;

/**
 * Created by Fck.r.sns on 04.05.2015.
 */
public class GameScreen implements Screen {
    private OrthographicCamera m_camera;
    private SpriteBatch m_spriteBatch;
    private Stage m_stage;
    private ActingGameObject m_player;
    private MovementPointer m_movementPointer;

    public GameScreen() {
        m_camera = new OrthographicCamera();
        m_spriteBatch = new SpriteBatch();
        m_stage = new Stage(new ScreenViewport(), m_spriteBatch);
        Assets.getInstance().init();

        m_player = new ActingGameObject();
        m_player.setSize(128, 128);
        m_player.setOrigin(Align.center);
        m_player.setPosition((Gdx.graphics.getWidth() - m_player.getWidth()) / 2,
                (Gdx.graphics.getHeight() - m_player.getHeight()) / 2);
        m_player.setTexture(Assets.getInstance().player);
        this.m_player.setName(Constants.PLAYER_ACTOR_NAME);
        m_stage.addActor(this.m_player);

        m_movementPointer = new MovementPointer();
        m_movementPointer.setSize(128, 128);
        m_movementPointer.setOrigin(Align.center);
        m_movementPointer.setPosition((Gdx.graphics.getWidth() - m_movementPointer.getWidth()) / 2,
                (Gdx.graphics.getHeight() - m_movementPointer.getHeight()) / 2);
        m_movementPointer.setTexture(Assets.getInstance().movementPointer);
        this.m_movementPointer.setName(Constants.MOVEMENT_POINTER_ACTOR_NAME);
        this.m_movementPointer.setVisible(false);
        m_stage.addActor(this.m_movementPointer);

        m_stage.getRoot().addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameScreen.this.m_movementPointer.setVisible(true);
                GameScreen.this.m_movementPointer.setPosition(x, y, Align.center);
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                GameScreen.this.m_movementPointer.setPosition(x, y, Align.center);
            }
        });
        Gdx.input.setInputProcessor(m_stage);

        m_player.setBehavior(new Seek<Vector2>(m_player, m_movementPointer));
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        m_stage.act(delta);
        m_stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        m_camera.setToOrtho(true, width, height);
        m_camera.update();
        m_spriteBatch.setProjectionMatrix(m_camera.combined);
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
        Assets.getInstance().dispose();
        m_stage.dispose();
    }
}
