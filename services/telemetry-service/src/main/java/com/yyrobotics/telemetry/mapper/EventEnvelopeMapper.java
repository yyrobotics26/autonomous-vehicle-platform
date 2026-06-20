package com.yyrobotics.telemetry.mapper;

import com.google.protobuf.Timestamp;
import com.yyrobotics.contracts.proto.EventEnvelope;
import com.yyrobotics.contracts.proto.EventSource;
import com.yyrobotics.contracts.proto.RoverTelemetry;
import com.yyrobotics.telemetry.entity.BaseEntity;
import com.yyrobotics.telemetry.entity.RoverTelemetryEntity;

import java.time.Instant;

public final class EventEnvelopeMapper {

    private EventEnvelopeMapper() {}

    public static Instant toInstant(Timestamp timestamp) {
        return Instant.ofEpochSecond(
                timestamp.getSeconds(),
                timestamp.getNanos()
        );
    }

    public static void populateBaseFields(
            BaseEntity entity,
            EventEnvelope envelope) {

        entity.setRoverId(envelope.getRoverId());
        entity.setTimestamp(toInstant(envelope.getTimestamp()));
        entity.setEventSource(
                EventSource.valueOf(envelope.getEventSource().name())
        );
    }
}
