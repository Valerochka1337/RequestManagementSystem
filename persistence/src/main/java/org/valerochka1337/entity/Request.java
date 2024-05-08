package org.valerochka1337.entity;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "requests")
public class Request {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private Status status;

  @Column(name = "creation_date", nullable = false)
  private LocalDateTime creationDate;

  @Column(name = "sent_date")
  private LocalDateTime sentDate;

  @Column(name = "message", nullable = false)
  private String message;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "author_id")
  private User author;
}
