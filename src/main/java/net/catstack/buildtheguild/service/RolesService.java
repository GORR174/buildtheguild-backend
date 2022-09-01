package net.catstack.buildtheguild.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catstack.buildtheguild.enums.UserRole;
import net.catstack.buildtheguild.repository.RoleRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Service
@Slf4j
@RequiredArgsConstructor
public class RolesService {
    private final RoleRepository repository;

    @PostConstruct
    public void init() {
        log.info("Adding roles to DB");
        Arrays.stream(UserRole.values())
                .map(UserRole::name)
                .forEach(repository::addIfNotExists);
    }
}
