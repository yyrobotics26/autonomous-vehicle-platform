package com.yyrobotics.gateway.mapper;

import com.google.protobuf.Timestamp;
import com.yyrobotics.contracts.proto.RoverTelemetry;
import com.yyrobotics.gateway.event.RoverTelemetryEvent;

public final class ModelMapper {

    private ModelMapper() {
    }

    public static RoverTelemetry toRoverTelemetryEvent(RoverTelemetryEvent telemetryEvent) {
        Timestamp timestamp = Timestamp.newBuilder()
                .setSeconds(telemetryEvent.timestamp().getEpochSecond())
                .setNanos(telemetryEvent.timestamp().getNano())
                .build();
        return RoverTelemetry.newBuilder()
                .setRoverId(telemetryEvent.roverId())
                .setX(telemetryEvent.x())
                .setY(telemetryEvent.y())
                .setBatteryLevel(telemetryEvent.batteryLevel())
                .setSpeed(telemetryEvent.speed())
                .setTimestamp(timestamp)
                .build();
    }
}
