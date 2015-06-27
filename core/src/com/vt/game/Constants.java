package com.vt.game;

/**
 * Created by Fck.r.sns on 08.05.2015.
 */
public class Constants {
    // game geometry ----------------------------------------------------------
    public static final float VIEWPORT_WIDTH = 10.0f;
    public static final float PLAYER_WIDTH = 0.75f;
    public static final float PLAYER_HEIGHT = 0.75f;
    public static final float PLAYER_ORIGIN_X = 0.25f; // in % of width
    public static final float PLAYER_ORIGIN_Y = 0.50f; // in % of height
    public static final float MOVEMENT_POINTER_HEIGHT = 0.5f;
    public static final float MOVEMENT_POINTER_WIDTH = 0.5f;
    public static final float VIEW_POINTER_WIDTH = 0.5f;
    public static final float VIEW_POINTER_HEIGHT = 0.5f;

    public static final float GUI_PANEL_WIDTH = 500;
    public static final float GUI_PANEL_HEIGHT = 1200;
    public static final float BUTTON_WIDTH = 1.0f;
    public static final float BUTTON_HEIGHT = 1.0f;

    public static final float TILE_SIZE = 1.0f;
    // ------------------------------------------------------------------------

    // resources --------------------------------------------------------------
    public static final String GUI_ATLAS_PACK = "gui.pack";
    public static final String BUTTON_UP_ASSET_NAME = "buttonUp";
    public static final String BUTTON_DOWN_ASSET_NAME = "buttonDown";
    public static final String GUI_SKIN = "gui.json";

    public static final String TEXTURE_ATLAS_PACK = "VT.pack";
    public static final String PLAYER_ASSET_NAME = "marine_rifle";
    public static final String MOVEMENT_POINTER_ASSET_NAME = "movementPointer";
    public static final String VIEW_POINTER_ASSET_NAME = "viewPointer";

    public static final String TERRAIN_ATLAS_PACK = "tiles.pack";
    public static final String PLAIN_FLOOR_LIGHT_ASSET_NAME = "tile1";
    public static final String PLAIN_FLOOR_WITH_DOTS_LIGHT_ASSET_NAME = "tile2";
    public static final String PLAIN_FLOOR_DARK_ASSET_NAME = "tile16";
    public static final String PLAIN_FLOOR_WITH_DOTS_DARK_ASSET_NAME = "tile17";
    // ------------------------------------------------------------------------

    // actors -----------------------------------------------------------------
    public final static String PLAYER_ACTOR_NAME = "plr";
    public final static String MOVEMENT_POINTER_ACTOR_NAME = "mp";
    public final static String VIEW_POINTER_ACTOR_NAME = "vp";
    public final static String CAMERA_TARGET_ACTOR_NAME = "ct";

    public final static String GUI_BUTTON_ACTOR_NAME = "button";
    // ------------------------------------------------------------------------

    // physics ----------------------------------------------------------------
    public final static float MAX_LINEAR_SPEED_DEFAULT = 5;
    public final static float MAX_LINEAR_ACCELERATION_DEFAULT = 10;
    public final static float MAX_ANGULAR_SPEED_DEFAULT = 5;
    public final static float MAX_ANGULAR_ACCELERATION_DEFAULT = 10;

    public final static float MAX_CAMERA_LINEAR_SPEED_DEFAULT = 8;
    public final static float MAX_CAMERA_LINEAR_ACCELERATION_DEFAULT = 14;

    public final static float PLAYER_ARRIVAL_TOLERANCE = 0.02f;
    public final static float PLAYER_ARRIVAL_DECELERATION_RADIUS = 1.6f;
    // ------------------------------------------------------------------------
}
