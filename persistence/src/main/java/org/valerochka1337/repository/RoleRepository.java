package org.valerochka1337.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.valerochka1337.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByNameIgnoreCase(String name);
}
