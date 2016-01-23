package com.vt.gameobjects.actionqueue;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Queue;
import com.vt.game.Constants;
import com.vt.gameobjects.TouchHandler;
import com.vt.gameobjects.actionqueue.actions.PlaceMovementPointer;
import com.vt.gameobjects.actionqueue.actions.PlaceViewPointer;
import com.vt.gameobjects.characters.CharacterObject;
import com.vt.gameobjects.pointers.PointerSwitcher;

/**
 * Created by Fck.r.sns on 22.08.2015.
 */
public class ActionQueue {
    private PlayerVirtualState m_virtualState;
    private CharacterObject m_character;
    private Context m_context;
    private AbstractQueueableAction m_candidate;
    private Queue<AbstractQueueableAction> m_actions = new Queue<AbstractQueueableAction>(16);
    private AbstractQueueableAction m_currentAction;
    public ActionQueue(CharacterObject character) {
        m_character = character;
        m_virtualState = new PlayerVirtualState(
                m_character.getMovementPointer().getPosition(),
                m_character.getViewPointer().getPosition()
                );
        m_context = new Context(m_character, m_virtualState);
    }

    public void act(float delta) {
        if (m_actions.size != 0) {
            // dispatch next action
            AbstractQueueableAction next = m_actions.first();
            if (m_currentAction == null) {
                m_currentAction = next;
                if (m_currentAction.start(m_context)) {
                    m_currentAction.stop(m_context);
                    m_currentAction = null;
                }
                m_actions.removeFirst();
            }
        }

        if (m_currentAction != null) {
            if (m_currentAction.execute(m_context)) {
                m_currentAction.stop(m_context);
                m_currentAction = null;
            }
        }
    }

    public void draw(ShapeRenderer renderer) {
        if (m_candidate != null)
            m_candidate.draw(renderer);
        for (QueueableAction action : m_actions)
            action.draw(renderer);
    }

    public void addAction(AbstractQueueableAction action) {
        m_actions.addLast(action);
        action.onAdd(m_context);
    }

    public void clear() {
        m_actions.clear();
    }

    public class Controller implements TouchHandler {
        private PointerSwitcher m_pointerSwitcher = new PointerSwitcher();

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
                m_candidate = new PlaceMovementPointer(x, y, m_context);
            } else {
                m_candidate = new PlaceViewPointer(x, y, m_context);
            }
            return true;
        }

        @Override
        public void handleTouchUp(InputEvent event, float x, float y, int pointer, int button) {
            addAction(m_candidate);
            m_candidate = null;
        }

        @Override
        public void handleTouchDragged(InputEvent event, float x, float y, int pointer) {
            m_candidate.setPosition(x, y);
        }
    }
}
