package ru.mloleg.onetimepassword.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mloleg.onetimepassword.dto.CheckRequest;
import ru.mloleg.onetimepassword.dto.CreateOTPRequest;
import ru.mloleg.onetimepassword.dto.common.CommonRequest;

@RestController
@RequestMapping("/otp/api/v1/otp")
public class OTPController {

    @PostMapping("/generateAndSend")
    public ResponseEntity<?> generateAndSend(@RequestBody @Valid CommonRequest<CreateOTPRequest> request) {
        return ResponseEntity.status(200).build();
    }

    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody @Valid CommonRequest<CheckRequest> request) {
        return ResponseEntity.status(200).build();
    }
}
