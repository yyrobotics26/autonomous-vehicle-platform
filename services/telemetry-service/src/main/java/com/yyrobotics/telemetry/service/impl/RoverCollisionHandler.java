package com.yyrobotics.telemetry.service.impl;

import com.google.protobuf.InvalidProtocolBufferException;
import com.yyrobotics.contracts.proto.EventEnvelope;
import com.yyrobotics.contracts.proto.EventType;
import com.yyrobotics.contracts.proto.RoverCollision;
import com.yyrobotics.telemetry.entity.RoverCollisionEntity;
import com.yyrobotics.telemetry.repository.RoverCollisionRepository;
import com.yyrobotics.telemetry.service.RoverEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.yyrobotics.contracts.proto.EventType.ROVER_COLLISION;
import static com.yyrobotics.telemetry.mapper.EventEnvelopeMapper.populateBaseFields;

@Service
@Slf4j
public class RoverCollisionHandler implements RoverEventHandler {

    private final RoverCollisionRepository roverCollisionRepository;

    public RoverCollisionHandler(RoverCollisionRepository roverCollisionRepository) {
        this.roverCollisionRepository = roverCollisionRepository;
    }

    @Override
    public void handle(EventEnvelope eventEnvelope) throws InvalidProtocolBufferException {
        RoverCollision proto = eventEnvelope.getPayload().unpack(RoverCollision.class);
        RoverCollisionEntity entity = RoverCollisionEntity.builder()
                .collisionType(proto.getCollisionType())
                .impactForce(proto.getImpactForce())
                .build();

        populateBaseFields(entity, eventEnvelope);
        log.info("Received RoverCollision event {}", entity);
        roverCollisionRepository.save(entity);
    }

    @Override
    public EventType supports() {
        return ROVER_COLLISION;
    }
}
