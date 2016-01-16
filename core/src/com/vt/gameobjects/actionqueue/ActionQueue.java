package com.vt.gameobjects.actionqueue;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Array;
import com.vt.game.Constants;
import com.vt.gameobjects.TouchHandler;
import com.vt.gameobjects.actionqueue.actions.PlaceMovePointer;
import com.vt.gameobjects.actionqueue.actions.PlaceViewPointer;
import com.vt.gameobjects.characters.CharacterObject;
import com.vt.gameobjects.pointers.PointerSwitcher;

/**
 * Created by Fck.r.sns on 22.08.2015.
 */
public class ActionQueue {
    private PlayerVirtualState m_virtualState;
    private QueueableAction m_candidate;
    private CharacterObject m_character;
    private Array<QueueableAction> m_actions = new Array<com.vt.gameobjects.actionqueue.QueueableAction>(16);
    private Context m_context = new Context();

    public ActionQueue(CharacterObject character, PlayerVirtualState virtualState) {
        m_character = character;
        m_virtualState = virtualState;
    }

    public void act(float delta) {
    }

    public void draw(ShapeRenderer renderer) {
        if (m_candidate != null)
            m_candidate.draw(renderer);
        for (QueueableAction action : m_actions)
            action.draw(renderer);
    }

    public void addAction(QueueableAction action) {
        m_actions.add(action);
        action.onAdd(m_context, m_virtualState);
    }

    public void clear() {
        m_actions.clear();
    }

    public class Controller implements TouchHandler {
        private PointerSwitcher m_pointerSwitcher = new PointerSwitcher();
        private AbstractQueueableAction m_currentAction;

        public void setCurrentPointerToMovement() {
            m_pointerSwitcher.setCurrentPointerToMovement();
        }

        public void setCurrentPointerToView() {
            m_pointerSwitcher.setCurrentPointerToView();
        }

        @Override
        public boolean handleTouchDown(InputEvent event, float x, float y, int pointer, int button) {
            if (m_actions.size == 0) {
                m_virtualState.changeMovementPtrPos(m_character.getX(Constants.ALIGN_ORIGIN), m_character.getY(Constants.ALIGN_ORIGIN));
                m_virtualState.changeViewPtrPos(m_character.getX(Constants.ALIGN_ORIGIN), m_character.getY(Constants.ALIGN_ORIGIN));
            }
            if (m_pointerSwitcher.isCurrentPointerMovement()) {
                m_currentAction = new PlaceMovePointer(x, y, m_virtualState);
            } else {
                m_currentAction = new PlaceViewPointer(x, y, m_virtualState);
            }
            m_candidate = m_currentAction;
            return true;
        }

        @Override
        public void handleTouchUp(InputEvent event, float x, float y, int pointer, int button) {
            addAction(m_currentAction);
            m_currentAction = null;
            m_candidate = null;
        }

        @Override
        public void handleTouchDragged(InputEvent event, float x, float y, int pointer) {
            m_currentAction.setPosition(x, y);
        }
    }
}
