package ru.mloleg.onetimepassword.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mloleg.onetimepassword.dto.CheckRequest;
import ru.mloleg.onetimepassword.dto.GenerateAndSendRequest;
import ru.mloleg.onetimepassword.dto.common.CommonRequest;
import ru.mloleg.onetimepassword.dto.common.CommonResponse;
import ru.mloleg.onetimepassword.service.OtpService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/otp/api/v1/otp")
public class OtpController {

    private final OtpService otpService;

    @PostMapping("/generateAndSend")
    public CommonResponse<?> generateAndSend(@RequestBody @Valid CommonRequest<GenerateAndSendRequest> request) {
        otpService.generateAndSend(request.body());

        return CommonResponse.builder()
                .build();
    }

    @PostMapping("/check")
    public CommonResponse<?> check(@RequestBody @Valid CommonRequest<CheckRequest> request) {
        otpService.check(request.body());

        return CommonResponse.builder()
                .build();
    }
}