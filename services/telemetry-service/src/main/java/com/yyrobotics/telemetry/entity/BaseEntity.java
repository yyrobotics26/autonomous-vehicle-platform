package com.yyrobotics.telemetry.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@MappedSuperclass
@FieldNameConstants
@SuperBuilder
public abstract class BaseEntity {

    private static final String DEFAULT_ACTOR = "telemetry-service";

    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant updatedAt;

    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        if (createdAt == null) {
            createdAt = now;
        }
        if (updatedAt == null) {
            updatedAt = now;
        }
        if (createdBy == null || createdBy.isBlank()) {
            createdBy = DEFAULT_ACTOR;
        }
        if (updatedBy == null || updatedBy.isBlank()) {
            updatedBy = createdBy;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
        if (updatedBy == null || updatedBy.isBlank()) {
            updatedBy = DEFAULT_ACTOR;
        }
    }

}
