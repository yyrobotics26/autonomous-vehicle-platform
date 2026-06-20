package com.yyrobotics.telemetry.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rover_control_command")
@ToString(callSuper = true)
@SuperBuilder
public class RoverControlCommandEntity extends BaseEntity {

    private double throttle;
    private double brake;
    private double steering;
    private boolean reverse;

}
