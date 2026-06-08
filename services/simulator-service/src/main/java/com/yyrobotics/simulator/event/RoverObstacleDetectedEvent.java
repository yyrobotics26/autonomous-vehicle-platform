package com.yyrobotics.simulator.event;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class RoverObstacleDetectedEvent {

    private String roverId;
    private String obstacleType;
    private double obstacleX;
    private double obstacleY;
    private double distanceMeters;
    private long timestamp;

}
