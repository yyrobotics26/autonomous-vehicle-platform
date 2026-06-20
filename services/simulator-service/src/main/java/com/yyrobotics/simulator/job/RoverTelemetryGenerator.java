package com.yyrobotics.simulator.job;

import com.yyrobotics.simulator.service.GatewayStompClient;
import com.yyrobotics.simulator.state.RoverTelemetryState;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

import static websocket.WebSocketEndpoints.TELEMETRY_DESTINATION;

@Component
@Slf4j
public class RoverTelemetryGenerator {

    @PostConstruct
    public void init() {
        System.out.println("SIMULATOR_BUILD_12:38");
    }

    private RoverTelemetryState telemetryState;

    private final GatewayStompClient gatewayStompClient;

    public RoverTelemetryGenerator(GatewayStompClient gatewayStompClient) {
        this.gatewayStompClient = gatewayStompClient;
    }

    @Scheduled(fixedRate = 1000)
    public void generateAndPublishTelemetry() {
        if (telemetryState == null) {
            telemetryState = createInitialTelemetryState();
        }

        if (telemetryState.getBatteryLevel() <= 0) {
            resetBattery();
        }

        switch (ThreadLocalRandom.current().nextInt(4)) {
            case 0:
                moveRoverForward();
                break;
            case 1:
                moveRoverBackward();
                break;
            case 2:
                moveRoverLeft();
                break;
            case 3:
                moveRoverRight();
                break;
        }
        updateSpeed();
        updateBatteryLevel();
        telemetryState.setTimestamp(Instant.now());

        publishTelemetry();
    }

    private void publishTelemetry() {
        dto.RoverTelemetryDto event = telemetryState.mapToEvent();
        gatewayStompClient.sendStompMessage(TELEMETRY_DESTINATION, event);
    }

    private void resetBattery() {
        telemetryState.setBatteryLevel(100.0);
    }

    private void moveRoverForward() {
        telemetryState.setY(telemetryState.getY() + ThreadLocalRandom.current().nextDouble(0.04));
    }

    private void moveRoverBackward() {
        telemetryState.setY(telemetryState.getY() - ThreadLocalRandom.current().nextDouble(0.04));
    }

    private void moveRoverLeft() {
        telemetryState.setX(telemetryState.getX() + ThreadLocalRandom.current().nextDouble(0.04));
    }

    private void moveRoverRight() {
        telemetryState.setX(telemetryState.getX() - ThreadLocalRandom.current().nextDouble(0.04));
    }

    private void updateBatteryLevel() {
        telemetryState.setBatteryLevel(telemetryState.getBatteryLevel() - (telemetryState.getSpeed() * 0.01));
    }

    private void updateSpeed() {
        double delta = ThreadLocalRandom.current().nextDouble(0.1);

        if (ThreadLocalRandom.current().nextBoolean()) {
            telemetryState.setSpeed(telemetryState.getSpeed() + delta);
        } else {
            telemetryState.setSpeed(
                    Math.max(0, telemetryState.getSpeed() - delta)
            );
        }
    }

    private RoverTelemetryState createInitialTelemetryState() {
        return RoverTelemetryState.builder()
                .timestamp(Instant.now())
                .x(ThreadLocalRandom.current().nextDouble())
                .y(ThreadLocalRandom.current().nextDouble())
                .speed(5.0)
                .batteryLevel(100.0)
                .build();
    }
}
