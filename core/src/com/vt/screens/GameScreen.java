package com.vt.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.vt.game.Constants;
import com.vt.gameobjects.GameObject;
import com.vt.resources.Assets;

/**
 * Created by Fck.r.sns on 04.05.2015.
 */
public class GameScreen implements Screen {
    private OrthographicCamera m_camera;
    private SpriteBatch m_spriteBatch;
    private Stage m_stage;
    private Actor m_actor;
    private Actor m_movementPointer;

    public GameScreen() {
        m_camera = new OrthographicCamera();
        m_spriteBatch = new SpriteBatch();
        m_stage = new Stage(new ScreenViewport(), m_spriteBatch);
        Assets.getInstance().init();

        GameObject obj = new GameObject();
        obj.setSize(128, 128);
        obj.setOrigin(Align.center);
        obj.setPosition((Gdx.graphics.getWidth() - obj.getWidth()) / 2,
                (Gdx.graphics.getHeight() - obj.getHeight()) / 2);
        obj.setTexture(Assets.getInstance().player);
        m_actor = obj;
        m_actor.setName(Constants.PLAYER_ACTOR_NAME);

        m_stage.addActor(m_actor);

        obj = new GameObject();
        obj.setSize(128, 128);
        obj.setOrigin(Align.center);
        obj.setTexture(Assets.getInstance().movementPointer);
        m_movementPointer = obj;
        m_movementPointer.setName(Constants.MOVEMENT_POINTER_ACTOR_NAME);
        m_movementPointer.setVisible(false);
        m_stage.addActor(m_movementPointer);

        m_stage.getRoot().addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                m_movementPointer.setVisible(true);
                m_movementPointer.setPosition(x, y, Align.center);
                return true;
            }

            @Override
            public void touchDragged (InputEvent event, float x, float y, int pointer) {
                m_movementPointer.setPosition(x, y, Align.center);
            }
        });
        Gdx.input.setInputProcessor(m_stage);
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
        m_actor.setRotation(m_actor.getRotation() + delta * 100);
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
