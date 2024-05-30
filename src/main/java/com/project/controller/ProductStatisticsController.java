package com.project.controller;

import com.project.dto.StatisticsDataDTO;
import com.project.repository.ProductRepository;
import com.project.exception.CustomBadRequestException;
import com.project.service.StatisticsService;
import com.project.service.impl.ProductStatisticsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics-product")
public class ProductStatisticsController {
  private final StatisticsService statisticsService;
  private final ProductRepository productRepository;

  public ProductStatisticsController(ProductStatisticsServiceImpl statisticsService, ProductRepository productRepository) {
    this.statisticsService = statisticsService;
    this.productRepository = productRepository;
  }

  @GetMapping("/{id}")
  public StatisticsDataDTO getStatisticsResponse(@PathVariable("id") Long productId) {
    if (!productRepository.existsById(productId)) {
      throw new CustomBadRequestException();
    }

    return statisticsService.getStatisticsDTO(productId, 12);
  }
}
