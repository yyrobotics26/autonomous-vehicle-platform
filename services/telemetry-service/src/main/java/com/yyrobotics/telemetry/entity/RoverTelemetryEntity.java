package com.yyrobotics.telemetry.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rover_telemetry")
@ToString(callSuper = true)
@Builder
public class RoverTelemetryEntity extends BaseEntity {

    private String roverId;

    @Column(name = "time_stamp")
    private Instant timestamp;

    private double x;

    private double y;

    private double speed;

    private double batteryLevel;

}
