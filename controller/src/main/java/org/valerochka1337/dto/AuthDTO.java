package org.valerochka1337.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthDTO {
  private String username;

  private String password;
}
