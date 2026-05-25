package com.yyrobotics.gateway.controller;

import com.yyrobotics.gateway.event.RoverTelemetryEvent;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class TelemetryController {

    @MessageMapping("/telemetry")
    public void processMessage(@Payload RoverTelemetryEvent telemetryEvent) {
        System.out.println("Received telemetry event: " + telemetryEvent);
    }

}
