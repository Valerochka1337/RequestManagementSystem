package org.valerochka1337.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.valerochka1337.model.RequestModel;

@Service
public interface RequestService {
  RequestModel createRequest(RequestModel request);

  void removeRequestById(Long id);

  List<RequestModel> getRequests();

  RequestModel getRequestById(Long id);

  List<RequestModel> getSentRequestsForUserByPartialUsername(String partialUsername);

  RequestModel editMessageRequestWithId(Long id, String editedMessage);

  RequestModel sendRequestWithId(Long id);

  RequestModel acceptRequestWithId(Long id);

  RequestModel rejectRequestWithId(Long id);
}
