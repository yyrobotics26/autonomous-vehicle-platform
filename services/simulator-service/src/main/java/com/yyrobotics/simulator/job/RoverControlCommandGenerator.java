package com.yyrobotics.simulator.job;

import dto.RoverControlCommandDto;
import com.yyrobotics.simulator.service.GatewayStompClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

import static websocket.WebSocketEndpoints.CONTROL_COMMAND_DESTINATION;

@Component
@Slf4j
public class RoverControlCommandGenerator {

    private final GatewayStompClient gatewayStompClient;

    public RoverControlCommandGenerator(GatewayStompClient gatewayStompClient) {
        this.gatewayStompClient = gatewayStompClient;
    }

    @Scheduled(fixedRate = 1000)
    public void generateAndPublishControlCommand() {
        RoverControlCommandDto event = RoverControlCommandDto.builder()
                .throttle(generateThrottle())
                .brake(generateBrake())
                .steering(generateSteering())
                .reverse(ThreadLocalRandom.current().nextBoolean())
                .build();

        gatewayStompClient.sendStompMessage(CONTROL_COMMAND_DESTINATION, event);
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
