package net.therap.scholarsphere.model;

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
@Entity
@Getter
@Setter
@Table(name = "tag")
public class Tag extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tag")
    @SequenceGenerator(name = "seq_tag", sequenceName = "tag_sequence", allocationSize = 1)
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(length = 32, unique = true)
    private String name;

    @ManyToMany(mappedBy = "tags")
    @OrderBy("title ASC")
    private Set<Paper> papers;

    public Tag() {
        this.papers = new HashSet<>();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Tag)) {
            return false;
        }

        return Objects.equals(this.getId(), ((Tag) object).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
