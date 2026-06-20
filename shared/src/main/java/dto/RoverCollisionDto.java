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
public class RoverCollisionDto extends BaseRoverDto {

    private String collisionType;
    private double impactForce;

    @Override
    public EventType getEventType() {
        return EventType.ROVER_COLLISION;
    }
}
