package com.yyrobotics.telemetry.service;

import com.yyrobotics.contracts.proto.EventEnvelope;
import jakarta.annotation.PostConstruct;
import kafka.KafkaTopics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RoverEventConsumer {

    @PostConstruct
    public void init() {
        log.info("Telemetry-service build 12:38");
    }

    private final RoverEventDispatcher eventDispatcher;

    public RoverEventConsumer(RoverEventDispatcher eventDispatcher) {
        this.eventDispatcher = eventDispatcher;
    }

    @KafkaListener(topics = KafkaTopics.TELEMETRY_TOPIC,
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeTelemetry(EventEnvelope eventEnvelope) {
            log.info("Received telemetry rover event: {}", eventEnvelope);
            eventDispatcher.handleRoverEvent(eventEnvelope);
    }

    @KafkaListener(topics = KafkaTopics.NAVIGATION_TOPIC,
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeNavigation(EventEnvelope eventEnvelope) {
        log.info("Received navigation rover event: {}", eventEnvelope);
        eventDispatcher.handleRoverEvent(eventEnvelope);
    }

    @KafkaListener(topics = KafkaTopics.ROVER_EVENTS_TOPIC,
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeRoverEvents(EventEnvelope eventEnvelope) {
        log.info("Received rover events: {}", eventEnvelope);
        eventDispatcher.handleRoverEvent(eventEnvelope);
    }

}
