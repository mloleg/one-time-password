package ru.mloleg.onetimepassword.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mloleg.onetimepassword.client.SendOtpKafkaProducer;
import ru.mloleg.onetimepassword.dto.*;
import ru.mloleg.onetimepassword.exception.*;
import ru.mloleg.onetimepassword.model.CheckOtp;
import ru.mloleg.onetimepassword.model.SendOtp;
import ru.mloleg.onetimepassword.model.Status;
import ru.mloleg.onetimepassword.repository.CheckOtpRepository;
import ru.mloleg.onetimepassword.repository.SendOtpRepository;
import ru.mloleg.onetimepassword.service.OtpService;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final SendOtpRepository sendOtpRepository;
    private final CheckOtpRepository checkOtpRepository;
    private final PasswordEncoder passwordEncoder;
    private final SendOtpKafkaProducer kafkaProducer;

    public void generateAndSend(GenerateAndSendRequest request) {
        List<SendOtp> sendOtpList = sendOtpRepository.findSendOtpByProcessId(request.processId().toString());

        if (!sendOtpList.isEmpty()) {
            SendOtp recentOtp = Collections.max(sendOtpList, Comparator.comparing(SendOtp::getCreateTime));

            if (recentOtp.getCreateTime().plusSeconds(request.ttl()).isBefore(ZonedDateTime.now())) {
                throw new OtpTtlExceededException("Превышено время жизни OTP");
            }

            long timeBetweenRequests = Duration.between(ZonedDateTime.now(), recentOtp.getCreateTime()).getSeconds();
            if (timeBetweenRequests < request.resendTimeout()) {
                throw new OtpRequestTimeoutException("Превышена частота попыток отравок OTP");
            }

            if (sendOtpList.size() >= request.resendAttempts()) {
                throw new OtpResendAttemptsExceededException("Превышено количество отправок OTP");
            }
        }

        String generatedPassword = RandomStringUtils.randomNumeric(request.length());
        String salt = passwordEncoder.encode(request.processId() + generatedPassword);
        String message = request.message().formatted(generatedPassword);
        UUID sendMessageKey = UUID.randomUUID();
        ZonedDateTime currentTime = ZonedDateTime.now();

        SendOtp sendOtp = SendOtp.builder()
                .processId(request.processId().toString())
                .telegramChatId(request.telegramChatId())
                .message(message)
                .length(request.length())
                .ttl(request.ttl())
                .resendAttempts(request.resendAttempts())
                .resendTimeout(request.resendTimeout())
                .salt(salt)
                .sendMessageKey(sendMessageKey.toString())
                .status(Status.IN_PROCESS)
                .sendTime(currentTime)
                .build();

        sendOtpRepository.save(sendOtp);

        MessageResponse response = kafkaProducer.sendRequest(MessageRequest.builder()
                .id(sendMessageKey)
                .telegramChatId(request.telegramChatId())
                .message(message)
                .build());

        SendOtp otp = sendOtpRepository.findSendOtpBySendMessageKey(response.id().toString())
                .orElseThrow(() -> new OtpNotFoundException("Не найдено OTP с SendMessageKey: {%s}"
                        .formatted(response.id().toString())));

        if (response.status().equals(MessageStatus.ERROR)) {
            otp.setStatus(Status.ERROR);

            throw new OtpException(response.errorMessage());
        } else if (response.status().equals(MessageStatus.SUCCESS)) {
            otp.setStatus(Status.DELIVERED);
        }

        sendOtpRepository.flush();
    }

    @Override
    public void check(CheckRequest request) {
        List<SendOtp> sendOtpList = sendOtpRepository.findSendOtpByProcessId(request.processId().toString());
        SendOtp recentOtp = Collections.max(sendOtpList, Comparator.comparing(SendOtp::getCreateTime));

        if (recentOtp.getCreateTime().plusSeconds(recentOtp.getTtl()).isBefore(ZonedDateTime.now())) {
            throw new OtpTtlExceededException("Превышено время жизни OTP");
        }

        List<CheckOtp> checkOtpList = checkOtpRepository.findCheckOtpByProcessId(request.processId().toString());

        List<CheckOtp> checkOtps = checkOtpList.stream()
                .filter(checkOtp -> checkOtp.getProcessId().equals(
                        request.processId().toString()) &&
                        checkOtp.getCorrect() &&
                        Objects.equals(checkOtp.getOtp(), request.otp()))
                .toList();

        if (!checkOtps.isEmpty()) {
            checkOtpRepository.save(CheckOtp.builder()
                    .processId(request.processId().toString())
                    .otp(request.otp())
                    .checkTime(ZonedDateTime.now())
                    .correct(false)
                    .build());

            throw new OtpConfirmationException("Попытка подтверждения ранее подтверждённого пароля");
        }

        String rawPassword = request.processId().toString() + request.otp();
        String sendOtpSalt = recentOtp.getSalt();

        CheckOtp toBeSavedOtp = checkOtpRepository.save(CheckOtp.builder()
                .processId(request.processId().toString())
                .otp(request.otp())
                .checkTime(ZonedDateTime.now())
                .build());

        if (passwordEncoder.matches(rawPassword, sendOtpSalt)) {
            toBeSavedOtp.setCorrect(true);
        } else {
            toBeSavedOtp.setCorrect(false);

            throw new IncorrectOtpException("Введен неверный OTP");
        }

        checkOtpRepository.flush();
    }
}
