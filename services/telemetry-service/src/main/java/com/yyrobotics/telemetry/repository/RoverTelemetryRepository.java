package com.yyrobotics.telemetry.repository;

import com.yyrobotics.telemetry.entity.RoverTelemetryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface RoverTelemetryRepository extends JpaRepository<RoverTelemetryEntity, Long> {

    List<RoverTelemetryEntity> findRoverTelemetryEntitiesByTimestampBetween(Instant start, Instant end);
}
