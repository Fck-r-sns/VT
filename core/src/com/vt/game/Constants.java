package com.vt.game;

import com.badlogic.gdx.graphics.Color;

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
    public static final float PLAYER_ORIGIN_RELATIVE_X = 0.50f; // in % of width
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

    public static final float PAUSE_BUTTON_WIDTH = 100.0f;
    public static final float PAUSE_BUTTON_HEIGHT = 100.0f;
    public static final float PAUSE_BUTTON_MARGIN_X = 10.0f;
    public static final float PAUSE_BUTTON_MARGIN_Y = -10.0f;
    public static final float VIEW_BUTTON_WIDTH = 90.0f;
    public static final float VIEW_BUTTON_HEIGHT = 90.0f;
    public static final float VIEW_BUTTON_MARGIN_X = 10.0f;
    public static final float VIEW_BUTTON_MARGIN_Y = PAUSE_BUTTON_MARGIN_Y - PAUSE_BUTTON_HEIGHT - 10.0f;
    public static final float SHOOT_BUTTON_WIDTH = 80.0f;
    public static final float SHOOT_BUTTON_HEIGHT = 80.0f;
    public static final float SHOOT_BUTTON_MARGIN_X = 10.0f;
    public static final float SHOOT_BUTTON_MARGIN_Y = VIEW_BUTTON_MARGIN_Y - VIEW_BUTTON_HEIGHT - 10.0f;
    public static final float REWIND_BUTTON_WIDTH = 80.0f;
    public static final float REWIND_BUTTON_HEIGHT = 80.0f;
    public static final float REWIND_BUTTON_MARGIN_X = PAUSE_BUTTON_MARGIN_X + PAUSE_BUTTON_WIDTH + 10.0f;
    public static final float REWIND_BUTTON_MARGIN_Y = -10.0f;


    public static final float TIME_LABEL_MARGIN_X = 200.0f;
    public static final float TIME_LABEL_MARGIN_Y = 10.0f;
    public static final float FPS_LABEL_MARGIN_X = 200.0f;
    public static final float FPS_LABEL_MARGIN_Y = 10.0f + TIME_LABEL_MARGIN_Y + 32.0f;
    // ------------------------------------------------------------------------

    // action queue -----------------------------------------------------------
    public static final Color MOVEMENT_POINTER_VECTOR_COLOR = Color.BLUE;
    public static final float MOVEMENT_POINTER_VECTOR_WIDTH = 0.05f;
    public static final Color VIEW_POINTER_VECTOR_COLOR = Color.RED;
    public static final float VIEW_POINTER_VECTOR_WIDTH = 0.05f;
    // ------------------------------------------------------------------------

    // resources --------------------------------------------------------------
    public static final String GUI_ATLAS_PACK = "gui.pack";
    public static final String RED_VECTOR_BASE_ASSET_NAME = "redPixel";
    public static final String BLUE_VECTOR_BASE_ASSET_NAME = "bluePixel";
    public static final String VIEW_BUTTON_UP_ASSET_NAME = "buttonUp";
    public static final String VIEW_BUTTON_DOWN_ASSET_NAME = "buttonDown";
    public static final String PAUSE_BUTTON_UP_ASSET_NAME = "buttonUp";
    public static final String PAUSE_BUTTON_DOWN_ASSET_NAME = "buttonDown";
    public static final String SHOOT_BUTTON_UP_ASSET_NAME = "buttonUp";
    public static final String SHOOT_BUTTON_DOWN_ASSET_NAME = "buttonDown";
    public static final String REWIND_BUTTON_UP_ASSET_NAME = "buttonUp";
    public static final String REWIND_BUTTON_DOWN_ASSET_NAME = "buttonDown";
    public static final String ARIAL_32_ASSET_NAME = "fonts/arial_32.fnt";

    public static final String TEXTURE_ATLAS_PACK = "entities.pack";
    public static final String CIRCLE_WITH_POINTER_ASSET_NAME = "circleWithPointer";
    public static final String MOVEMENT_POINTER_ASSET_NAME = "movementPointer";
    public static final String VIEW_POINTER_ASSET_NAME = "viewPointer";
    public static final String PROJECTILE_ASSET_NAME = "projectile";

    public static final String TERRAIN_ATLAS_PACK = "tiles.pack";
    public static final String SIMPLE_FLOOR_ASSET_NAME = "tile_floor";
    public static final String SIMPLE_WALL_ASSET_NAME = "tile_wall";
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
    public final static String REWIND_BUTTON_ACTOR_NAME = "rb";
    // ------------------------------------------------------------------------

    // physics ----------------------------------------------------------------
    public final static float MAX_LINEAR_SPEED_DEFAULT = 2.5f;
    public final static float MAX_LINEAR_ACCELERATION_DEFAULT = 4.0f;
    public final static float MAX_ANGULAR_SPEED_DEFAULT = 30;
    public final static float MAX_ANGULAR_ACCELERATION_DEFAULT = 30;

    public final static float MAX_PLAYER_LINEAR_SPEED = 3.5f; // meters per second (4 m/s = 14.4 km/h)
    public final static float MAX_PLAYER_LINEAR_ACCELERATION = 10.0f;
    public final static float MAX_PLAYER_ANGULAR_SPEED = 120; // old value = 30
    public final static float MAX_PLAYER_ANGULAR_ACCELERATION = 120; // old value = 30

    public final static float PLAYER_ARRIVAL_TOLERANCE = 0.08f;
    public final static float PLAYER_ARRIVAL_TOLERANCE_POW_2 = PLAYER_ARRIVAL_TOLERANCE * PLAYER_ARRIVAL_TOLERANCE;
    public final static float PLAYER_ARRIVAL_DECELERATION_RADIUS = 1.0f;

    public final static float MAX_CAMERA_LINEAR_SPEED_DEFAULT = 8;
    public final static float MAX_CAMERA_LINEAR_ACCELERATION_DEFAULT = 14;

    public final static float CAMERA_ARRIVAL_TOLERANCE = 0.02f;
    public final static float CAMERA_ARRIVAL_TOLERANCE_POW_2 = CAMERA_ARRIVAL_TOLERANCE * CAMERA_ARRIVAL_TOLERANCE;
    public final static float CAMERA_ARRIVAL_DECELERATION_RADIUS = 1.6f;

    public final static float CHARACTER_VISIBILITY_RANGE_ANGLE_DEG = 120.0f;

    public final static float SPATIAL_HASH_TABLE_BUCKET_WIDTH = 3.0f;
    public final static float SPATIAL_HASH_TABLE_BUCKET_HEIGHT = 3.0f;
    // ------------------------------------------------------------------------

    // time and game logic ----------------------------------------------------
    public final static float REWIND_TIME = 3.0f;
    public final static float REWIND_SPEED_MULTIPLIER = 2;
    public final static float MAX_HISTORY_TIME = REWIND_TIME * 5;
    // ------------------------------------------------------------------------

    // terrain ----------------------------------------------------------------
    static public class Level {
        public final static String LEVEL_TEST_FILE = "levels/test";
        public final static String PATHFINDING_TEST_FILE = "levels/pathfinding";
        public final static String RAYCASTING_TEST_FILE = "levels/raycasting_test";

        public final static int FLOOR_CODE = '0';
        public final static int WALL_CODE = '1';
        public final static int PLAYER_START_POS_CODE = 'S';
        public final static int ENEMY_START_POS_CODE = 'E';
    }
    // ------------------------------------------------------------------------
}
