package net.catstack.buildtheguild.model;

import lombok.Data;
import net.catstack.buildtheguild.enums.UserRole;

import javax.persistence.*;

@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private UserRole name;
}
