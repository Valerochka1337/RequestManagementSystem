package org.valerochka1337.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.valerochka1337.entity.Request;
import org.valerochka1337.entity.Status;

public interface RequestRepository extends JpaRepository<Request, Long> {
  List<Request> findAllByStatus(Status status, Pageable pageable);

  List<Request> findAllByAuthor_Id(Long author_id, Pageable pageable);

  List<Request> findAllByStatusAndAuthor_Id(Status status, Long authorId, Pageable pageable);
}
