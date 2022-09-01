package net.catstack.buildtheguild.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class LoginUserRequestDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
