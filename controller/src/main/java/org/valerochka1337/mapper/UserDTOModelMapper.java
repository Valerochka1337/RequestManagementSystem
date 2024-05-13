package org.valerochka1337.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.valerochka1337.dto.UserDTO;
import org.valerochka1337.entity.Role;
import org.valerochka1337.entity.User;
import org.valerochka1337.model.UserModel;

@Mapper(componentModel = "spring", uses = RequestDTOModelMapper.class)
public interface UserDTOModelMapper {

  @Mapping(target = "roles", qualifiedByName = "RolesToDTO")
  @Mapping(target = "requests")
  UserDTO toDTO(UserModel model);

  @Mapping(target = "roles", qualifiedByName = "RolesToModel")
  User toModel(UserDTO dto);

  @Named("RolesToDTO")
  default List<String> mapRolesToDTO(Set<Role> roles) {
    if (roles == null) {
      return null;
    }

    return roles.stream().map(Role::getName).collect(Collectors.toList());
  }

  @Named("RolesToModel")
  default Set<Role> mapRolesToModel(List<String> roles) {
    if (roles == null) {
      return null;
    }

    return roles.stream().map(Role::new).collect(Collectors.toSet());
  }
}
