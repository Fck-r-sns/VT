package com.vt.game;

/**
 * Created by Fck.r.sns on 08.05.2015.
 */
public class Constants {
    // game geometry ----------------------------------------------------------
    public static float SCREEN_RATIO = 1.0f;
    public static final int ALIGN_ORIGIN = -1;

    public static final float VIEWPORT_WIDTH = 10.0f;
    public static final float PLAYER_WIDTH = 0.75f;
    public static final float PLAYER_HEIGHT = 0.75f;
    public static final float PLAYER_BOUNDING_RADIUS = 0.3f;
    public static final float PLAYER_ORIGIN_RELATIVE_X = 0.25f; // in % of width
    public static final float PLAYER_ORIGIN_RELATIVE_Y = 0.50f; // in % of height
    public static final float MOVEMENT_POINTER_HEIGHT = 0.5f;
    public static final float MOVEMENT_POINTER_WIDTH = 0.5f;
    public static final float VIEW_POINTER_WIDTH = 0.5f;
    public static final float VIEW_POINTER_HEIGHT = 0.5f;
    public static final float RIFLE_SHOOTING_POINT_X = 0.7f - PLAYER_ORIGIN_RELATIVE_X * PLAYER_WIDTH;
    public static final float RIFLE_SHOOTING_POINT_Y = 0.23f - PLAYER_ORIGIN_RELATIVE_Y * PLAYER_HEIGHT;
    public static final float PISTOL_SHOOTING_POINT_X = 0.7f - PLAYER_ORIGIN_RELATIVE_X * PLAYER_WIDTH;
    public static final float PISTOL_SHOOTING_POINT_Y = 0.0f; // relative to origin
    public static final float PROJECTILE_WIDTH = 0.2f;
    public static final float PROJECTILE_HEIGHT = 0.1f;
    public static final float PROJECTILE_BOUNDING_RADIUS = 0.5f * PROJECTILE_HEIGHT;
    public static final float PROJECTILE_ORIGIN_RELATIVE_X = 0.77f;
    public static final float PROJECTILE_ORIGIN_RELATIVE_Y = 0.5f;

    public static final float TILE_SIZE = 1.0f;

    public static final float GUI_VIEWPORT_WIDTH = 1000.0f;
    public static final float GUI_PANEL_WIDTH = 500;
    public static final float GUI_PANEL_HEIGHT = 1200;
    public static final float VIEW_BUTTON_WIDTH = 100.0f;
    public static final float VIEW_BUTTON_HEIGHT = 100.0f;
    public static final float VIEW_BUTTON_MARGIN_X = 20.0f;
    public static final float VIEW_BUTTON_MARGIN_Y = -20.0f;
    public static final float PAUSE_BUTTON_WIDTH = 75.0f;
    public static final float PAUSE_BUTTON_HEIGHT = 75.0f;
    public static final float PAUSE_BUTTON_MARGIN_X = VIEW_BUTTON_MARGIN_X + VIEW_BUTTON_WIDTH + 20.0f;
    public static final float PAUSE_BUTTON_MARGIN_Y = -20.0f;
    public static final float SHOOT_BUTTON_WIDTH = 75.0f;
    public static final float SHOOT_BUTTON_HEIGHT = 75.0f;
    public static final float SHOOT_BUTTON_MARGIN_X = 20.0f;
    public static final float SHOOT_BUTTON_MARGIN_Y = VIEW_BUTTON_MARGIN_Y - VIEW_BUTTON_HEIGHT - 20.0f;
    // ------------------------------------------------------------------------

    // resources --------------------------------------------------------------
    public static final String GUI_ATLAS_PACK = "gui.pack";
    public static final String VIEW_BUTTON_UP_ASSET_NAME = "buttonUp";
    public static final String VIEW_BUTTON_DOWN_ASSET_NAME = "buttonDown";
    public static final String PAUSE_BUTTON_UP_ASSET_NAME = "buttonUp";
    public static final String PAUSE_BUTTON_DOWN_ASSET_NAME = "buttonDown";
    public static final String SHOOT_BUTTON_UP_ASSET_NAME = "buttonUp";
    public static final String SHOOT_BUTTON_DOWN_ASSET_NAME = "buttonDown";

    public static final String TEXTURE_ATLAS_PACK = "entities.pack";
    public static final String PLAYER_ASSET_NAME = "marine_pistol";
    public static final String PLAYER_SHOOTING_ASSET_NAME = "marine_pistol_fire";
    public static final String MOVEMENT_POINTER_ASSET_NAME = "movementPointer";
    public static final String VIEW_POINTER_ASSET_NAME = "viewPointer";
    public static final String PROJECTILE_ASSET_NAME = "projectile";

    public static final float PLAYER_ANIMATION_FRAME_TIME_BASE = 0.35f;
    public static final float PLAYER_SHOOTING_ANIMATION_FRAME_TIME = 0.25f;
    public static final float PLAYER_SHOOTING_ANIMATION_DURATION = 0.6f;

    public static final String TERRAIN_ATLAS_PACK = "tiles.pack";
    public static final String PLAIN_FLOOR_LIGHT_ASSET_NAME = "tile1";
    public static final String PLAIN_FLOOR_WITH_DOTS_LIGHT_ASSET_NAME = "tile2";
    public static final String PLAIN_FLOOR_DARK_ASSET_NAME = "tile16";
    public static final String PLAIN_FLOOR_WITH_DOTS_DARK_ASSET_NAME = "tile17";
    public static final String WALL_STRIPED_CLEAN = "tile10";
    public static final String WALL_STRIPED_DIRTY = "tile8";
    public static final String WALL_ROCKY = "tile19";
    public static final String BOX_GREY_ROWS = "tile7";
    public static final String BOX_GREY_CROSS = "tile6";
    public static final String BOX_BROWN_ROWS = "tile4";
    public static final String BOX_BROWN_CROSS = "tile3";
    // ------------------------------------------------------------------------

    // actors -----------------------------------------------------------------
    public final static String PLAYER_ACTOR_NAME = "plr";
    public final static String MOVEMENT_POINTER_ACTOR_NAME = "mp";
    public final static String VIEW_POINTER_ACTOR_NAME = "vp";
    public final static String CAMERA_TARGET_ACTOR_NAME = "ct";

    public final static String VIEW_BUTTON_ACTOR_NAME = "vb";
    public final static String PAUSE_BUTTON_ACTOR_NAME = "pb";
    public final static String SHOOT_BUTTON_ACTOR_NAME = "sb";
    // ------------------------------------------------------------------------

    // physics ----------------------------------------------------------------
    public final static float MAX_LINEAR_SPEED_DEFAULT = 2.5f;
    public final static float MAX_LINEAR_ACCELERATION_DEFAULT = 4.0f;
    public final static float MAX_ANGULAR_SPEED_DEFAULT = 30;
    public final static float MAX_ANGULAR_ACCELERATION_DEFAULT = 30;

    public final static float MAX_PLAYER_LINEAR_SPEED = 2.0f; // meters per second (4 m/s = 14.4 km/h)
    public final static float MAX_PLAYER_LINEAR_ACCELERATION = 6.0f;
    public final static float MAX_PLAYER_ANGULAR_SPEED = 30;
    public final static float MAX_PLAYER_ANGULAR_ACCELERATION = 30;

    public final static float PLAYER_ARRIVAL_TOLERANCE = 0.08f;
    public final static float PLAYER_ARRIVAL_DECELERATION_RADIUS = 1.0f;

    public final static float MAX_CAMERA_LINEAR_SPEED_DEFAULT = 8;
    public final static float MAX_CAMERA_LINEAR_ACCELERATION_DEFAULT = 14;

    public final static float CAMERA_ARRIVAL_TOLERANCE = 0.02f;
    public final static float CAMERA_ARRIVAL_DECELERATION_RADIUS = 1.6f;
    // ------------------------------------------------------------------------

    // terrain ----------------------------------------------------------------
    static public class Level {
        public final static String LEVEL_TEST_FILE = "levels/test";

        public final static int FLOOR_CODE = '0';
        public final static int WALL_CODE = '1';
        public final static int PLAYER_START_POS_CODE = 'S';
        public final static int ENEMY_START_POS_CODE = 'E';
    }
    // ------------------------------------------------------------------------
}
