package com.project.service.impl;

import com.project.entity.characteristic.Form;
import com.project.repository.FormRepository;
import com.project.service.FormService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class FormServiceImpl implements FormService {
    private final FormRepository formRepository;

    @Override
    public List<Form> getAllForms() {
        try {
            return formRepository.findAll();
        } catch (
                DataAccessException exception) {
            return Collections.emptyList();
        }
    }
}
