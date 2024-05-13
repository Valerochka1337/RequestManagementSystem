package org.valerochka1337.mapper;

import org.mapstruct.Mapper;
import org.valerochka1337.entity.User;
import org.valerochka1337.model.UserModel;

@Mapper(componentModel = "spring", uses = RequestModelEntityMapper.class)
public interface UserModelEntityMapper {
  UserModel toModel(User entity);

  User toEntity(UserModel model);
}
