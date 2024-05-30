package com.project.mapper;

import com.project.dto.SupplierDTO;
import com.project.entity.Supplier;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface SupplierMapper {
  Supplier mapDTOtoSupplier(SupplierDTO supplierDTO);
}
