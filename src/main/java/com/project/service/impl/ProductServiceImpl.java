package com.project.service.impl;

import com.project.dto.ProductDTO;
import com.project.dto.UpdateRequest;
import com.project.entity.StorageProduct;
import com.project.entity.Product;
import com.project.entity.user.User;
import com.project.repository.*;
import com.project.mapper.ProductMapper;
import com.project.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FormRepository formRepository;
    private final AccountingTypeRepository accountingTypeRepository;
    private final StorageProductRepository storageProductRepository;

    private final ProductMapper productMapper;

    @Override
    public List<Product> getAllProducts() {
        try {
            return productRepository.findAll();
        } catch (DataAccessException exception) {
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional
    public Boolean addProduct(ProductDTO productDTO) {
        try {
            Product product = productMapper.mapDTOtoProduct(productDTO);
            productRepository.save(product);
            return true;
        } catch (DataAccessException exception) {
            return false;
        }
    }

    @Override
    @Transactional
    public Boolean updateProduct(Long productId, UpdateRequest updateRequest) {
        try {
            if (productRepository.existsById(productId)) {
                Product product = productRepository.findById(productId).get();

                switch (updateRequest.getType()) {
                    case "name" -> product.setName(updateRequest.getValue());
                    case "categoryId" ->
                            product.setCategory(categoryRepository.findById(Long.valueOf(updateRequest.getValue())).get());
                    case "formId" ->
                            product.setForm(formRepository.findById(Long.valueOf(updateRequest.getValue())).get());
                    case "accountingTypeId" ->
                            product.setAccountingType(accountingTypeRepository.findById(Long.valueOf(updateRequest.getValue())).get());
                    case "expirationDays" -> {
                        List<StorageProduct> storageProducts = product.getStorageProducts();

                        for (StorageProduct sp : storageProducts) {
                            sp.setExpiresAt(sp.getExpiresAt().plusDays(-product.getExpirationDays()));
                            sp.setExpiresAt(sp.getExpiresAt().plusDays(Long.parseLong(updateRequest.getValue())));
                            storageProductRepository.save(sp);
                        }

                        product.setExpirationDays(Long.valueOf(updateRequest.getValue()));
                    }
                    case "isPrescriptive" -> product.setIsPrescriptive(updateRequest.getValue().equals("true"));
                }

                productRepository.save(product);

                return true;
            }
        } catch (DataAccessException ex) {
            return false;
        }

        return false;
    }

    @Override
    @Transactional
    public Boolean deleteProduct(Long productId) {
      try {
        if (productRepository.existsById(productId)) {
          productRepository.deleteById(productId);
          return true;
        }

        return false;
      }
      catch (DataAccessException ex) {
        return false;
      }
  }
}
