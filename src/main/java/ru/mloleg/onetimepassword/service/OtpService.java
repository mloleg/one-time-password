package ru.mloleg.onetimepassword.service;

import ru.mloleg.onetimepassword.dto.CheckRequest;
import ru.mloleg.onetimepassword.dto.GenerateAndSendRequest;

public interface OtpService {

    void generateAndSend(GenerateAndSendRequest request);

    void check(CheckRequest request);
}
