package com.yyrobotics.simulator.job;

import com.yyrobotics.simulator.event.RoverObstacleDetectedEvent;
import com.yyrobotics.simulator.service.GatewayStompClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Slf4j
public class RoverObstacleDetectedGenerator {

    private static final String ROVER_ID = "rover-id";
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
        RoverObstacleDetectedEvent event = RoverObstacleDetectedEvent.builder()
                .roverId(ROVER_ID)
                .obstacleType(generateObstacleType())
                .obstacleX(generateCoordinate())
                .obstacleY(generateCoordinate())
                .distanceMeters(generateDistanceMeters())
                .timestamp(Instant.now().toEpochMilli())
                .build();

        log.info("Rover Obstacle Detected Event: {}", event);
        gatewayStompClient.sendObstacleDetected(event);
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
