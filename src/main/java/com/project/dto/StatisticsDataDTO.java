package com.project.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StatisticsDataDTO {
  private List<String> labels;
  private List<Integer> outflowValues;
  private Double nextWeekOutflowPrediction;
  private Double nextMonthOutflowPrediction;
}
