package com.yyrobotics.telemetry.service.impl;

import com.google.protobuf.InvalidProtocolBufferException;
import com.yyrobotics.contracts.proto.EventEnvelope;
import com.yyrobotics.contracts.proto.EventType;
import com.yyrobotics.contracts.proto.RoverRouteProgress;
import com.yyrobotics.telemetry.entity.RoverRouteProgressEntity;
import com.yyrobotics.telemetry.repository.RoverRouteProgressRepository;
import com.yyrobotics.telemetry.service.RoverEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.yyrobotics.contracts.proto.EventType.ROVER_ROUTE_PROGRESS;
import static com.yyrobotics.telemetry.mapper.EventEnvelopeMapper.populateBaseFields;

@Service
@Slf4j
public class RoverRouteProgressHandler implements RoverEventHandler {

    private final RoverRouteProgressRepository roverRouteProgressRepository;

    public RoverRouteProgressHandler(RoverRouteProgressRepository roverRouteProgressRepository) {
        this.roverRouteProgressRepository = roverRouteProgressRepository;
    }


    @Override
    public void handle(EventEnvelope eventEnvelope) throws InvalidProtocolBufferException {
        RoverRouteProgress proto = eventEnvelope.getPayload().unpack(RoverRouteProgress.class);
        RoverRouteProgressEntity entity = RoverRouteProgressEntity.builder()
                .currentWaypoint(proto.getCurrentWaypoint())
                .totalWaypoints(proto.getTotalWaypoints())
                .navigationState(proto.getNavigationState())
                .remainingDistanceMeters(proto.getRemainingDistanceMeters())
                .build();

        populateBaseFields(entity, eventEnvelope);
        log.info("Received RoverObstacleDetected event {}", entity);
        roverRouteProgressRepository.save(entity);
    }

    @Override
    public EventType supports() {
        return ROVER_ROUTE_PROGRESS;
    }
}
