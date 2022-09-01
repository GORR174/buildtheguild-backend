package net.catstack.buildtheguild.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RegisterUserRequestDto {
    @NotBlank
    @Size(max = 15, min = 4)
    private String username;
    @NotBlank
    @Size(max = 120)
    private String password;
    @NotBlank
    @Email
    private String email;
}
