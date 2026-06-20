package com.yyrobotics.gateway.controller;

import com.yyrobotics.gateway.mapper.ModelMapper;
import com.yyrobotics.gateway.service.KafkaMessageSender;
import dto.RoverRouteProgressDto;
import dto.RoverTelemetryDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import websocket.WebSocketEndpoints;

import static kafka.KafkaTopics.TELEMETRY_TOPIC;

@Controller
public class TelemetryController {

    private final KafkaMessageSender kafkaSender;
    private final ModelMapper modelMapper;

    public TelemetryController(KafkaMessageSender kafkaSender, ModelMapper modelMapper) {
        this.kafkaSender = kafkaSender;
        this.modelMapper = modelMapper;
    }

    @MessageMapping(value = WebSocketEndpoints.TELEMETRY_DESTINATION)
    public void processTelemetryMessage(@Payload RoverTelemetryDto telemetryEvent) {
        kafkaSender.sendMessage(TELEMETRY_TOPIC, modelMapper.toRoverEvent(telemetryEvent));
    }

}
