package com.project.service;

import com.project.dto.ProductDTO;
import com.project.dto.UpdateRequest;
import com.project.entity.Product;

import java.util.List;

public interface ProductService {
  List<Product> getAllProducts();

  Boolean addProduct(ProductDTO productDTO);

  Boolean updateProduct(Long productId, UpdateRequest updateRequest);

  Boolean deleteProduct(Long productId);
}
