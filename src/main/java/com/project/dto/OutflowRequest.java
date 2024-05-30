package com.project.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutflowRequest {

  @NotNull
  private String type;

  @NotNull
  @Min(value = 1, message = "Value must be greater than zero")
  private Long quantity;

  private Double priceSold;


  public boolean typeMatchesAny(String[] strings) {
    if (type == null || strings == null) {
      return false;
    }
    for (String str : strings) {
      if (type.equals(str)) {
        return true;
      }
    }
    return false;
  }
}
