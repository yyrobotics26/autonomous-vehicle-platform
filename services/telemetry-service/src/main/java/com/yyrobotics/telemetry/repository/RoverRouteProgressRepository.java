package com.yyrobotics.telemetry.repository;

import com.yyrobotics.telemetry.entity.RoverRouteProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoverRouteProgressRepository extends JpaRepository<RoverRouteProgressEntity, Long> {
}
