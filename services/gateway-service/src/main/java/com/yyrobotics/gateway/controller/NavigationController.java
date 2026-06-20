package com.yyrobotics.gateway.controller;

import com.yyrobotics.gateway.mapper.ModelMapper;
import com.yyrobotics.gateway.service.KafkaMessageSender;
import dto.RoverControlCommandDto;
import dto.RoverRouteProgressDto;
import kafka.KafkaTopics;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import websocket.WebSocketEndpoints;

import static kafka.KafkaTopics.NAVIGATION_TOPIC;
import static kafka.KafkaTopics.TELEMETRY_TOPIC;

@Controller
public class NavigationController {

    private final KafkaMessageSender kafkaSender;
    private final ModelMapper modelMapper;

    public NavigationController(KafkaMessageSender kafkaSender, ModelMapper modelMapper) {
        this.kafkaSender = kafkaSender;
        this.modelMapper = modelMapper;
    }

    @MessageMapping(value = WebSocketEndpoints.CONTROL_COMMAND_DESTINATION)
    public void processControlCommandMessage(@Payload RoverControlCommandDto roverControlCommandDto) {
        kafkaSender.sendMessage(NAVIGATION_TOPIC, modelMapper.toRoverEvent(roverControlCommandDto));
    }

    @MessageMapping(value = WebSocketEndpoints.ROUTE_PROGRESS_DESTINATION)
    public void processRouteProgress(@Payload RoverRouteProgressDto roverRouteProgressDto) {
        kafkaSender.sendMessage(NAVIGATION_TOPIC, modelMapper.toRoverEvent(roverRouteProgressDto));
    }

}
