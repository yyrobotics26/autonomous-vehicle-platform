package com.yyrobotics.simulator.event;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class RoverRouteProgressEvent {

    private String roverId;
    private String routeId;
    private int currentWaypoint;
    private int totalWaypoints;
    private double remainingDistanceMeters;
    private String navigationState;
    private long timestamp;

}
