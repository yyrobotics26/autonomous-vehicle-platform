package com.yyrobotics.gateway.mapper.impl;

import com.google.protobuf.Any;
import com.yyrobotics.contracts.proto.EventEnvelope;
import com.yyrobotics.contracts.proto.EventType;
import com.yyrobotics.contracts.proto.RoverRouteProgress;
import com.yyrobotics.gateway.mapper.RoverEventMapper;
import dto.BaseRoverDto;
import dto.RoverRouteProgressDto;
import org.springframework.stereotype.Component;

@Component
public class RoverRouteProgressMapper implements RoverEventMapper {

    @Override
    public EventType supports() {
        return EventType.ROVER_ROUTE_PROGRESS;
    }

    @Override
    public EventEnvelope map(BaseRoverDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Cannot map null BaseRoverDto");
        }

        if (!(dto instanceof RoverRouteProgressDto roverRouteProgressDto)) {
            throw new IllegalArgumentException(
                    "Expected RoverRouteProgressDto but received: " + dto.getClass().getSimpleName());
        }

        return createBaseEventEnvelope(dto)
                .setEventType(supports())
                .setPayload(
                        Any.pack(
                        RoverRouteProgress.newBuilder()
                                .setRouteId(roverRouteProgressDto.getRouteId())
                                .setCurrentWaypoint(roverRouteProgressDto.getCurrentWaypoint())
                                .setNavigationState(roverRouteProgressDto.getNavigationState())
                                .setTotalWaypoints(roverRouteProgressDto.getTotalWaypoints())
                                .setRemainingDistanceMeters(roverRouteProgressDto.getRemainingDistanceMeters())
                                .build()))
                .build();
    }
}
