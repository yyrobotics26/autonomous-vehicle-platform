package com.yyrobotics.gateway.service;

import com.yyrobotics.contracts.proto.EventEnvelope;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaMessageSender {


    @PostConstruct
    public void init() {
        log.info("Gateway-service build 15:57");
    }

    private final KafkaTemplate<String, EventEnvelope> kafkaTemplate;

    public KafkaMessageSender(KafkaTemplate<String, EventEnvelope> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, EventEnvelope message) {
        log.info("Sending message: {}, to topic: {}", message, topic);
        kafkaTemplate.send(topic, message)
                .whenComplete((res, e) -> {
                    if (e != null) {
                        log.error("Error while sending message", e);
                    } else {
                        log.info("Message: {} sent successfully to topic {} at {}", message, res.getRecordMetadata().topic(), res.getRecordMetadata().timestamp());
                    }
                });
    }

}
