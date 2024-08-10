package ru.mloleg.onetimepassword.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "check_otp")
public class CheckOTP extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;
    public String processId;
    public int otp;
    public ZonedDateTime checkTime;
    public boolean correct;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckOTP checkOTP)) return false;
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
