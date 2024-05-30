package com.project.entity.record;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "inflow_records")
public class InflowRecord extends Record {
  @Column(name = "price_bought")
  private Double priceBought;

  @Column(name = "written_at")
  private LocalDateTime writtenAt;
}
