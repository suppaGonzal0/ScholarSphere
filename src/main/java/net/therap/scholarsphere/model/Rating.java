package net.therap.scholarsphere.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
@Table(name = "rating")
@NoArgsConstructor
@NamedQueries({
		@NamedQuery(name = "Rating.findRating",
				query = "SELECT r FROM Rating r WHERE r.user = :user AND r.paper = :paper")
})
public class Rating extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_rating")
	@SequenceGenerator(name = "seq_rating", sequenceName = "rating_sequence", allocationSize = 1)
	private Long id;

	@NotNull
	@Min(0)
	@Max(5)
	private int rating;

	@NotNull
	@ManyToOne(cascade = {
			CascadeType.MERGE,
			CascadeType.PERSIST
	})
	@JoinColumn(name = "paper_id")
	private Paper paper;

	@NotNull
	@ManyToOne(cascade = {
			CascadeType.MERGE,
			CascadeType.PERSIST
	})
	@JoinColumn(name = "user_id")
	private User user;

	public Rating(int rating, Paper paper, User user) {
		this.rating = rating;
		this.paper = paper;
		this.user = user;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Rating)) {
			return false;
		}

		return Objects.equals(this.getId(), ((Rating) object).getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getId());
	}
}
