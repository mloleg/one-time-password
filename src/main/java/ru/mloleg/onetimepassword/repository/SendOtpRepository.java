package ru.mloleg.onetimepassword.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mloleg.onetimepassword.model.SendOtp;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SendOtpRepository extends JpaRepository<SendOtp, UUID> {

    Optional<SendOtp> findSendOtpBySendMessageKey(String sendMessageKey);

    List<SendOtp> findSendOtpByProcessId(String processId);
}
