package org.valerochka1337.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.valerochka1337.dto.RequestDTO;
import org.valerochka1337.dto.UserDTO;
import org.valerochka1337.entity.Request;
import org.valerochka1337.entity.Role;
import org.valerochka1337.entity.Status;
import org.valerochka1337.entity.User;
import org.valerochka1337.model.RequestModel;
import org.valerochka1337.model.UserModel;

@Mapper(componentModel = "spring")
public interface RequestDTOModelMapper {
  @Mapping(target = "creationDate", source = "model.creationDate", dateFormat = "yyyy-MM-dd HH:mm")
  @Mapping(target = "sentDate", source = "model.sentDate", dateFormat = "yyyy-MM-dd HH:mm")
  @Mapping(target = "author", qualifiedByName = "UserToDTO")
  RequestDTO toDTO(RequestModel model);

  @Mapping(target = "creationDate", qualifiedByName = "StringToLocalDate")
  @Mapping(target = "sentDate", qualifiedByName = "StringToLocalDate")
  @Mapping(target = "status", qualifiedByName = "StringToStatus")
  @Mapping(target = "author", qualifiedByName = "UserToModel")
  RequestModel toModel(RequestDTO dto);

  @Named("UserToDTO")
  default UserDTO mapUserToDTO(UserModel user) {
    if (user == null) {
      return null;
    }

    return UserDTO.builder()
        .id(user.getId())
        .username(user.getUsername())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .roles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
        .build();
  }

  @Named("UserToModel")
  default UserModel mapUserToEntity(UserDTO user) {
    if (user == null) {
      return null;
    }

    return UserModel.builder()
        .id(user.getId())
        .username(user.getUsername())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .roles(user.getRoles().stream().map(Role::new).collect(Collectors.toSet()))
        .build();
  }

  @Named("StringToLocalDate")
  default LocalDateTime mapStringToLocalDate(String date) {
    if (date == null) {
      return null;
    }
    try {
      return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    } catch (DateTimeParseException exception) {
      throw new IllegalArgumentException("Invalid date!");
    }
  }

  @Named("StringToStatus")
  default Status mapStringToStatus(String status) {
    if (status == null) {
      return null;
    }
    try {
      return Status.valueOf(status.toUpperCase());
    } catch (IllegalArgumentException exception) {
      throw new IllegalArgumentException("No such color");
    }
  }
}

