package net.therap.scholarsphere.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.therap.scholarsphere.model.embedded.Pdf;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.util.*;

/**
 * @author mehzabinaothoi
 * @since 1/25/24
 */
@Entity
@Getter
@Setter
@Table(name = "paper")
@ToString
public class Paper extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_paper")
	@SequenceGenerator(name = "seq_paper", sequenceName = "paper_sequence", allocationSize = 1)
	private Long id;

	@NotNull
	@Size(max = 250)
	@Column(length = 256)
	private String title;

	@NotNull
	@Size(min = 100, max = 1020)
	@Column(length = 1024)
	private String summary;

	@NotNull
	@Column(name = "is_approved")
	private boolean isApproved;

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yy")
	@Past
	@Column(length = 8, name = "publication_date")
	private Date publicationDate;

	@NotNull
	@Column(name = "download_count")
	private int downloadCount;

	@NotNull
	@Embedded
	private Pdf pdf;

	@NotNull
	@ManyToOne(cascade = {
			CascadeType.PERSIST,
			CascadeType.MERGE
	})
	@JoinColumn(name = "conference_id")
	private Conference conference;

	@Column(name = "total_rating")
	private long totalRating;

	@Column(name = "total_rated")
	private long totalRated;

	@ManyToMany(fetch = FetchType.EAGER,
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE
			})
	@JoinTable(name = "paper_user",
			joinColumns = @JoinColumn(name = "paper_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id")
	)
	@NotEmpty
	private Set<User> authors;

	@ManyToMany(fetch = FetchType.EAGER,
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE
			})
	@JoinTable(name = "paper_tag",
			joinColumns = @JoinColumn(name = "paper_id"),
			inverseJoinColumns = @JoinColumn(name = "tag_id")
	)
	@OrderBy("name")
	private Set<Tag> tags;

	public Paper() {
		this.authors = new LinkedHashSet<>();
		this.tags = new HashSet<>();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Paper)) {
			return false;
		}

		return Objects.equals(this.getId(), ((Paper) object).getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getId());
	}
}