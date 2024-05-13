package org.valerochka1337.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.valerochka1337.entity.Request;
import org.valerochka1337.entity.User;
import org.valerochka1337.model.RequestModel;
import org.valerochka1337.model.UserModel;

@Mapper(componentModel = "spring")
public interface RequestModelEntityMapper {
  @Mapping(target = "author", qualifiedByName = "mapUserToModel")
  RequestModel toModel(Request entity);

  @Mapping(target = "author", qualifiedByName = "mapUserToEntity")
  Request toEntity(RequestModel model);

  @Named("mapUserToModel")
  default UserModel mapUserToModel(User user) {
    if (user == null) {
      return null;
    }

    return UserModel.builder()
        .id(user.getId())
        .username(user.getUsername())
        .roles(user.getRoles())
        .build();
  }

  @Named("mapUserToEntity")
  default User mapUserToEntity(UserModel user) {
    if (user == null) {
      return null;
    }

    return User.builder()
        .id(user.getId())
        .username(user.getUsername())
        .roles(user.getRoles())
        .build();
  }
}
