package com.yyrobotics.telemetry.service.impl;

import com.google.protobuf.InvalidProtocolBufferException;
import com.yyrobotics.contracts.proto.EventEnvelope;
import com.yyrobotics.contracts.proto.EventType;
import com.yyrobotics.contracts.proto.RoverObstacleDetected;
import com.yyrobotics.telemetry.entity.RoverObstacleDetectedEntity;
import com.yyrobotics.telemetry.repository.RoverObstacleDetectedRepository;
import com.yyrobotics.telemetry.service.RoverEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.yyrobotics.contracts.proto.EventType.ROVER_OBSTACLE_DETECTED;
import static com.yyrobotics.telemetry.mapper.EventEnvelopeMapper.populateBaseFields;

@Service
@Slf4j
public class RoverObstacleDetectedHandler implements RoverEventHandler {

    private final RoverObstacleDetectedRepository roverObstacleDetectedRepository;

    public RoverObstacleDetectedHandler(RoverObstacleDetectedRepository roverObstacleDetectedRepository) {
        this.roverObstacleDetectedRepository = roverObstacleDetectedRepository;
    }

    @Override
    public void handle(EventEnvelope eventEnvelope) throws InvalidProtocolBufferException {
        RoverObstacleDetected proto = eventEnvelope.getPayload().unpack(RoverObstacleDetected.class);
        RoverObstacleDetectedEntity entity = RoverObstacleDetectedEntity.builder()
                .distanceMeters(proto.getDistanceMeters())
                .obstacleType(proto.getObstacleType())
                .obstacleY(proto.getObstacleY())
                .obstacleX(proto.getObstacleX())
                .build();

        populateBaseFields(entity, eventEnvelope);
        log.info("Received RoverObstacleDetected event {}", entity);
        roverObstacleDetectedRepository.save(entity);
    }

    @Override
    public EventType supports() {
        return ROVER_OBSTACLE_DETECTED;
    }
}
