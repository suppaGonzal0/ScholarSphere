package net.therap.scholarsphere.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import org.springframework.data.annotation.Version;

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
