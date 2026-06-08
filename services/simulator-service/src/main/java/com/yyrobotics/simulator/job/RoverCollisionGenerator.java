package com.yyrobotics.simulator.job;

import com.yyrobotics.simulator.event.RoverCollisionEvent;
import com.yyrobotics.simulator.service.GatewayStompClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Slf4j
public class RoverCollisionGenerator {

    private static final String ROVER_ID = "rover-id";
    private static final String[] COLLISION_TYPES = {
            "FRONTAL",
            "REAR",
            "SIDE",
            "GLANCING"
    };

    private final GatewayStompClient gatewayStompClient;

    public RoverCollisionGenerator(GatewayStompClient gatewayStompClient) {
        this.gatewayStompClient = gatewayStompClient;
    }

    @Scheduled(fixedRate = 1000)
    public void generateAndPublishCollision() {
        RoverCollisionEvent event = RoverCollisionEvent.builder()
                .roverId(ROVER_ID)
                .collisionType(generateCollisionType())
                .impactForce(generateImpactForce())
                .timestamp(Instant.now().toEpochMilli())
                .build();

        log.info("Rover Collision Event: {}", event);
        gatewayStompClient.sendCollision(event);
    }

    private String generateCollisionType() {
        return COLLISION_TYPES[ThreadLocalRandom.current().nextInt(COLLISION_TYPES.length)];
    }

    private double generateImpactForce() {
        return ThreadLocalRandom.current().nextDouble(50.0, 1500.0);
    }
}
