package com.avaand.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {


    private final WebSocketHandler webSocketHandler = new WebSocketHandler() {

        final List<WebSocketSession> webSocketSessions = Collections.synchronizedList(new ArrayList<>());

        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            webSocketSessions.add(session);
        }

        @Override
        public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
            for (WebSocketSession webSocketSession : webSocketSessions) {
                webSocketSession.sendMessage(message);
            }
        }

        @Override
        public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

        }

        @Override
        public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
            webSocketSessions.remove(session);
        }

        @Override
        public boolean supportsPartialMessages() {
            return false;
        }
    };

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/chat")
                .setAllowedOrigins("http://localhost:3000");
    }
}
