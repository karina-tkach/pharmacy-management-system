package com.project.service;

import com.project.dto.StatisticsDataDTO;

import java.util.List;

public interface StatisticsService {

  List<Integer> collectOutflowValues(Long id, Integer numberOfLastWeeks);

  StatisticsDataDTO getStatisticsDTO(Long id, Integer numberOfLastWeeks);

  Double getNextWeekPrediction(List<Integer> weekOutflowValues);

  Double getNextMonthPrediction(List<Integer> monthOutflowValues);
}