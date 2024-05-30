package com.project.service;

import com.project.entity.characteristic.Category;

import java.util.List;

public interface CategoryService {
  List<Category> getAllCategories();

  List<Category> getAllCategoriesWithNbsp();
}
