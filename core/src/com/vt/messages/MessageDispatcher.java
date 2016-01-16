package com.vt.messages;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Created by Fck.r.sns on 28.08.2015.
 */
public class MessageDispatcher {
    public enum BroadcastMessageType {
        Rewind
    }

    private static MessageDispatcher m_instance;
    private ObjectMap<BroadcastMessageType, Array<MessageHandler>> m_subscribers;

    private MessageDispatcher() {
        m_subscribers = new ObjectMap<BroadcastMessageType, Array<MessageHandler>>();
    }

    public static MessageDispatcher getInstance() {
        if (m_instance == null)
            m_instance = new MessageDispatcher();
        return m_instance;
    }

    public void subscribeToBroadcast(BroadcastMessageType type, MessageHandler handler) {
        if (!m_subscribers.containsKey(type))
            m_subscribers.put(type, new Array<MessageHandler>(32));
        m_subscribers.get(type).add(handler);
    }

    public void sendBroadcast(BroadcastMessageType type, Context ctx) {
        Array<MessageHandler> handlers = m_subscribers.get(type);
        if (handlers == null)
            return;
        for (MessageHandler handler : handlers)
            handler.onMessageReceived(ctx);
    }
}
