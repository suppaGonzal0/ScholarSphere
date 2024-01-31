package net.therap.scholarsphere.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.util.Objects;

/**
 * @author mehzabinaothoi
 * @since 1/25/24
 */

@Getter
@Setter
@Entity
@Table(name = "notification")
@NoArgsConstructor
@NamedQueries({
		@NamedQuery(name = "Notification.findAll", query = "SELECT n FROM Notification n WHERE n.receiver.id = :id")
})
public class Notification extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_notification")
	@SequenceGenerator(name = "seq_notification", sequenceName = "notification_sequence", allocationSize = 1)
	private Long id;

	@ManyToOne(cascade = {
			CascadeType.MERGE,
			CascadeType.PERSIST
	})
	@JoinColumn(name = "receiver_id")
	private User receiver;

	@NotNull
	private String message;

	public Notification(User receiver, String message) {
		this.receiver = receiver;
		this.message = message;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Notification)) {
			return false;
		}

		return Objects.equals(this.getId(), ((Notification) object).getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getId());
	}
}
