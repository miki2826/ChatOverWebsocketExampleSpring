package com.miki2826.chat.handler;

import com.miki2826.chat.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @author michaeld
 * Simple websocket handler implementation
 */
public class MessageWebSocketHandler extends TextWebSocketHandler {
    @Autowired
    private MessagesService messagesService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        messagesService.onOpen(session);
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        messagesService.onClose(session);

    }
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        messagesService.onClose(session);

    }
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        messagesService.onData(message.getPayload());
    }
}

