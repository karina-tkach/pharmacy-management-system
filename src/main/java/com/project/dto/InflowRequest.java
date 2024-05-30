package com.project.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class InflowRequest {
  @NotNull
  private Long productId;

  @NotNull
  private Long supplierId;

  @NotNull
  @Min(value = 1, message = "Value must be greater than zero")
  private Long quantity;

  @NotNull
  @Min(value = 1, message = "Value must be greater than zero")
  private Double priceBought;

  @NotNull
  private LocalDateTime manufacturedOn;
}
