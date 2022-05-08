package com.javaee.web.starter.repository;

import com.javaee.web.starter.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
