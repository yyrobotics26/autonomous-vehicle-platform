package com.yyrobotics.gateway.mapper.impl;

import com.google.protobuf.Any;
import com.yyrobotics.contracts.proto.EventEnvelope;
import com.yyrobotics.contracts.proto.EventType;
import com.yyrobotics.contracts.proto.RoverControlCommand;
import com.yyrobotics.gateway.mapper.RoverEventMapper;
import dto.BaseRoverDto;
import dto.RoverControlCommandDto;
import org.springframework.stereotype.Component;

@Component
public class RoverControlCommandMapper implements RoverEventMapper {

    @Override
    public EventType supports() {
        return EventType.ROVER_CONTROL_COMMAND;
    }

    @Override
    public EventEnvelope map(BaseRoverDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Cannot map null BaseRoverDto");
        }

        if (!(dto instanceof RoverControlCommandDto roverControlCommandDto)) {
            throw new IllegalArgumentException(
                    "Expected RoverControlCommandDto but received: " + dto.getClass().getSimpleName());
        }

        return createBaseEventEnvelope(dto)
                .setEventType(supports())
                .setPayload(
                        Any.pack(
                        RoverControlCommand.newBuilder()
                                .setBrake(roverControlCommandDto.getBrake())
                                .setReverse(roverControlCommandDto.isReverse())
                                .setSteering(roverControlCommandDto.getSteering())
                                .setThrottle(roverControlCommandDto.getThrottle())
                                .build()))
                .build();
    }
}
