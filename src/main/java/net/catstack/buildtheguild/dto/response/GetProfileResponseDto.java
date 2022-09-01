package net.catstack.buildtheguild.dto.response;

import lombok.Data;

@Data
public class GetProfileResponseDto {
    private Long id;
    private String username;
    private String email;
}
