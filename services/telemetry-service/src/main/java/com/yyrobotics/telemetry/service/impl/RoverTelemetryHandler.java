package com.yyrobotics.telemetry.service.impl;

import com.google.protobuf.InvalidProtocolBufferException;
import com.yyrobotics.contracts.proto.EventEnvelope;
import com.yyrobotics.contracts.proto.EventType;
import com.yyrobotics.contracts.proto.RoverTelemetry;
import com.yyrobotics.telemetry.entity.RoverTelemetryEntity;
import com.yyrobotics.telemetry.repository.RoverTelemetryRepository;
import com.yyrobotics.telemetry.service.RoverEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.yyrobotics.contracts.proto.EventType.ROVER_TELEMETRY;
import static com.yyrobotics.telemetry.mapper.EventEnvelopeMapper.populateBaseFields;

@Service
@Slf4j
public class RoverTelemetryHandler implements RoverEventHandler {

    private final RoverTelemetryRepository roverTelemetryRepository;

    public RoverTelemetryHandler(RoverTelemetryRepository roverTelemetryRepository) {
        this.roverTelemetryRepository = roverTelemetryRepository;
    }

    @Override
    public void handle(EventEnvelope eventEnvelope) throws InvalidProtocolBufferException {
        RoverTelemetry telemetryProto = eventEnvelope.getPayload().unpack(RoverTelemetry.class);
        RoverTelemetryEntity telemetryEntity = RoverTelemetryEntity.builder()
                .y(telemetryProto.getY())
                .x(telemetryProto.getX())
                .batteryLevel(telemetryProto.getBatteryLevel())
                .speed(telemetryProto.getSpeed())
                .build();

        populateBaseFields(telemetryEntity, eventEnvelope);
        log.info("Received RoverTelemetry event {}", telemetryEntity);
        roverTelemetryRepository.save(telemetryEntity);
    }

    @Override
    public EventType supports() {
        return ROVER_TELEMETRY;
    }
}
