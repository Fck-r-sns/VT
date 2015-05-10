package com.vt.game;

/**
 * Created by Fck.r.sns on 08.05.2015.
 */
public class Constants {
    // game geometry ----------------------------------------------------------
    public static final float VIEWPORT_WIDTH = 10.0f;
    public static final float PLAYER_WIDTH = 1.0f;
    public static final float PLAYER_HEIGHT = 1.0f;
    public static final float MOVEMENT_POINTER_HEIGHT = 1.0f;
    public static final float MOVEMENT_POINTER_WIDTH = 1.0f;
    public static final float FIRE_POINTER_WIDTH = 1.0f;
    public static final float FIRE_POINTER_HEIGHT = 1.0f;
    // ------------------------------------------------------------------------

    // resources --------------------------------------------------------------
    public static final String TEXTURE_ATLAS_PACK = "VT.pack";
    public static final String PLAYER_ASSET_NAME = "circleWithPointer_white";
    public static final String MOVEMENT_POINTER_ASSET_NAME = "movementPointer";
    public static final String FIRE_POINTER_ASSET_NAME = "firePointer";
    // ------------------------------------------------------------------------

    // actors -----------------------------------------------------------------
    public final static String PLAYER_ACTOR_NAME = "plr";
    public final static String MOVEMENT_POINTER_ACTOR_NAME = "mp";
    public final static String FIRE_POINTER_ACTOR_NAME = "fp";
    // ------------------------------------------------------------------------

    // physics ----------------------------------------------------------------
    public final static float MAX_LINEAR_SPEED_DEFAULT = 10;
    public final static float MAX_LINEAR_ACCELERATION_DEFAULT = 20;
    public final static float MAX_ANGULAR_SPEED_DEFAULT = 5;
    public final static float MAX_ANGULAR_ACCELERATION_DEFAULT = 10;
    // ------------------------------------------------------------------------
}
