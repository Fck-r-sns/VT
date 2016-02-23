package com.vt.gameobjects.actionqueue;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
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
    private Queue<Queue<AbstractQueueableAction>> m_actions = new Queue<Queue<AbstractQueueableAction>>(128);
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
            Queue<AbstractQueueableAction> subqueue = m_actions.first();
            AbstractQueueableAction next = subqueue.first();
            if (m_currentAction == null) {
                m_currentAction = next;
                if (m_currentAction.start(m_context)) {
                    m_currentAction.stop(m_context);
                    m_currentAction = null;
                }
                subqueue.removeFirst();
                if (subqueue.size == 0)
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

    public void draw(SpriteBatch batch) {
        if (m_currentAction != null)
            m_currentAction.draw(batch);
        if (m_candidate != null)
            m_candidate.draw(batch);
        for (Queue<AbstractQueueableAction> subqueue : m_actions)
            for (QueueableAction action : subqueue)
                action.draw(batch);
    }

    public void addAction(AbstractQueueableAction action) {
        if (action == null)
            return;
        Queue<AbstractQueueableAction> subQueue = new Queue<AbstractQueueableAction>(1);
        subQueue.addLast(action);
        m_actions.addLast(subQueue);
        action.onAdd(m_context);
    }

    public void addAction(Queue<AbstractQueueableAction> subqueue, AbstractQueueableAction action) {
        if (action == null)
            return;
        subqueue.addLast(action);
        action.onAdd(m_context);
    }

    public void clear() {
        m_actions.clear();
        m_currentAction = null;
        m_candidate = null;
    }

    public void removeLast() {
        m_actions.removeLast();
    }

    public class Controller implements TouchHandler {
        private PointerSwitcher m_pointerSwitcher = new PointerSwitcher();
        private Queue<AbstractQueueableAction> nearestSubqueue;
        private float lastX;
        private float lastY;

        public void setCurrentPointerToMovement() {
            m_pointerSwitcher.setCurrentPointerToMovement();
        }

        public void setCurrentPointerToView() {
            m_pointerSwitcher.setCurrentPointerToView();
        }

        @Override
        public boolean handleTouchDown(InputEvent event, float x, float y, int pointer, int button) {
            lastX = x;
            lastY = y;
            if (m_actions.size == 0) {
                float xChar = m_character.getX(Constants.ALIGN_ORIGIN);
                float yChar = m_character.getY(Constants.ALIGN_ORIGIN);
                m_virtualState.changeMovementPtrPos(xChar, yChar);
                m_virtualState.changeViewPtrPos(xChar, yChar);
                addAction(new PlaceMovementPointer(xChar, yChar, m_context, true));
            }
            if (m_pointerSwitcher.isCurrentPointerMovement()) {
                m_candidate = new PlaceMovementPointer(x, y, m_context);
            } else {
                nearestSubqueue = null;
                float dst2 = Float.MAX_VALUE;
                float xOrig = x, yOrig = y;
                for (Queue<AbstractQueueableAction> subqueue : m_actions) {
                    for (AbstractQueueableAction action : subqueue) {
                        if (!action.getFlags().CHANGE_MOVE_PTR)
                            continue;
                        float newDst2 = action.getPosition().dst2(x, y);
                        if (newDst2 < dst2) {
                            nearestSubqueue = subqueue;
                            dst2 = newDst2;
                            xOrig = action.getX();
                            yOrig = action.getY();
                        }
                    }
                }
                if (nearestSubqueue != null)
                    m_candidate = new PlaceViewPointer(xOrig, yOrig, x, y, m_context);
                else
                    m_candidate = new PlaceViewPointer(x, y, m_context);
            }
            return true;
        }

        @Override
        public void handleTouchUp(InputEvent event, float x, float y, int pointer, int button) {
            if (m_pointerSwitcher.isCurrentPointerMovement() || nearestSubqueue == null) {
                addAction(m_candidate);
            } else {
                addAction(nearestSubqueue, m_candidate);
                nearestSubqueue = null;
            }
            m_candidate = null;
        }

        @Override
        public void handleTouchDragged(InputEvent event, float x, float y, int pointer) {
            if (m_pointerSwitcher.isCurrentPointerMovement()) {
                float dst = new Vector2(x, y).dst2(lastX, lastY);
                if (dst > 0.4f) {
                    handleTouchUp(event, x, y, pointer, 0);
                    handleTouchDown(event, x, y, pointer, 0);
                    lastX = x;
                    lastY = y;
                }
            }
            m_candidate.setPosition(x, y);
        }
    }
}
