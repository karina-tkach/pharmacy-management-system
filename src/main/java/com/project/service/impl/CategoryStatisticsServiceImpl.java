package com.project.service.impl;

import com.project.dto.StatisticsDataDTO;
import com.project.entity.Product;
import com.project.repository.ProductRepository;
import com.project.repository.SoldRecordRepository;
import com.project.entity.record.SoldRecord;
import com.project.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryStatisticsServiceImpl implements StatisticsService {
    private final SoldRecordRepository soldRecordRepository;
    private final ProductRepository productRepository;

    private final StatisticsDataDTO statisticsDataDTO = new StatisticsDataDTO();

    private List<Integer> collectOutflowValueByProduct(Long productId, Integer numberOfLastWeeks) {
        int dayDifference;
        List<Integer> outflowValues = new ArrayList<>();


        LocalDateTime endDateTime = LocalDateTime.now().plusDays(1).truncatedTo(ChronoUnit.DAYS);
        LocalDateTime startDateTime = endDateTime.minusWeeks(numberOfLastWeeks).truncatedTo(ChronoUnit.DAYS);

        LocalDate startDate = startDateTime.toLocalDate();

        List<SoldRecord> soldRecords = soldRecordRepository.findAllByProductIdAndBetweenDates(productId, startDateTime, endDateTime);

        for (int i = 0; i < numberOfLastWeeks * 7; i++) {
            outflowValues.add(0);
        }


        for (SoldRecord obj : soldRecords) {
            dayDifference = (int) ChronoUnit.DAYS.between(startDate, obj.getSoldAt().toLocalDate());

            outflowValues.set(dayDifference, outflowValues.get(dayDifference) + obj.getQuantity().intValue());
        }

        return outflowValues;
    }

    @Override
    public List<Integer> collectOutflowValues(Long categoryId, Integer numberOfLastWeeks) {
        LocalDateTime endDateTime = LocalDateTime.now().plusDays(1).truncatedTo(ChronoUnit.DAYS);

        List<Product> products = productRepository.findProductsByCategoryId(categoryId);

        List<Long> productIds = new ArrayList<>();

        for(Product product : products) {
            productIds.add(product.getId());
        }

        List<List<Integer>> outflowProductValueLists = new ArrayList<>();

        for (Long id : productIds) {
            outflowProductValueLists.add(collectOutflowValueByProduct(id, numberOfLastWeeks));
        }

        List<Integer> outflowValues = new ArrayList<>();

        for (List<Integer> innerList : outflowProductValueLists) {
            for (int i = 0; i < innerList.size(); i++) {
                if (outflowValues.size() <= i) {
                    outflowValues.add(innerList.get(i));
                } else {
                    outflowValues.set(i, outflowValues.get(i) + innerList.get(i));
                }
            }
        }

        statisticsDataDTO.setOutflowValues(outflowValues);

        LocalDate endDate = endDateTime.toLocalDate().minusDays(1);
        LocalDate currentDate = endDate.minusDays(outflowValues.size() - 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        while (!currentDate.isAfter(endDate)) {
            statisticsDataDTO.getLabels().add(currentDate.format(formatter));
            currentDate = currentDate.plusDays(1);
        }


        return outflowValues;
    }

    @Override
    public StatisticsDataDTO getStatisticsDTO(Long categoryId, Integer numberOfLastWeeks) {
        int currentOutflowValue;
        List<Integer> weekOutflowValues = new ArrayList<>();
        List<Integer> monthOutflowValues = new ArrayList<>();

        statisticsDataDTO.setLabels(new ArrayList<>());
        statisticsDataDTO.setOutflowValues(new ArrayList<>());
        statisticsDataDTO.setNextWeekOutflowPrediction(null);
        statisticsDataDTO.setNextMonthOutflowPrediction(null);

        List<Integer> outflowValues = collectOutflowValues(categoryId, numberOfLastWeeks);

        if (outflowValues.isEmpty()) {
            statisticsDataDTO.setNextWeekOutflowPrediction(0.0);
            statisticsDataDTO.setNextMonthOutflowPrediction(0.0);

            statisticsDataDTO.setLabels(new ArrayList<>());
            statisticsDataDTO.setOutflowValues(new ArrayList<>());

            return statisticsDataDTO;
        }

        for (int i = 0; i < outflowValues.size() / 7; i++) {
            currentOutflowValue = 0;

            for (int j = 0; j < 7; j++) {
                currentOutflowValue += outflowValues.get(i * 7 + j);
            }

            weekOutflowValues.add(currentOutflowValue);
        }

        for (int i = 0; i < weekOutflowValues.size() / 4; i++) {
            currentOutflowValue = 0;

            for (int j = 0; j < 4; j++) {
                currentOutflowValue += weekOutflowValues.get(i * 4 + j);
            }

            monthOutflowValues.add(currentOutflowValue);
        }


        statisticsDataDTO.setNextWeekOutflowPrediction(getNextWeekPrediction(weekOutflowValues));


        if (!monthOutflowValues.isEmpty()) {
            statisticsDataDTO.setNextMonthOutflowPrediction(getNextMonthPrediction(monthOutflowValues));
        } else {
            statisticsDataDTO.setNextMonthOutflowPrediction(0.0);
        }


        return statisticsDataDTO;
    }

    @Override
    public Double getNextWeekPrediction(List<Integer> weekOutflowValues) {
        int outflowValue;

        double alpha = 0.3;

        double forecast = weekOutflowValues.get(0);

        for (int i = 1; i < weekOutflowValues.size(); i++) {
            outflowValue = weekOutflowValues.get(i);
            forecast = alpha * outflowValue + (1 - alpha) * forecast;
        }

        return forecast;
    }

    @Override
    public Double getNextMonthPrediction(List<Integer> monthOutflowValues) {
        int outflowValue;

        double alpha = 0.3;

        double forecast = monthOutflowValues.get(0);

        for (int i = 1; i < monthOutflowValues.size(); i++) {
            outflowValue = monthOutflowValues.get(i);
            forecast = alpha * outflowValue + (1 - alpha) * forecast;
        }

        return forecast;
    }

}
