package com.yyrobotics.telemetry.entity;

import jakarta.persistence.Column;
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
@Table(name = "rover_obstacle_detected")
@ToString(callSuper = true)
@SuperBuilder
public class RoverObstacleDetectedEntity extends BaseEntity {

    private String obstacleType;

    @Column(name = "obstacle_x")
    private double obstacleX;

    @Column(name = "obstacle_y")
    private double obstacleY;
    private double distanceMeters;

}
