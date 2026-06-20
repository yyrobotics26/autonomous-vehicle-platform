package com.yyrobotics.gateway.mapper;

import com.yyrobotics.contracts.proto.EventEnvelope;
import com.yyrobotics.contracts.proto.EventType;
import dto.BaseRoverDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ModelMapper {

    private final Map<EventType, RoverEventMapper> mappers;

    public ModelMapper(List<RoverEventMapper> mapperList) {
        this.mappers = mapperList.stream()
                .collect(Collectors.toMap(
                        RoverEventMapper::supports,
                        Function.identity()));
    }

    public EventEnvelope toRoverEvent(BaseRoverDto dto) {
        final RoverEventMapper mapper = Optional.ofNullable(mappers.get(dto.getEventType()))
                .orElseThrow(() -> new IllegalArgumentException("Unknown event type: " + dto.getEventType()));

        log.debug("Converting event type {} to Rover event by mapper {}", dto.getEventType(), mapper.getClass());
        return mapper.map(dto);
    }
}
