package com.project.mapper;

import com.project.dto.InflowRequest;
import com.project.entity.StorageProduct;
import com.project.repository.ProductRepository;
import com.project.repository.SupplierRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public abstract class StorageProductMapper {
  @Autowired
  protected ProductRepository productRepository;

  @Autowired
  protected SupplierRepository supplierRepository;

  @Mapping(target = "product", expression = "java(productRepository.findById(inflowRequest.getProductId()).get())")
  @Mapping(target = "supplier", expression = "java(supplierRepository.findById(inflowRequest.getSupplierId()).get())")
  @Mapping(target = "expiresAt", expression = "java(inflowRequest.getManufacturedOn().plusDays(productRepository.findById(inflowRequest.getProductId()).get().getExpirationDays()))")
  public abstract StorageProduct mapInflowRequestToProduct(InflowRequest inflowRequest);
}
