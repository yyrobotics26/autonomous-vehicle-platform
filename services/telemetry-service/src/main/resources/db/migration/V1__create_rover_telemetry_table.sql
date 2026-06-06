CREATE TABLE IF NOT EXISTS rover_telemetry (
    id SERIAL         PRIMARY KEY,
    created_at        TIMESTAMPTZ NOT NULL,
    updated_at        TIMESTAMPTZ,
    created_by        VARCHAR(255) NOT NULL,
    updated_by        VARCHAR(255),
    rover_id          VARCHAR(255),
    time_stamp        TIMESTAMPTZ,
    x                 DOUBLE PRECISION,
    y                 DOUBLE PRECISION,
    speed             DOUBLE PRECISION,
    battery_level     DOUBLE PRECISION);