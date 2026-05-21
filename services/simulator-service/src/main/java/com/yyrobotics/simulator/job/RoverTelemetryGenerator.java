package com.yyrobotics.simulator.job;

import com.yyrobotics.simulator.event.RoverTelemetryEvent;
import com.yyrobotics.simulator.state.RoverTelemetryState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Slf4j
public class RoverTelemetryGenerator {

    private RoverTelemetryState telemetryState;

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
        log.info("Rover Telemetry Event: " + telemetryState);
        RoverTelemetryEvent event = telemetryState.mapToEvent();
        //sendEvent(event);
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
                .roverId("rover-id")
                .timestamp(Instant.now())
                .x(ThreadLocalRandom.current().nextDouble())
                .y(ThreadLocalRandom.current().nextDouble())
                .speed(5.0)
                .batteryLevel(100.0)
                .build();
    }
}
