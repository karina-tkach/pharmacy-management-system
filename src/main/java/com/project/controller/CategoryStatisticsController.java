package com.project.controller;

import com.project.dto.StatisticsDataDTO;
import com.project.exception.CustomBadRequestException;
import com.project.repository.CategoryRepository;
import com.project.service.StatisticsService;
import com.project.service.impl.CategoryStatisticsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics-category")
public class CategoryStatisticsController {
  private final StatisticsService statisticsService;
  private final CategoryRepository categoryRepository;

  public CategoryStatisticsController(CategoryStatisticsServiceImpl statisticsService, CategoryRepository categoryRepository) {
    this.statisticsService = statisticsService;
    this.categoryRepository = categoryRepository;
  }

  @GetMapping("/{id}")
  public StatisticsDataDTO getStatisticsResponse(@PathVariable("id") Long categoryId) {
    if (!categoryRepository.existsById(categoryId)) {
      throw new CustomBadRequestException();
    }

    return statisticsService.getStatisticsDTO(categoryId, 12);
  }
}
