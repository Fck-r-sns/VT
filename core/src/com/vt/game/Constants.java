package com.vt.game;

/**
 * Created by Fck.r.sns on 08.05.2015.
 */
public class Constants {
    // game geometry ----------------------------------------------------------
    public static final float VIEWPORT_WIDTH = 10.0f;
    public static final float PLAYER_WIDTH = 0.5f;
    public static final float PLAYER_HEIGHT = 0.5f;
    public static final float MOVEMENT_POINTER_HEIGHT = 0.3f;
    public static final float MOVEMENT_POINTER_WIDTH = 0.3f;
    public static final float VIEW_POINTER_WIDTH = 0.3f;
    public static final float VIEW_POINTER_HEIGHT = 0.3f;

    public static final float GUI_PANEL_WIDTH = 500;
    public static final float GUI_PANEL_HEIGHT = 1200;
    // ------------------------------------------------------------------------

    // resources --------------------------------------------------------------
    public static final String GUI_ATLAS_PACK = "gui.pack";
    public static final String PANEL_ASSET_NAME = "guiPanel";
    public static final String GUI_SKIN = "gui.json";

    public static final String TEXTURE_ATLAS_PACK = "VT.pack";
    public static final String PLAYER_ASSET_NAME = "circleWithPointer_white";
    public static final String MOVEMENT_POINTER_ASSET_NAME = "movementPointer";
    public static final String VIEW_POINTER_ASSET_NAME = "viewPointer";
    // ------------------------------------------------------------------------

    // actors -----------------------------------------------------------------
    public final static String PLAYER_ACTOR_NAME = "plr";
    public final static String MOVEMENT_POINTER_ACTOR_NAME = "mp";
    public final static String VIEW_POINTER_ACTOR_NAME = "vp";

    public final static String GUI_PANEL_ACTOR_NAME = "panel";
    // ------------------------------------------------------------------------

    // physics ----------------------------------------------------------------
    public final static float MAX_LINEAR_SPEED_DEFAULT = 5;
    public final static float MAX_LINEAR_ACCELERATION_DEFAULT = 10;
    public final static float MAX_ANGULAR_SPEED_DEFAULT = 5;
    public final static float MAX_ANGULAR_ACCELERATION_DEFAULT = 10;

    public final static float PLAYER_ARRIVAL_TOLERANCE = 0.02f;
    public final static float PLAYER_ARRIVAL_DECELERATION_RADIUS = 1.6f;
    // ------------------------------------------------------------------------
}
