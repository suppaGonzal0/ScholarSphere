package net.therap.scholarsphere.model.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import lombok.*;
import net.therap.scholarsphere.validation.ValidationGroups;

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
	private String city;

	@NotNull(groups = {Default.class, ValidationGroups.RegStepOne.class})
	@Size(max = 40, groups = {Default.class, ValidationGroups.RegStepOne.class})
	@Column(length = 48)
	private String country;

	@Override
	public String toString() {
		return city + ", " + country;
	}
}
