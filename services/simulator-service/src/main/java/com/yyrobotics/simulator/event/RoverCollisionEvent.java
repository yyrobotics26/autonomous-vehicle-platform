package com.yyrobotics.simulator.event;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class RoverCollisionEvent {

    private String roverId;
    private String collisionType;
    private double impactForce;
    private long timestamp;

}
