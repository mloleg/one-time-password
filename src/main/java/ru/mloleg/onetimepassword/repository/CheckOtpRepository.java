package ru.mloleg.onetimepassword.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mloleg.onetimepassword.model.CheckOtp;

import java.util.List;
import java.util.UUID;

public interface CheckOtpRepository extends JpaRepository<CheckOtp, UUID> {

    List<CheckOtp> findCheckOtpByProcessId(String processId);

}
