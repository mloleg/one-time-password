package ru.mloleg.onetimepassword.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.mloleg.onetimepassword.dto.MessageStatus;
import ru.mloleg.onetimepassword.dto.MessageOut;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "otp.kafka.send-otp", name = "enabled", havingValue = "true")
public class SendOtpKafkaProducer {

    @Value("${otp.kafka.send-otp.topic-out}")
    private String topic;

    private final KafkaTemplate<String, MessageOut> kafkaTemplate;

    public void sendResponse(UUID id) {
        kafkaTemplate.send(topic, MessageOut.builder()
                                            .id(id)
                                            .status(MessageStatus.SUCCESS)
                                            .build())
                     .thenAccept(sendResult -> log.info("Ответ отправлен: {}", sendResult));
    }

}
