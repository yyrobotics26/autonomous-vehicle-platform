package com.yyrobotics.gateway.mapper.impl;

import com.google.protobuf.Any;
import com.yyrobotics.contracts.proto.EventEnvelope;
import com.yyrobotics.contracts.proto.EventType;
import com.yyrobotics.contracts.proto.RoverObstacleDetected;
import com.yyrobotics.gateway.mapper.RoverEventMapper;
import dto.BaseRoverDto;
import dto.RoverObstacleDetectedDto;
import org.springframework.stereotype.Component;

@Component
public class RoverObstacleDetectedMapper implements RoverEventMapper {

    @Override
    public EventType supports() {
        return EventType.ROVER_OBSTACLE_DETECTED;
    }

    @Override
    public EventEnvelope map(BaseRoverDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Cannot map null BaseRoverDto");
        }

        if (!(dto instanceof RoverObstacleDetectedDto roverObstacleDetectedDto)) {
            throw new IllegalArgumentException(
                    "Expected RoverObstacleDetectedDto but received: " + dto.getClass().getSimpleName());
        }

        return createBaseEventEnvelope(dto)
                .setEventType(supports())
                .setPayload(
                        Any.pack(
                        RoverObstacleDetected.newBuilder()
                                .setObstacleX(roverObstacleDetectedDto.getObstacleX())
                                .setObstacleY(roverObstacleDetectedDto.getObstacleY())
                                .setDistanceMeters(roverObstacleDetectedDto.getDistanceMeters())
                                .setObstacleType(roverObstacleDetectedDto.getObstacleType())
                                .build()))
                .build();
    }
}
