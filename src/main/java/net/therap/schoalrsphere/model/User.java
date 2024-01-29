package net.therap.schoalrsphere.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import lombok.Getter;
import lombok.Setter;
import net.therap.schoalrsphere.annotation.Password;
import net.therap.schoalrsphere.enums.Role;
import net.therap.schoalrsphere.model.embedded.FullName;
import net.therap.schoalrsphere.model.embedded.Location;
import net.therap.schoalrsphere.validation.ValidationGroups;

import java.io.Serial;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author mehzabinaothoi
 * @since 1/25/24
 */

@Entity
@Getter
@Setter
@Table(name = "users")
@NamedQueries({
		@NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
		@NamedQuery(name = "User.findAllUsers", query = "SELECT u FROM User u WHERE u.role = 'REGULAR'"),
		@NamedQuery(name = "User.findPapers", query = "SELECT p FROM User u JOIN u.papers p WHERE p.isApproved = true AND u.id = :id"),
		@NamedQuery(name = "User.findFollowers", query = "SELECT f FROM User u JOIN u.followings f WHERE u.id = :id"),
		@NamedQuery(name = "User.findFollowings", query = "SELECT f FROM User u JOIN u.followers f WHERE u.id = :id"),
})
public class User extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user")
	@SequenceGenerator(name = "seq_user", sequenceName = "user_sequence", allocationSize = 1)
	private Long id;

	@NotNull(groups = {Default.class, ValidationGroups.RegStepTwo.class})
	@Size(max = 30, groups = {Default.class, ValidationGroups.RegStepTwo.class})
	@Column(length = 32, unique = true)
	private String username;

	@NotNull(groups = {Default.class, ValidationGroups.RegStepTwo.class})
	@Email(groups = {Default.class, ValidationGroups.RegStepTwo.class})
	@Size(max = 120, groups = {Default.class, ValidationGroups.RegStepTwo.class})
	@Column(length = 128, unique = true)
	private String email;

	@Password(groups = {Default.class, ValidationGroups.RegStepTwo.class})
	@Size(max = 68, groups = {Default.class, ValidationGroups.RegStepTwo.class})
	@Column(length = 70)
	private String password;

	@NotNull(groups = {Default.class, ValidationGroups.RegStepOne.class})
	@Embedded
	@Valid
	private FullName fullName;

	@NotNull(groups = {Default.class, ValidationGroups.RegStepOne.class})
	@Embedded
	@Valid
	private Location location;

	@Size(max = 250, groups = {Default.class, ValidationGroups.RegStepOne.class})
	@Column(length = 256)
	private String bio;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Role role;

	@NotNull
	private short enabled;

	@ManyToMany(cascade = {
			CascadeType.PERSIST,
			CascadeType.MERGE
	})
	@JoinTable(
			name = "follow",
			joinColumns = @JoinColumn(name = "following_id"),
			inverseJoinColumns = @JoinColumn(name = "follower_id")
	)
	private final Set<User> followers;

	@ManyToMany(mappedBy = "followers",
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE
			})
	private final Set<User> followings;

	@ManyToMany(mappedBy = "authors")
	@OrderBy("publicationDate DESC")
	private final Set<Paper> papers;

	public User() {
		this.setRole(Role.REGULAR);

		this.papers = new HashSet<>();
		this.followers = new HashSet<>();
		this.followings = new HashSet<>();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof User)) {
			return false;
		}

		return Objects.equals(this.getId(), ((User) object).getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getId());
	}
}
