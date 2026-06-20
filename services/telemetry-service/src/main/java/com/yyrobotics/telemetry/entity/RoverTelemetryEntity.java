package com.yyrobotics.telemetry.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rover_telemetry")
@ToString(callSuper = true)
@SuperBuilder
public class RoverTelemetryEntity extends BaseEntity {

    private double x;
    private double y;
    private double speed;
    private double batteryLevel;

}
