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

@Mapper
public interface UserDTOModelMapper {

  @Mapping(target="roles", qualifiedByName = "RolesToDTO")
  UserDTO toDTO(UserModel model);

  @Mapping(target="roles", qualifiedByName = "RolesToModel")
  User toEntity(UserDTO dto);

  @Named("RolesToDTO")
  default List<String> mapRolesToDTO(Set<Role> roles) {
    if (roles == null) {
      return null;
    }

    return roles.stream().map(Role::toString).collect(Collectors.toList());
  }

  @Named("RolesToModel")
  default Set<Role> mapRolesToModel(List<String> roles) {
    if (roles == null) {
      return null;
    }

    return roles.stream().map(Role::new).collect(Collectors.toSet());
  }
}
