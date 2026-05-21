package com.yyrobotics.simulator.state;

import com.yyrobotics.simulator.event.RoverTelemetryEvent;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.Instant;

@Data
@Builder
@ToString
public class RoverTelemetryState {

    private String roverId;
    private Instant timestamp;
    private double x;
    private double y;
    private double speed;
    private double batteryLevel;

    public RoverTelemetryEvent mapToEvent(){
        return RoverTelemetryEvent.builder()
                .timestamp(timestamp)
                .roverId(roverId)
                .x(x)
                .y(y)
                .speed(speed)
                .batteryLevel(batteryLevel)
                .build();
    }

}