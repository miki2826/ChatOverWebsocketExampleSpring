package com.miki2826.chat.service;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author michaeld
 */
@Service
public class MessagesService {

    private Set<WebSocketSession> connections = java.util.Collections.synchronizedSet(new HashSet<WebSocketSession>());

    public void onOpen(WebSocketSession session) {
        connections.add(session);
    }

    public void onClose(WebSocketSession session) {
        connections.remove(session);
    }

    //Broadcast the message back to all clients
    public void onData(String message) {
        for (WebSocketSession sock : connections) {
            try {
                sock.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                System.out.println("Error when sending message");
            }
        }
    }

}
