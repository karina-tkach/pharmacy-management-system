package com.project.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

  @NotBlank
  private String name;

  @NotNull
  private Long categoryId;

  @NotNull
  private Long formId;

  @NotNull
  private Long accountingTypeId;

  @NotNull
  @Min(value = 1, message = "Value must be greater than zero")
  private Long expirationDays;

  @NotNull
  private Boolean isPrescriptive;
}
