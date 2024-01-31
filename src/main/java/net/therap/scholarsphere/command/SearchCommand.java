package net.therap.scholarsphere.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author mehzabinaothoi
 * @since 1/11/24
 */
@Getter
@Setter
@NoArgsConstructor
public class SearchCommand {

	private String searchBy;
	private String searchTerm;
}
