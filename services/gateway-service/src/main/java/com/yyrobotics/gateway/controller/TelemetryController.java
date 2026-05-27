package com.yyrobotics.gateway.controller;

import com.yyrobotics.gateway.event.RoverTelemetryEvent;
import com.yyrobotics.gateway.mapper.ModelMapper;
import com.yyrobotics.gateway.service.KafkaMessageSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class TelemetryController {

    @Value("${spring.kafka.topic.telemetry}")
    private String telemetryTopic;

    private final KafkaMessageSender kafkaSender;

    public TelemetryController(KafkaMessageSender kafkaSender) {
        this.kafkaSender = kafkaSender;
    }

    @MessageMapping("/telemetry")
    public void processMessage(@Payload RoverTelemetryEvent telemetryEvent) {
        kafkaSender.sendMessage(telemetryTopic, ModelMapper.toRoverTelemetryEvent(telemetryEvent));
    }

}
