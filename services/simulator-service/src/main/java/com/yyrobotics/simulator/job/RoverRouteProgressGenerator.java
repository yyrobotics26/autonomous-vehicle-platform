package com.yyrobotics.simulator.job;

import com.yyrobotics.simulator.event.RoverRouteProgressEvent;
import com.yyrobotics.simulator.service.GatewayStompClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Slf4j
public class RoverRouteProgressGenerator {

    private static final String ROVER_ID = "rover-id";
    private static final String ROUTE_ID = "route-id";
    private static final int TOTAL_WAYPOINTS = 12;

    private final GatewayStompClient gatewayStompClient;

    private int currentWaypoint;

    public RoverRouteProgressGenerator(GatewayStompClient gatewayStompClient) {
        this.gatewayStompClient = gatewayStompClient;
    }

    @Scheduled(fixedRate = 1000)
    public void generateAndPublishRouteProgress() {
        currentWaypoint = nextWaypoint();

        RoverRouteProgressEvent event = RoverRouteProgressEvent.builder()
                .roverId(ROVER_ID)
                .routeId(ROUTE_ID)
                .currentWaypoint(currentWaypoint)
                .totalWaypoints(TOTAL_WAYPOINTS)
                .remainingDistanceMeters(generateRemainingDistanceMeters(currentWaypoint))
                .navigationState(resolveNavigationState(currentWaypoint))
                .timestamp(Instant.now().toEpochMilli())
                .build();

        log.info("Rover Route Progress Event: {}", event);
        gatewayStompClient.sendRouteProgress(event);
    }

    private int nextWaypoint() {
        return currentWaypoint >= TOTAL_WAYPOINTS ? 1 : currentWaypoint + 1;
    }

    private double generateRemainingDistanceMeters(int waypoint) {
        int remainingWaypoints = TOTAL_WAYPOINTS - waypoint;
        return Math.max(0.0, remainingWaypoints * 25.0 + ThreadLocalRandom.current().nextDouble(0.0, 10.0));
    }

    private String resolveNavigationState(int waypoint) {
        if (waypoint >= TOTAL_WAYPOINTS) {
            return "ARRIVED";
        }

        return ThreadLocalRandom.current().nextBoolean() ? "EN_ROUTE" : "RECALCULATING";
    }
}
