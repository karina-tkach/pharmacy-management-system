package com.project.controller;

import com.project.dto.InflowRequest;
import com.project.dto.OutflowRequest;
import com.project.exception.CustomBadRequestException;
import com.project.exception.CustomValidationException;
import com.project.service.StorageProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/storage")
public class StorageProductController {
  private final StorageProductService storageProductService;

  @PostMapping
  public ResponseEntity<String> inflowStorageProduct(@RequestBody @Valid InflowRequest inflowRequest) {
    boolean result = storageProductService.addNewStorageProduct(inflowRequest);
    if (result) {
      return ResponseEntity.ok().body("{}");
    }
    else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{}");
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> outflowStorageProduct(@PathVariable("id") Long storageProductId, @RequestBody @Valid OutflowRequest outflowRequest) {
    if (outflowRequest.getType() == null || !outflowRequest.typeMatchesAny(new String[] {"sold", "writtenOff"})) {
      throw new CustomBadRequestException();
    }

    if (outflowRequest.getType().equals("sold")) {
      if (outflowRequest.getPriceSold() == null) {
        throw new CustomBadRequestException();
      } else if (outflowRequest.getPriceSold() <= 0.0) {
        throw new CustomValidationException("Price must be greater than zero");
      }
    }

    if (outflowRequest.getQuantity() > storageProductService.getRemainingQuantityOfStorageProduct(storageProductId)) {
      throw new CustomValidationException("Quantity must not exceed available");
    }

    if(outflowRequest.getType().equals("sold")) {
      storageProductService.sellStorageProduct(storageProductId, outflowRequest);
    } else {
      storageProductService.writeOffStorageProduct(storageProductId, outflowRequest);
    }

    return ResponseEntity.ok().body("{}");
  }
}
