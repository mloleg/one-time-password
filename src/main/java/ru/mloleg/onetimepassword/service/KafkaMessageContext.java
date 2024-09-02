package ru.mloleg.onetimepassword.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.mloleg.onetimepassword.dto.MessageResponse;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class KafkaMessageContext {

    private final ConcurrentHashMap<String, CompletableFuture<MessageResponse>> messageContext = new ConcurrentHashMap<>();

    public CompletableFuture<MessageResponse> createMessageCompletableFuture(String id) {
        CompletableFuture<MessageResponse> completableFuture = new CompletableFuture<>();
        messageContext.put(id, completableFuture);

        return completableFuture;
    }

    public CompletableFuture<MessageResponse> findById(String id) {
        return messageContext.get(id);
    }
}
