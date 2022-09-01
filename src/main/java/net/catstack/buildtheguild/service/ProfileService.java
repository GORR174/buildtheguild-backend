package net.catstack.buildtheguild.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catstack.buildtheguild.dto.response.GetProfileResponseDto;
import net.catstack.buildtheguild.repository.UserRepository;
import net.catstack.buildtheguild.util.AuthUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileService {
    private final UserRepository userRepository;

    public GetProfileResponseDto getCurrentProfile() {
        var response = new GetProfileResponseDto();
        var user = userRepository.findByUsername(AuthUtils.getCurrentUsername());

        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());

        return response;
    }
}
