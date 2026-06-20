package com.yyrobotics.gateway.controller;

import com.yyrobotics.gateway.mapper.ModelMapper;
import com.yyrobotics.gateway.service.KafkaMessageSender;
import dto.RoverCollisionDto;
import dto.RoverObstacleDetectedDto;
import kafka.KafkaTopics;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import websocket.WebSocketEndpoints;

@Controller
public class RoverEventsController {

    private final KafkaMessageSender kafkaSender;
    private final ModelMapper modelMapper;

    public RoverEventsController(KafkaMessageSender kafkaSender, ModelMapper modelMapper) {
        this.kafkaSender = kafkaSender;
        this.modelMapper = modelMapper;
    }

    @MessageMapping(value = WebSocketEndpoints.OBSTACLE_DETECTED_DESTINATION)
    public void processControlCommandMessage(@Payload RoverObstacleDetectedDto obstacleDetectedEvent) {
        kafkaSender.sendMessage(KafkaTopics.ROVER_EVENTS_TOPIC, modelMapper.toRoverEvent(obstacleDetectedEvent));
    }

    @MessageMapping(value = WebSocketEndpoints.COLLISION_DESTINATION)
    public void processControlCommandMessage(@Payload RoverCollisionDto roverCollisionEvent) {
        kafkaSender.sendMessage(KafkaTopics.ROVER_EVENTS_TOPIC, modelMapper.toRoverEvent(roverCollisionEvent));
    }

}
