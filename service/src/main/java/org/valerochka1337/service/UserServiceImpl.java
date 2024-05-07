package org.valerochka1337.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.valerochka1337.entity.Role;
import org.valerochka1337.entity.User;
import org.valerochka1337.exceptions.user.AmbiguousUsernameUserException;
import org.valerochka1337.exceptions.user.NoSuchRoleUserException;
import org.valerochka1337.exceptions.user.NoUserFoundUserException;
import org.valerochka1337.mapper.UserModelEntityMapper;
import org.valerochka1337.model.UserModel;
import org.valerochka1337.repository.RoleRepository;
import org.valerochka1337.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  private final UserModelEntityMapper userMapper;

  public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserModelEntityMapper userMapper) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.userMapper = userMapper;
  }

  @Override
  public List<UserModel> getUsers() {
    return userRepository.findAll().stream().map(userMapper::toModel).collect(Collectors.toList());
  }

  @Override
  public UserModel getUserByPartialUsername(String partialUsername) {
    List<User> foundUsers = userRepository.findAllByUsernameContaining(partialUsername);

    if (foundUsers.isEmpty()) {
      throw new NoUserFoundUserException();
    }
    if (foundUsers.size() > 1) {
      throw new AmbiguousUsernameUserException();
    }

    return userMapper.toModel(foundUsers.get(0));
  }

  @Override
  public UserModel giveRoleToUser(Long id, String roleName) {
    User userEntity = userRepository.findById(id).orElseThrow(NoUserFoundUserException::new);
    Role roleEntity =
        roleRepository.findByName(roleName).orElseThrow(() -> new NoSuchRoleUserException(roleName));

    userEntity.getRoles().add(roleEntity);
    userRepository.save(userEntity);

    return userMapper.toModel(userEntity);
  }
}
