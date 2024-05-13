package org.valerochka1337.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.valerochka1337.model.UserModel;

@Service
public interface UserService {
  List<UserModel> getUsers();

  UserModel getUserByPartialUsername(String partialUsername);

  UserModel giveRoleToUser(Long id, String roleName);
}
