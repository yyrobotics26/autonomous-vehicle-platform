package dto;

import com.yyrobotics.contracts.proto.EventType;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class RoverTelemetryDto extends BaseRoverDto{

    private double x;
    private double y;
    private double speed;
    private double batteryLevel;

    @Override
    public EventType getEventType() {
        return EventType.ROVER_TELEMETRY;
    }
}
