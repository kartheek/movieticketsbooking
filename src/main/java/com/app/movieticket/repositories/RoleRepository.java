package com.app.movieticket.repositories;

import com.app.movieticket.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> getRoleByName(String customer);
}
