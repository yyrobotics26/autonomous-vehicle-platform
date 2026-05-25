package com.yyrobotics.simulator.service;

import com.yyrobotics.simulator.event.RoverTelemetryEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.converter.JacksonJsonMessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class GatewayStompClient {

    private static final String TELEMETRY_DESTINATION = "/rover/telemetry";

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
                    new StompSessionHandlerAdapter() {
                    }
            ).get(1000, TimeUnit.MILLISECONDS);

            log.info("Connected to gateway");
        } catch (Exception exception) {
            log.warn("Gateway websocket is not available yet: {}", exception.getMessage());
        }
    }

    public void sendTelemetry(
            RoverTelemetryEvent telemetry
    ) {
        if (!isConnected()) {
            log.warn("Skipping telemetry event because gateway websocket is not connected");
            return;
        }

        stompSession.send(
                TELEMETRY_DESTINATION,
                telemetry
        );
    }

    private boolean isConnected() {
        return stompSession != null && stompSession.isConnected();
    }
}
