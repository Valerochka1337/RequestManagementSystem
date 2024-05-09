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
  private Long id;

  private String status;

  private String creationDate;

  private String sentDate;

  private String message;

  private UserDTO author;
}
