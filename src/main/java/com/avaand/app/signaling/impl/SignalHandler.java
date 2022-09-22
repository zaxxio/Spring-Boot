package com.avaand.app.signaling.impl;

import com.avaand.app.signaling.Signal;
import com.avaand.app.signaling.SignalType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Log
@Component
public class SignalHandler extends TextWebSocketHandler {

    private final List<WebSocketSession> sessions = Collections.synchronizedList(new ArrayList<>());

    private ConcurrentHashMap<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        synchronized (sessions){
            for (WebSocketSession webSocketSession : sessions) {
                if (!session.equals(webSocketSession)){
                    webSocketSession.sendMessage(new TextMessage(message.getPayload()));
                }
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }
}
