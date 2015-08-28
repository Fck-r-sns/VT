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
    private ObjectMap<BroadcastMessageType, Array<MessageHandler>> m_receivers;

    private MessageDispatcher() {
        m_receivers = new ObjectMap<BroadcastMessageType, Array<MessageHandler>>();
    }

    public static MessageDispatcher getInstance() {
        if (m_instance == null)
            m_instance = new MessageDispatcher();
        return m_instance;
    }

    public void subscribeToBroadcast(BroadcastMessageType type, MessageHandler handler) {
        if (!m_receivers.containsKey(type))
            m_receivers.put(type, new Array<MessageHandler>(32));
        m_receivers.get(type).add(handler);
    }

    public void sendBroadcast(BroadcastMessageType type, Context ctx) {
        Array<MessageHandler> handlers = m_receivers.get(type);
        if (handlers == null)
            return;
        for (MessageHandler handler : handlers)
            handler.onMessageReceived(ctx);
    }
}
