package net.catstack.buildtheguild.repository;

import net.catstack.buildtheguild.enums.UserRole;
import net.catstack.buildtheguild.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(UserRole name);

    @Modifying
    @Query(value = "INSERT INTO roles(name) SELECT :name WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name=:name)", nativeQuery = true)
    @Transactional
    void addIfNotExists(@Param("name") String name);
}
