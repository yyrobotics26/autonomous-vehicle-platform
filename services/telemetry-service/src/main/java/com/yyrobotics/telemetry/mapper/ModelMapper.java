package com.yyrobotics.telemetry.mapper;

import com.google.protobuf.Timestamp;
import com.yyrobotics.contracts.proto.RoverTelemetry;
import com.yyrobotics.telemetry.entity.RoverTelemetryEntity;

import java.time.Instant;

public final class ModelMapper {

    private  ModelMapper() {}

    public static RoverTelemetryEntity mapToRoverTelemetryEntity(RoverTelemetry roverTelemetry) {
        Timestamp timestamp = roverTelemetry.getTimestamp();
        Instant instant = Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
        return RoverTelemetryEntity.builder()
                .roverId(roverTelemetry.getRoverId())
                .y(roverTelemetry.getY())
                .x(roverTelemetry.getX())
                .speed(roverTelemetry.getSpeed())
                .batteryLevel(roverTelemetry.getBatteryLevel())
                .timestamp(instant)
                .build();
    }
}
