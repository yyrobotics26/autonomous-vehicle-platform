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
public class RoverRouteProgressDto extends BaseRoverDto {

    private String routeId;
    private int currentWaypoint;
    private int totalWaypoints;
    private double remainingDistanceMeters;
    private String navigationState;

    @Override
    public EventType getEventType() {
        return EventType.ROVER_ROUTE_PROGRESS;
    }
}
