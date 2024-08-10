package ru.mloleg.onetimepassword.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.mloleg.onetimepassword.client.SendOtpKafkaProducer;
import ru.mloleg.onetimepassword.dto.MessageIn;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "otp.kafka.send-otp", name = "enabled", havingValue = "true")
public class SendOtpKafkaListener {

    private final SendOtpKafkaProducer kafkaProducer;

    @KafkaListener(topics = "${otp.kafka.send-otp.topic-in}")
    public void consumeSendOtp(ConsumerRecord<String, MessageIn> record) {
        log.info("Запрос получен: {}", record);

        kafkaProducer.sendResponse(record.value().id());
    }
}
