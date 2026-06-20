package com.yyrobotics.simulator.service;

import com.yyrobotics.contracts.proto.EventSource;
import dto.BaseRoverDto;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.converter.JacksonJsonMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.TimeUnit;

import static websocket.WebSocketEndpoints.ROVER_PREFIX;

@Component
@Slf4j
public class GatewayStompClient {


    @Value("${gateway-service.telemetry.destination}")
    private String telemetryEndpointUrl;

    private volatile StompSession stompSession;

    @Scheduled(fixedDelayString = "${gateway-service.telemetry.reconnect-delay-ms:5000}")
    public void connectIfDisconnected() {
        if (isConnected()) {
            return;
        }

        try {
            WebSocketClient webSocketClient =
                    new StandardWebSocketClient();

            WebSocketStompClient stompClient =
                    new WebSocketStompClient(webSocketClient);

            stompClient.setMessageConverter(
                    new JacksonJsonMessageConverter()
            );

            stompSession = stompClient.connectAsync(
                    telemetryEndpointUrl,
                    createStompSessionHandlerAdapter()
            ).get(1000, TimeUnit.MILLISECONDS);

            log.info("Connected to gateway");
        } catch (Exception exception) {
            log.warn("Gateway websocket is not available yet: {}", exception.getMessage());
        }
    }

    private StompSessionHandler createStompSessionHandlerAdapter() {
        return new StompSessionHandlerAdapter() {

            @Override
            public void afterConnected(@NonNull StompSession session, @NonNull StompHeaders connectedHeaders) {
                log.info("STOMP connected. Session id={}", session.getSessionId());
            }

            @Override
            public void handleException(
                    @NonNull StompSession session,
                    StompCommand command,
                    @NonNull StompHeaders headers,
                     byte @NonNull [] payload,
                    @NonNull Throwable exception) {

                log.error("STOMP exception", exception);
            }

            @Override
            public void handleTransportError(
                    @NonNull StompSession session,
                    @NonNull Throwable exception) {

                log.error("Transport error", exception);
            }
        };
    }

    public void sendStompMessage(String destination, BaseRoverDto event) {
        if (!isConnected()) {
            log.warn("Skipping sending event because gateway websocket is not connected");
            return;
        }

        event.setTimestamp(System.currentTimeMillis());
        event.setSource(EventSource.SIMULATOR_SERVICE);
        event.setRoverId("simulator-service-rover1");

        log.info("Sending event to gateway: {}", event);
        stompSession.send(
                ROVER_PREFIX + destination,
                event
        );
    }

    private boolean isConnected() {
        return stompSession != null && stompSession.isConnected();
    }
}
