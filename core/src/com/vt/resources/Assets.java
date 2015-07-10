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
    public class Gui {
        public TextureAtlas.AtlasRegion viewButtonUp;
        public TextureAtlas.AtlasRegion viewButtonDown;
        public TextureAtlas.AtlasRegion pauseButtonUp;
        public TextureAtlas.AtlasRegion pauseButtonDown;
    }

    public class GameEntities {
        public TextureAtlas.AtlasRegion player;
        public TextureAtlas.AtlasRegion movementPointer;
        public TextureAtlas.AtlasRegion viewPointer;
        public TextureAtlas.AtlasRegion rifle;
    }

    public class Terrain {
        public TextureAtlas.AtlasRegion plainFloorLight;
        public TextureAtlas.AtlasRegion plainFloorWithDotsLight;
        public TextureAtlas.AtlasRegion plainFloorDark;
        public TextureAtlas.AtlasRegion plainFloorWithDotsDark;
        public TextureAtlas.AtlasRegion wallStripedClean;
        public TextureAtlas.AtlasRegion wallStripedDirty;
        public TextureAtlas.AtlasRegion wallRocky;
        public TextureAtlas.AtlasRegion boxGreyRows;
        public TextureAtlas.AtlasRegion boxGreyCross;
        public TextureAtlas.AtlasRegion boxBrownRows;
        public TextureAtlas.AtlasRegion boxBrownCross;

    }

    public GameEntities gameEntities;
    public Gui gui;
    public Terrain terrain;

    private static final String TAG = Assets.class.getName();
    private static final Assets m_instance = new Assets();
    private AssetManager m_assetsManager;

    private Assets() {}

    public static Assets getInstance() {
        return m_instance;
    }

    public void init() {
        m_assetsManager = new AssetManager();
        m_assetsManager.setErrorListener(this);
        m_assetsManager.load(Constants.TEXTURE_ATLAS_PACK, TextureAtlas.class);
        m_assetsManager.load(Constants.GUI_ATLAS_PACK, TextureAtlas.class);
        m_assetsManager.load(Constants.TERRAIN_ATLAS_PACK, TextureAtlas.class);
        m_assetsManager.finishLoading();

        TextureAtlas atlas = m_assetsManager.get(Constants.TEXTURE_ATLAS_PACK);
        for (Texture t : atlas.getTextures())
            t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        gameEntities = new GameEntities();
        gameEntities.player = atlas.findRegion(Constants.PLAYER_ASSET_NAME);
        gameEntities.movementPointer = atlas.findRegion(Constants.MOVEMENT_POINTER_ASSET_NAME);
        gameEntities.viewPointer = atlas.findRegion(Constants.VIEW_POINTER_ASSET_NAME);
        gameEntities.rifle = atlas.findRegion(Constants.RIFLE_ASSET_NAME);

        atlas = m_assetsManager.get(Constants.GUI_ATLAS_PACK);
        for (Texture t : atlas.getTextures())
            t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        gui = new Gui();
        gui.viewButtonUp = atlas.findRegion(Constants.VIEW_BUTTON_UP_ASSET_NAME);
        gui.viewButtonDown = atlas.findRegion(Constants.VIEW_BUTTON_DOWN_ASSET_NAME);
        gui.pauseButtonUp = atlas.findRegion(Constants.PAUSE_BUTTON_UP_ASSET_NAME);
        gui.pauseButtonDown = atlas.findRegion(Constants.PAUSE_BUTTON_DOWN_ASSET_NAME);

        atlas = m_assetsManager.get(Constants.TERRAIN_ATLAS_PACK);
        for (Texture t : atlas.getTextures())
            t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        terrain = new Terrain();
        terrain.plainFloorLight = atlas.findRegion(Constants.PLAIN_FLOOR_LIGHT_ASSET_NAME);
        terrain.plainFloorWithDotsLight = atlas.findRegion(Constants.PLAIN_FLOOR_WITH_DOTS_LIGHT_ASSET_NAME);
        terrain.plainFloorDark = atlas.findRegion(Constants.PLAIN_FLOOR_DARK_ASSET_NAME);
        terrain.plainFloorWithDotsDark = atlas.findRegion(Constants.PLAIN_FLOOR_WITH_DOTS_DARK_ASSET_NAME);
        terrain.wallStripedClean = atlas.findRegion(Constants.WALL_STRIPED_CLEAN);
        terrain.wallStripedDirty = atlas.findRegion(Constants.WALL_STRIPED_DIRTY);
        terrain.wallRocky = atlas.findRegion(Constants.WALL_ROCKY);
        terrain.boxGreyRows = atlas.findRegion(Constants.BOX_GREY_ROWS);
        terrain.boxGreyCross = atlas.findRegion(Constants.BOX_GREY_CROSS);
        terrain.boxBrownRows = atlas.findRegion(Constants.BOX_BROWN_ROWS);
        terrain.boxBrownCross = atlas.findRegion(Constants.BOX_BROWN_CROSS);
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Asset wasn't loaded", throwable);
    }

    @Override
    public void dispose() {
        m_assetsManager.dispose();
    }
}
