package com.yyrobotics.telemetry.service;

import com.yyrobotics.telemetry.entity.RoverTelemetryEntity;

import java.time.Instant;
import java.util.List;

public interface RoverTelemetryService {

    RoverTelemetryEntity save(RoverTelemetryEntity entity);

    List<RoverTelemetryEntity> saveAll(List<RoverTelemetryEntity> entities);

    List<RoverTelemetryEntity> findAllBetweenDates(Instant start, Instant end);

}
