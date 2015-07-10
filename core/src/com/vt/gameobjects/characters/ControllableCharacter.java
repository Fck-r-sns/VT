package com.vt.gameobjects.characters;

/**
 * Created by Fck.r.sns on 10.07.2015.
 */
public interface ControllableCharacter {
    void setMovementPointerPosition(float x, float y);
    void setViewPointerPosition(float x, float y);
    void shot();
    void activateAbility();
}
