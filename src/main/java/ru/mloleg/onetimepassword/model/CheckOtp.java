package ru.mloleg.onetimepassword.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "check_otp")
public class CheckOtp extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String processId;
    private Integer otp;
    private ZonedDateTime checkTime;
    private Boolean correct;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckOtp checkOTP)) return false;
        return Objects.equals(id, checkOTP.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CheckOTP{" +
                "id=" + id +
                ", processId='" + processId + '\'' +
                ", otp=" + otp +
                ", checkTime=" + checkTime +
                ", correct=" + correct +
                '}';
    }
}
