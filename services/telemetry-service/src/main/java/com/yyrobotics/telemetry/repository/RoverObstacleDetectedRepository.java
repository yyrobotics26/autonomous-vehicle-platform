package com.yyrobotics.telemetry.repository;

import com.yyrobotics.telemetry.entity.RoverObstacleDetectedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoverObstacleDetectedRepository extends JpaRepository<RoverObstacleDetectedEntity, Long> {
}
