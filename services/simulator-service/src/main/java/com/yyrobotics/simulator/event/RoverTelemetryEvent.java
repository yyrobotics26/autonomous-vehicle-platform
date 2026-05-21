package com.yyrobotics.simulator.event;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Builder
@ToString
public class RoverTelemetryEvent {

    private String roverId;
    private Instant timestamp;
    private double x;
    private double y;
    private double speed;
    private double batteryLevel;

}
