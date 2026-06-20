package com.yyrobotics.gateway.mapper.impl;

import com.google.protobuf.Any;
import com.yyrobotics.contracts.proto.EventEnvelope;
import com.yyrobotics.contracts.proto.EventType;
import com.yyrobotics.contracts.proto.RoverTelemetry;
import com.yyrobotics.gateway.mapper.RoverEventMapper;
import dto.BaseRoverDto;
import dto.RoverTelemetryDto;
import org.springframework.stereotype.Component;

@Component
public class RoverTelemetryMapper implements RoverEventMapper {

    @Override
    public EventType supports() {
        return EventType.ROVER_TELEMETRY;
    }

    @Override
    public EventEnvelope map(BaseRoverDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Cannot map null BaseRoverDto");
        }

        if (!(dto instanceof RoverTelemetryDto roverTelemetryDto)) {
            throw new IllegalArgumentException(
                    "Expected RoverTelemetryDto but received: " + dto.getClass().getSimpleName());
        }

        return createBaseEventEnvelope(dto)
                .setEventType(supports())
                .setPayload(
                        Any.pack(
                        RoverTelemetry.newBuilder()
                                .setX(roverTelemetryDto.getX())
                                .setY(roverTelemetryDto.getY())
                                .setSpeed(roverTelemetryDto.getSpeed())
                                .setBatteryLevel(roverTelemetryDto.getBatteryLevel())
                                .build()))
                .build();
    }
}
