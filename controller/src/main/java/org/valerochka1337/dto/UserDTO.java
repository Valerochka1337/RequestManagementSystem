package org.valerochka1337.dto;

import java.util.List;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
  @NotNull
  private Long id;
  @NotNull
  private String username;
  @NotNull
  private List<String> roles;
  private List<RequestDTO> requests;
}
