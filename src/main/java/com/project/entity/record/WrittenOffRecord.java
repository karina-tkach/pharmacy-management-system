package com.project.entity.record;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "written_off_records")
public class WrittenOffRecord extends Record {
  @Column(name = "written_off_at")
  private LocalDateTime writtenOffAt;
}
