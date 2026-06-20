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
public class RoverObstacleDetectedDto extends BaseRoverDto {

    private String obstacleType;
    private double obstacleX;
    private double obstacleY;
    private double distanceMeters;

    @Override
    public EventType getEventType() {
        return EventType.ROVER_OBSTACLE_DETECTED;
    }
}
