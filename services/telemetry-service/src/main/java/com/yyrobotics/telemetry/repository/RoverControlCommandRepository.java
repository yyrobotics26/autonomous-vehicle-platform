package com.yyrobotics.telemetry.repository;

import com.yyrobotics.telemetry.entity.RoverControlCommandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoverControlCommandRepository extends JpaRepository<RoverControlCommandEntity, Long> {
}
