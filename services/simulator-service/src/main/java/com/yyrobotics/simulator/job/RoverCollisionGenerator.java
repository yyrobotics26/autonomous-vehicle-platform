package com.yyrobotics.simulator.job;

import dto.RoverCollisionDto;
import com.yyrobotics.simulator.service.GatewayStompClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

import static websocket.WebSocketEndpoints.COLLISION_DESTINATION;

@Component
@Slf4j
public class RoverCollisionGenerator {

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
        RoverCollisionDto event = RoverCollisionDto.builder()
                .collisionType(generateCollisionType())
                .impactForce(generateImpactForce())
                .build();

        gatewayStompClient.sendStompMessage(COLLISION_DESTINATION, event);
    }

    private String generateCollisionType() {
        return COLLISION_TYPES[ThreadLocalRandom.current().nextInt(COLLISION_TYPES.length)];
    }

    private double generateImpactForce() {
        return ThreadLocalRandom.current().nextDouble(50.0, 1500.0);
    }
}
