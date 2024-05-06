package org.valerochka1337.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
  private Long id;

  private String username;

  private String firstName;

  private String lastName;

  private List<String> roles;

  private List<RequestDTO> requests;
}
