package org.valerochka1337.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.valerochka1337.entity.Request;
import org.valerochka1337.entity.Status;
import org.valerochka1337.entity.User;
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
    requestEntity.setCreationDate(LocalDateTime.now());

    requestEntity = requestRepository.save(requestEntity);

    return requestMapper.toModel(requestEntity);
  }

  @Override
  public void removeRequestById(Long id) {
    if (!requestRepository.existsById(id)) {
      throw new NoSuchRequestException(id);
    }

    requestRepository.deleteById(id);
  }

  @Override
  public List<RequestModel> getRequests() {
    return requestRepository.findAll().stream()
        .map(requestMapper::toModel)
        .collect(Collectors.toList());
  }

  @Override
  public RequestModel getRequestById(Long id) {
    Request requestEntity =
        requestRepository.findById(id).orElseThrow(() -> new NoSuchRequestException(id));

    return requestMapper.toModel(requestEntity);
  }

  @Override
  public List<RequestModel> getSentRequestsForUserByPartialUsername(String partialUsername) {
    List<User> foundUsers = userRepository.findAllByUsernameContaining(partialUsername);

    if (foundUsers.isEmpty()) {
      throw new NoUserFoundUserException();
    }
    if (foundUsers.size() > 1) {
      throw new AmbiguousUsernameUserException();
    }

    User foundUser = foundUsers.get(0);
    return foundUser.getRequests().stream()
        .map(requestMapper::toModel)
        .collect(Collectors.toList());
  }

  @Override
  public RequestModel editMessageRequestWithId(Long id, String editedMessage) {
    Request existingRequest =
        requestRepository.findById(id).orElseThrow(() -> new NoSuchRequestException(id));

    existingRequest.setMessage(editedMessage);
    requestRepository.save(existingRequest);

    return requestMapper.toModel(existingRequest);
  }

  @Override
  public RequestModel sendRequestWithId(Long id) {
    Request existingRequest =
        requestRepository.findById(id).orElseThrow(() -> new NoSuchRequestException(id));

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

  private String formatMessageIfOperator(String message) {
    // TODO
    return messageFormatter.format(message);
  }
}
