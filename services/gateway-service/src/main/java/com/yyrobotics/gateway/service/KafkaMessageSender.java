package com.yyrobotics.gateway.service;

import com.google.protobuf.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaMessageSender {

    private final KafkaTemplate<String, Message> kafkaTemplate;

    public KafkaMessageSender(KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, Message message) {
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
