package net.therap.schoalrsphere.model.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author mehzabinaothoi
 * @since 1/25/24
 */

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class Pdf {

	@Column(name = "file_name")
	private String name;

	private byte[] file;
}
