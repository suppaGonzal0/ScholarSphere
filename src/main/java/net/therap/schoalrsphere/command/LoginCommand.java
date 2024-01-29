package net.therap.schoalrsphere.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginCommand {

    @NotNull
    @Email
    @Size(max = 120)
    private String email;

    @NotNull
    @Size(max = 120)
    private String password;
}
