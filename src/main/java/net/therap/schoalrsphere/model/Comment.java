package net.therap.schoalrsphere.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author mehzabinaothoi
 * @since 1/25/24
 */

@Getter
@Setter
@Entity
@Table(name = "comment")
@NamedQueries({
		@NamedQuery(name = "Comment.findAll", query = "SELECT c FROM Comment c LEFT JOIN FETCH c.likedByUsers " +
				"WHERE c.paper.id = :paper_id ORDER BY " +
				"CASE WHEN :sort = 'top' THEN SIZE(c.likedByUsers) END DESC, " +
				"CASE WHEN :sort = 'newest' THEN c.createdOn END DESC, " +
				"CASE WHEN :sort = 'oldest' THEN c.createdOn END ASC, c.id")
})
public class Comment extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_comment")
	@SequenceGenerator(name = "seq_comment", sequenceName = "comment_sequence", allocationSize = 1)
	private Long id;

	@NotNull
	@Size(max = 500)
	@Column(length = 512)
	private String text;

	@NotNull
	@ManyToMany(cascade = {
			CascadeType.MERGE,
			CascadeType.PERSIST
	})
	@JoinTable(name = "comment_likes",
			joinColumns = @JoinColumn(name = "comment_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id")
	)
	private Set<User> likedByUsers;

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

	public Comment() {
		likedByUsers = new HashSet<>();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Comment)) {
			return false;
		}

		return Objects.equals(this.getId(), ((Comment) object).getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getId());
	}
}
