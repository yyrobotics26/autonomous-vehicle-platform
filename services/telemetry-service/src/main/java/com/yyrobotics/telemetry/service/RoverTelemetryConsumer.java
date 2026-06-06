package com.yyrobotics.telemetry.service;

import com.yyrobotics.contracts.proto.RoverTelemetry;
import com.yyrobotics.telemetry.mapper.ModelMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RoverTelemetryConsumer {

    @PostConstruct
    public void init() {
        log.info("Telemetry fixed");
    }
    private final RoverTelemetryService roverTelemetryService;

    public RoverTelemetryConsumer(RoverTelemetryService roverTelemetryService) {
        this.roverTelemetryService = roverTelemetryService;
    }

    @KafkaListener(topics = "${spring.kafka.topic.telemetry}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeTelemetry(RoverTelemetry telemetry) {
            log.info("Received telemetry message: {}", telemetry);
            roverTelemetryService.save(ModelMapper.mapToRoverTelemetryEntity(telemetry));
    }

}
