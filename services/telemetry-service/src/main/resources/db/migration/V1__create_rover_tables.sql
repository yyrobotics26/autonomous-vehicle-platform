-- V1__create_rover_event_tables.sql

CREATE TABLE rover_telemetry (
                                 id BIGSERIAL PRIMARY KEY,
                                 created_at TIMESTAMP NOT NULL,
                                 updated_at TIMESTAMP,
                                 created_by VARCHAR(255) NOT NULL,
                                 updated_by VARCHAR(255),
                                 rover_id VARCHAR(255),
                                 time_stamp TIMESTAMP,
                                 event_source VARCHAR(50),

                                 x DOUBLE PRECISION NOT NULL,
                                 y DOUBLE PRECISION NOT NULL,
                                 speed DOUBLE PRECISION NOT NULL,
                                 battery_level DOUBLE PRECISION NOT NULL
);

CREATE TABLE rover_route_progress (
                                      id BIGSERIAL PRIMARY KEY,
                                      created_at TIMESTAMP NOT NULL,
                                      updated_at TIMESTAMP,
                                      created_by VARCHAR(255) NOT NULL,
                                      updated_by VARCHAR(255),
                                      rover_id VARCHAR(255),
                                      time_stamp TIMESTAMP,
                                      event_source VARCHAR(50),

                                      route_id VARCHAR(255),
                                      current_waypoint INTEGER NOT NULL,
                                      total_waypoints INTEGER NOT NULL,
                                      remaining_distance_meters DOUBLE PRECISION NOT NULL,
                                      navigation_state VARCHAR(255)
);

CREATE TABLE rover_obstacle_detected (
                                         id BIGSERIAL PRIMARY KEY,
                                         created_at TIMESTAMP NOT NULL,
                                         updated_at TIMESTAMP,
                                         created_by VARCHAR(255) NOT NULL,
                                         updated_by VARCHAR(255),
                                         rover_id VARCHAR(255),
                                         time_stamp TIMESTAMP,
                                         event_source VARCHAR(50),

                                         obstacle_type VARCHAR(255),
                                         obstacle_x DOUBLE PRECISION NOT NULL,
                                         obstacle_y DOUBLE PRECISION NOT NULL,
                                         distance_meters DOUBLE PRECISION NOT NULL
);

CREATE TABLE rover_control_command (
                                       id BIGSERIAL PRIMARY KEY,
                                       created_at TIMESTAMP NOT NULL,
                                       updated_at TIMESTAMP,
                                       created_by VARCHAR(255) NOT NULL,
                                       updated_by VARCHAR(255),
                                       rover_id VARCHAR(255),
                                       time_stamp TIMESTAMP,
                                       event_source VARCHAR(50),

                                       throttle DOUBLE PRECISION NOT NULL,
                                       brake DOUBLE PRECISION NOT NULL,
                                       steering DOUBLE PRECISION NOT NULL,
                                       reverse BOOLEAN NOT NULL
);

CREATE TABLE rover_collision (
                                 id BIGSERIAL PRIMARY KEY,
                                 created_at TIMESTAMP NOT NULL,
                                 updated_at TIMESTAMP,
                                 created_by VARCHAR(255) NOT NULL,
                                 updated_by VARCHAR(255),
                                 rover_id VARCHAR(255),
                                 time_stamp TIMESTAMP,
                                 event_source VARCHAR(50),

                                 collision_type VARCHAR(255),
                                 impact_force DOUBLE PRECISION NOT NULL
);

-- Common indexes
CREATE INDEX idx_rover_telemetry_rover_id ON rover_telemetry(rover_id);
CREATE INDEX idx_rover_telemetry_timestamp ON rover_telemetry(time_stamp);

CREATE INDEX idx_rover_route_progress_rover_id ON rover_route_progress(rover_id);
CREATE INDEX idx_rover_route_progress_timestamp ON rover_route_progress(time_stamp);

CREATE INDEX idx_rover_obstacle_detected_rover_id ON rover_obstacle_detected(rover_id);
CREATE INDEX idx_rover_obstacle_detected_timestamp ON rover_obstacle_detected(time_stamp);

CREATE INDEX idx_rover_control_command_rover_id ON rover_control_command(rover_id);
CREATE INDEX idx_rover_control_command_timestamp ON rover_control_command(time_stamp);

CREATE INDEX idx_rover_collision_rover_id ON rover_collision(rover_id);
CREATE INDEX idx_rover_collision_timestamp ON rover_collision(time_stamp);