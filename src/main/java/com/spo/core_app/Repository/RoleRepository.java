package com.spo.core_app.Repository;

import com.spo.core_app.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Roles, UUID> {
}
