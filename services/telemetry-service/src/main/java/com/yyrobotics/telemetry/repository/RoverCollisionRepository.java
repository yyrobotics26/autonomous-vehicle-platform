package com.yyrobotics.telemetry.repository;

import com.yyrobotics.telemetry.entity.RoverCollisionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoverCollisionRepository extends JpaRepository<RoverCollisionEntity, Long> {
}
