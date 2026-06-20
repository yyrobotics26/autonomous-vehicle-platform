package com.yyrobotics.gateway.mapper;

import com.google.protobuf.util.Timestamps;
import com.yyrobotics.contracts.proto.EventEnvelope;
import com.yyrobotics.contracts.proto.EventType;
import dto.BaseRoverDto;

public interface RoverEventMapper {

    EventType supports();

    EventEnvelope map(BaseRoverDto dto);

   default EventEnvelope.Builder createBaseEventEnvelope(BaseRoverDto dto){
        return EventEnvelope.newBuilder()
                .setEventSource(dto.getSource())
                .setRoverId(dto.getRoverId())
                .setTimestamp(Timestamps.fromMillis(dto.getTimestamp()));
    }

}
