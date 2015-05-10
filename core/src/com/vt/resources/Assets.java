package com.vt.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.vt.game.Constants;

/**
 * Created by Fck.r.sns on 08.05.2015.
 */
public class Assets implements Disposable, AssetErrorListener {
    private static final String LOG_CONTEXT = Assets.class.getName();
    private static final Assets m_instance = new Assets();
    private AssetManager m_assetsManager;

    private Assets() {}

    public TextureAtlas.AtlasRegion player;
    public TextureAtlas.AtlasRegion movementPointer;
    public TextureAtlas.AtlasRegion firePointer;

    public static Assets getInstance() {
        return m_instance;
    }

    public void init() {
        m_assetsManager = new AssetManager();
        m_assetsManager.setErrorListener(this);
        m_assetsManager.load(Constants.TEXTURE_ATLAS_PACK, TextureAtlas.class);
        m_assetsManager.finishLoading();

        TextureAtlas atlas = m_assetsManager.get(Constants.TEXTURE_ATLAS_PACK);
        for (Texture t : atlas.getTextures())
            t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        player = atlas.findRegion(Constants.PLAYER_ASSET_NAME);
        movementPointer = atlas.findRegion(Constants.MOVEMENT_POINTER_ASSET_NAME);
        firePointer = atlas.findRegion(Constants.FIRE_POINTER_ASSET_NAME);
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(LOG_CONTEXT, "Asset wasn't loaded", throwable);
    }

    @Override
    public void dispose() {
        m_assetsManager.dispose();
    }
}