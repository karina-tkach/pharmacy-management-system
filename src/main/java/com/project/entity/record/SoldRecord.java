package com.project.entity.record;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "sold_records")
public class SoldRecord extends Record {
  @Column(name = "price_sold")
  private Double priceSold;

  @Column(name = "sold_at")
  private LocalDateTime soldAt;
}
