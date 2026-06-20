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
@Table(name = "rover_route_progress")
@ToString(callSuper = true)
@SuperBuilder
public class RoverRouteProgressEntity extends BaseEntity {

    private int currentWaypoint;
    private int totalWaypoints;
    private double remainingDistanceMeters;
    private String navigationState;

}
