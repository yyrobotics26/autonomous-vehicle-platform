package com.yyrobotics.telemetry.service.impl;

import com.yyrobotics.telemetry.entity.RoverTelemetryEntity;
import com.yyrobotics.telemetry.repository.RoverTelemetryRepository;
import com.yyrobotics.telemetry.service.RoverTelemetryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
public class RoverTelemetryServiceImpl implements RoverTelemetryService {

    private final RoverTelemetryRepository roverTelemetryRepository;

    public RoverTelemetryServiceImpl(RoverTelemetryRepository roverTelemetryRepository) {
        this.roverTelemetryRepository = roverTelemetryRepository;
    }

    @Override
    public RoverTelemetryEntity save(RoverTelemetryEntity entity) {
        log.debug("Saving entity");
        RoverTelemetryEntity savedEntity = roverTelemetryRepository.save(entity);
        log.info("saved telemetry entity: {}", savedEntity);
        return savedEntity;
    }

    @Override
    public List<RoverTelemetryEntity> saveAll(List<RoverTelemetryEntity> entities) {
        List<RoverTelemetryEntity> savedEntities = roverTelemetryRepository.saveAll(entities);
        log.info("saved telemetry entities: {}", savedEntities.size());
        return savedEntities;
    }

    @Override
    public List<RoverTelemetryEntity> findAllBetweenDates(Instant start, Instant end) {
        List<RoverTelemetryEntity> roverTelemetryEntities = roverTelemetryRepository.findRoverTelemetryEntitiesByTimestampBetween(start, end);
        log.info("findAllBetweenDates start: {}, end: {}, size: {}", start, end, roverTelemetryEntities.size());
        return roverTelemetryEntities;
    }
}
