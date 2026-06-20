package com.yyrobotics.telemetry.service;

import com.yyrobotics.contracts.proto.EventEnvelope;

public interface RoverEventDispatcher {

    void handleRoverEvent(EventEnvelope eventEnvelope);

}
