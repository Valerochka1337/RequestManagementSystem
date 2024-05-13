package org.valerochka1337.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.valerochka1337.dto.RequestCreationDTO;
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
  @PreAuthorize("hasAuthority('requests:create')")
  public RequestDTO createRequest(@RequestBody RequestCreationDTO request) {
    return requestMapper.toDTO(
        requestService.createRequest(requestMapper.toModelRequestCreation(request)));
  }

  @DeleteMapping(path = "/{id}")
  @PreAuthorize("hasAuthority('requests:remove:owned')")
  public void removeRequest(@PathVariable Long id) {
    requestService.removeRequestById(id);
  }

  @GetMapping
  @PreAuthorize("hasAuthority('requests:read:owned') or hasAuthority('requests:read:sent:all')")
  public List<RequestDTO> getAllRequests(
      @RequestParam(required = false) String status, @PageableDefault(size=5) Pageable pageable) {
    return requestService.getRequests(requestMapper.mapStringToStatus(status), pageable).stream()
        .map(requestMapper::toDTO)
        .collect(Collectors.toList());
  }

  @GetMapping(params = "partialUsername")
  @PreAuthorize("hasAuthority('requests:read:sent:all')")
  public List<RequestDTO> getSentRequestsForUserByPartialUsername(
      @RequestParam String partialUsername,
      @PageableDefault(size=5) Pageable pageable) {
    return requestService.getSentRequestsForUserByPartialUsername(partialUsername, pageable).stream()
        .map(requestMapper::toDTO)
        .collect(Collectors.toList());
  }

  @GetMapping(path = "/{id}")
  @PreAuthorize("hasAuthority('requests:read:owned')")
  public RequestDTO getRequest(@PathVariable Long id) {
    return requestMapper.toDTO(requestService.getRequestById(id));
  }

  @PatchMapping(path = "/{id}/send")
  @PreAuthorize("hasAuthority('requests:send:draft')")
  public RequestDTO sendRequest(@PathVariable Long id) {
    return requestMapper.toDTO(requestService.sendRequestWithId(id));
  }

  @PatchMapping(path = "/{id}/accept")
  @PreAuthorize("hasAuthority('requests:accept:sent')")
  public RequestDTO acceptRequest(@PathVariable Long id) {
    return requestMapper.toDTO(requestService.acceptRequestWithId(id));
  }

  @PatchMapping(path = "/{id}/reject")
  @PreAuthorize("hasAuthority('requests:reject:sent')")
  public RequestDTO rejectRequest(@PathVariable Long id) {
    return requestMapper.toDTO(requestService.rejectRequestWithId(id));
  }

  @PutMapping(path = "/{id}/edit")
  @PreAuthorize("hasAuthority('requests:edit:owned:draft')")
  public RequestDTO editRequest(
      @PathVariable Long id, @RequestBody RequestCreationDTO editedMessage) {
    return requestMapper.toDTO(
        requestService.editMessageRequestWithId(id, editedMessage.getMessage()));
  }
}
