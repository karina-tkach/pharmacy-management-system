package com.project.service;

import com.project.dto.SupplierDTO;
import com.project.dto.UpdateRequest;
import com.project.entity.Supplier;

import java.util.List;

public interface SupplierService {
  List<Supplier> getAllSuppliers();

  Boolean addSupplier(SupplierDTO supplierDTO);

  Boolean updateSupplier(Long supplierId, UpdateRequest updateRequest);

  Boolean deleteSupplier(Long supplierId);
}
