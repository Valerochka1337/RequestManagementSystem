package org.valerochka1337.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {
  @NotNull
  private Long id;
  @NotNull
  private String status;
  @NotNull
  private String creationDate;

  private String sentDate;
  @NotNull
  private String message;
  @NotNull
  private UserDTO author;
}
