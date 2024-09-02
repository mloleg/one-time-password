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
@Table(name = "send_otp")
public class SendOtp extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String processId;
    private String telegramChatId;
    private String message;
    private Integer length;
    private Integer ttl;
    private Integer resendAttempts;
    private Integer resendTimeout;
    private String salt;
    private String sendMessageKey;
    @Enumerated(EnumType.STRING)
    private Status status;
    private ZonedDateTime sendTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SendOtp sendOTP)) return false;
        return Objects.equals(id, sendOTP.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "SendOTP{" +
                "id=" + id +
                ", processId='" + processId + '\'' +
                ", telegramChatId='" + telegramChatId + '\'' +
                ", message='" + message + '\'' +
                ", length=" + length +
                ", ttl=" + ttl +
                ", resendAttempts=" + resendAttempts +
                ", resendTimeout=" + resendTimeout +
                ", salt='" + salt + '\'' +
                ", sendMessageKey='" + sendMessageKey + '\'' +
                ", status=" + status +
                ", sendTime=" + sendTime +
                '}';
    }
}
