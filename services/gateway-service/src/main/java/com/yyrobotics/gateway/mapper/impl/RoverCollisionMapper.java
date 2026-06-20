package com.yyrobotics.gateway.mapper.impl;

import com.google.protobuf.Any;
import com.yyrobotics.contracts.proto.EventEnvelope;
import com.yyrobotics.contracts.proto.EventType;
import com.yyrobotics.contracts.proto.RoverCollision;
import com.yyrobotics.gateway.mapper.RoverEventMapper;
import dto.BaseRoverDto;
import dto.RoverCollisionDto;
import org.springframework.stereotype.Component;

@Component
public class RoverCollisionMapper implements RoverEventMapper {


    @Override
    public EventType supports() {
        return EventType.ROVER_COLLISION;
    }

    @Override
    public EventEnvelope map(BaseRoverDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Cannot map null BaseRoverDto");
        }

        if (!(dto instanceof RoverCollisionDto roverCollisionDto)) {
            throw new IllegalArgumentException(
                    "Expected RoverCollisionDto but received: " + dto.getClass().getSimpleName());
        }

        return createBaseEventEnvelope(dto)
                .setEventType(supports())
                .setPayload(
                        Any.pack(
                        RoverCollision.newBuilder()
                                .setCollisionType(roverCollisionDto.getCollisionType())
                                .setImpactForce(roverCollisionDto.getImpactForce())
                                .build()))
                .build();
    }
}
