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
@Table(name = "send_otp")
public class SendOTP extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;
    public String processId;
    public String telegramChatId;
    public String message;
    public int length;
    public int ttl;
    public int resendAttempts;
    public int resendTimeout;
    public String salt;
    public String sendMessageKey;
    @Enumerated(EnumType.STRING)
    public Status status;
    public ZonedDateTime sendTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SendOTP sendOTP)) return false;
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
