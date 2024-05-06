package org.valerochka1337.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.valerochka1337.mapper.RequestModelEntityMapper;
import org.valerochka1337.model.RequestModel;
import org.valerochka1337.repository.RequestRepository;

@Service
public class RequestServiceImpl implements RequestService {
  private final RequestRepository requestRepository;
  private final RequestModelEntityMapper mapper;

  public RequestServiceImpl(RequestRepository requestRepository, RequestModelEntityMapper mapper) {
    this.requestRepository = requestRepository;
    this.mapper = mapper;
  }

  @Override
  public List<RequestModel> getAllRequests() {
    return requestRepository.findAll().stream().map(mapper::toModel).collect(Collectors.toList());
  }
}
