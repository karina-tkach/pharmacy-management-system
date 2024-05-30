package com.project.controller;

import com.project.dto.SupplierDTO;
import com.project.dto.UpdateRequest;
import com.project.exception.CustomValidationException;
import com.project.exception.CustomBadRequestException;
import com.project.service.SupplierService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/suppliers")
public class SupplierController {
  private final SupplierService supplierService;

  @PostMapping
  public ResponseEntity<String> addSupplier(@RequestBody @Valid SupplierDTO supplierDTO) {
    supplierService.addSupplier(supplierDTO);
    return ResponseEntity.ok().body("{}");
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateSupplier(@PathVariable("id") Long supplierId, @RequestBody UpdateRequest updateRequest) {
    if (updateRequest.getType() == null || !updateRequest.typeMatchesAny(new String[] {"name", "phoneNumber", "address"})) {
      throw new CustomBadRequestException();
    }

    if (updateRequest.getValue() == null || updateRequest.getValue().isBlank()) {
      throw new CustomValidationException("Field must not be blank");
    }

    if (updateRequest.getType().equals("phoneNumber") && !updateRequest.getValue().matches("\\+\\d{12}")) {
      throw new CustomValidationException("String doesn't match phone number pattern");
    }

    supplierService.updateSupplier(supplierId, updateRequest);
    return ResponseEntity.ok().body("{}");
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteSupplier(@PathVariable("id") Long supplierId) {
    supplierService.deleteSupplier(supplierId);
    return ResponseEntity.ok().body("{}");
  }
}
