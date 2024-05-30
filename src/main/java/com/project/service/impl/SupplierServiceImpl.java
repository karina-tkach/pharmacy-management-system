package com.project.service.impl;

import com.project.dto.SupplierDTO;
import com.project.dto.UpdateRequest;
import com.project.entity.Supplier;
import com.project.repository.SupplierRepository;
import com.project.mapper.SupplierMapper;
import com.project.service.SupplierService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;

    private final SupplierMapper supplierMapper;


    @Override
    public List<Supplier> getAllSuppliers() {
        try {
            return supplierRepository.findAll();
        } catch (DataAccessException ex) {
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional
    public Boolean addSupplier(SupplierDTO supplierDTO) {
        try {
            Supplier supplier = supplierMapper.mapDTOtoSupplier(supplierDTO);
            supplierRepository.save(supplier);

            return true;
        } catch (DataAccessException ex) {
            return false;
        }
    }

    @Override
    @Transactional
    public Boolean updateSupplier(Long supplierId, UpdateRequest updateRequest) {
        try {
            if (supplierRepository.existsById(supplierId)) {
                Supplier supplier = supplierRepository.findById(supplierId).get();

                switch (updateRequest.getType()) {
                    case "name" -> supplier.setName(updateRequest.getValue());
                    case "phoneNumber" -> supplier.setPhoneNumber(updateRequest.getValue());
                    case "address" -> supplier.setAddress(updateRequest.getValue());
                }

                supplierRepository.save(supplier);

                return true;
            }

            return false;
        } catch (DataAccessException ex) {
            return false;
        }
    }

    @Override
    @Transactional
    public Boolean deleteSupplier(Long supplierId) {
        try {
            if (supplierRepository.existsById(supplierId)) {
                supplierRepository.deleteById(supplierId);
                return true;
            }

            return false;
        } catch (DataAccessException ex) {
            return false;
        }
    }
}
