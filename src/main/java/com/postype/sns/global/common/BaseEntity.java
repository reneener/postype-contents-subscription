package com.postype.sns.global.common;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {
	@CreatedDate
	@Column(name = "register_at", updatable = false)
	private LocalDateTime registeredAt;

	@LastModifiedDate
	@Column(name = "updated_at", updatable = true)
	private LocalDateTime updatedAt;
}

