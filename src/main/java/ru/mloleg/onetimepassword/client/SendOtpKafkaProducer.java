package ru.mloleg.onetimepassword.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.mloleg.onetimepassword.dto.MessageRequest;
import ru.mloleg.onetimepassword.dto.MessageResponse;
import ru.mloleg.onetimepassword.exception.OtpException;
import ru.mloleg.onetimepassword.exception.ResponseTimeoutException;
import ru.mloleg.onetimepassword.service.KafkaMessageContext;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "otp.kafka.send-otp", name = "enabled", havingValue = "true")
public class SendOtpKafkaProducer {

    private final KafkaTemplate<String, MessageRequest> kafkaTemplate;
    private final KafkaMessageContext kafkaMessageContext;

    @Value("${otp.kafka.send-otp.topic-in}")
    private String topic;

    public MessageResponse sendRequest(MessageRequest request) {
        CompletableFuture<MessageResponse> completableFuture =
                kafkaMessageContext.createMessageCompletableFuture(request.id().toString());

        kafkaTemplate.send(topic, request)
                .thenAccept(sendResult -> log.info("Запрос отправлен: {}", sendResult));

        try {
            return completableFuture.get(5000, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            throw new ResponseTimeoutException("Превышено время ожидания ответа от сервиса отправки сообщений");
        } catch (InterruptedException | ExecutionException e) {
            log.warn("Ошибка при получении ответа об отправке сообщения в телеграм: {}", e.getMessage(), e);
        }

        throw new OtpException("Произошла ошибка при отправке сообщения в телеграм");
    }
}
