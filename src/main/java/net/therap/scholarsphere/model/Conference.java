package net.therap.scholarsphere.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import net.therap.scholarsphere.model.embedded.Location;

import java.io.Serial;
import java.util.Objects;

/**
 * @author mehzabinaothoi
 * @since 1/25/24
 */

@Getter
@Setter
@Entity
@Table(name = "conference")
public class Conference extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_conference")
	@SequenceGenerator(name = "seq_conference", sequenceName = "conference_sequence", allocationSize = 1)
	private Long id;

	@NotNull
	@Size(max = 250)
	@Column(length = 256, unique = true)
	private String title;

	@NotNull
	@Embedded
	@Valid
	private Location location;

	public Conference() {
		location = new Location();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Conference)) {
			return false;
		}

		return Objects.equals(this.getId(), ((Conference) object).getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getId());
	}
}
