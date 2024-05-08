package org.valerochka1337.dto;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthDTO {
  @NotNull
  private String username;
  @NotNull
  private String password;
}
