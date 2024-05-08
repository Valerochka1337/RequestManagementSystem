package org.valerochka1337.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.valerochka1337.dto.UserDTO;
import org.valerochka1337.mapper.UserDTOModelMapper;
import org.valerochka1337.service.UserService;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {
  private final UserService userService;
  private final UserDTOModelMapper userMapper;

  public UserController(UserService userService, UserDTOModelMapper userMapper) {
    this.userService = userService;
    this.userMapper = userMapper;
  }

  @GetMapping
  @PreAuthorize("hasAuthority('users:read:all')")
  public List<UserDTO> getUsers() {
    return userService.getUsers().stream().map(userMapper::toDTO).collect(Collectors.toList());
  }

  @GetMapping(params = {"partialUsername"})
  @PreAuthorize("hasAuthority('users:read:all')")
  public UserDTO getUserByPartialUsername(@RequestParam String partialUsername) {
    return userMapper.toDTO(userService.getUserByPartialUsername(partialUsername));
  }

  @PatchMapping(
      path = "/{id}",
      params = {"role"})
  @PreAuthorize("hasAuthority('users:give_role:operator')")
  public UserDTO giveRoleToUser(@PathVariable Long id, @RequestParam String role) {
    return userMapper.toDTO(userService.giveRoleToUser(id, role));
  }
}
