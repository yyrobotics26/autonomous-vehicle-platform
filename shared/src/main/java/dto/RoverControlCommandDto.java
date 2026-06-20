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
public class RoverControlCommandDto extends BaseRoverDto {

    private double throttle;
    private double brake;
    private double steering;
    private boolean reverse;

    @Override
    public EventType getEventType() {
        return EventType.ROVER_CONTROL_COMMAND;
    }
}
