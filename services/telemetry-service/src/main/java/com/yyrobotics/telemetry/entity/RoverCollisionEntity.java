package com.yyrobotics.telemetry.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rover_collision")
@ToString(callSuper = true)
@Builder
public class RoverCollisionEntity extends BaseEntity {

    private String collisionType;
    private double impactForce;

}
