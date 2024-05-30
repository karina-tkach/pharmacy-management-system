package com.project.service.impl;

import com.project.entity.characteristic.AccountingType;
import com.project.repository.AccountingTypeRepository;
import com.project.service.AccountingTypeService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountingTypeServiceImpl implements AccountingTypeService {
    private final AccountingTypeRepository accountingTypeRepository;

    @Override
    public List<AccountingType> getAllAccountingTypes() {
        try {
            return accountingTypeRepository.findAll();
        } catch (
                DataAccessException exception) {
            return Collections.emptyList();
        }
    }
}
