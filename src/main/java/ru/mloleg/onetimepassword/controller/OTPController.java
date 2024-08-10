package ru.mloleg.onetimepassword.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mloleg.onetimepassword.dto.CheckRequest;
import ru.mloleg.onetimepassword.dto.GenerateAndSendRequest;
import ru.mloleg.onetimepassword.dto.common.CommonRequest;
import ru.mloleg.onetimepassword.dto.common.CommonResponse;

import java.util.ArrayList;

@RestController
@RequestMapping("/otp/api/v1/otp")
public class OTPController {

    @PostMapping("/generateAndSend")
    public CommonResponse<?> generateAndSend(@RequestBody @Valid CommonRequest<GenerateAndSendRequest> request) {
        return new CommonResponse<>(new Object(), "", new ArrayList<>());
    }

    @PostMapping("/check")
    public CommonResponse<?> check(@RequestBody @Valid CommonRequest<CheckRequest> request) {
        return new CommonResponse<>(new Object(), "", new ArrayList<>());
    }
}
