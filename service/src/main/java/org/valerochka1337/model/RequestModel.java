package org.valerochka1337.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import org.valerochka1337.entity.Status;

@Data
@Builder
public class RequestModel {
  private Long id;

  private Status status;

  private LocalDateTime creationDate;

  private LocalDateTime sentDate;

  private String message;

  private UserModel author;
}
