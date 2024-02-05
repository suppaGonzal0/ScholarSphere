package net.therap.scholarsphere.enums;

import lombok.Getter;

/**
 * @author aothoi
 * @since 05/02/2024
 */
@Getter
public enum Status {
    ENABLED((short) 1),
    DISABLED((short) 0);

    private final short value;

    Status(short value) {
        this.value = value;
    }
}
