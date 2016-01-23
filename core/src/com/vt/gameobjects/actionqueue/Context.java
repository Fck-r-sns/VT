package com.vt.gameobjects.actionqueue;

import com.vt.gameobjects.characters.CharacterObject;

/**
 * Created by Fck.r.sns on 22.08.2015.
 */
public class Context {
    public int placeMovementPtrCount = 0;
    public int placeViewPtrCount = 0;
    public CharacterObject character;
    public PlayerVirtualState virtualState;

    Context(CharacterObject character, PlayerVirtualState virtualState) {
        this.character = character;
        this.virtualState = virtualState;
    }
}
