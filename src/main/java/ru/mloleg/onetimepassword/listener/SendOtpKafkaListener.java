package ru.mloleg.onetimepassword.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.mloleg.onetimepassword.dto.MessageResponse;
import ru.mloleg.onetimepassword.service.KafkaMessageContext;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "otp.kafka.send-otp", name = "enabled", havingValue = "true")
public class SendOtpKafkaListener {

    private final KafkaMessageContext kafkaMessageContext;

    @KafkaListener(topics = "${otp.kafka.send-otp.topic-out}")
    public void consumeRequest(ConsumerRecord<String, MessageResponse> record) {
        log.info("Запрос получен: {}", record);

        MessageResponse message = record.value();

        kafkaMessageContext.findById(message.id().toString())
                .complete(message);
    }
}
