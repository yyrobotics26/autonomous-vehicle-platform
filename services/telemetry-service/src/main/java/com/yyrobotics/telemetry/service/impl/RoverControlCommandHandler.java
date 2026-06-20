package com.yyrobotics.telemetry.service.impl;

import com.google.protobuf.InvalidProtocolBufferException;
import com.yyrobotics.contracts.proto.EventEnvelope;
import com.yyrobotics.contracts.proto.EventType;
import com.yyrobotics.contracts.proto.RoverControlCommand;
import com.yyrobotics.telemetry.entity.RoverControlCommandEntity;
import com.yyrobotics.telemetry.repository.RoverControlCommandRepository;
import com.yyrobotics.telemetry.service.RoverEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.yyrobotics.contracts.proto.EventType.ROVER_CONTROL_COMMAND;
import static com.yyrobotics.telemetry.mapper.EventEnvelopeMapper.populateBaseFields;

@Service
@Slf4j
public class RoverControlCommandHandler implements RoverEventHandler {

    private final RoverControlCommandRepository controlCommandRepository;

    public RoverControlCommandHandler(RoverControlCommandRepository controlCommandRepository) {
        this.controlCommandRepository = controlCommandRepository;
    }

    @Override
    public void handle(EventEnvelope eventEnvelope) throws InvalidProtocolBufferException {
        RoverControlCommand proto = eventEnvelope.getPayload().unpack(RoverControlCommand.class);
        RoverControlCommandEntity entity = RoverControlCommandEntity.builder()
                .brake(proto.getBrake())
                .reverse(proto.getReverse())
                .steering(proto.getSteering())
                .throttle(proto.getThrottle())
                .build();

        populateBaseFields(entity, eventEnvelope);
        log.info("Received RoverControlCommand event {}", entity);
        controlCommandRepository.save(entity);
    }

    @Override
    public EventType supports() {
        return ROVER_CONTROL_COMMAND;
    }
}
