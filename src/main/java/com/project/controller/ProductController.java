package com.project.controller;

import com.project.dto.ProductDTO;
import com.project.dto.UpdateRequest;
import com.project.exception.CustomBadRequestException;
import com.project.exception.CustomValidationException;
import com.project.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
  private final ProductService productService;

  @PostMapping
  public ResponseEntity<String> addProduct(@RequestBody @Valid ProductDTO productDTO) {
    productService.addProduct(productDTO);
    return ResponseEntity.ok().body("{}");
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateProduct(@PathVariable("id") Long productId, @RequestBody UpdateRequest updateRequest) {
    if (updateRequest.getType() == null || !updateRequest.typeMatchesAny(new String[] {"name", "categoryId", "formId", "accountingTypeId", "expirationDays", "isPrescriptive"})) {
      throw new CustomBadRequestException();
    }

    if (updateRequest.getValue() == null || updateRequest.getValue().isBlank()) {
      throw new CustomValidationException("Field must not be blank");
    }

    if (updateRequest.getType().equals("expirationDays")) {
      try {
        Long value = Long.parseLong(updateRequest.getValue());

        if (value <= 0) {
          throw new CustomValidationException("Value must be greater than zero");
        }
      } catch (NumberFormatException e) {
        throw new CustomValidationException("Value is not numeric");
      }
    }

    productService.updateProduct(productId, updateRequest);
    return ResponseEntity.ok().body("{}");
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteProduct(@PathVariable("id") Long productId) {
    productService.deleteProduct(productId);
    return ResponseEntity.ok().body("{}");
  }
}
