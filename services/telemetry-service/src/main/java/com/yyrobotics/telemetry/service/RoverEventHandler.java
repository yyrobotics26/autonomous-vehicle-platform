package com.yyrobotics.telemetry.service;

import com.google.protobuf.InvalidProtocolBufferException;
import com.yyrobotics.contracts.proto.EventEnvelope;
import com.yyrobotics.contracts.proto.EventType;

public interface RoverEventHandler {

    void handle(EventEnvelope eventEnvelope) throws InvalidProtocolBufferException;

    EventType supports();

}
