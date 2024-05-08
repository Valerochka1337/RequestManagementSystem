package org.valerochka1337.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.valerochka1337.entity.Request;
import org.valerochka1337.entity.Status;
import org.valerochka1337.entity.User;
import org.valerochka1337.exceptions.request.EditNotDraftRequestException;
import org.valerochka1337.exceptions.request.InvalidStatusRequestException;
import org.valerochka1337.exceptions.request.NoSuchRequestException;
import org.valerochka1337.exceptions.request.NotNULLIdCreationRequestException;
import org.valerochka1337.exceptions.user.AmbiguousUsernameUserException;
import org.valerochka1337.exceptions.user.NoUserFoundUserException;
import org.valerochka1337.mapper.RequestModelEntityMapper;
import org.valerochka1337.model.RequestModel;
import org.valerochka1337.repository.RequestRepository;
import org.valerochka1337.repository.UserRepository;
import org.valerochka1337.service.utils.RequestMessageFormatter;

@Service
public class RequestServiceImpl implements RequestService {
  private final RequestRepository requestRepository;

  private final UserRepository userRepository;

  private final RequestModelEntityMapper requestMapper;

  private final RequestMessageFormatter messageFormatter;

  public RequestServiceImpl(
      RequestRepository requestRepository,
      UserRepository userRepository,
      RequestModelEntityMapper mapper,
      RequestMessageFormatter messageFormatter) {
    this.requestRepository = requestRepository;
    this.userRepository = userRepository;
    this.requestMapper = mapper;
    this.messageFormatter = messageFormatter;
  }

  @Override
  public RequestModel createRequest(RequestModel request) {
    if (request.getId() != null) {
      throw new NotNULLIdCreationRequestException();
    }
    Request requestEntity = requestMapper.toEntity(request);

    requestEntity.setAuthor(getAuthorizedUser());
    requestEntity.setStatus(Status.DRAFT);
    requestEntity.setCreationDate(LocalDateTime.now());

    requestEntity = requestRepository.save(requestEntity);

    return requestMapper.toModel(requestEntity);
  }

  @Override
  public void removeRequestById(Long id) {
    if (!requestRepository.existsById(id)) {
      throw new NoSuchRequestException(id);
    }

    if (!getAuthorizedUser()
        .getId()
        .equals(requestRepository.findById(id).get().getAuthor().getId())) {
      throw new AccessDeniedException("Can't delete not owned request");
    }

    requestRepository.deleteById(id);
  }

  @Override
  public List<RequestModel> getRequests(Status status, Pageable pageable) {
    User user = getAuthorizedUser();
    List<Request> resultRequests = new ArrayList<>();
    if (status == null) {
      if (hasPermission(user, "requests:read:owned")) {
        resultRequests = requestRepository.findAllByAuthor_Id(user.getId(), pageable);
      }
    } else {
      if (status == Status.SENT && hasPermission(user, "requests:read:sent:all")) {
        resultRequests = requestRepository.findAllByStatus(Status.SENT, pageable);
      } else {
        resultRequests =
            requestRepository.findAllByStatusAndAuthor_Id(status, user.getId(), pageable);
      }
    }

    if (user.getRoles().stream().anyMatch(role -> role.getName().compareTo("OPERATOR") == 0)) {
      resultRequests =
          resultRequests.stream()
              .peek(req -> req.setMessage(messageFormatter.format(req.getMessage())))
              .collect(Collectors.toList());
    }

    return resultRequests.stream().map(requestMapper::toModel).collect(Collectors.toList());
  }

  @Override
  public RequestModel getRequestById(Long id) {
    Request requestEntity =
        requestRepository.findById(id).orElseThrow(() -> new NoSuchRequestException(id));
    User user = getAuthorizedUser();
    if (requestEntity.getAuthor().getId().compareTo(user.getId()) != 0) {
      throw new AccessDeniedException("Can't get request of another user");
    }

    if (user.getRoles().stream().anyMatch(role -> role.getName().compareTo("OPERATOR") == 0)) {
      requestEntity.setMessage(messageFormatter.format(requestEntity.getMessage()));
    }

    return requestMapper.toModel(requestEntity);
  }

  @Override
  public List<RequestModel> getSentRequestsForUserByPartialUsername(
      String partialUsername, Pageable pageable) {
    List<User> foundUsers = userRepository.findAllByUsernameContaining(partialUsername);

    if (foundUsers.isEmpty()) {
      throw new NoUserFoundUserException();
    }
    if (foundUsers.size() > 1) {
      throw new AmbiguousUsernameUserException();
    }

    User foundUser = foundUsers.get(0);
    List<Request> resultRequest =
        requestRepository.findAllByStatusAndAuthor_Id(Status.SENT, foundUser.getId(), pageable);
    if (getAuthorizedUser().getRoles().stream()
        .anyMatch(role -> role.getName().compareTo("OPERATOR") == 0)) {
      resultRequest =
          resultRequest.stream()
              .peek(req -> req.setMessage(messageFormatter.format(req.getMessage())))
              .collect(Collectors.toList());
    }

    return resultRequest.stream().map(requestMapper::toModel).collect(Collectors.toList());
  }

  @Override
  public RequestModel editMessageRequestWithId(Long id, String editedMessage) {
    Request existingRequest =
        requestRepository.findById(id).orElseThrow(() -> new NoSuchRequestException(id));

    if (getAuthorizedUser().getRequests().stream()
        .allMatch(request -> request.getId().compareTo(id) != 0)) {
      throw new AccessDeniedException("Cannot edit not owned request");
    }
    if (existingRequest.getStatus().compareTo(Status.DRAFT) != 0) {
      throw new EditNotDraftRequestException();
    }

    existingRequest.setMessage(editedMessage);
    requestRepository.save(existingRequest);

    return requestMapper.toModel(existingRequest);
  }

  @Override
  public RequestModel sendRequestWithId(Long id) {
    Request existingRequest =
        requestRepository.findById(id).orElseThrow(() -> new NoSuchRequestException(id));

    if (getAuthorizedUser().getRequests().stream()
        .allMatch(request -> request.getId().compareTo(id) != 0)) {
      throw new AccessDeniedException("Can't send not owned request");
    }

    if (existingRequest.getStatus().compareTo(Status.DRAFT) != 0) {
      throw new InvalidStatusRequestException();
    }

    existingRequest.setStatus(Status.SENT);
    existingRequest.setSentDate(LocalDateTime.now());
    requestRepository.save(existingRequest);

    return requestMapper.toModel(existingRequest);
  }

  @Override
  public RequestModel acceptRequestWithId(Long id) {
    Request existingRequest =
        requestRepository.findById(id).orElseThrow(() -> new NoSuchRequestException(id));

    if (existingRequest.getStatus().compareTo(Status.SENT) != 0) {
      throw new InvalidStatusRequestException();
    }

    existingRequest.setStatus(Status.ACCEPTED);
    requestRepository.save(existingRequest);

    return requestMapper.toModel(existingRequest);
  }

  @Override
  public RequestModel rejectRequestWithId(Long id) {
    Request existingRequest =
        requestRepository.findById(id).orElseThrow(() -> new NoSuchRequestException(id));

    if (existingRequest.getStatus().compareTo(Status.SENT) != 0) {
      throw new InvalidStatusRequestException();
    }

    existingRequest.setStatus(Status.REJECTED);
    requestRepository.save(existingRequest);

    return requestMapper.toModel(existingRequest);
  }

  private User getAuthorizedUser() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    return userRepository.findByUsername(username).orElseThrow(NoUserFoundUserException::new);
  }

  private boolean hasPermission(User user, String permissionName) {
    for (var role : user.getRoles()) {
      for (var permission : role.getPermissions()) {
        if (permission.getName().equals(permissionName)) {
          return true;
        }
      }
    }

    return false;
  }
}
