package org.valerochka1337.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;
import org.valerochka1337.dto.RequestDTO;
import org.valerochka1337.mapper.RequestDTOModelMapper;
import org.valerochka1337.service.RequestService;

@RestController
@RequestMapping(path = "/api/v1/requests")
public class RequestController {
  private final RequestService requestService;
  private final RequestDTOModelMapper requestMapper;

  public RequestController(
      RequestService requestService, RequestDTOModelMapper requestDTOModelMapper) {
    this.requestService = requestService;
    this.requestMapper = requestDTOModelMapper;
  }

  @PostMapping
  public RequestDTO createRequest(@RequestBody RequestDTO request) {
    return requestMapper.toDTO(requestService.createRequest(requestMapper.toModel(request)));
  }

  @DeleteMapping(path = "/{id}")
  public void removeRequest(@PathVariable Long id) {
    requestService.removeRequestById(id);
  }

  @GetMapping
  public List<RequestDTO> getRequests() {
    return requestService.getRequests().stream().map(requestMapper::toDTO).collect(Collectors.toList());
  }

  @GetMapping(params = "partialUsername")
  public List<RequestDTO> getSentRequestsForUserByPartialUsername(
      @RequestParam String partialUsername) {
    return requestService.getSentRequestsForUserByPartialUsername(partialUsername).stream().map(requestMapper::toDTO).collect(Collectors.toList());
  }

  @GetMapping(path = "/{id}")
  public RequestDTO getRequest(@PathVariable Long id) {
    return requestMapper.toDTO(requestService.getRequestById(id));
  }

  @PatchMapping(path = "/{id}/send")
  public RequestDTO sendRequest(@PathVariable Long id) {
    return requestMapper.toDTO(requestService.sendRequestWithId(id));
  }

  @PatchMapping(path = "/{id}/accept")
  public RequestDTO acceptRequest(@PathVariable Long id) {
    return requestMapper.toDTO(requestService.acceptRequestWithId(id));
  }

  @PatchMapping(path = "/{id}/reject")
  public RequestDTO rejectRequest(@PathVariable Long id) {
    return requestMapper.toDTO(requestService.rejectRequestWithId(id));
  }

  @PutMapping(path = "/{id}/edit")
  public RequestDTO editRequest(@PathVariable Long id, @RequestBody String editedMessage) {
    return requestMapper.toDTO(requestService.editMessageRequestWithId(id, editedMessage));
  }
}
