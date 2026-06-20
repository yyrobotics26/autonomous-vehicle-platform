package com.yyrobotics.simulator.job;

import dto.RoverObstacleDetectedDto;
import com.yyrobotics.simulator.service.GatewayStompClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import websocket.WebSocketEndpoints;

import java.util.concurrent.ThreadLocalRandom;

@Component
@Slf4j
public class RoverObstacleDetectedGenerator {

    private static final String[] OBSTACLE_TYPES = {
            "PEDESTRIAN",
            "ROCK",
            "BARRIER",
            "VEHICLE",
            "DEBRIS"
    };

    private final GatewayStompClient gatewayStompClient;

    public RoverObstacleDetectedGenerator(GatewayStompClient gatewayStompClient) {
        this.gatewayStompClient = gatewayStompClient;
    }

    @Scheduled(fixedRate = 1000)
    public void generateAndPublishObstacleDetected() {
        RoverObstacleDetectedDto event = RoverObstacleDetectedDto.builder()
                .obstacleType(generateObstacleType())
                .obstacleX(generateCoordinate())
                .obstacleY(generateCoordinate())
                .distanceMeters(generateDistanceMeters())
                .build();

        gatewayStompClient.sendStompMessage(WebSocketEndpoints.OBSTACLE_DETECTED_DESTINATION, event);
    }

    private String generateObstacleType() {
        return OBSTACLE_TYPES[ThreadLocalRandom.current().nextInt(OBSTACLE_TYPES.length)];
    }

    private double generateCoordinate() {
        return ThreadLocalRandom.current().nextDouble(-10.0, 10.0);
    }

    private double generateDistanceMeters() {
        return ThreadLocalRandom.current().nextDouble(0.5, 25.0);
    }
}
