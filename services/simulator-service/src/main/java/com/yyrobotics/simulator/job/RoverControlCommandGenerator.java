package com.yyrobotics.simulator.job;

import com.yyrobotics.simulator.event.RoverControlCommandEvent;
import com.yyrobotics.simulator.service.GatewayStompClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Slf4j
public class RoverControlCommandGenerator {

    private static final String ROVER_ID = "rover-id";

    private final GatewayStompClient gatewayStompClient;

    public RoverControlCommandGenerator(GatewayStompClient gatewayStompClient) {
        this.gatewayStompClient = gatewayStompClient;
    }

    @Scheduled(fixedRate = 1000)
    public void generateAndPublishControlCommand() {
        RoverControlCommandEvent event = RoverControlCommandEvent.builder()
                .roverId(ROVER_ID)
                .throttle(generateThrottle())
                .brake(generateBrake())
                .steering(generateSteering())
                .reverse(ThreadLocalRandom.current().nextBoolean())
                .timestamp(Instant.now().toEpochMilli())
                .build();

        log.info("Rover Control Command Event: {}", event);
        gatewayStompClient.sendControlCommand(event);
    }

    private double generateThrottle() {
        return ThreadLocalRandom.current().nextDouble(0.0, 1.0);
    }

    private double generateBrake() {
        return ThreadLocalRandom.current().nextDouble(0.0, 0.3);
    }

    private double generateSteering() {
        return ThreadLocalRandom.current().nextDouble(-1.0, 1.0);
    }
}
