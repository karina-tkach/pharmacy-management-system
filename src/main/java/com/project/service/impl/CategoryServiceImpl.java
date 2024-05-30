package com.project.service.impl;

import com.project.repository.CategoryRepository;
import com.project.entity.characteristic.Category;
import com.project.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        try {
            return categoryRepository.findAll();
        } catch (DataAccessException exception) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Category> getAllCategoriesWithNbsp() {
        try {
            List<Category> categories = categoryRepository.findAll();

            for (Category category : categories) {
                int occurences = countOccurrences(category.getCode(), "-");

                String nbsps = "&emsp;&emsp;";
                nbsps = nbsps.repeat(occurences);

                category.setName(nbsps + category.getName());
            }

            return categories;
        } catch (DataAccessException exception) {
            return Collections.emptyList();
        }
    }


    public static int countOccurrences(String text, String pattern) {
        return text.split(pattern, -1).length - 1;
    }
}
