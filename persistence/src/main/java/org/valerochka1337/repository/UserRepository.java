package org.valerochka1337.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.valerochka1337.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  boolean existsByUsername(String username);
}
