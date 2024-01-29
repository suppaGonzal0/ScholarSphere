package net.therap.schoalrsphere.model.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class Location {

	@NotNull(groups = {Default.class, ValidationGroups.RegStepOne.class})
	@Size(max = 40, groups = {Default.class, ValidationGroups.RegStepOne.class})
	@Column(length = 48)
	private String country;

	@NotNull(groups = {Default.class, ValidationGroups.RegStepOne.class})
	@Size(max = 40, groups = {Default.class, ValidationGroups.RegStepOne.class})
	@Column(length = 48)
	private String city;
}
