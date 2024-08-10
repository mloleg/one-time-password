package ru.mloleg.onetimepassword.model;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@MappedSuperclass
public class AuditableEntity {

    private ZonedDateTime createTime;
    private ZonedDateTime updateTime;

    @PrePersist
    public void prePersist() {
        this.createTime = ZonedDateTime.now();
        this.updateTime = ZonedDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updateTime = ZonedDateTime.now();
    }
}