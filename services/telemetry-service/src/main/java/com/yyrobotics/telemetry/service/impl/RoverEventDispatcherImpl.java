package com.yyrobotics.telemetry.service.impl;

import com.google.protobuf.InvalidProtocolBufferException;
import com.yyrobotics.contracts.proto.EventEnvelope;
import com.yyrobotics.contracts.proto.EventType;
import com.yyrobotics.telemetry.service.RoverEventDispatcher;
import com.yyrobotics.telemetry.service.RoverEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RoverEventDispatcherImpl implements RoverEventDispatcher {

    private final Map<EventType, RoverEventHandler> handlers;

    public RoverEventDispatcherImpl(List<RoverEventHandler> handlerList) {
        this.handlers = handlerList.stream()
                .collect(Collectors.toMap(
                        RoverEventHandler::supports,
                        Function.identity()));
    }

    @Override
    public void handleRoverEvent(EventEnvelope eventEnvelope) {
        EventType eventType = eventEnvelope.getEventType();
        final RoverEventHandler handler = Optional.ofNullable(handlers.get(eventType))
                .orElseThrow(() -> new IllegalArgumentException("Unknown event type: " + eventType));

        log.debug("Converting event type {} to Rover event by handler {}", eventType, handler.getClass());
        try {
             handler.handle(eventEnvelope);
        } catch (InvalidProtocolBufferException e) {
            log.error("Error happened during handling event: {}", eventEnvelope, e);
            throw new RuntimeException(e);
        }
    }
}
