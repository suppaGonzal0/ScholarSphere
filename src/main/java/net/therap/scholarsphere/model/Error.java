package net.therap.scholarsphere.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author mehzabinaothoi
 * @since 1/25/24
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Error {

	private String code;
	private String description;
	private String message;
}
