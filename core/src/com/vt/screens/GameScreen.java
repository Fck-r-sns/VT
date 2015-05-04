package com.vt.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.vt.gameobjects.GameObject;

/**
 * Created by Fck.r.sns on 04.05.2015.
 */
public class GameScreen implements Screen {
    private OrthographicCamera m_camera;
    private SpriteBatch m_spriteBatch;
    private Stage m_stage;
    private Actor m_actor;
    private Actor m_movingCrosshair;
    private Actor m_shootingCrosshair;

    private final static String PLAYER_ACTOR_NAME = "plr";
    private final static String MOVING_CROSSHAIR_ACTOR_NAME = "mc";
    private final static String SHOOTING_CROSSHAIR_ACTOR_NAME = "sc";

    public GameScreen() {
        m_camera = new OrthographicCamera();
        m_spriteBatch = new SpriteBatch();
        m_stage = new Stage(new ScreenViewport(), m_spriteBatch);
        Texture texture = new Texture(Gdx.files.internal("circleWithPointer_white.png"));
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        GameObject obj = new GameObject();
        obj.setSize(128, 128);
        obj.setOrigin(Align.center);
        obj.setPosition((Gdx.graphics.getWidth() - obj.getWidth()) / 2,
                (Gdx.graphics.getHeight() - obj.getHeight()) / 2);
        obj.setTexture(new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight()));
        m_actor = obj;
        m_actor.setName(PLAYER_ACTOR_NAME);

        m_stage.addActor(m_actor);

        obj = new GameObject();
        obj.setSize(128, 128);
        obj.setOrigin(Align.center);
        texture = new Texture(Gdx.files.internal("movingCrosshair.png"));
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        obj.setTexture(new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight()));
        m_movingCrosshair = obj;
        m_movingCrosshair.setName(MOVING_CROSSHAIR_ACTOR_NAME);
        m_stage.addActor(m_movingCrosshair);

        m_stage.getRoot().addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                m_stage.getRoot().findActor(MOVING_CROSSHAIR_ACTOR_NAME).setPosition(x, y, Align.center);
                return true;
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
        Actor actor = m_stage.getRoot().findActor(MOVING_CROSSHAIR_ACTOR_NAME);
//        actor.setPosition(actor.getX() + delta * 300, actor.getY() + delta * 300);
        m_actor.setRotation(m_actor.getRotation() + delta * 100);
    }

    @Override
    public void resize(int width, int height) {
        m_camera.setToOrtho(true, width, height);
        m_spriteBatch.setProjectionMatrix(m_camera.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
