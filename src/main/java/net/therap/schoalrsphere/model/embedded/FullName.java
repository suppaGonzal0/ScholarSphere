package net.therap.schoalrsphere.model.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.therap.schoalrsphere.validation.ValidationGroups;

/**
 * @author mehzabinaothoi
 * @since 1/25/24
 */

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class FullName {

	@NotNull(groups = {Default.class, ValidationGroups.RegStepOne.class})
	@Size(max = 40, groups = {Default.class, ValidationGroups.RegStepOne.class})
	@Column(name = "first_name", length = 48)
	private String firstName;

	@NotNull(groups = {Default.class, ValidationGroups.RegStepOne.class})
	@Size(max = 40, groups = {Default.class, ValidationGroups.RegStepOne.class})
	@Column(name = "Last_name", length = 48)
	private String lastName;
}
