package net.therap.schoalrsphere.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author mehzabinaothoi
 * @since 1/25/24
 */

@Getter
@MappedSuperclass
public class BaseEntity implements Serializable {

	@Column(name = "created_on", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	private LocalDateTime createdOn;

	@Column(name = "updated_on", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	private LocalDateTime updatedOn;

	@Version
	@NotNull
	private int version;

	@PrePersist
	public void prePersist() {
		createdOn = LocalDateTime.now();
	}

	@PreUpdate
	public void preUpdate() {
		updatedOn = LocalDateTime.now();
	}
}
