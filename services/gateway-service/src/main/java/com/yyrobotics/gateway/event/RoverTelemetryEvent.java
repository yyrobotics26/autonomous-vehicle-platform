package com.yyrobotics.gateway.event;

import java.time.Instant;

public record RoverTelemetryEvent(String roverId,
                                  Instant timestamp,
                                  double x,
                                  double y,
                                  double speed,
                                  double batteryLevel) {
}
