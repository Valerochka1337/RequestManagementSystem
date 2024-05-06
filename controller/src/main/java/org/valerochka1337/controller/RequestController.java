package org.valerochka1337.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.valerochka1337.dto.RequestDTO;
import org.valerochka1337.mapper.RequestDTOModelMapper;
import org.valerochka1337.service.RequestService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/requests")
public class RequestController {
  private final RequestService requestService;
  private final RequestDTOModelMapper requestDTOModelMapper;

  public RequestController(RequestService requestService, RequestDTOModelMapper requestDTOModelMapper) {
    this.requestService = requestService;
    this.requestDTOModelMapper = requestDTOModelMapper;
  }

  @GetMapping
  public List<RequestDTO> getRequests() {
    return requestService.getAllRequests().stream()
        .map(requestDTOModelMapper::toDTO)
        .collect(Collectors.toList());
  }

  
}
