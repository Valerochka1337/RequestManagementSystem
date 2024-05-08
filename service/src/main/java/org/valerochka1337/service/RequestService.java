package org.valerochka1337.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.valerochka1337.entity.Status;
import org.valerochka1337.model.RequestModel;

@Service
public interface RequestService {
  RequestModel createRequest(RequestModel request);

  void removeRequestById(Long id);

  List<RequestModel> getRequests(Status status, Pageable pageable);

  RequestModel getRequestById(Long id);

  List<RequestModel> getSentRequestsForUserByPartialUsername(String partialUsername, Pageable pageable);

  RequestModel editMessageRequestWithId(Long id, String editedMessage);

  RequestModel sendRequestWithId(Long id);

  RequestModel acceptRequestWithId(Long id);

  RequestModel rejectRequestWithId(Long id);
}
