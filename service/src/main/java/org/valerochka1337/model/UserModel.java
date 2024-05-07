package org.valerochka1337.model;

import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Data;
import org.valerochka1337.entity.Role;

@Data
@Builder
public class UserModel {
  private Long id;

  private String username;

  private String password;

  private Set<Role> roles;

  private List<RequestModel> requests;
}
