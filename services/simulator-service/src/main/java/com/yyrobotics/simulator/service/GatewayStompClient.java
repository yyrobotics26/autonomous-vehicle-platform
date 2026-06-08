package com.yyrobotics.simulator.service;

import com.yyrobotics.simulator.event.RoverCollisionEvent;
import com.yyrobotics.simulator.event.RoverControlCommandEvent;
import com.yyrobotics.simulator.event.RoverObstacleDetectedEvent;
import com.yyrobotics.simulator.event.RoverRouteProgressEvent;
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

    private static final String COLLISION_DESTINATION = "/rover/collision";
    private static final String CONTROL_COMMAND_DESTINATION = "/rover/control-command";
    private static final String OBSTACLE_DETECTED_DESTINATION = "/rover/obstacle-detected";
    private static final String ROUTE_PROGRESS_DESTINATION = "/rover/route-progress";
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

    public void sendControlCommand(
            RoverControlCommandEvent controlCommand
    ) {
        if (!isConnected()) {
            log.warn("Skipping control command event because gateway websocket is not connected");
            return;
        }

        stompSession.send(
                CONTROL_COMMAND_DESTINATION,
                controlCommand
        );
    }

    public void sendRouteProgress(
            RoverRouteProgressEvent routeProgress
    ) {
        if (!isConnected()) {
            log.warn("Skipping route progress event because gateway websocket is not connected");
            return;
        }

        stompSession.send(
                ROUTE_PROGRESS_DESTINATION,
                routeProgress
        );
    }

    public void sendObstacleDetected(
            RoverObstacleDetectedEvent obstacleDetected
    ) {
        if (!isConnected()) {
            log.warn("Skipping obstacle detected event because gateway websocket is not connected");
            return;
        }

        stompSession.send(
                OBSTACLE_DETECTED_DESTINATION,
                obstacleDetected
        );
    }

    public void sendCollision(
            RoverCollisionEvent collision
    ) {
        if (!isConnected()) {
            log.warn("Skipping collision event because gateway websocket is not connected");
            return;
        }

        stompSession.send(
                COLLISION_DESTINATION,
                collision
        );
    }

    private boolean isConnected() {
        return stompSession != null && stompSession.isConnected();
    }
}
