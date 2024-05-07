package org.valerochka1337.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.valerochka1337.entity.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

}
