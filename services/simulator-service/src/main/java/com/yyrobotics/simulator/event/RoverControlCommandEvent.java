package com.yyrobotics.simulator.event;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class RoverControlCommandEvent {

    private String roverId;
    private double throttle;
    private double brake;
    private double steering;
    private boolean reverse;
    private long timestamp;

}
