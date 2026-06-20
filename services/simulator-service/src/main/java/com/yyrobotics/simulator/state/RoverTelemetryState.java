package com.yyrobotics.simulator.state;

import com.yyrobotics.contracts.proto.EventSource;
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

    public dto.RoverTelemetryDto mapToEvent(){
        return dto.RoverTelemetryDto.builder()
                .timestamp(timestamp.toEpochMilli())
                .roverId(roverId)
                .source(EventSource.SIMULATOR_SERVICE)
                .x(x)
                .y(y)
                .speed(speed)
                .batteryLevel(batteryLevel)
                .build();
    }

}