package dto;

import com.yyrobotics.contracts.proto.EventSource;
import com.yyrobotics.contracts.proto.EventType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@ToString
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseRoverDto {

    private String roverId;

    private EventSource source;

    @Builder.Default
    private long timestamp = LocalDateTime.now().getNano();

    public abstract EventType getEventType();


}
