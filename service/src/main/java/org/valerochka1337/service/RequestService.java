package org.valerochka1337.service;

import org.springframework.stereotype.Service;
import org.valerochka1337.entity.Request;
import org.valerochka1337.model.RequestModel;

import java.util.List;

@Service
public interface RequestService {
  List<RequestModel> getAllRequests();
}
